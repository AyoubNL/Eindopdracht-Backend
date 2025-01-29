package nl.novi.backend_it_helpdesk.enums;

public enum UserRoleEnum {
    CLIENT("client"),
    AGENT("agent"),
    MANAGER("manager");

    private final String value;

    private UserRoleEnum(String value) {
        this.value = value;
    }




}
