package nl.novi.backend_it_helpdesk.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;
import nl.novi.backend_it_helpdesk.config.CaId;
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
    @CaId
    private String id;
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
    @JsonIgnoreProperties(value = {"contents", "contentType"})
    List<Screenshot> screenshots = new ArrayList<>();

    @CreatedBy
    @Column(updatable = false, name = "user_id")
    private String user;

    public Ticket(long l, LocalDateTime now, PriorityTicketEnum priorityTicketEnum, LocalDateTime localDateTime) {
    }

}
