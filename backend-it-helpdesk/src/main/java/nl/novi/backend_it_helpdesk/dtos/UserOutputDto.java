package nl.novi.backend_it_helpdesk.dtos;

import jakarta.persistence.Column;
import nl.novi.backend_it_helpdesk.enums.UserRoleEnum;

public class UserOutputDto {

    private String username;
    private String password;
    private UserRoleEnum role;
    private String email;

    public UserOutputDto() {}

    public UserOutputDto(String username, String password, UserRoleEnum role, String email) {
        this.username = username;
        this.password = password;
        this.role = role;
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UserRoleEnum getRole() {
        return role;
    }

    public void setRole(UserRoleEnum role) {
        this.role = role;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}


