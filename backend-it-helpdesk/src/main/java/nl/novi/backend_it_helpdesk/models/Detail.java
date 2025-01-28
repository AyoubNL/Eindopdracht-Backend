package nl.novi.backend_it_helpdesk.models;

import jakarta.persistence.*;
import nl.novi.backend_it_helpdesk.enums.PriorityTicketEnum;
import nl.novi.backend_it_helpdesk.enums.TypeTicketEnum;

@Entity
@Table(name = "details")
public class Detail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String description;
    private PriorityTicketEnum priority;
    private TypeTicketEnum type;






}
