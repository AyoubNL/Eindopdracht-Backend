package nl.novi.backend_it_helpdesk.services;

import jakarta.validation.Valid;
import nl.novi.backend_it_helpdesk.dtos.TicketInputDto;
import nl.novi.backend_it_helpdesk.dtos.TicketOutputDto;
import nl.novi.backend_it_helpdesk.enums.StatusTicketEnum;
import nl.novi.backend_it_helpdesk.exceptions.RecordNotFoundException;
import nl.novi.backend_it_helpdesk.mappers.CategoryMapper;
import nl.novi.backend_it_helpdesk.mappers.DetailMapper;
import nl.novi.backend_it_helpdesk.mappers.FixMapper;
import nl.novi.backend_it_helpdesk.mappers.ScreenshotMapper;
import nl.novi.backend_it_helpdesk.models.Screenshot;
import nl.novi.backend_it_helpdesk.models.Ticket;
import nl.novi.backend_it_helpdesk.models.User;
import nl.novi.backend_it_helpdesk.repositories.ScreenshotRepository;
import nl.novi.backend_it_helpdesk.repositories.TicketRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static nl.novi.backend_it_helpdesk.mappers.TicketMapper.*;

@Service
public class TicketService {

    final private TicketRepository ticketRepository;
    private final ScreenshotRepository screenshotRepository;

    public TicketService(TicketRepository ticketRepository, ScreenshotRepository screenshotRepository) {
        this.ticketRepository = ticketRepository;
        this.screenshotRepository = screenshotRepository;
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

            if (tk.getScreenshots() != null) {
                dto.setScreenshots(ScreenshotMapper.transferScreenshotListToDtoList(tk.getScreenshots()));
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

    public List<TicketOutputDto> getAllTicketsByUser(User user) {
        List<Ticket> ticketList = ticketRepository.findAllByUser(user);
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

        if (ticketRepository.findById(id).isPresent()) {

            Ticket tk = ticketRepository.findById(id).get();

            Ticket tk1 = transferToTicket(updateTicket);


            tk1.setId(tk.getId());
            tk1.setCreatedAt(tk.getCreatedAt());

            if (tk1.getCategory() != null) {
                tk1.getCategory().setId(tk.getCategory().getId());

                if (tk1.getCategory().getCategoryName() == null) {
                    tk1.getCategory().setCategoryName(tk.getCategory().getCategoryName());
                }
                if (tk1.getCategory().getSubCategoryName() == null) {
                    tk1.getCategory().setSubCategoryName(tk.getCategory().getSubCategoryName());
                }
            } else {tk1.setCategory(tk.getCategory());}

            if (tk1.getDetail() != null) {
                tk1.getDetail().setId(tk.getDetail().getId());

                if (tk1.getDetail().getTitle() == null) {
                    tk1.getDetail().setTitle(tk.getDetail().getTitle());
                }
                if (tk1.getDetail().getType() == null) {
                    tk1.getDetail().setType(tk.getDetail().getType());
                }
                if (tk1.getDetail().getDescription() == null) {
                    tk1.getDetail().setDescription(tk.getDetail().getDescription());
                }
            } else {tk1.setDetail(tk.getDetail());}

            if (tk1.getFix() != null) {
                tk1.getFix().setId(tk.getFix().getId());

                if (tk1.getFix().getSolution() == null) {
                    tk1.getFix().setSolution(tk.getFix().getSolution());
                }
                if (tk1.getFix().getFeedback() == null) {
                    tk1.getFix().setFeedback(tk.getFix().getFeedback());
                }
                if(tk1.getFix().getStatus().equals(StatusTicketEnum.CLOSED)){
                    tk1.setClosedAt(LocalDateTime.now());
                }
                if (tk1.getFix().getStatus() == null) {
                    tk1.getFix().setStatus(tk.getFix().getStatus());
                }

            } else {tk1.setFix(tk.getFix());}

            if (tk1.getUser() != null) {
                tk1.getUser().setUsername(tk1.getUser().getUsername());

                if (tk1.getUser().getEmail() == null) {
                    tk1.getUser().setEmail(tk.getUser().getEmail());
                }
                if (tk1.getUser().getPassword() == null) {
                    tk1.getUser().setPassword(tk.getUser().getPassword());
                }
                if (tk1.getUser().getRole() == null) {
                    tk1.getUser().setRole(tk.getUser().getRole());
                } else {
                    tk1.setUser(tk1.getUser());
                }
            } else {tk1.setUser(tk.getUser());}

            if (tk1.getPriority() == null) {
                tk1.setPriority(tk.getPriority());
            }

            ticketRepository.save(tk1);

            return transferToDto(tk1);

        } else {
            throw new RecordNotFoundException("Het ticketnummer: " + id + " is onbekend");
        }

    }

    public void addScreenshotToTicket(Long id, Screenshot st) {

        var optionalTicket = ticketRepository.findById(id);

        if(optionalTicket.isPresent()){
            var ticket = optionalTicket.get();
            st.setTicket(ticket);
            ticket.getScreenshots().add(st);
            ticketRepository.save(ticket);

        }
        else{
            throw new RecordNotFoundException("Het ticketnummer:" + id + " is onbekend");
        }

    }
}

