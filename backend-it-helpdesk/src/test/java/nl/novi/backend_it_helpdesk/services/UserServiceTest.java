package nl.novi.backend_it_helpdesk.services;

import nl.novi.backend_it_helpdesk.dtos.UserOutputDto;
import nl.novi.backend_it_helpdesk.exceptions.RecordNotFoundException;
import nl.novi.backend_it_helpdesk.exceptions.UsernameNotFoundException;
import nl.novi.backend_it_helpdesk.models.Authority;
import nl.novi.backend_it_helpdesk.models.Ticket;
import nl.novi.backend_it_helpdesk.models.User;
import nl.novi.backend_it_helpdesk.repositories.UserRepository;
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

import java.util.*;

import static nl.novi.backend_it_helpdesk.enums.UserRoleEnum.AGENT;
import static nl.novi.backend_it_helpdesk.enums.UserRoleEnum.MANAGER;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @Captor
    ArgumentCaptor<User> captor;

    @Mock
    PasswordEncoder passwordEncoder;

    List<User> mockUsers;

    @BeforeEach
    void setUp() {

        mockUsers = Arrays.asList(new User("Test01", "$2a$10$oh2jnssxP9pT/TcHPfhXb.au5/Fn1.7/AO.A1dRKA1jhunmuybd0.", MANAGER, "test01@novi.nl", Set.of(new Authority("Test01", MANAGER)), List.of(new Ticket())),
                new User("Test02", "$2a$10$.k.Ug5Pf7CGRf/QIw5zuy.BYCH17d5R.IlxHxS1r5SeZXgD6ptKRW", AGENT, "Test02@novi.nl", Set.of(new Authority("Test02", AGENT)), List.of(new Ticket()))
        );

    }

    @Test
    void testGetUser() {

        when(userRepository.findById("Test01")).thenReturn(Optional.of(mockUsers.getFirst()));

        Optional<UserOutputDto> result = Optional.ofNullable(userService.getUser("Test01"));
        System.out.println(mockUsers);
        System.out.println(result);

        assertTrue(result.isPresent());

        assertFalse(mockUsers.isEmpty());

        assertEquals("Test01", result.get().getUsername());
        assertEquals("test01@novi.nl", result.get().getEmail());

    }

    @Test
    @DisplayName("throwExceptionGetUser")
    void testGetUserThrowsException() {
        assertThrows(UsernameNotFoundException.class, () -> userService.getUser(null));
    }




    @Test
    void getAllUsers() {
    }

    @Test
    void addUser() {
    }

    @Test
    void deleteUser() {
    }

    @Test
    void updateUser() {
    }

    @Test
    void getUserByUsername() {
    }
}