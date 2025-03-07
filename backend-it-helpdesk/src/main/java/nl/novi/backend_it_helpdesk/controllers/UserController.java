package nl.novi.backend_it_helpdesk.controllers;

import jakarta.validation.Valid;

import nl.novi.backend_it_helpdesk.dtos.TicketInputDto;
import nl.novi.backend_it_helpdesk.dtos.TicketOutputDto;
import nl.novi.backend_it_helpdesk.dtos.UserInputDto;
import nl.novi.backend_it_helpdesk.dtos.UserOutputDto;
import nl.novi.backend_it_helpdesk.repositories.UserRepository;
import nl.novi.backend_it_helpdesk.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final UserRepository userRepository;

    public UserController(UserService userService, UserRepository userRepository) {
        this.userService = userService;
        this.userRepository = userRepository;
    }

    @GetMapping("/{username}")
    public ResponseEntity<UserOutputDto> getUser(@PathVariable("username") String username) {

        UserOutputDto user = userService.getUser(username.toLowerCase());

        return ResponseEntity.ok().body(user);

    }

    @GetMapping
    public ResponseEntity<List<UserOutputDto>> getAllUsers() {

        List<UserOutputDto> dtos = userService.getAllUsers();

        return ResponseEntity.ok().body(dtos);

    }

    @PostMapping()
    public ResponseEntity<Object> addUser(@Valid @RequestBody UserInputDto user) {

        if (userRepository.existsById(user.getUsername())){
            return ResponseEntity.unprocessableEntity().body("Username already exists");
        }

        try {
            UserOutputDto dto = userService.addUser(user);

            URI uri = ServletUriComponentsBuilder
                    .fromCurrentRequest()
                    .path("/" + dto.getUsername().toLowerCase())
                    .buildAndExpand(dto.getUsername().toLowerCase()).toUri();

            return ResponseEntity.created(uri).body(dto);
        } catch (Exception e) {
            return ResponseEntity.unprocessableEntity().body(e.getMessage());
        }

    }

    @DeleteMapping("/{username}")
    public ResponseEntity<Object> deleteUser(@PathVariable String username) {

        userService.deleteUser(username.toLowerCase());

        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{username}")
    public ResponseEntity<Object> updateUser(@PathVariable String username, @Valid @RequestBody UserInputDto updateUser) {

        UserOutputDto outputDto = userService.updateUser(username, updateUser);

        return ResponseEntity.ok().body(outputDto);


    }




}
