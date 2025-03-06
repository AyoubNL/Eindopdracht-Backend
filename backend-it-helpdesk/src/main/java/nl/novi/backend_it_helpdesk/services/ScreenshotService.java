package nl.novi.backend_it_helpdesk.services;


import nl.novi.backend_it_helpdesk.exceptions.RecordNotFoundException;
import nl.novi.backend_it_helpdesk.models.Screenshot;
import nl.novi.backend_it_helpdesk.repositories.ScreenshotRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class ScreenshotService {

    ScreenshotRepository screenshotRepository;

    public ScreenshotService(ScreenshotRepository screenshotRepository) {
        this.screenshotRepository = screenshotRepository;
    }


    public Screenshot storeFile(MultipartFile file, String url) throws IOException {

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
}
