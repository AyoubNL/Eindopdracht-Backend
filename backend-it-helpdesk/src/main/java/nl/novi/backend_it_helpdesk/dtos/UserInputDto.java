package nl.novi.backend_it_helpdesk.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import nl.novi.backend_it_helpdesk.enums.UserRoleEnum;

public class UserInputDto {

    @Size(min=1, max=8, message = "Username too long (max 8 characters)")
    private String username;
    @Size(min=1, max=8, message = "Password too long (max 8 characters)")
    private String password;
    private UserRoleEnum role;
    @Email(message = "Please enter a valid email Id")
    @NotNull(message = "Email is required")
    private String email;

    public UserInputDto(){}

    public UserInputDto(String username, String password, UserRoleEnum role, String email) {
        this.username = username;
        this.password = password;
        this.role = role;
        this.email = email;
    }

    public @Size(min = 1, max = 8, message = "Username too long (max 8 characters)") String getUsername() {
        return username;
    }

    public void setUsername(@Size(min = 1, max = 8, message = "Username too long (max 8 characters)") String username) {
        this.username = username;
    }

    public @Size(min = 1, max = 8, message = "Password too long (max 8 characters)") String getPassword() {
        return password;
    }

    public void setPassword(@Size(min = 1, max = 8, message = "Password too long (max 8 characters)") String password) {
        this.password = password;
    }

    public UserRoleEnum getRole() {
        return role;
    }

    public void setRole(UserRoleEnum role) {
        this.role = role;
    }

    public @Email(message = "Please enter a valid email Id") @NotNull(message = "Email is required") String getEmail() {
        return email;
    }

    public void setEmail(@Email(message = "Please enter a valid email Id") @NotNull(message = "Email is required") String email) {
        this.email = email;
    }
}
