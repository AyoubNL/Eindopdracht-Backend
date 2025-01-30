package nl.novi.backend_it_helpdesk.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum PriorityTicketEnum {
    P1_ORGANIZATION("p1_organisation"),
    P2_DEPARTEMENT("p2_departement"),
    P3_TEAM("p3_team"),
    P4_INDIVIDUAL("p4_individual");

    private final String priorityTicketEnum;

    PriorityTicketEnum(String priorityTicketEnum){
        this.priorityTicketEnum = priorityTicketEnum;
    }

    @JsonValue
    public String getPriorityTicketEnum(){
        return priorityTicketEnum;
    }

    @JsonCreator
    public static PriorityTicketEnum fromPriority(String value){
        for (PriorityTicketEnum priority : values()){
            String currentPriorityTicket = priority.getPriorityTicketEnum();
            if(currentPriorityTicket.equals(value)){
                return priority;
            }
        }

        throw new IllegalArgumentException("Invalid priority ticket enum" + value);

    }






}
