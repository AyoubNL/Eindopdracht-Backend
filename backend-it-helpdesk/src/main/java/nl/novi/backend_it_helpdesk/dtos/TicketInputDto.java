package nl.novi.backend_it_helpdesk.dtos;

import nl.novi.backend_it_helpdesk.enums.PriorityTicketEnum;
import nl.novi.backend_it_helpdesk.models.*;

public class TicketInputDto {

    private PriorityTicketEnum priority;
    private CategoryInputDto category;
    private DetailInputDto detail;
    private FixInputDto fix;
    private User createdBy;

}
