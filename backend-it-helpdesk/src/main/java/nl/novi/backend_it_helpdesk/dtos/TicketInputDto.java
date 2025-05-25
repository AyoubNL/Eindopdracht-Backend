package nl.novi.backend_it_helpdesk.dtos;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nl.novi.backend_it_helpdesk.enums.PriorityTicketEnum;
import nl.novi.backend_it_helpdesk.models.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class TicketInputDto {

    private PriorityTicketEnum priority;
    @Valid
    private CategoryInputDto category;
    @Valid
    private DetailInputDto detail;
    @Valid
    private FixInputDto fix;
    List<Screenshot> screenshots = new ArrayList<>();

    public TicketInputDto(PriorityTicketEnum priorityTicketEnum, CategoryInputDto categoryInputDto, FixInputDto fixInputDto, DetailInputDto detailInputDto) {
        this.priority = priorityTicketEnum;
        this.category = categoryInputDto;
        this.fix = fixInputDto;
        this.detail = detailInputDto;
    }

    public TicketInputDto(PriorityTicketEnum priorityTicketEnum, FixInputDto fixInputDto, DetailInputDto detailInputDto, UserInputDto test04) {
    }
}
