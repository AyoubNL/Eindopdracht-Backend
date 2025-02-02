package nl.novi.backend_it_helpdesk.dtos;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import nl.novi.backend_it_helpdesk.enums.PriorityTicketEnum;
import nl.novi.backend_it_helpdesk.enums.TypeTicketEnum;
import nl.novi.backend_it_helpdesk.models.Ticket;

import java.util.ArrayList;
import java.util.List;

public class DetailInputDto {

    @NotNull(message = "Title is required")
    @Size(min=1, max=50, message = "Category too long (max 50 characters)")
    private String title;
    @NotNull(message = "Description is required")
    @Size(min=1, max=400, message = "Category too long (max 400 characters)")
    private String description;
    private PriorityTicketEnum priority;
    private TypeTicketEnum type;

    public DetailInputDto() {}

    public DetailInputDto(String title, String description, PriorityTicketEnum priority, TypeTicketEnum type) {
        this.title = title;
        this.description = description;
        this.priority = priority;
        this.type = type;
    }

    public @NotNull(message = "Title is required") @Size(min = 1, max = 50, message = "Category too long (max 50 characters)") String getTitle() {
        return title;
    }

    public void setTitle(@NotNull(message = "Title is required") @Size(min = 1, max = 50, message = "Category too long (max 50 characters)") String title) {
        this.title = title;
    }

    public @NotNull(message = "Description is required") @Size(min = 1, max = 400, message = "Category too long (max 400 characters)") String getDescription() {
        return description;
    }

    public void setDescription(@NotNull(message = "Description is required") @Size(min = 1, max = 400, message = "Category too long (max 400 characters)") String description) {
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
