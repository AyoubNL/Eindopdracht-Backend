package nl.novi.backend_it_helpdesk.enums;

public enum TypeTicketEnum {

    COMPLAINT("complaint"),
    QUESTION("question"),
    REQUEST("request"),
    MALFUNCTION("malfunction");

    private final String value;

    TypeTicketEnum(String value) {
        this.value = value;
    }
}
