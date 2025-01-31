package nl.novi.backend_it_helpdesk.dtos;

import nl.novi.backend_it_helpdesk.enums.PriorityTicketEnum;
import nl.novi.backend_it_helpdesk.enums.TypeTicketEnum;
import nl.novi.backend_it_helpdesk.models.Ticket;

import java.util.ArrayList;
import java.util.List;

public class DetailOutputDto {

    private Long id;
    private String title;
    private String description;
    private PriorityTicketEnum priority;
    private TypeTicketEnum type;
    List<Ticket> tickets = new ArrayList<>();

    public DetailOutputDto() {}

    public DetailOutputDto(Long id, String title, String description, PriorityTicketEnum priority, TypeTicketEnum type, List<Ticket> tickets) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.priority = priority;
        this.type = type;
        this.tickets = tickets;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public PriorityTicketEnum getPriority() {
        return priority;
    }

    public void setPriority(PriorityTicketEnum priority) {
        this.priority = priority;
    }

    public TypeTicketEnum getType() {
        return type;
    }

    public void setType(TypeTicketEnum type) {
        this.type = type;
    }

    public List<Ticket> getTickets() {
        return tickets;
    }

    public void setTickets(List<Ticket> tickets) {
        this.tickets = tickets;
    }
}
