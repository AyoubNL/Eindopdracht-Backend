package nl.novi.backend_it_helpdesk.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nl.novi.backend_it_helpdesk.enums.*;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tickets")
@EntityListeners(AuditingEntityListener.class)
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(updatable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;
    @Enumerated(EnumType.STRING)
    private PriorityTicketEnum priority;
    private LocalDateTime closedAt;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "category_id")
    private Category category;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "detail_id")
    private Detail detail;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "fix_id")
    private Fix fix;

    @OneToMany(mappedBy = "ticket", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @JsonIgnoreProperties(value = {"contents","contentType"} )
    List<Screenshot> screenshots = new ArrayList<>();

    @CreatedBy
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private User user;

    public <E> Ticket(long l, LocalDateTime now, PriorityTicketEnum priorityTicketEnum, LocalDateTime localDateTime, Detail detail, Fix fix, List<E> es, User user) {
    }

    public Ticket(long l, LocalDateTime now, PriorityTicketEnum priorityTicketEnum, LocalDateTime localDateTime, Category category, Detail detail, User user) {
    }

    public Ticket(long l, LocalDateTime now, PriorityTicketEnum priorityTicketEnum, LocalDateTime localDateTime) {
    }
}
