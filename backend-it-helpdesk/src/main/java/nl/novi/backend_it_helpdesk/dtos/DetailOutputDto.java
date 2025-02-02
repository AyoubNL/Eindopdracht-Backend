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

    public DetailOutputDto() {}

    public DetailOutputDto(Long id, String title, String description, PriorityTicketEnum priority, TypeTicketEnum type) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.priority = priority;
        this.type = type;
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
}
