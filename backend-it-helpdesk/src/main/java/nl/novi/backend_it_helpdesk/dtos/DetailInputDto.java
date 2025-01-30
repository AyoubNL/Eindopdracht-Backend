package nl.novi.backend_it_helpdesk.dtos;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import nl.novi.backend_it_helpdesk.enums.PriorityTicketEnum;
import nl.novi.backend_it_helpdesk.enums.TypeTicketEnum;

public class DetailInputDto {

    @NotNull(message = "Title is required")
    @Size(min=1, max=50, message = "Category too long (max 50 characters)")
    private String title;
    @NotNull(message = "Description is required")
    @Size(min=1, max=400, message = "Category too long (max 400 characters)")
    private String description;
    private PriorityTicketEnum priority;
    private TypeTicketEnum type;

}
