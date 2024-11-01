package app.shifter.mappers;

import app.shifter.domain.User;
import app.shifter.DTOs.UserDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;


@Mapper(componentModel = "spring")
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    @Mapping(target = "role", source = "role")
    @Mapping(target = "password", source = "passwordHash")
    UserDTO userToUserDTO(User user);

    @Mapping(target = "role", source = "role")
    @Mapping(target = "passwordHash", source = "password")
    User userDTOToUser(UserDTO userDTO);


}
