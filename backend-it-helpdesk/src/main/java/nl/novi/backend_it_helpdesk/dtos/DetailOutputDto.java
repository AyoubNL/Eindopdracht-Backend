package nl.novi.backend_it_helpdesk.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nl.novi.backend_it_helpdesk.enums.TypeTicketEnum;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DetailOutputDto {

    private Long id;
    private String title;
    private String description;
    private TypeTicketEnum type;

}
