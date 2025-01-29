package nl.novi.backend_it_helpdesk.enums;

public enum PriorityTicketEnum {
    P1_ORGANIZATION("p1_organisation"),
    P2_DEPARTEMENT("p2_departement"),
    P3_TEAM("p3_team"),
    P4_INDIVIDUAL("p4_individual");


    private String value;

    private PriorityTicketEnum(String value) {
        this.value = value;
    }

}
