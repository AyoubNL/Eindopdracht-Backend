package nl.novi.backend_it_helpdesk.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum UserRoleEnum {
    CLIENT("client"),
    AGENT("agent"),
    MANAGER("manager");

    private final String userRoleEnum;

    UserRoleEnum(String userRoleEnum) {
        this.userRoleEnum = userRoleEnum;
    }

    @JsonValue
    public String getUserRoleEnum() {
        return userRoleEnum;
    }

    @JsonCreator
    public static UserRoleEnum fromUser(String value) {
        for (UserRoleEnum user : values()) {
            String currentUser = user.getUserRoleEnum();
            if (currentUser.equals(value)) {
                return user;
            }
        }

        throw new IllegalArgumentException("Invalid user: " + value);

    }


}
