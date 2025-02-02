package nl.novi.backend_it_helpdesk.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import nl.novi.backend_it_helpdesk.enums.*;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.CreatedBy;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "tickets")
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(updatable = false)
    @CreationTimestamp
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
    @CreatedBy
    private User createdBy;

    public Ticket() {}

    public Ticket(Long id, Date createdAt, PriorityTicketEnum priority, Category category, Detail detail, Fix fix, List<Screenshot> screenshots, User createdBy) {
        this.id = id;
        this.createdAt = createdAt;
        this.priority = priority;
        this.category = category;
        this.detail = detail;
        this.fix = fix;
        this.screenshots = screenshots;
        this.createdBy = createdBy;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public PriorityTicketEnum getPriority() {
        return priority;
    }

    public void setPriority(PriorityTicketEnum priority) {
        this.priority = priority;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Detail getDetail() {
        return detail;
    }

    public void setDetail(Detail detail) {
        this.detail = detail;
    }

    public Fix getFix() {
        return fix;
    }

    public void setFix(Fix fix) {
        this.fix = fix;
    }

    public List<Screenshot> getScreenshots() {
        return screenshots;
    }

    public void setScreenshots(List<Screenshot> screenshots) {
        this.screenshots = screenshots;
    }

    public User getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(User createdBy) {
        this.createdBy = createdBy;
    }
}
