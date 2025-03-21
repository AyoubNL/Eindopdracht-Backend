package nl.novi.backend_it_helpdesk.services;

import jakarta.validation.Valid;
import nl.novi.backend_it_helpdesk.dtos.UserInputDto;
import nl.novi.backend_it_helpdesk.dtos.UserOutputDto;
import nl.novi.backend_it_helpdesk.enums.UserRoleEnum;
import nl.novi.backend_it_helpdesk.exceptions.UsernameNotFoundException;
import nl.novi.backend_it_helpdesk.mappers.UserMapper;
import nl.novi.backend_it_helpdesk.models.Authority;
import nl.novi.backend_it_helpdesk.models.User;
import nl.novi.backend_it_helpdesk.repositories.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public UserOutputDto getUser(String username) {
        var dto = new UserOutputDto();

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

        dto.setPassword(passwordEncoder.encode(dto.getPassword()));
        User ur = UserMapper.transferToUser(dto);
        ur.addAuthority(new Authority(dto.getUsername(), dto.getRole()));

        userRepository.save(ur);

        return UserMapper.transferToDto(ur);

    }

    public void deleteUser(String username) {

        userRepository.deleteById(username);
    }

    public UserOutputDto updateUser(String username, @Valid UserInputDto updateUser) {

        if (userRepository.existsById(username)) {

            User us = userRepository.findById(username).get();

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

            if (us1.getAuthorities() == null) {
                us1.setAuthorities(us.getAuthorities());
            }


            userRepository.save(us1);

            return UserMapper.transferToDto(us1);

        } else {
            throw new UsernameNotFoundException(username);
        }

    }

    public User getUserByUsername(String username) {

        var user0 = new User();
        Optional<User> user1 = userRepository.findById(username);

        if (user1.isPresent()) {
            user0.setPassword(user1.get().getPassword());
            user0.addAuthority(new Authority(user1.get().getUsername(), user1.get().getRole()));

        }
        return user0;

    }


}
