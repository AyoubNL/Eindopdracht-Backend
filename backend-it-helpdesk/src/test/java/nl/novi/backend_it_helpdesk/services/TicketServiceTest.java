package nl.novi.backend_it_helpdesk.services;

import nl.novi.backend_it_helpdesk.dtos.*;
import nl.novi.backend_it_helpdesk.exceptions.RecordNotFoundException;
import nl.novi.backend_it_helpdesk.models.*;
import nl.novi.backend_it_helpdesk.repositories.TicketRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.*;

import static nl.novi.backend_it_helpdesk.enums.PriorityTicketEnum.*;
import static nl.novi.backend_it_helpdesk.enums.StatusTicketEnum.IN_PROGRESS;
import static nl.novi.backend_it_helpdesk.enums.TypeTicketEnum.*;
import static nl.novi.backend_it_helpdesk.enums.UserRoleEnum.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class TicketServiceTest {
    @Mock
    private TicketRepository ticketRepository;

    @InjectMocks
    private TicketService ticketService;

    @Captor
    ArgumentCaptor<Ticket> captor;

    @Mock
    PasswordEncoder passwordEncoder;

    List<Ticket> mockTickets;
    TicketInputDto ticketInputDto;
    byte[] data = {122,5,112,66};
    Screenshot sh = new Screenshot("Foutmelding", "application/json", "www.test.nl", 2313L, data);

    @BeforeEach
    void setUp() {
        mockTickets = Arrays.asList((new Ticket(1L, LocalDateTime.now(), P1_ORGANIZATION, LocalDateTime.now().plusMinutes(60), new Category(1L, "Hardware", "Muis", List.of(new Ticket())), new Detail(1L, "Mijn muis kan ik niet verbinden met de laptop (Dell)", "Geen verbinding", MALFUNCTION, List.of(new Ticket())), new Fix(1L, "Maak opnieuw verbinding via bluetooth.", "Uitstekende oplossing", IN_PROGRESS), List.of(new Screenshot()), new User("Test01", "$2a$10$oh2jnssxP9pT/TcHPfhXb.au5/Fn1.7/AO.A1dRKA1jhunmuybd0.", MANAGER, "Test01@novi.nl", Set.of(new Authority("Test01", MANAGER)), List.of(new Ticket())))),
                (new Ticket(2L, LocalDateTime.now(), P2_DEPARTEMENT, LocalDateTime.now().plusMinutes(45), new Category(2L, "Software", "Licentie", List.of(new Ticket())), new Detail(2L, "Licentie verlopen", "De licenties van ons Office pakket is bijna verlopen", QUESTION, List.of(new Ticket())), new Fix(2L, "De afdeling inkoop is bezig met een verlenging", "Wij kijken er naar uit!", IN_PROGRESS), List.of(new Screenshot()), new User("Test02", "$2a$10$.k.Ug5Pf7CGRf/QIw5zuy.BYCH17d5R.IlxHxS1r5SeZXgD6ptKRW", AGENT, "Test02@novi.nl", Set.of(new Authority("Test02", AGENT)), List.of(new Ticket())))),
                (new Ticket(3L, LocalDateTime.now(), P3_TEAM, LocalDateTime.now().plusMinutes(30), new Category(3L, "Netwerk", "Internet", List.of(new Ticket())), new Detail(3L, "Internet traag", "Het hele bedrijf heeft soms last van trage internet", COMPLAINT, List.of(new Ticket())), new Fix(3L, "Er loopt een case bij onze ISP (KPN)", "Trage afhandeling", IN_PROGRESS), List.of(new Screenshot()), new User("Test03", "$2a$10$wMMChXMeYRqPSwSP/4Nns.CrFArfWhaBfswig.ljtEjbSvnd45gn6", CLIENT, "Test03@novi.nl", Set.of(new Authority("Test03", CLIENT)), List.of(new Ticket())))),
                (new Ticket(4L, LocalDateTime.now(), P4_INDIVIDUAL, LocalDateTime.now().plusMinutes(30), new Category(4L, "Kantoor", "Bureaustoel", List.of(new Ticket())), new Detail(4L, "Bureaustoel stuk", "Mijn ergonomische bureaustoel is stuk", MALFUNCTION, List.of(new Ticket())), new Fix(4L, "Een nieuwe stoel besteld bij CoolBlue", "Helemaal top!", IN_PROGRESS), List.of(new Screenshot()), new User("Test04", "$2a$10$wMMChXMeYRqPSwSP/4Nns.CrFArfWhaBfswig.ljtEjbSvnd45gn7", CLIENT, "Test04@novi.nl", Set.of(new Authority("Test04", CLIENT)), List.of(new Ticket()))))
        );
        ticketInputDto = new TicketInputDto(P4_INDIVIDUAL, new UserInputDto("Test04", "test04@novi.nl", AGENT, "Test04@novi.nl"), new CategoryInputDto("Kantoor", "Bureaustoel"), new DetailInputDto("Bureaustoel stuk", "Mijn ergonomische bureaustoel is stuk", MALFUNCTION), new FixInputDto("Een nieuwe stoel besteld bij CoolBlue", "Helemaal top!", IN_PROGRESS), List.of(new Screenshot()));

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

        assertFalse(mockTickets.isEmpty());

        assertNotNull(mockTickets.get(1).getCategory().getId());
        assertNotNull(mockTickets.get(1).getCategory().getCategoryName());
        assertNotNull(mockTickets.get(1).getCategory().getSubCategoryName());
        assertNotNull(mockTickets.get(1).getCategory().getTickets());

        assertEquals(2L, result.get().getId());
        assertEquals("Test02@novi.nl", result.get().getUser().getEmail());
        assertEquals("Software", result.get().getCategory().getCategoryName());
        assertEquals("Licentie", result.get().getCategory().getSubCategoryName());
        assertEquals("Test02", result.get().getUser().getUsername());
        assertEquals(P2_DEPARTEMENT, result.get().getPriority());

    }

    @Test
    @DisplayName("throwExceptionGetTicketById")
    void testGetTicketThrowsException() {
        assertThrows(RecordNotFoundException.class, () -> ticketService.getTicketById(null));
    }

    @Test
    @DisplayName("GetAllTickets")
    void testGetAllTickets() {
        //arrange
        when(ticketRepository.findAll()).thenReturn(mockTickets);
        //act
        List<TicketOutputDto> result = ticketService.getAllTickets();
        //assert
        assertEquals(4, result.size());
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

        ticketService.deleteTicket(2L);

        verify(ticketRepository).deleteById(2L);

    }

    @Test
    @DisplayName("GetAllTicketsByUser")
    void testGetAllTicketsByUser() {

        doReturn(mockTickets).when(ticketRepository).findAllByUser(mockTickets.get(2).getUser());

        List<TicketOutputDto> result = ticketService.getAllTicketsByUser(mockTickets.get(2).getUser());

        assertEquals(4, result.size());

    }

    @Test
    @DisplayName("UpdateTicket")
    void testUpdateTicket() {

        when(ticketRepository.findById(2L)).thenReturn(Optional.of(mockTickets.get(3)));

        ticketService.updateTicket(2L, ticketInputDto);

        verify(ticketRepository, times(1)).save(captor.capture());

        Ticket captured = captor.getValue();

        assertEquals(mockTickets.get(3).getPriority(), captured.getPriority());
        assertEquals(mockTickets.get(3).getCategory().getCategoryName(), captured.getCategory().getCategoryName());
        assertEquals(mockTickets.get(3).getCategory().getSubCategoryName(), captured.getCategory().getSubCategoryName());
        assertEquals(mockTickets.get(3).getCategory().getCategoryName(), captured.getCategory().getCategoryName());
        assertEquals(mockTickets.get(3).getDetail().getTitle(), captured.getDetail().getTitle());
        assertEquals(mockTickets.get(3).getDetail().getDescription(), captured.getDetail().getDescription());
        assertEquals(mockTickets.get(3).getDetail().getType(), captured.getDetail().getType());


    }

    @Test
    @DisplayName("throwExceptionUpdateTicket")
    void testUpdateTicketThrowsException() {
        assertThrows(RecordNotFoundException.class, () -> ticketService.updateTicket(null, ticketInputDto));
    }

    @Test
    @DisplayName("AddTicket")
    void testAddTicket() {
        when(ticketRepository.save(captor.capture())).thenReturn(mockTickets.get(3));

        ticketService.addTicket(ticketInputDto);
        verify(ticketRepository, times(1)).save(captor.capture());

        Ticket captured = captor.getValue();

        captor.getValue().getUser().setPassword(passwordEncoder.encode(ticketInputDto.getUser().getPassword()));
        assertEquals(mockTickets.get(3).getPriority(), captured.getPriority());
        assertEquals(mockTickets.get(3).getCategory().getCategoryName(), captured.getCategory().getCategoryName());
        assertEquals(mockTickets.get(3).getCategory().getSubCategoryName(), captured.getCategory().getSubCategoryName());


    }


    @Test
    @DisplayName("AddScreenshotToTicket")
    void testAddScreenshotToTicket() {

        when(ticketRepository.findById(1L)).thenReturn(Optional.of(mockTickets.getFirst()));

        assertNotNull(mockTickets.getFirst());

        ticketService.addScreenshotToTicket(1L, sh);

        sh.setTicket(mockTickets.getFirst());

//        mockTickets.getFirst().getScreenshots().add(sh);

        Ticket captured = captor.getValue();

        when(ticketRepository.save(captor.capture())).thenReturn(mockTickets.getFirst());

        verify(ticketRepository, times(1)).save(captor.capture());

        assertEquals(mockTickets.getFirst().getScreenshots().getFirst().getId(), captured.getScreenshots().getFirst().getId());

    }
}
