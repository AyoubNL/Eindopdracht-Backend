package nl.novi.backend_it_helpdesk.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nl.novi.backend_it_helpdesk.enums.StatusTicketEnum;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FixOutputDto {

    private Long id;
    private String solution;
    private String feedback;
    private StatusTicketEnum status;

}
