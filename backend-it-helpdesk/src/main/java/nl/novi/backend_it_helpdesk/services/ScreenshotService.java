package nl.novi.backend_it_helpdesk.services;


import nl.novi.backend_it_helpdesk.dtos.ScreenshotOutputDto;
import nl.novi.backend_it_helpdesk.exceptions.NotAuthorizedUserException;
import nl.novi.backend_it_helpdesk.exceptions.RecordNotFoundException;
import nl.novi.backend_it_helpdesk.mappers.ScreenshotMapper;
import nl.novi.backend_it_helpdesk.models.Screenshot;
import nl.novi.backend_it_helpdesk.models.Ticket;
import nl.novi.backend_it_helpdesk.repositories.ScreenshotRepository;
import nl.novi.backend_it_helpdesk.repositories.TicketRepository;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class ScreenshotService {

    private final ScreenshotRepository screenshotRepository;
    private final TicketRepository ticketRepository;

    public ScreenshotService(ScreenshotRepository screenshotRepository, TicketRepository ticketRepository) {
        this.screenshotRepository = screenshotRepository;
        this.ticketRepository = ticketRepository;
    }

    public Screenshot storeFile(MultipartFile file, String url) throws IOException {


        if(file.isEmpty()){
            throw new NullPointerException("Het bestand is leeg");
        }

        String title = file.getOriginalFilename();
        String contentType = file.getContentType();
        Long size = file.getSize();
        byte[] bytes = file.getBytes();

        Screenshot sh = new Screenshot(title, contentType, url,size, bytes);

        return screenshotRepository.save(sh);

    }

    public Screenshot getScreenshotFromTicket(Long id) {

        return screenshotRepository.findById(id).orElseThrow(RecordNotFoundException::new);
    }

    public void addScreenshotToTicket(String id, Screenshot st) {

        var optionalTicket = ticketRepository.findById(id);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUser = authentication.getName();

        String caseOwner = optionalTicket.get().getUser();

        if(currentUser.equals(caseOwner)){

            if (optionalTicket.isPresent()) {
                var ticket = optionalTicket.get();
                st.setTicket(ticket);
                ticket.getScreenshots().add(st);
                ticketRepository.save(ticket);

            } else {
                throw new RecordNotFoundException("Het ticketnummer:" + id + " is onbekend");
            }
        }
        else throw new NotAuthorizedUserException(caseOwner +" mag alleen bestanden toevoegen aan de case.");
    }

    public void deleteScreenshot(Long id) {

        if(screenshotRepository.existsById(id)) {

            screenshotRepository.deleteById(id);
        }
        else{
            throw new EmptyResultDataAccessException("Onbekende entiteit", 1);
        }

    }

    public void deleteScreenshotsByTicketId(String id) {

        if(ticketRepository.existsById(id)) {

            Optional<Ticket> tk = ticketRepository.findById(id);

            tk.get().getScreenshots().clear();

            ticketRepository.save(tk.get());
        }
        else{
            throw new EmptyResultDataAccessException("Onbekende entiteit", 1);
        }


    }

    public List<ScreenshotOutputDto> getScreenshotByTicketId(String id) {

        Optional<Ticket> tk = ticketRepository.findById(id);

        List<ScreenshotOutputDto> listDto = ScreenshotMapper.transferScreenshotListToDtoList(tk.get().getScreenshots());

        return listDto;

    }


}
