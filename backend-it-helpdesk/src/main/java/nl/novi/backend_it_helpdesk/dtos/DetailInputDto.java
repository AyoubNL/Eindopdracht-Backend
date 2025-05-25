package nl.novi.backend_it_helpdesk.dtos;

import jakarta.validation.constraints.NotBlank;
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


    @NotBlank(message = "Title is required")
    @Size(min=1, max=50, message = "Title needed (max 50 characters)")
    private String title;
    @NotBlank(message = "Description is required")
    @Size(min=1, max=400, message = "Description needed (max 400 characters)")
    private String description;
    private TypeTicketEnum type;

}
