package nl.novi.backend_it_helpdesk.services;

import jakarta.validation.Valid;
import nl.novi.backend_it_helpdesk.dtos.UserInputDto;
import nl.novi.backend_it_helpdesk.dtos.UserOutputDto;
import nl.novi.backend_it_helpdesk.exceptions.UsernameNotFoundException;
import nl.novi.backend_it_helpdesk.mappers.UserMapper;
import nl.novi.backend_it_helpdesk.models.User;
import nl.novi.backend_it_helpdesk.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserOutputDto getUser(String username) {

        UserOutputDto dto = new UserOutputDto();

        Optional<User> user = userRepository.findById(username);

        if (user.isPresent()) {
            dto = UserMapper.transferToDto(user.get());
        } else {
            throw new UsernameNotFoundException(username);
        }
        return dto;


    }

    public List<UserOutputDto> getAllUsers() {

        List<User> userList = userRepository.findAll();

        return UserMapper.transferUserListToDtoList(userList);

    }

    public UserOutputDto addUser(@Valid UserInputDto dto) {

        User ur = UserMapper.transferToUser(dto);

        userRepository.save(ur);

        return UserMapper.transferToDto(ur);

    }

    public void deleteUser(String username) {

        userRepository.deleteById(username.toLowerCase());
    }

    public UserOutputDto updateUser(String username, @Valid UserInputDto updateUser) {

        if (userRepository.existsById(username)) {

            User us = userRepository.findById(username.toLowerCase()).get();

            User us1 = UserMapper.transferToUser(updateUser);

            us1.setUsername(us.getUsername());

            if (us1.getEmail() == null) {
                us1.setEmail(us.getEmail());
            }

            if (us1.getPassword() == null) {
                us1.setPassword(us.getPassword());
            }

            if (us1.getRole() == null) {
                us1.setRole(us.getRole());
            }

            userRepository.save(us1);

            return UserMapper.transferToDto(us1);

        }
        else{
            throw new UsernameNotFoundException(username);
        }


    }












}
