package nl.novi.backend_it_helpdesk.services;

import nl.novi.backend_it_helpdesk.exceptions.RecordNotFoundException;
import nl.novi.backend_it_helpdesk.models.Screenshot;
import nl.novi.backend_it_helpdesk.repositories.ScreenshotRepository;
import nl.novi.backend_it_helpdesk.repositories.TicketRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class ScreenshotService {

    private final ScreenshotRepository screenshotRepository;
    private final TicketRepository ticketRepository;

    public ScreenshotService(ScreenshotRepository screenshotRepository, TicketRepository ticketRepository) {
        this.screenshotRepository = screenshotRepository;
        this.ticketRepository = ticketRepository;
    }

    public Screenshot storeFile(MultipartFile file, String url) throws IOException {

        String title = file.getOriginalFilename();
        String contentType = file.getContentType();
        Long size = file.getSize();
        byte[] bytes = file.getBytes();

        Screenshot sh = new Screenshot(title, contentType, url,size, bytes);

        return screenshotRepository.save(sh);


    }

    public Screenshot getScreenshotFromTicket(String id) {

        return screenshotRepository.findById(id).orElseThrow(RecordNotFoundException::new);
    }

    public void addScreenshotToTicket(String id, Screenshot st) {

        var optionalTicket = ticketRepository.findById(id);

        if (optionalTicket.isPresent()) {
            var ticket = optionalTicket.get();
            st.setTicket(ticket);
            ticket.getScreenshots().add(st);
            ticketRepository.save(ticket);

        } else {
            throw new RecordNotFoundException("Het ticketnummer:" + id + " is onbekend");
        }

    }





}
