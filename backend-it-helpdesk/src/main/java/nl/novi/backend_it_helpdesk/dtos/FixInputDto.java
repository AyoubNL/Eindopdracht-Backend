package nl.novi.backend_it_helpdesk.dtos;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import nl.novi.backend_it_helpdesk.enums.StatusTicketEnum;

@Getter
@Setter
public class FixInputDto {

    @NotNull(message = "Solution is required")
    @Size(min=1, max=500, message = "Solution too long (max 500 characters)")
    private String solution;
    @Size(min=1, max=200, message = "Solution too long (max 200 characters)")
    private String feedback;
    private StatusTicketEnum status;

}
