package nl.novi.backend_it_helpdesk.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nl.novi.backend_it_helpdesk.enums.UserRoleEnum;
import nl.novi.backend_it_helpdesk.models.Authority;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserInputDto {

    @Size(min=1, max=8, message = "Username too long (max 8 characters)")
    private String username;
    @Size(min=1, max=60, message = "Password too long (max 60 characters)")
    private String password;
    private UserRoleEnum role;
    @Email(message = "Please enter a valid email Id")
    @NotNull(message = "Email is required")
    private String email;
}
