package nl.novi.backend_it_helpdesk.dtos;

import nl.novi.backend_it_helpdesk.enums.StatusTicketEnum;

public class FixOutputDto {


    private Long id;
    private String solution;
    private String feedback;
    private StatusTicketEnum status;

}
