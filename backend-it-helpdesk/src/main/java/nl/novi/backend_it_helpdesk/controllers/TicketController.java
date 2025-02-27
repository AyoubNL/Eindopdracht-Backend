package nl.novi.backend_it_helpdesk.controllers;

import jakarta.validation.Valid;
import nl.novi.backend_it_helpdesk.dtos.TicketInputDto;
import nl.novi.backend_it_helpdesk.dtos.TicketOutputDto;
import nl.novi.backend_it_helpdesk.models.User;
import nl.novi.backend_it_helpdesk.services.TicketService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/tickets")
public class TicketController {

    private final TicketService ticketService;

    public TicketController(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<TicketOutputDto> getTicket(@PathVariable("id") Long id) {

        TicketOutputDto ticket = ticketService.getTicketById(id);

        return ResponseEntity.ok().body(ticket);

    }

    @GetMapping
    public ResponseEntity<List<TicketOutputDto>> getAllTickets(@RequestParam(value = "user", required = false) Optional<User> user) {

        List<TicketOutputDto> dtos;

        if (user.isEmpty()) {

            dtos = ticketService.getAllTickets();

        }

        else{
            dtos = ticketService.getAllTicketsByCreatedBy(user.get());
        }

        return ResponseEntity.ok().body(dtos);

    }

    @PostMapping()
    public ResponseEntity<Object> addTicket(@Valid @RequestBody TicketInputDto ticket) {

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

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteTicket(@PathVariable Long id) {

        ticketService.deleteTicket(id);

        return ResponseEntity.noContent().build();

    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateTicket(@PathVariable Long id, @Valid @RequestBody TicketInputDto updateTicket) {

        TicketOutputDto outputDto = ticketService.updateTicket(id, updateTicket);

        return ResponseEntity.ok().body(outputDto);


    }



}
