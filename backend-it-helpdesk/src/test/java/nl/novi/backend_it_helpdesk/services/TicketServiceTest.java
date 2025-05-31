package nl.novi.backend_it_helpdesk.services;

import lombok.extern.slf4j.Slf4j;
import nl.novi.backend_it_helpdesk.dtos.*;
import nl.novi.backend_it_helpdesk.exceptions.RecordNotFoundException;
import nl.novi.backend_it_helpdesk.models.*;
import nl.novi.backend_it_helpdesk.repositories.TicketRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.*;

import static nl.novi.backend_it_helpdesk.enums.PriorityTicketEnum.*;
import static nl.novi.backend_it_helpdesk.enums.StatusTicketEnum.CLOSED;
import static nl.novi.backend_it_helpdesk.enums.StatusTicketEnum.IN_PROGRESS;
import static nl.novi.backend_it_helpdesk.enums.TypeTicketEnum.*;
import static nl.novi.backend_it_helpdesk.enums.UserRoleEnum.*;
import static nl.novi.backend_it_helpdesk.mappers.TicketMapper.transferToTicket;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@Slf4j
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

    List<Ticket> mockTickets = new ArrayList<>();
    TicketInputDto ticketInputDto;
    TicketInputDto ticketInputDtoE;
    TicketInputDto ticketInputDtoNull;
    TicketInputDto ticketInputDtoNullS;
    Ticket tk;

    @BeforeEach
    void setUp() {

        Authentication authentication = new UsernamePasswordAuthenticationToken(
                "Test_Manager",
                "geheim",
                List.of(new SimpleGrantedAuthority("manager"))
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);


        mockTickets = Arrays.asList((new Ticket("CASE00001", LocalDateTime.now(), P1_ORGANIZATION, LocalDateTime.now().plusMinutes(60), new Category("CASE00001", "Hardware", "Muis", List.of(new Ticket())), new Detail("CASE00001", "Mijn muis kan ik niet verbinden met de laptop (Dell)", "Geen verbinding", MALFUNCTION, List.of(new Ticket())), new Fix("CASE00001", "Maak opnieuw verbinding via bluetooth.", "Uitstekende oplossing", IN_PROGRESS), List.of(new Screenshot()), "Test_Manager")),
                (new Ticket("CASE00002", LocalDateTime.now(), P2_DEPARTEMENT, LocalDateTime.now().plusMinutes(45), new Category("CASE00002", "Software", "Licentie", List.of(new Ticket())), new Detail("CASE00002", "Licentie verlopen", "De licenties van ons Office pakket is bijna verlopen", QUESTION, List.of(new Ticket())), new Fix("CASE00002", "De afdeling inkoop is bezig met een verlenging", "Wij kijken er naar uit!", IN_PROGRESS), List.of(new Screenshot()), "Test_Agent")),
                (new Ticket("CASE00003", LocalDateTime.now(), P3_TEAM, LocalDateTime.now().plusMinutes(30), new Category("CASE00003", "Netwerk", "Internet", List.of(new Ticket())), new Detail("CASE00003", "Internet traag", "Het hele bedrijf heeft soms last van trage internet", COMPLAINT, List.of(new Ticket())), new Fix("CASE00003", "Er loopt een case bij onze ISP (KPN)", "Trage afhandeling", IN_PROGRESS), List.of(new Screenshot()), "Test_Client")),
                (new Ticket("CASE00004", LocalDateTime.now(), P4_INDIVIDUAL, LocalDateTime.now().plusMinutes(30), new Category("CASE00004", "Kantoor", "Bureaustoel", List.of(new Ticket())), new Detail("CASE00004", "Bureaustoel stuk", "Mijn ergonomische bureaustoel is stuk", MALFUNCTION, List.of(new Ticket())), new Fix("CASE00004", "Een nieuwe stoel besteld bij CoolBlue", "Helemaal top!", IN_PROGRESS), List.of(new Screenshot()), "Test_Manager")));

        ticketInputDto = new TicketInputDto(P4_INDIVIDUAL, new CategoryInputDto("Kantoor", "Bureaustoel"), new DetailInputDto("Bureaustoel stuk", "Mijn ergonomische bureaustoel is stuk", MALFUNCTION), new FixInputDto("Een nieuwe stoel besteld bij CoolBlue", "Helemaal top!", IN_PROGRESS), List.of(new Screenshot()));

        ticketInputDtoNull = new TicketInputDto(null, new CategoryInputDto(null, null), new FixInputDto(null, null, CLOSED), new DetailInputDto(null, null, null));

        ticketInputDtoNullS = new TicketInputDto(null, new CategoryInputDto(null, null), new FixInputDto(null, null, null), new DetailInputDto(null, "Mijn ergonomische bureaustoel is stuk", null));

        ticketInputDtoE = new TicketInputDto(P4_INDIVIDUAL, new FixInputDto("Een nieuwe stoel besteld bij CoolBlue", "Helemaal top!", IN_PROGRESS),new DetailInputDto("Bureaustoel stuk", "Mijn ergonomische bureaustoel is stuk", MALFUNCTION), new UserInputDto("Test04", "test04@novi.nl", AGENT, "Test04@novi.nl"));

        tk = new Ticket(1L, LocalDateTime.now(), P4_INDIVIDUAL, LocalDateTime.now().plusMinutes(30));




    }

    @Test
    @DisplayName("GetTicket")
    void testGetTicketById() {
        //arrange
        when(ticketRepository.findById("CASE00002")).thenReturn(Optional.of(mockTickets.get(1)));

        //act
        Optional<TicketOutputDto> result = Optional.ofNullable(ticketService.getTicketById("CASE00002"));
        //assert
        assertTrue(result.isPresent());

        assertEquals("CASE00002", result.get().getId());
        assertEquals("Software", result.get().getCategory().getCategoryName());
        assertEquals("Licentie", result.get().getCategory().getSubCategoryName());
        assertEquals("Test_Agent", result.get().getUser());
        assertEquals(P2_DEPARTEMENT, result.get().getPriority());

    }

    @Test
    @DisplayName("GetTicketNotNull")
    void testGetTicketByIdNotNull() {

        //arrange
        when(ticketRepository.findById("CASE00001")).thenReturn(Optional.ofNullable(tk));
        //act
        Optional<TicketOutputDto> result = Optional.ofNullable(ticketService.getTicketById("CASE00001"));
        //assert
        assertNull(result.get().getCategory());
        assertNull(result.get().getFix());
        assertNull(result.get().getDetail());

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
        assertEquals("CASE00001", result.get(0).getId());
        assertEquals("CASE00002", result.get(1).getId());
        assertEquals("CASE00003", result.get(2).getId());
        assertEquals(P1_ORGANIZATION, result.get(0).getPriority());
        assertEquals(P2_DEPARTEMENT, result.get(1).getPriority());
        assertEquals(P3_TEAM, result.get(2).getPriority());
        assertEquals("Test_Manager", result.get(0).getUser());
        assertEquals("Test_Agent", result.get(1).getUser());
        assertEquals("Test_Client", result.get(2).getUser());
    }

    @Test
    @DisplayName("DeleteTickets")
    void testDeleteTicket() {

        when(ticketRepository.existsById("CASE00002")).thenReturn(true);

        ticketService.deleteTicket("CASE00002");

        verify(ticketRepository).deleteById("CASE00002");

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

        when(ticketRepository.findById("CASE00004")).thenReturn(Optional.of(mockTickets.get(3)));

        ticketService.updateTicket("CASE00004", ticketInputDto);

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
    @DisplayName("UpdateTicketNull")
    void testUpdateTicketNull() {

        when(ticketRepository.findById("CASE00002")).thenReturn(Optional.ofNullable(mockTickets.get(3)));

        ticketService.updateTicket("CASE00002", ticketInputDtoNull);

        verify(ticketRepository, times(1)).save(captor.capture());

        Ticket tk1 = transferToTicket(ticketInputDtoNull);

        assertNotNull(tk1.getCategory());
        assertNull(tk1.getCategory().getCategoryName());
        assertNull(tk1.getCategory().getSubCategoryName());
        assertNull(tk1.getPriority());
        assertNotNull(tk1.getDetail());
        assertNull(tk1.getDetail().getTitle());
        assertNull(tk1.getDetail().getDescription());
        assertNull(tk1.getDetail().getType());
        assertNull(tk1.getUser());
        assertNotNull(tk1.getFix());
        assertNull(tk1.getFix().getFeedback());
        assertNull(tk1.getFix().getSolution());

    }

    @Test
    @DisplayName("UpdateTicketNullS")
    void testUpdateTicketNullS() {

        when(ticketRepository.findById("CASE00002")).thenReturn(Optional.ofNullable(mockTickets.get(3)));
        ticketService.updateTicket("CASE00002", ticketInputDtoNullS);
        verify(ticketRepository, times(1)).save(captor.capture());
        Ticket tk1 = transferToTicket(ticketInputDtoNullS);
        assertNull(tk1.getFix().getStatus());
    }

    @Test
    @DisplayName("UpdateTicketEmpty")
    void testUpdateTicketE() {

        when(ticketRepository.findById("CASE00002")).thenReturn(Optional.of(mockTickets.get(3)));
        ticketService.updateTicket("CASE00002", ticketInputDtoE);
        verify(ticketRepository, times(1)).save(captor.capture());
        Ticket captured = captor.getValue();
        assertEquals(mockTickets.get(3).getCategory(), captured.getCategory());

    }

    @Test
    @DisplayName("throwExceptionUpdateTicket")
    void testUpdateTicketThrowsException() {
        assertThrows(RecordNotFoundException.class, () -> ticketService.updateTicket("CASE00009", ticketInputDto));
    }

    @Test
    @DisplayName("AddTicket")
    void testAddTicket() {
        when(ticketRepository.save(captor.capture())).thenReturn(mockTickets.get(3));

        ticketService.addTicket(ticketInputDto);
        verify(ticketRepository, times(1)).save(captor.capture());

        Ticket captured = captor.getValue();

        assertEquals(mockTickets.get(3).getPriority(), captured.getPriority());
        assertEquals(mockTickets.get(3).getCategory().getCategoryName(), captured.getCategory().getCategoryName());
        assertEquals(mockTickets.get(3).getCategory().getSubCategoryName(), captured.getCategory().getSubCategoryName());


    }

}
