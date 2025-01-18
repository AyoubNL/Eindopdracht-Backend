package nl.novi.backend_it_helpdesk.models;

import jakarta.persistence.*;
import nl.novi.backend_it_helpdesk.enums.PriorityTicketEnum;
import nl.novi.backend_it_helpdesk.enums.StatusTicketEnum;

import java.util.Date;

@Entity
@Table(name = "tickets")
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String description;
    private Date createdAt;
    private PriorityTicketEnum priority;
    private StatusTicketEnum status;

}
