package nl.novi.backend_it_helpdesk.mappers;

import nl.novi.backend_it_helpdesk.dtos.UserInputDto;
import nl.novi.backend_it_helpdesk.dtos.UserOutputDto;
import nl.novi.backend_it_helpdesk.models.User;

import java.util.ArrayList;
import java.util.List;

public class UserMapper {

    public static UserOutputDto transferToDto(User user) {

        UserOutputDto dto = new UserOutputDto();

        dto.setUsername(user.getUsername());
        dto.setEmail(user.getEmail());
        dto.setPassword(user.getPassword());
        dto.setAuthorities(user.getAuthorities());

        return dto;

    }

    public static User transferToUser(UserInputDto dto) {

        User user = new User();

        user.setUsername(dto.getUsername());
        user.setEmail(dto.getEmail());
        user.setPassword(dto.getPassword());
        user.setRole(dto.getRole());

        return user;

    }

    public static List<UserOutputDto> transferUserListToDtoList(List<User> userList) {

        List<UserOutputDto> userDtoList = new ArrayList<>();

        for(User user : userList) {
            UserOutputDto dto = transferToDto(user);

            if(user.getPassword() != null) {
                dto.setPassword(user.getPassword());
            }

            if(user.getAuthorities() != null) {
                dto.setAuthorities(user.getAuthorities());
            }

            if(user.getEmail() != null) {
                dto.setEmail(user.getEmail());
            }
            userDtoList.add(dto);
        }
        return userDtoList;


    }
}