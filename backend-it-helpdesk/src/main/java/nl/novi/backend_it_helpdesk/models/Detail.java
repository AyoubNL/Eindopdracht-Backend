package nl.novi.backend_it_helpdesk.models;

import jakarta.persistence.*;
import nl.novi.backend_it_helpdesk.enums.PriorityTicketEnum;
import nl.novi.backend_it_helpdesk.enums.TypeTicketEnum;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "details")
public class Detail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String description;
    @Enumerated(EnumType.STRING)
    private PriorityTicketEnum priority;
    @Enumerated(EnumType.STRING)
    private TypeTicketEnum type;

    @OneToMany(mappedBy = "detail")
    List<Ticket> tickets = new ArrayList<>();






}
