package nl.novi.backend_it_helpdesk.services;

import jakarta.validation.Valid;
import nl.novi.backend_it_helpdesk.dtos.TicketInputDto;
import nl.novi.backend_it_helpdesk.dtos.TicketOutputDto;
import nl.novi.backend_it_helpdesk.enums.StatusTicketEnum;
import nl.novi.backend_it_helpdesk.exceptions.RecordNotFoundException;
import nl.novi.backend_it_helpdesk.mappers.*;
import nl.novi.backend_it_helpdesk.models.Ticket;
import nl.novi.backend_it_helpdesk.models.User;
import nl.novi.backend_it_helpdesk.repositories.TicketRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

import static nl.novi.backend_it_helpdesk.mappers.TicketMapper.*;

@Service
public class TicketService {

    private final TicketRepository ticketRepository;
    private final PasswordEncoder passwordEncoder;

    public TicketService(TicketRepository ticketRepository, PasswordEncoder passwordEncoder){
        this.ticketRepository = ticketRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public TicketOutputDto getTicketById(String id) {

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

            if(tk.getUser() != null) {
                dto.setUser(tk.getUser());
            }

            return transferToDto(tk);

        } else {
            throw new RecordNotFoundException("Het ticketnummer: " + id + " is onbekend");
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

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        tk.setUser(authentication.getName());

        String info1 = authentication.getName();
        String info3 = authentication.getDetails().toString();
        String info4 = authentication.getAuthorities().toString();
        String info5 = authentication.getPrincipal().toString();

        System.out.println(info1);
        System.out.println(info3);
        System.out.println(info4);
        System.out.println(info5);

        ticketRepository.save(tk);

        return transferToDto(tk);
    }

    public void deleteTicket(String id) {

        ticketRepository.deleteById(id);

    }

    public TicketOutputDto updateTicket(String id, @Valid TicketInputDto updateTicket) {

        if (ticketRepository.findById(id).isPresent()) {
            Ticket tk = ticketRepository.findById(id).get();
            Ticket tk1 = transferToTicket(updateTicket);

            tk1.setId(tk.getId());
            tk1.setCreatedAt(tk.getCreatedAt());
            tk1.setUser(tk.getUser());

            if (tk1.getCategory() != null) {
                tk1.getCategory().setId(tk.getCategory().getId());

                if (tk1.getCategory().getCategoryName() == null) {
                    tk1.getCategory().setCategoryName(tk.getCategory().getCategoryName());
                }
                if (tk1.getCategory().getSubCategoryName() == null) {
                    tk1.getCategory().setSubCategoryName(tk.getCategory().getSubCategoryName());
                }
            } else {
                tk1.setCategory(tk.getCategory());}

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
            } else {
                tk1.setDetail(tk.getDetail());}

            if (tk1.getFix() != null) {
                tk1.getFix().setId(tk.getFix().getId());

                if (tk1.getFix().getSolution() == null) {
                    tk1.getFix().setSolution(tk.getFix().getSolution());
                }
                if (tk1.getFix().getFeedback() == null) {
                    tk1.getFix().setFeedback(tk.getFix().getFeedback());
                }

                if (tk1.getFix().getStatus() == null) {
                    tk1.getFix().setStatus(tk.getFix().getStatus());
                }
                if (tk1.getFix().getStatus().equals(StatusTicketEnum.CLOSED)) {
                    tk1.setClosedAt(LocalDateTime.now());
                }

            } else {
                tk1.setFix(tk.getFix());}

            if (tk1.getPriority() == null) {
                tk1.setPriority(tk.getPriority());
            }

            ticketRepository.save(tk1);

            return transferToDto(tk1);

        } else {
            throw new RecordNotFoundException("Het ticketnummer: " + id + " is onbekend");
        }

    }



}

