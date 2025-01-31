package nl.novi.backend_it_helpdesk.mappers;

import nl.novi.backend_it_helpdesk.dtos.FixOutputDto;
import nl.novi.backend_it_helpdesk.models.Fix;

public class FixMapper {

    public static FixOutputDto transferToDto(Fix fix){
        FixOutputDto dto = new FixOutputDto();

        dto.setId(fix.getId());
        dto.setSolution(fix.getSolution());
        dto.setFeedback(fix.getFeedback());
        dto.setStatus(fix.getStatus());
        dto.setTickets(fix.getTickets());

        return dto;

    }


}
