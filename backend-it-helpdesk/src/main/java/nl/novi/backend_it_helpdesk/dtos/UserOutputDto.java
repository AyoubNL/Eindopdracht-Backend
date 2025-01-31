package nl.novi.backend_it_helpdesk.dtos;

import jakarta.persistence.Column;
import nl.novi.backend_it_helpdesk.enums.UserRoleEnum;

public class UserOutputDto {

    private String username;

    private String password;
    private UserRoleEnum role;
    private String email;

}
