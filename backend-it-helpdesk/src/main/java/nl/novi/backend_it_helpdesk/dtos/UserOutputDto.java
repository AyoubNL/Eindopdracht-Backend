package nl.novi.backend_it_helpdesk.dtos;

import jakarta.persistence.Column;
import nl.novi.backend_it_helpdesk.enums.UserRoleEnum;

public class UserOutputDto {

    private String username;

    @Column(nullable = false, length = 255)
    private String password;
    private UserRoleEnum role;
    private String email;

}
