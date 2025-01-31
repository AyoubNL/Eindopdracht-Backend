package nl.novi.backend_it_helpdesk.mappers;

import nl.novi.backend_it_helpdesk.dtos.TicketOutputDto;
import nl.novi.backend_it_helpdesk.models.Ticket;

public class TicketMapper {

    public static Ticket transferToDto(TicketOutputDto ticketOutputDto) {

        Ticket ticket = new Ticket();

        ticket.setId(ticketOutputDto.getId());
        ticket.setCreatedAt(ticketOutputDto.getCreatedAt());
        ticket.setCreatedBy(ticketOutputDto.getCreatedBy());
        ticket.setPriority(ticketOutputDto.getPriority());
        ticket.setCategory(ticketOutputDto.getCategory());
        ticket.setDetail(ticketOutputDto.getDetail());
        ticket.setFix(ticketOutputDto.getFix());
        ticket.setScreenshots(ticketOutputDto.getScreenshots());

    }



}
