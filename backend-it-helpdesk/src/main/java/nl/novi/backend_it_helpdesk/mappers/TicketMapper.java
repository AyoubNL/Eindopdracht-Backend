package nl.novi.backend_it_helpdesk.mappers;

import nl.novi.backend_it_helpdesk.dtos.TicketOutputDto;
import nl.novi.backend_it_helpdesk.models.Ticket;

public class TicketMapper {

    public static TicketOutputDto transferToDto(Ticket ticket) {

        TicketOutputDto dto = new TicketOutputDto();

        dto.setId(ticket.getId());
        dto.setCreatedAt(ticket.getCreatedAt());
        dto.setCreatedBy(ticket.getCreatedBy());
        dto.setPriority(ticket.getPriority());
        dto.setScreenshots(ticket.getScreenshots());


        if(ticket.getCategory() != null) {
            dto.setCategoryDto(CategoryMapper.transferToDto(ticket.getCategory()));
        }

        if(ticket.getDetail() != null) {
            dto.setDetailDto(DetailMapper.transferToDto(ticket.getDetail()));
        }



        dto.setCategoryDto(ticket.getCategory());
        dto.setDetailDto(ticket.getDetail());
        dto.setFixDto(ticket.getFix());

        return dto;
    }



}
