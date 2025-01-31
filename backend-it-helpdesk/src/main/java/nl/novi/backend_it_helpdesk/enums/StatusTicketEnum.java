package nl.novi.backend_it_helpdesk.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum StatusTicketEnum {

    REGISTERED("registered"),
    REJECTED("rejected"),
    CLOSED("closed"),
    IN_PROGRESS("in_progress");

    private final String statusTicketEnum;

    StatusTicketEnum(String statusTicketEnum) {
        this.statusTicketEnum = statusTicketEnum;
    }

    @JsonValue
    public String getStatus() {
        return statusTicketEnum;
    }

    @JsonCreator
    public static StatusTicketEnum fromStatus(String value) {
        for(StatusTicketEnum status : values()){
            String currentStatus = status.getStatus();
            if(currentStatus.equals(value)){
                return status;
            }
        }
        throw new IllegalArgumentException("Invalid status: " + value);
    }





}
