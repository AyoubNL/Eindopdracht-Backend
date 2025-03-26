package nl.novi.backend_it_helpdesk.services;

import nl.novi.backend_it_helpdesk.models.Ticket;
import nl.novi.backend_it_helpdesk.repositories.TicketRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TicketServiceTest {

    @Mock
    private TicketRepository ticketRepository;

    @InjectMocks
    private TicketService ticketService;

    private List<Ticket> mockTickets;

    @BeforeEach
    void setUp() {

        mockTickets = Arrays.asList((new Ticket(1L, "2025-03-26 04:27:13.287764", "P1_ORGANIZATION", "2025-03-26 05:27:13.287764", 1, 1, 1, 1, "Test01")),
                (new Ticket(2L, "2025-03-26 05:27:13.287764", "P2_DEPARTEMENT", "2025-03-26 06:27:13.287764", 2, 2, 2, 2, "Test02")),
                (new Ticket(3L, "2025-03-26 06:27:13.287764", "P3_TEAM", "2025-03-26 07:27:13.287764", 3, 3, 3, 3, "Test03"))
        );
    }

    @Test
    void testGetTicketById() {
        when(ticketRepository.findById(2L)).thenReturn(mockTickets.subList(0, 2));


    }
}
