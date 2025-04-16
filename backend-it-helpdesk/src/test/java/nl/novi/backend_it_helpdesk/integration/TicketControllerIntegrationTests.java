package nl.novi.backend_it_helpdesk.integration;

import nl.novi.backend_it_helpdesk.models.*;
import nl.novi.backend_it_helpdesk.repositories.TicketRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import static nl.novi.backend_it_helpdesk.enums.PriorityTicketEnum.*;
import static nl.novi.backend_it_helpdesk.enums.StatusTicketEnum.IN_PROGRESS;
import static nl.novi.backend_it_helpdesk.enums.TypeTicketEnum.*;
import static nl.novi.backend_it_helpdesk.enums.UserRoleEnum.*;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@WithMockUser(username = "User", authorities = {"manager"})
public class TicketControllerIntegrationTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    TicketRepository ticketRepository;


    @BeforeEach
    void setUp() {
        ticketRepository.deleteAll();

        List<Ticket> testTickets = List.of(
                new Ticket(null, LocalDateTime.now(), P1_ORGANIZATION, LocalDateTime.now().plusMinutes(60), new Category(null, "Hardware", "Muis", List.of(new Ticket())), new Detail(null, "Mijn muis kan ik niet verbinden met de laptop (Dell)", "Geen verbinding", MALFUNCTION, List.of(new Ticket())), new Fix(null, "Maak opnieuw verbinding via bluetooth.", "Uitstekende oplossing", IN_PROGRESS), List.of(new Screenshot()), new User("Test01", "$2a$10$oh2jnssxP9pT/TcHPfhXb.au5/Fn1.7/AO.A1dRKA1jhunmuybd0.", MANAGER, "Test01@novi.nl", Set.of(new Authority("Test01", MANAGER)), List.of(new Ticket()))),
                new Ticket(null, LocalDateTime.now(), P2_DEPARTEMENT, LocalDateTime.now().plusMinutes(45), new Category(null, "Software", "Licentie", List.of(new Ticket())), new Detail(null, "Licentie verlopen", "De licenties van ons Office pakket is bijna verlopen", QUESTION, List.of(new Ticket())), new Fix(null, "De afdeling inkoop is bezig met een verlenging", "Wij kijken er naar uit!", IN_PROGRESS), List.of(new Screenshot()), new User("Test02", "$2a$10$.k.Ug5Pf7CGRf/QIw5zuy.BYCH17d5R.IlxHxS1r5SeZXgD6ptKRW", AGENT, "Test02@novi.nl", Set.of(new Authority("Test02", AGENT)), List.of(new Ticket()))));
//                new Ticket(3L, LocalDateTime.now(), P3_TEAM, LocalDateTime.now().plusMinutes(30), new Category(3L, "Netwerk", "Internet", List.of(new Ticket())), new Detail(3L, "Internet traag", "Het hele bedrijf heeft soms last van trage internet", COMPLAINT, List.of(new Ticket())), new Fix(3L, "Er loopt een case bij onze ISP (KPN)", "Trage afhandeling", IN_PROGRESS), List.of(new Screenshot()), new User("Test03", "$2a$10$wMMChXMeYRqPSwSP/4Nns.CrFArfWhaBfswig.ljtEjbSvnd45gn6", CLIENT, "Test03@novi.nl", Set.of(new Authority("Test03", CLIENT)), List.of(new Ticket())))
//        );

        ticketRepository.saveAll(testTickets);

    }

    @AfterEach
    void tearDown() {
        ticketRepository.deleteAll();
    }

    @Test
    void testGetAllTickets() throws Exception {


    mockMvc.perform(get("/tickets")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$",hasSize(2)))
            .andExpect(jsonPath("$[0].priorityTicketEnum").value("P1_ORGANIZATION"))
            .andExpect(jsonPath("$[1].categoryName").value("Software"))
            .andExpect(jsonPath("$[2].categoryName").value("Netwerk"));
    }





}
