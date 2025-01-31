package nl.novi.backend_it_helpdesk.services;

import nl.novi.backend_it_helpdesk.repositories.TicketRepository;
import org.springframework.stereotype.Service;

@Service
public class TicketService {

    final private TicketRepository ticketRepository;

    public TicketService(TicketRepository ticketRepository) {
        this.ticketRepository = ticketRepository;
    }




}
