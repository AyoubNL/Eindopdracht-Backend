package nl.novi.backend_it_helpdesk.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nl.novi.backend_it_helpdesk.enums.PriorityTicketEnum;
import nl.novi.backend_it_helpdesk.models.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TicketOutputDto {

    private Long id;
    private UserOutputDto user;
    private LocalDateTime createdAt;
    private PriorityTicketEnum priority;
    private CategoryOutputDto category;
    private DetailOutputDto detail;
    private FixOutputDto fix;
    List<ScreenshotOutputDto> screenshots = new ArrayList<>();
    private LocalDateTime closedAt;

}
