package nl.novi.backend_it_helpdesk.controllers;

import jakarta.validation.Valid;
import nl.novi.backend_it_helpdesk.dtos.TicketInputDto;
import nl.novi.backend_it_helpdesk.dtos.TicketOutputDto;
import nl.novi.backend_it_helpdesk.enums.StatusTicketEnum;
import nl.novi.backend_it_helpdesk.services.TicketService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
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
    public ResponseEntity<TicketOutputDto> getTicket(@PathVariable("id") String id) {

        TicketOutputDto ticketOutputDto = ticketService.getTicketById(id.toUpperCase());

        return ResponseEntity.ok().body(ticketOutputDto);

    }

    @GetMapping
    public ResponseEntity<List<TicketOutputDto>> getAllTickets(@RequestParam(value = "user", required = false) Optional<String> user) {

        List<TicketOutputDto> dtos;

        if (user.isEmpty()) {
            dtos = ticketService.getAllTickets();
        } else {
            dtos = ticketService.getAllTicketsByUser(user.get());
        }

        return ResponseEntity.ok().body(dtos);

    }

    @GetMapping("/priority/{priority}")
    public ResponseEntity<List<TicketOutputDto>> getAllTicketsByPriority(@PathVariable("priority") String priorityTicketEnum ) {

        List<TicketOutputDto> dtos = ticketService.getAllTicketsByPriority(priorityTicketEnum);

        return ResponseEntity.ok().body(dtos);

    }

    @GetMapping("/rejected")
    public ResponseEntity<List<TicketOutputDto>> getAllTicketsByRejected() {

        List<TicketOutputDto> dtos = ticketService.getAllTicketsByRejected();

        return ResponseEntity.ok().body(dtos);

    }

    @PostMapping()
    public ResponseEntity<Object> addTicket(@Valid @RequestBody TicketInputDto ticket) {

        try {
            TicketOutputDto dto = ticketService.addTicket(ticket);

            URI uri = ServletUriComponentsBuilder
                    .fromCurrentRequest()
                    .path("/" + dto.getId())
                    .buildAndExpand(dto.getId()).toUri();

            return ResponseEntity.created(uri).body(dto);
        } catch (Exception e) {
            return ResponseEntity.unprocessableEntity().body(e.getMessage());
        }

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteTicket(@PathVariable String id) {

        ticketService.deleteTicket(id);

        return ResponseEntity.noContent().build();

    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateTicket(@PathVariable String id, @Valid @RequestBody TicketInputDto updateTicket) {

        TicketOutputDto outputDto = ticketService.updateTicket(id, updateTicket);
        return ResponseEntity.ok().body(outputDto);

    }

    @PutMapping()
    public ResponseEntity<Object> changeStatusTicket(@RequestParam String id, @RequestParam("status") StatusTicketEnum changeStatusTicket) {

        TicketOutputDto outputDto = ticketService.changeStatusTicket(id, changeStatusTicket);

        return ResponseEntity.ok().body(outputDto);


    }


}
