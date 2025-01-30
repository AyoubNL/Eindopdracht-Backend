package nl.novi.backend_it_helpdesk.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import nl.novi.backend_it_helpdesk.enums.UserRoleEnum;

public class UserInputDto {

    @Size(min=1, max=8, message = "Password too long (max 8 characters)")
    private String password;
    private UserRoleEnum role;
    @Email(message = "Please enter a valid email Id")
    @NotNull(message = "Email is required")
    private String email;


}
