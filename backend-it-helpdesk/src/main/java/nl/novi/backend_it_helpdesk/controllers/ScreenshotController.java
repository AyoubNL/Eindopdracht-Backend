package nl.novi.backend_it_helpdesk.controllers;


import nl.novi.backend_it_helpdesk.dtos.ScreenshotOutputDto;
import nl.novi.backend_it_helpdesk.mappers.ScreenshotMapper;
import nl.novi.backend_it_helpdesk.models.Screenshot;
import nl.novi.backend_it_helpdesk.services.ScreenshotService;
import nl.novi.backend_it_helpdesk.services.TicketService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.InvalidMediaTypeException;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.net.URI;
import java.util.Objects;

@RestController
@RequestMapping("/screenshots")
public class ScreenshotController {

    private final ScreenshotService screenshotService;
    private final TicketService ticketService;

    public ScreenshotController(ScreenshotService screenshotService, TicketService ticketService) {
        this.screenshotService = screenshotService;
        this.ticketService = ticketService;
    }

    @PostMapping("/{id}/tickets")
    public ResponseEntity<ScreenshotOutputDto> uploadScreenshot(@PathVariable("id") Long id, @RequestBody MultipartFile file) throws IOException {

        String url = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/tickets/")
                .path(Objects.requireNonNull(id.toString()))
                .path("/screenshot")
                .toUriString();

        Screenshot st = screenshotService.storeFile(file, url);

        screenshotService.addScreenshotToTicket(id, st);

        return ResponseEntity.created(URI.create(url)).body(ScreenshotMapper.transferToDto(st));

    }

    @GetMapping("/{id}")
    ResponseEntity<byte[]> getScreenshot(@PathVariable("id") Long id) {

        Screenshot screenshot = screenshotService.getScreenshotFromTicket(id);

        MediaType mediaType;

        try {
            mediaType = MediaType.parseMediaType(screenshot.getContentType());
        } catch (InvalidMediaTypeException ignore) {
            mediaType = MediaType.APPLICATION_OCTET_STREAM;
        }

        return ResponseEntity
                .ok()
                .contentType(mediaType)
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline;fileName=" + screenshot.getTitle())
                .body(screenshot.getContents());
    }

}
