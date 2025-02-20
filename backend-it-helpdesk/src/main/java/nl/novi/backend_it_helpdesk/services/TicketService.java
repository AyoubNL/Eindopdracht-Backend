package nl.novi.backend_it_helpdesk.services;

import jakarta.validation.Valid;
import nl.novi.backend_it_helpdesk.dtos.TicketInputDto;
import nl.novi.backend_it_helpdesk.dtos.TicketOutputDto;
import nl.novi.backend_it_helpdesk.exceptions.RecordNotFoundException;
import nl.novi.backend_it_helpdesk.mappers.CategoryMapper;
import nl.novi.backend_it_helpdesk.mappers.DetailMapper;
import nl.novi.backend_it_helpdesk.mappers.FixMapper;
import nl.novi.backend_it_helpdesk.models.Ticket;
import nl.novi.backend_it_helpdesk.models.User;
import nl.novi.backend_it_helpdesk.repositories.TicketRepository;
import org.springframework.stereotype.Service;

import java.util.List;

import static nl.novi.backend_it_helpdesk.mappers.TicketMapper.*;

@Service
public class TicketService {

    final private TicketRepository ticketRepository;

    public TicketService(TicketRepository ticketRepository) {
        this.ticketRepository = ticketRepository;
    }

    public TicketOutputDto getTicketById(Long id) {

        if (ticketRepository.findById(id).isPresent()) {
            Ticket tk = ticketRepository.findById(id).get();
            TicketOutputDto dto = transferToDto(tk);

            if (tk.getCategory() != null) {
                dto.setCategory(CategoryMapper.transferToDto(tk.getCategory()));
            }

            if (tk.getDetail() != null) {
                dto.setDetail(DetailMapper.transferToDto(tk.getDetail()));
            }

            if (tk.getFix() != null) {
                dto.setFix(FixMapper.transferToDto(tk.getFix()));
            }

            return transferToDto(tk);

        } else {
            throw new RecordNotFoundException("Het ticketnummer:" + id + " is onbekend");
        }

    }

    public List<TicketOutputDto> getAllTickets() {
        List<Ticket> ticketList = ticketRepository.findAll();
        return transferTicketListToDtoList(ticketList);
    }

    public List<TicketOutputDto> getAllTicketsByCreatedBy(User createdBy) {
        List<Ticket> ticketList = ticketRepository.findAllByCreatedBy(createdBy);
        return transferTicketListToDtoList(ticketList);
    }

    public TicketOutputDto addTicket(TicketInputDto dto) {

        Ticket tk = transferToTicket(dto);
        ticketRepository.save(tk);

        return transferToDto(tk);

    }

    public void deleteTicket(Long id) {

        ticketRepository.deleteById(id);

    }

    public TicketOutputDto updateTicket(Long id, @Valid TicketInputDto updateTicket) {

        if(ticketRepository.findById(id).isPresent()) {

            Ticket tk = ticketRepository.findById(id).get();

            Ticket tk1 = transferToTicket(updateTicket);

            if (updateTicket.getCategory().getCategoryName() == null) {
                tk1.getCategory().setCategoryName(tk.getCategory().getCategoryName());
            }
            else{
                tk1.getCategory().setCategoryName(updateTicket.getCategory().getCategoryName());
            }

            if (updateTicket.getCategory().getSubCategoryName() == null) {
                tk1.getCategory().setSubCategoryName(tk.getCategory().getSubCategoryName());
            }
            else{
                tk1.getCategory().setSubCategoryName(updateTicket.getCategory().getSubCategoryName());
            }


//                tk1.getDetail().setTitle(tk.getDetail().getTitle());
//                tk1.getDetail().setDescription(tk.getDetail().getDescription());
//                tk1.getDetail().setType(tk.getDetail().getType());
//

            if(updateTicket.getFix() == null){
                tk1.setFix(tk.getFix());
            }
            if(updateTicket.getCreatedBy() == null){
                tk1.setCreatedBy(tk.getCreatedBy());
            }
            if(updateTicket.getPriority() == null){
                tk1.setPriority(tk.getPriority());
            }
            if(updateTicket.getScreenshots() == null){
                tk1.setScreenshots(tk.getScreenshots());
            }
            tk1.setId(tk.getId());
            tk1.getCategory().setId(tk.getCategory().getId());
//            tk1.getDetail().setId(tk.getDetail().getId());
//            tk1.getFix().setId(tk.getFix().getId());
//            tk1.getCreatedBy().setUsername(tk.getCreatedBy().getUsername());

            ticketRepository.save(tk1);

            return transferToDto(tk1);

        }
        else {
            throw new RecordNotFoundException("Het ticketnummer: " + id + " is onbekend");
        }

    }
}
