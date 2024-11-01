package app.shifter.mappers;

import app.shifter.domain.User;
import app.shifter.domain.Role;
import app.shifter.DTOs.UserDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    @Mapping(target = "roles", expression = "java(mapRolesToRoleNames(user.getRoles()))")
    UserDTO userToUserDTO(User user);

    @Mapping(target = "roles", expression = "java(mapRoleNamesToRoles(userDTO.getRoles()))")
    User userDTOToUser(UserDTO userDTO);

   
    default Set<String> mapRolesToRoleNames(Set<Role> roles) {
        return roles.stream()
                    .map(Role::getName)
                    .collect(Collectors.toSet());
    }

    
    default Set<Role> mapRoleNamesToRoles(Set<String> roleNames) {
        return roleNames.stream()
                        .map(Role::new) 
                        .collect(Collectors.toSet());
    }
}
