package nl.novi.backend_it_helpdesk.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum TypeTicketEnum {

    COMPLAINT("complaint"),
    QUESTION("question"),
    REQUEST("request"),
    MALFUNCTION("malfunction");

    private final String typeTicketEnum;

    TypeTicketEnum(String typeTicketEnum) {
        this.typeTicketEnum = typeTicketEnum;
    }

    @JsonValue
    public String getTypeTicketEnum() {
        return typeTicketEnum;
    }

    @JsonCreator
    public static TypeTicketEnum fromType(String value) {
        for(TypeTicketEnum type : values()) {
            String currenType = type.getTypeTicketEnum();
            if(currenType.equals(value)) {
                return type;
            }
        }

        throw new IllegalArgumentException("Invalid type: " + value);
    }






}
