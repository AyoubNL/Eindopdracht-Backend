package nl.novi.backend_it_helpdesk.services;

import nl.novi.backend_it_helpdesk.dtos.TicketInputDto;
import nl.novi.backend_it_helpdesk.dtos.TicketOutputDto;
import nl.novi.backend_it_helpdesk.enums.PriorityTicketEnum;
import nl.novi.backend_it_helpdesk.enums.StatusTicketEnum;
import nl.novi.backend_it_helpdesk.exceptions.NotAuthorizedUserException;
import nl.novi.backend_it_helpdesk.exceptions.RecordNotFoundException;
import nl.novi.backend_it_helpdesk.mappers.*;
import nl.novi.backend_it_helpdesk.models.Ticket;
import nl.novi.backend_it_helpdesk.repositories.TicketRepository;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

import static nl.novi.backend_it_helpdesk.enums.StatusTicketEnum.REJECTED;
import static nl.novi.backend_it_helpdesk.mappers.TicketMapper.*;

@Service
public class TicketService {

    private final TicketRepository ticketRepository;

    public TicketService(TicketRepository ticketRepository) {
        this.ticketRepository = ticketRepository;
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

            if (tk.getUser() != null) {
                dto.setUser(tk.getUser());
            }

            return transferToDto(tk);

        } else {
            throw new RecordNotFoundException("Het ticketnummer: " + id + " is onbekend.");
        }

    }

    public List<TicketOutputDto> getAllTickets() {
        List<Ticket> ticketList = ticketRepository.findAll();
        return transferTicketListToDtoList(ticketList);
    }

    public List<TicketOutputDto> getAllTicketsByUser(String user) {
        List<Ticket> ticketList = ticketRepository.findAllByUser(user);
        return transferTicketListToDtoList(ticketList);
    }

    public List<TicketOutputDto> getAllTicketsByPriority(String priorityTicketEnum) {

        switch (priorityTicketEnum.toUpperCase()) {

            case "P1":
                List<Ticket> ticketListP1 = ticketRepository.findAllByPriority(PriorityTicketEnum.P1_ORGANIZATION);
                return transferTicketListToDtoList(ticketListP1);
            case "P2":
                List<Ticket> ticketListP2 = ticketRepository.findAllByPriority(PriorityTicketEnum.P2_DEPARTEMENT);
                return transferTicketListToDtoList(ticketListP2);
            case "P3":
                List<Ticket> ticketListP3 = ticketRepository.findAllByPriority(PriorityTicketEnum.P3_TEAM);
                return transferTicketListToDtoList(ticketListP3);
            case "P4":
                List<Ticket> ticketListP4 = ticketRepository.findAllByPriority(PriorityTicketEnum.P4_INDIVIDUAL);
                return transferTicketListToDtoList(ticketListP4);
            default:
                throw new IllegalArgumentException("Prioriteit " +priorityTicketEnum+ " is onbekend.");
        }


    }

    public List<TicketOutputDto> getAllTicketsByRejected() {

        List<Ticket> ticketList = ticketRepository.findAll();

        List<Ticket> newList = ticketList.stream().filter(a -> a.getFix().getStatus() == REJECTED).toList();

        return transferTicketListToDtoList(newList);

    }

    public TicketOutputDto addTicket(TicketInputDto dto) {

        Ticket tk = transferToTicket(dto);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        tk.setUser(authentication.getName());

        ticketRepository.save(tk);

        return transferToDto(tk);
    }

    public void deleteTicket(String id) {

        if (ticketRepository.existsById(id.toUpperCase())) {

            ticketRepository.deleteById(id.toUpperCase());
        } else {
            throw new EmptyResultDataAccessException("Onbekende entiteit", 1);
        }

    }

    public TicketOutputDto updateTicket(String id, TicketInputDto updateTicket) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUser = authentication.getName();

        if (ticketRepository.findById(id.toUpperCase()).isPresent()) {
            Ticket tk = ticketRepository.findById(id.toUpperCase()).get();
            Ticket tk1 = transferToTicket(updateTicket);

            tk1.setId(tk.getId());
            tk1.setCreatedAt(tk.getCreatedAt());

            if (currentUser.equals(tk.getUser())) {
                tk1.setUser(tk.getUser());
            } else {
                throw new NotAuthorizedUserException("De gebruiker " + currentUser + " is niet gemachtigd, om aanpassingen te doen aan " + id);
            }

            if (tk1.getCategory() != null) {
                tk1.getCategory().setId(tk.getCategory().getId());

                if (tk1.getCategory().getCategoryName() == null) {
                    tk1.getCategory().setCategoryName(tk.getCategory().getCategoryName());
                }
                if (tk1.getCategory().getSubCategoryName() == null) {
                    tk1.getCategory().setSubCategoryName(tk.getCategory().getSubCategoryName());
                }
            } else {
                tk1.setCategory(tk.getCategory());
            }

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
                tk1.setDetail(tk.getDetail());
            }

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
                tk1.setFix(tk.getFix());
            }

            if (tk1.getPriority() == null) {
                tk1.setPriority(tk.getPriority());
            }

            ticketRepository.save(tk1);

            return transferToDto(tk1);

        } else {
            throw new RecordNotFoundException("Het ticketnummer: " + id + " is onbekend");
        }

    }

    public TicketOutputDto changeStatusTicket(String id, StatusTicketEnum changeStatusTicket) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUser = authentication.getName();

        if (ticketRepository.findById(id).isPresent()) {
            Ticket tk = ticketRepository.findById(id).get();

            if (currentUser.equals(tk.getUser())) {
                tk.getFix().setStatus(changeStatusTicket);

                if (changeStatusTicket == StatusTicketEnum.CLOSED) {

                    tk.setClosedAt(LocalDateTime.now());

                }

                ticketRepository.save(tk);

                return transferToDto(tk);
            } else {
                throw new NotAuthorizedUserException("De gebruiker " + currentUser + " is niet gemachtigd, om aanpassingen te doen aan " + id);
            }

        } else {
            throw new RecordNotFoundException("Het ticketnummer: " + id + " is onbekend");
        }

    }


}

