package nl.novi.backend_it_helpdesk.controllers;

import nl.novi.backend_it_helpdesk.dtos.TicketInputDto;
import nl.novi.backend_it_helpdesk.dtos.TicketOutputDto;
import nl.novi.backend_it_helpdesk.repositories.TicketRepository;
import nl.novi.backend_it_helpdesk.services.TicketService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
public class TicketController {

    private final TicketService ticketService;

    public TicketController(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    @GetMapping("/tickets/{id}")
    public ResponseEntity<TicketOutputDto> getTicket(@PathVariable("id") Long id) {

        TicketOutputDto ticket = ticketService.getTicketById(id);

        return ResponseEntity.ok().body(ticket);

    }

    @PostMapping("/tickets")
    public ResponseEntity<Object> addTicket(@RequestBody TicketInputDto ticket) {

        try{
        TicketOutputDto dto = ticketService.addTicket(ticket);

        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/" + dto.getId())
                .buildAndExpand(dto.getId()).toUri();

        return ResponseEntity.created(uri).body(dto);
        }

        catch(Exception e){
            return ResponseEntity.unprocessableEntity().body(e.getMessage());
        }

    }



}
