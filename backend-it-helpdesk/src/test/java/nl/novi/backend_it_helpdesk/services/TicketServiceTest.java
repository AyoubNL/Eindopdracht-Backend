package nl.novi.backend_it_helpdesk.services;

import nl.novi.backend_it_helpdesk.dtos.TicketOutputDto;
import nl.novi.backend_it_helpdesk.models.*;
import nl.novi.backend_it_helpdesk.repositories.TicketRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static nl.novi.backend_it_helpdesk.enums.PriorityTicketEnum.*;
import static nl.novi.backend_it_helpdesk.enums.StatusTicketEnum.IN_PROGRESS;
import static nl.novi.backend_it_helpdesk.enums.TypeTicketEnum.*;
import static nl.novi.backend_it_helpdesk.enums.UserRoleEnum.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TicketServiceTest {

    @Mock
    private TicketRepository ticketRepository;

    @InjectMocks
    private TicketService ticketService;

    private List<Ticket> mockTickets;

    @BeforeEach
    void setUp() {
        mockTickets = Arrays.asList((new Ticket(1L, LocalDateTime.now(), P1_ORGANIZATION, LocalDateTime.now().plusMinutes(60), new Category(1L, "Hardware", "Muis", List.of(new Ticket())), new Detail(1L, "Mijn muis kan ik niet verbinden met de laptop (Dell)", "Geen verbinding", MALFUNCTION, List.of(new Ticket())), new Fix(1L, "Maak opnieuw verbinding via bluetooth.", "Uitstekende oplossing", IN_PROGRESS), List.of(new Screenshot()), new User("Test01", "$2a$10$oh2jnssxP9pT/TcHPfhXb.au5/Fn1.7/AO.A1dRKA1jhunmuybd0.", MANAGER, "Test01@novi.nl", Set.of(new Authority("Test01", MANAGER)), List.of(new Ticket())))),
                (new Ticket(2L, LocalDateTime.now(), P2_DEPARTEMENT, LocalDateTime.now().plusMinutes(45), new Category(2L, "Software", "Licentie", List.of(new Ticket())), new Detail(2L, "Licentie verlopen", "De licenties van ons Office pakket is bijna verlopen", QUESTION, List.of(new Ticket())), new Fix(2L, "De afdeling inkoop is bezig met een verlenging", "Wij kijken er naar uit!", IN_PROGRESS), List.of(new Screenshot()), new User("Test02", "$2a$10$.k.Ug5Pf7CGRf/QIw5zuy.BYCH17d5R.IlxHxS1r5SeZXgD6ptKRW", AGENT, "Test02@novi.nl", Set.of(new Authority("Test02", AGENT)), List.of(new Ticket())))),
                (new Ticket(3L, LocalDateTime.now(), P3_TEAM, LocalDateTime.now().plusMinutes(30), new Category(3L, "Netwerk", "Internet", List.of(new Ticket())), new Detail(3L, "Internet traag", "Het hele bedrijf heeft soms last van trage internet", COMPLAINT, List.of(new Ticket())), new Fix(3L, "Er loopt een case bij onze ISP (KPN)", "Trage afhandeling", IN_PROGRESS), List.of(new Screenshot()), new User("Test03", "$2a$10$wMMChXMeYRqPSwSP/4Nns.CrFArfWhaBfswig.ljtEjbSvnd45gn6", CLIENT, "Test03@novi.nl", Set.of(new Authority("Test03", CLIENT)), List.of(new Ticket()))))
        );
    }
    @Test
    @DisplayName("GetTicket")
    void testGetTicketById() {
        //arrange
        when(ticketRepository.findById(2L)).thenReturn(Optional.of(mockTickets.get(1)));
        //act
        Optional<TicketOutputDto> result = Optional.ofNullable(ticketService.getTicketById(2L));
        //assert
        assertTrue(result.isPresent());
        assertEquals(2L, result.get().getId());
        assertEquals("Test02@novi.nl", result.get().getUser().getEmail());
        assertEquals("Software", result.get().getCategory().getCategoryName());
        assertEquals("Licentie", result.get().getCategory().getSubCategoryName());
        assertEquals("Test02", result.get().getUser().getUsername());
        assertEquals(P2_DEPARTEMENT, result.get().getPriority());

    }

    @Test
    @DisplayName("GetAllTickets")
    void testGetAllTickets() {
        //arrange
        when(ticketRepository.findAll()).thenReturn(mockTickets);
        //act
        List<TicketOutputDto> result = ticketService.getAllTickets();
        //assert
        assertEquals(3, result.size());
        assertEquals(1L, result.get(0).getId());
        assertEquals(2L, result.get(1).getId());
        assertEquals(3L, result.get(2).getId());
        assertEquals(P1_ORGANIZATION, result.get(0).getPriority());
        assertEquals(P2_DEPARTEMENT, result.get(1).getPriority());
        assertEquals(P3_TEAM, result.get(2).getPriority());
        assertEquals("Test01@novi.nl", result.get(0).getUser().getEmail());
        assertEquals("Test02@novi.nl", result.get(1).getUser().getEmail());
        assertEquals("Test03@novi.nl", result.get(2).getUser().getEmail());
    }

    @Test
    @DisplayName("DeleteTickets")
    void testDeleteTicket() {
        when(ticketRepository.existsById(1L)).thenReturn(true);
//        doNothing().when(ticketRepository).deleteById(2L);

        boolean result = ticketService.deleteTicket(1L);

        assertTrue(result);
        verify(ticketRepository, times(1)).deleteById(2L);
    }

    @Test
    @DisplayName("DeleteTickets")
    void testDeleteTicketNotExist() {
        when(ticketRepository.existsById(5L)).thenReturn(false);

        boolean result = ticketService.deleteTicket(5L);

        assertFalse(false);
        verify(ticketRepository, never()).deleteById(5L);

    }

}
