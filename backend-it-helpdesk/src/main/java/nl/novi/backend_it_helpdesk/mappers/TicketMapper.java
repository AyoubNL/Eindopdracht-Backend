package nl.novi.backend_it_helpdesk.mappers;

import nl.novi.backend_it_helpdesk.dtos.TicketInputDto;
import nl.novi.backend_it_helpdesk.dtos.TicketOutputDto;
import nl.novi.backend_it_helpdesk.models.Ticket;

import java.util.ArrayList;
import java.util.List;

public class TicketMapper {

    public static TicketOutputDto transferToDto(Ticket ticket) {

        TicketOutputDto dto = new TicketOutputDto();

        dto.setId(ticket.getId());
        dto.setCreatedAt(ticket.getCreatedAt());
        dto.setPriority(ticket.getPriority());
        dto.setScreenshots(ticket.getScreenshots());

        if(ticket.getCreatedBy() != null) {
            dto.setCreatedBy(UserMapper.transferToDto(ticket.getCreatedBy()));
        }


        if(ticket.getCategory() != null) {
            dto.setCategory(CategoryMapper.transferToDto(ticket.getCategory()));
        }

        if(ticket.getDetail() != null) {
            dto.setDetail(DetailMapper.transferToDto(ticket.getDetail()));
        }

        if(ticket.getFix() != null) {
            dto.setFix(FixMapper.transferToDto(ticket.getFix()));
        }

        return dto;
    }

    public static Ticket transferToTicket(TicketInputDto dto) {
        Ticket ticket = new Ticket();

        ticket.setPriority(dto.getPriority());
        ticket.setScreenshots(dto.getScreenshots());

        if(dto.getCreatedBy() != null) {
            ticket.setCreatedBy(UserMapper.transferToUser(dto.getCreatedBy()));
        }

        if(dto.getCategory() != null) {

            ticket.setCategory(CategoryMapper.transferToCategory(dto.getCategory()));

        }

        if(dto.getDetail() != null) {

            ticket.setDetail(DetailMapper.transferToDetail(dto.getDetail()));

        }

        if(dto.getFix() != null){

            ticket.setFix(FixMapper.transferToFix(dto.getFix()));
        }

        return ticket;

    }

    public static List<TicketOutputDto> transferTicketListToDtoList(List<Ticket> tickets) {

        List<TicketOutputDto> ticketDtoList = new ArrayList<>();

        for(Ticket ticket : tickets) {
            TicketOutputDto dto = transferToDto(ticket);
            if(ticket.getCreatedBy() != null) {
                dto.setCreatedBy(UserMapper.transferToDto(ticket.getCreatedBy()));
            }
            if(ticket.getCategory() != null) {
                dto.setCategory(CategoryMapper.transferToDto(ticket.getCategory()));
            }
            if(ticket.getDetail() != null) {
                dto.setDetail(DetailMapper.transferToDto(ticket.getDetail()));
            }
            if(ticket.getFix() != null) {
                dto.setFix(FixMapper.transferToDto(ticket.getFix()));
            }
            ticketDtoList.add(dto);


        }

        return ticketDtoList;

    }

}
