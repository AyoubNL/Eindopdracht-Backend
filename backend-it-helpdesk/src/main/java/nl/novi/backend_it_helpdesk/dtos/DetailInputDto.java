package nl.novi.backend_it_helpdesk.dtos;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nl.novi.backend_it_helpdesk.enums.TypeTicketEnum;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DetailInputDto {

    @NotNull(message = "Title is required")
    @Size(min=1, max=50, message = "Title too long (max 50 characters)")
    private String title;
    @NotNull(message = "Description is required")
    @Size(min=1, max=400, message = "Description too long (max 400 characters)")
    private String description;
    private TypeTicketEnum type;

}
