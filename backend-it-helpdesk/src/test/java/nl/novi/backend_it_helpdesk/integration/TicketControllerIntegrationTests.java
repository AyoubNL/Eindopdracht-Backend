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
                new Ticket(null, LocalDateTime.now(), P2_DEPARTEMENT, LocalDateTime.now().plusMinutes(45), new Category(null, "Software", "Licentie", List.of(new Ticket())), new Detail(null, "Licentie verlopen", "De licenties van ons Office pakket is bijna verlopen", QUESTION, List.of(new Ticket())), new Fix(null, "De afdeling inkoop is bezig met een verlenging", "Wij kijken er naar uit!", IN_PROGRESS), List.of(new Screenshot()), new User("Test02", "$2a$10$.k.Ug5Pf7CGRf/QIw5zuy.BYCH17d5R.IlxHxS1r5SeZXgD6ptKRW", AGENT, "Test02@novi.nl", Set.of(new Authority("Test02", AGENT)), List.of(new Ticket()))),
                new Ticket(null, LocalDateTime.now(), P3_TEAM, LocalDateTime.now().plusMinutes(30), new Category(null, "Netwerk", "Internet", List.of(new Ticket())), new Detail(null, "Internet traag", "Het hele bedrijf heeft soms last van trage internet", COMPLAINT, List.of(new Ticket())), new Fix(null, "Er loopt een case bij onze ISP (KPN)", "Trage afhandeling", IN_PROGRESS), List.of(new Screenshot()), new User("Test03", "$2a$10$wMMChXMeYRqPSwSP/4Nns.CrFArfWhaBfswig.ljtEjbSvnd45gn6", CLIENT, "Test03@novi.nl", Set.of(new Authority("Test03", CLIENT)), List.of(new Ticket())))
        );


        ticketRepository.saveAll(testTickets);

    }

    @Test
    void testGetAllTickets() throws Exception {

    mockMvc.perform(get("/tickets")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$",hasSize(3)))
            .andExpect(jsonPath("$[0].priority").value("p1_organisation"))
            .andExpect(jsonPath("$[1].priority").value("p2_departement"))
            .andExpect(jsonPath("$[0].category.categoryName").value("Hardware"))
            .andExpect(jsonPath("$[1].category.categoryName").value("Software"))
            .andExpect(jsonPath("$[1].fix.feedback").value("Wij kijken er naar uit!"))
            .andExpect(jsonPath("$[2].fix.feedback").value("Trage afhandeling"));
    }

    @Test
    void testGetTicket() throws Exception {
        mockMvc.perform(get("/tickets/" + ticketRepository.getOne(3L).getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.priority").value("p3_team"))
                .andExpect(jsonPath("$.category.categoryName").value("Netwerk"))
                .andExpect(jsonPath("$.category.subCategoryName").value("Internet"))
                .andExpect(jsonPath("$.detail.title").value("Internet traag"))
                .andExpect(jsonPath("$.fix.solution").value("Er loopt een case bij onze ISP (KPN)"))
                .andExpect(jsonPath("$.user.username").value("Test03"));

    }





}
