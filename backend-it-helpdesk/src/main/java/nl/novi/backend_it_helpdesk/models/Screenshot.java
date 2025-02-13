package nl.novi.backend_it_helpdesk.models;

import jakarta.persistence.*;

@Entity
@Table(name = "screenshots")
public class Screenshot {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String url;
    private String contentType;

    @Lob
    private byte[] contents;

    @ManyToOne()
    @JoinColumn(name = "ticket_id")
    private Ticket ticket;
}
