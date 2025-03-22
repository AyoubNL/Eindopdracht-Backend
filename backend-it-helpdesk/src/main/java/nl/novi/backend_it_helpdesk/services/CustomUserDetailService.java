package nl.novi.backend_it_helpdesk.services;

import nl.novi.backend_it_helpdesk.models.Authority;
import nl.novi.backend_it_helpdesk.models.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CustomUserDetailService implements UserDetailsService {

    private final UserService userService;

    public CustomUserDetailService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String username){

        User us = userService.getUserByUsername(username);
        String password = us.getPassword();
        Set<Authority> authorities = us.getAuthorities();

        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();

        for(Authority authority : authorities){
            grantedAuthorities.add(new SimpleGrantedAuthority(authority.getRole().getUserRoleEnum()));
        }

        return new org.springframework.security.core.userdetails.User(username, password, grantedAuthorities);

    }
}
