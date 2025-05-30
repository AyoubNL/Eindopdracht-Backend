package nl.novi.backend_it_helpdesk.controllers;


import nl.novi.backend_it_helpdesk.dtos.ScreenshotOutputDto;
import nl.novi.backend_it_helpdesk.exceptions.NotAuthorizedUserException;
import nl.novi.backend_it_helpdesk.mappers.ScreenshotMapper;
import nl.novi.backend_it_helpdesk.models.Screenshot;
import nl.novi.backend_it_helpdesk.models.Ticket;
import nl.novi.backend_it_helpdesk.repositories.TicketRepository;
import nl.novi.backend_it_helpdesk.services.ScreenshotService;
import nl.novi.backend_it_helpdesk.services.TicketService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.InvalidMediaTypeException;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.net.URI;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping("/screenshots")
public class ScreenshotController {

    private final ScreenshotService screenshotService;
    private final TicketRepository ticketRepository;

    public ScreenshotController(ScreenshotService screenshotService, TicketRepository ticketRepository) {
        this.screenshotService = screenshotService;
        this.ticketRepository = ticketRepository;

    }

    @PostMapping("/{id}")
    public ResponseEntity<ScreenshotOutputDto> uploadScreenshot(@PathVariable("id") String id, @RequestBody MultipartFile file) throws IOException {

        String url = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/screenshots/")
                .path(Objects.requireNonNull(id))
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

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteScreenshot(@PathVariable("id") Long id) {

        screenshotService.deleteScreenshot(id);

        return ResponseEntity.noContent().build();

    }

    @DeleteMapping("/ticket/{id}")
    public ResponseEntity<Object> deleteScreenshotsByTicketId(@PathVariable("id") String id) {

        screenshotService.deleteScreenshotsByTicketId(id);

        return ResponseEntity.noContent().build();

    }


    @GetMapping("/ticket/{id}")
    public List<ScreenshotOutputDto> getScreenshotByTicketId(@PathVariable("id") String id) {

       List<ScreenshotOutputDto> dto = screenshotService.getScreenshotByTicketId(id);

        return ResponseEntity.ok(dto).getBody();

    }



}
