package nl.novi.backend_it_helpdesk.services;

import nl.novi.backend_it_helpdesk.dtos.UserInputDto;
import nl.novi.backend_it_helpdesk.dtos.UserOutputDto;
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

import static nl.novi.backend_it_helpdesk.enums.UserRoleEnum.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

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
    UserInputDto userInputDto;

    @BeforeEach
    void setUp() {

        mockUsers = Arrays.asList(new User("Test01", "$2a$10$oh2jnssxP9pT/TcHPfhXb.au5/Fn1.7/AO.A1dRKA1jhunmuybd0.", MANAGER, "Test01@novi.nl", Set.of(new Authority("Test01", MANAGER)), List.of(new Ticket())),
                new User("Test02", "$2a$10$.k.Ug5Pf7CGRf/QIw5zuy.BYCH17d5R.IlxHxS1r5SeZXgD6ptKRW", AGENT, "Test02@novi.nl", Set.of(new Authority("Test02", AGENT)), List.of(new Ticket()))
        );

        userInputDto = new UserInputDto("Test03", "$2a$10$wMMChXMeYRqPSwSP/4Nns.CrFArfWhaBfswig.ljtEjbSvnd45gn6", CLIENT, "Test03@novi.nl");
    }

    @Test
    @DisplayName("GetUser")
    void testGetUser() {

        when(userRepository.findById("Test01")).thenReturn(Optional.of(mockUsers.getFirst()));

        Optional<UserOutputDto> result = Optional.ofNullable(userService.getUser("Test01"));

        assertTrue(result.isPresent());

        assertFalse(mockUsers.isEmpty());

        assertEquals("Test01", result.get().getUsername());
        assertEquals("Test01@novi.nl", result.get().getEmail());

    }

    @Test
    @DisplayName("throwExceptionGetUser")
    void testGetUserThrowsException() {
        assertThrows(UsernameNotFoundException.class, () -> userService.getUser(null));
    }

    @Test
    @DisplayName("GetAllUsers")
    void testGetAllUsers() {

        when(userRepository.findAll()).thenReturn(mockUsers);

        List<UserOutputDto> result = userService.getAllUsers();

        assertEquals(2, result.size());
        assertEquals("Test01", result.get(0).getUsername());
        assertEquals("Test02", result.get(1).getUsername());
        assertEquals("Test01@novi.nl", result.get(0).getEmail());
        assertEquals("Test02@novi.nl", result.get(1).getEmail());

    }

    @Test
    @DisplayName("AddUser")
    void testAddUser() {

        when(userRepository.save(captor.capture())).thenReturn(mockUsers.getFirst());

        userService.addUser(userInputDto);

        verify(userRepository, times(1)).save(captor.capture());

        User captured = captor.getValue();

        captor.getValue().setPassword(passwordEncoder.encode(userInputDto.getPassword()));

        assertEquals(userInputDto.getPassword(), captured.getPassword());
        assertEquals(userInputDto.getEmail(), captured.getEmail());
        assertEquals(userInputDto.getUsername(), captured.getUsername());
        assertEquals(userInputDto.getRole(), captured.getRole());


    }

    @Test
    @DisplayName("DeleteUser")
    void testDeleteUser() {

        userService.deleteUser("Test01");

        verify(userRepository).deleteById("Test01");

    }

    @Test
    @DisplayName("UpdateUser")
    void testUpdateUser() {

        when(userRepository.existsById("Test01")).thenReturn(true);

        when(userRepository.findById("Test01")).thenReturn(Optional.of(mockUsers.getFirst()));

        userService.updateUser("Test01", userInputDto);

        verify(userRepository, times(1)).save(captor.capture());

        User captured = captor.getValue();

//        captured.setPassword(passwordEncoder.encode(userInputDto.getPassword()));

        assertEquals(userInputDto.getEmail(), captured.getEmail());
        assertEquals(userInputDto.getRole(), captured.getRole());
        assertEquals(userInputDto.getPassword(), captured.getPassword());

    }

    @Test
    @DisplayName("throwExceptionUpdateUser")
    void testUpdateUserThrowsException() {
        assertThrows(UsernameNotFoundException.class, () -> userService.updateUser(null, userInputDto));
    }

    @Test
    @DisplayName("GetUserByUsername")
    void testGetUserByUsername() {

        when(userRepository.findById("Test01")).thenReturn(Optional.of(mockUsers.getFirst()));

        assertEquals("Test01", mockUsers.getFirst().getUsername());
        assertEquals("Test01@novi.nl", mockUsers.getFirst().getEmail());
        assertEquals(MANAGER, mockUsers.getFirst().getRole());
        assertEquals("$2a$10$oh2jnssxP9pT/TcHPfhXb.au5/Fn1.7/AO.A1dRKA1jhunmuybd0.", mockUsers.getFirst().getPassword());

    }

}