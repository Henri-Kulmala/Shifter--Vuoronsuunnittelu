package app.shifter.mappers;

import app.shifter.domain.User;
import app.shifter.DTOs.UserDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    
    UserDTO userToUserDTO(User user);

    User userDTOToUser(UserDTO userDTO);
}
