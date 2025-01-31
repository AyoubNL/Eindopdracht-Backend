package nl.novi.backend_it_helpdesk.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import nl.novi.backend_it_helpdesk.enums.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "tickets")
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Date createdAt;
    @Enumerated(EnumType.STRING)
    private PriorityTicketEnum priority;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "category_id")
    private Category category;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "detail_id")
    private Detail detail;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "fix_id")
    private Fix fix;

    @OneToMany(mappedBy = "ticket", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnoreProperties(value = {"contents","contentType"} )
    List<Screenshot> screenshots = new ArrayList<>();

    @ManyToOne
    private User createdBy;

}
