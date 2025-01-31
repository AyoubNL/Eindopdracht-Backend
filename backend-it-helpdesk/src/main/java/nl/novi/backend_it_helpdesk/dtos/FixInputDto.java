package nl.novi.backend_it_helpdesk.dtos;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import nl.novi.backend_it_helpdesk.enums.StatusTicketEnum;

public class FixInputDto {

    @NotNull(message = "Solution is required")
    @Size(min=1, max=500, message = "Category too long (max 500 characters)")
    private String solution;
    private String feedback;
    private StatusTicketEnum status;


}
