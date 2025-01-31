package nl.novi.backend_it_helpdesk.services;

import nl.novi.backend_it_helpdesk.dtos.TicketInputDto;
import nl.novi.backend_it_helpdesk.dtos.TicketOutputDto;
import nl.novi.backend_it_helpdesk.mappers.CategoryMapper;
import nl.novi.backend_it_helpdesk.mappers.DetailMapper;
import nl.novi.backend_it_helpdesk.mappers.FixMapper;
import nl.novi.backend_it_helpdesk.models.Ticket;
import nl.novi.backend_it_helpdesk.repositories.TicketRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import static nl.novi.backend_it_helpdesk.mappers.TicketMapper.transferToDto;

@Service
public class TicketService {

    final private TicketRepository ticketRepository;

    public TicketService(TicketRepository ticketRepository) {
        this.ticketRepository = ticketRepository;
    }

public TicketOutputDto getTicketById(Long id) {

        if(ticketRepository.findById(id).isPresent() {
        Ticket tk = ticketRepository.findById(id).get();
        TicketOutputDto dto = transferToDto(tk);

        if (tk.getCategory() != null) {
            dto.setCategoryDto(CategoryMapper.transferToDto(tk.getCategory()));
        }

        if (tk.getDetail() != null) {
            dto.setDetailDto(DetailMapper.transferToDto(tk.getDetail()));
        }


        if (tk.getFix() != null) {
            dto.setFixDto(FixMapper.transferToDto(tk.getFix()));
        }

        return transferToDto(tk);
    }
    throw new RecordNotFoundException("Het ticketnummer:" + id + " is onbekend");



    }


}


}
