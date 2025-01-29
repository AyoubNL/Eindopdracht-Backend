package nl.novi.backend_it_helpdesk.dtos;

import nl.novi.backend_it_helpdesk.enums.PriorityTicketEnum;
import nl.novi.backend_it_helpdesk.enums.TypeTicketEnum;

public class DetailOutputDto {

    private Long id;
    private String title;
    private String description;
    private PriorityTicketEnum priority;
    private TypeTicketEnum type;


}
