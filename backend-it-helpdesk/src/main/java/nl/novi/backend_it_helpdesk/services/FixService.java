package nl.novi.backend_it_helpdesk.services;

import jakarta.validation.Valid;
import nl.novi.backend_it_helpdesk.dtos.FixInputDto;
import nl.novi.backend_it_helpdesk.dtos.FixOutputDto;
import nl.novi.backend_it_helpdesk.exceptions.NotAuthorizedUserException;
import nl.novi.backend_it_helpdesk.exceptions.RecordNotFoundException;
import nl.novi.backend_it_helpdesk.mappers.FixMapper;
import nl.novi.backend_it_helpdesk.models.Fix;
import nl.novi.backend_it_helpdesk.models.Ticket;
import nl.novi.backend_it_helpdesk.repositories.FixRepository;
import nl.novi.backend_it_helpdesk.repositories.TicketRepository;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class FixService {

    final private FixRepository fixRepository;
    final private TicketRepository ticketRepository;

    public FixService(FixRepository fixRepository, TicketRepository ticketRepository) {
        this.fixRepository = fixRepository;
        this.ticketRepository = ticketRepository;
    }

    public FixOutputDto getFixById(String id) {

        Optional<Fix> fix = fixRepository.findById(id.toUpperCase());

        if (fix.isPresent()) {
            FixOutputDto dto = FixMapper.transferToDto(fix.get());
            return dto;
        } else {
            throw new RecordNotFoundException("Het fixnummer:" + id + " is onbekend");

        }
    }

    public List<FixOutputDto> getAllFixes() {

        List<Fix> fixes = fixRepository.findAll();
        List<FixOutputDto> dtos = new ArrayList<>();

        for (Fix fix : fixes) {
            dtos.add(FixMapper.transferToDto(fix));
        }

        return dtos;

    }

    public FixOutputDto addFix(FixInputDto fix) {

        Fix fx = FixMapper.transferToFix(fix);
        fixRepository.save(fx);

        return FixMapper.transferToDto(fx);
    }

    public void deleteFix(String id) {

            if(fixRepository.existsById(id.toUpperCase())) {

                fixRepository.deleteById(id.toUpperCase());
            }
            else{
                throw new EmptyResultDataAccessException("Onbekende entiteit", 1);
            }

    }

    public FixOutputDto updateFix(String id, FixInputDto updateFix) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUser = authentication.getName();

        Fix fx = fixRepository.findById(id.toUpperCase()).get();
        Fix fx1 = FixMapper.transferToFix(updateFix);

        if (fixRepository.findById(id.toUpperCase()).isPresent()) {

            Ticket tk = ticketRepository.findById(id.toUpperCase()).get();

            if (currentUser.equals(tk.getUser())){
                fx1.setId(fx.getId());
                if (fx1.getSolution() == null) {
                    fx1.setSolution(fx.getSolution());
                }
                if (fx1.getFeedback() == null) {
                    fx1.setFeedback(fx.getFeedback());
                }
                if (fx1.getStatus() == null) {
                    fx1.setStatus(fx.getStatus());
                }
                fixRepository.save(fx1);
                return FixMapper.transferToDto(fx1);
            }
            else{
                throw new NotAuthorizedUserException("De gebruiker " + currentUser+ " is niet gemachtigd, om aanpassingen te doen aan " +id);}

        }
        throw new RecordNotFoundException("Het fixnummer:" + id + " is onbekend");


    }
}