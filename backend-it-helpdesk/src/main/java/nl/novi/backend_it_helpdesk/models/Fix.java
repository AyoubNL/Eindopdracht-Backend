package nl.novi.backend_it_helpdesk.models;

import jakarta.persistence.*;
import nl.novi.backend_it_helpdesk.enums.StatusTicketEnum;

@Entity
@Table(name = "fixes")
public class Fix {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String solution;
    private String feedback;
    private StatusTicketEnum status;

}
