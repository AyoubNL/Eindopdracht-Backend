package nl.novi.backend_it_helpdesk.dtos;

import nl.novi.backend_it_helpdesk.enums.PriorityTicketEnum;
import nl.novi.backend_it_helpdesk.models.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TicketOutputDto {

    private Long id;
    private Date createdAt;
    private Date closedAt;
    private PriorityTicketEnum priority;
    private CategoryOutputDto category;
    private DetailOutputDto detail;
    private FixOutputDto fix;
    List<Screenshot> screenshots = new ArrayList<>();
    private User createdBy;

}
