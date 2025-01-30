package nl.novi.backend_it_helpdesk.models;

import jakarta.persistence.*;
import nl.novi.backend_it_helpdesk.enums.StatusTicketEnum;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "fixes")
public class Fix {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String solution;
    private String feedback;
    @Enumerated(EnumType.STRING)
    private StatusTicketEnum status;

    @OneToMany(mappedBy = "fix")
    List<Ticket> tickets = new ArrayList<>();

}
