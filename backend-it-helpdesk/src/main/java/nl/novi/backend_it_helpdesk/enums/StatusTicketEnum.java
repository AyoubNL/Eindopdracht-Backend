package nl.novi.backend_it_helpdesk.enums;

public enum StatusTicketEnum {

    REGISTERED("registered"),
    REJECTED("rejected"),
    CLOSED("closed"),
    IN_PROGRESS("in_progress");

    private String value;

    private StatusTicketEnum(String value) {
        this.value = value;
    }


}
