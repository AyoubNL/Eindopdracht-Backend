package nl.novi.backend_it_helpdesk.services;

import nl.novi.backend_it_helpdesk.dtos.UserInputDto;
import nl.novi.backend_it_helpdesk.dtos.UserOutputDto;
import nl.novi.backend_it_helpdesk.exceptions.NotAuthorizedUserException;
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
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
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
    UserInputDto userInputDtoNull;

    @BeforeEach
    void setUp() {

        Authentication authentication = new UsernamePasswordAuthenticationToken(
                "Test_Manager",
                "geheim",
                List.of(new SimpleGrantedAuthority("manager"))
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        mockUsers = Arrays.asList(new User("Test_Manager", "$2a$10$oh2jnssxP9pT/TcHPfhXb.au5/Fn1.7/AO.A1dRKA1jhunmuybd0.", MANAGER, "Test01@novi.nl", Set.of(new Authority("Test01", MANAGER)), List.of(new Ticket())),
                new User("Test_Agent", "$2a$10$.k.Ug5Pf7CGRf/QIw5zuy.BYCH17d5R.IlxHxS1r5SeZXgD6ptKRW", AGENT, "Test02@novi.nl", Set.of(new Authority("Test02", AGENT)), List.of(new Ticket()))
        );

        userInputDto = new UserInputDto("Test_Manager", "$2a$10$wMMChXMeYRqPSwSP/4Nns.CrFArfWhaBfswig.ljtEjbSvnd45gn6", CLIENT, "Test03@novi.nl");


        userInputDtoNull = new UserInputDto(null, null, null, null);
    }

    @Test
    @DisplayName("GetUser")
    void testGetUser() {

        when(userRepository.findById("Test_Manager")).thenReturn(Optional.of(mockUsers.getFirst()));

        Optional<UserOutputDto> result = Optional.ofNullable(userService.getUser("Test_Manager"));

        assertTrue(result.isPresent());

        assertFalse(mockUsers.isEmpty());

        assertEquals("Test_Manager", result.get().getUsername());
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
        assertEquals("Test_Manager", result.get(0).getUsername());
        assertEquals("Test_Agent", result.get(1).getUsername());
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

        when(userRepository.existsById("Test_Manager")).thenReturn(true);

        userService.deleteUser("Test_Manager");

        verify(userRepository).deleteById("Test_Manager");

    }

    @Test
    @DisplayName("throwExceptionDeleteUser")
    void testDeleteUserThrowsException() {
        assertThrows(EmptyResultDataAccessException.class, () -> userService.deleteUser("Test_X"));
    }

    @Test
    @DisplayName("UpdateUser")
    void testUpdateUser() {

        when(userRepository.existsById("Test_Manager")).thenReturn(true);

        when(userRepository.findById("Test_Manager")).thenReturn(Optional.of(mockUsers.getFirst()));

        userService.updateUser("Test_Manager", userInputDto);

        verify(userRepository, times(1)).save(captor.capture());

        User captured = captor.getValue();

        assertEquals(userInputDto.getEmail(), captured.getEmail());
        assertEquals(userInputDto.getRole(), captured.getRole());
        assertEquals(userInputDto.getPassword(), captured.getPassword());

    }

    @Test
    @DisplayName("UpdateUserAuthThrowsException")
    void testUpdateUserAuthThrowsException() {

        Authentication authentication = new UsernamePasswordAuthenticationToken(
                "Test_Agent",
                "geheim",
                List.of(new SimpleGrantedAuthority("agent"))
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        when(userRepository.existsById("Test_Manager")).thenReturn(true);

        when(userRepository.findById("Test_Manager")).thenReturn(Optional.of(mockUsers.getFirst()));

        assertThrows(NotAuthorizedUserException.class, () -> userService.updateUser("Test_Manager", userInputDto));

    }

    @Test
    @DisplayName("UpdateUserNull")
    void testUpdateUserNull() {

        when(userRepository.existsById("Test_Manager")).thenReturn(true);

        when(userRepository.findById("Test_Manager")).thenReturn(Optional.of(mockUsers.getFirst()));

        userService.updateUser("Test_Manager", userInputDtoNull);

        verify(userRepository, times(1)).save(captor.capture());

        assertNull(userInputDtoNull.getEmail());
        assertNull(userInputDtoNull.getRole());
        assertNull(userInputDtoNull.getUsername());
        assertNull(userInputDtoNull.getPassword());

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

     User us = userService.getUserByUsername("Test01");

        assertEquals("$2a$10$oh2jnssxP9pT/TcHPfhXb.au5/Fn1.7/AO.A1dRKA1jhunmuybd0.", us.getPassword());

    }

}