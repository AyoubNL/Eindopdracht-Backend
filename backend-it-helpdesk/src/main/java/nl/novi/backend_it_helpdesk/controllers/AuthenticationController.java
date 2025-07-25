package nl.novi.backend_it_helpdesk.controllers;

import nl.novi.backend_it_helpdesk.payload.AuthenticationRequest;
import nl.novi.backend_it_helpdesk.payload.AuthenticationResponse;
import nl.novi.backend_it_helpdesk.services.CustomUserDetailService;
import nl.novi.backend_it_helpdesk.utils.JwtUtil;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@CrossOrigin
@RestController
public class AuthenticationController {

    private final AuthenticationManager authenticationManager;
    private final CustomUserDetailService userDetailService;
    private final JwtUtil jwtUtil;

    public AuthenticationController(AuthenticationManager authenticationManager, CustomUserDetailService userDetailService, JwtUtil jwtUtil) {
        this.authenticationManager = authenticationManager;
        this.userDetailService = userDetailService;
        this.jwtUtil = jwtUtil;
    }

    @GetMapping(value = "/authenticated")
    public ResponseEntity<Object> authenticated(Principal principal) {

        return ResponseEntity.ok().body(principal);

    }

    @PostMapping("/authenticate")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest) throws Exception {

        String username = authenticationRequest.getUsername();
        String password = authenticationRequest.getPassword();

        try{
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        }
        catch (BadCredentialsException ex){
            throw new Exception("Incorrect username or password", ex);
        }

        final UserDetails userDetails = userDetailService.loadUserByUsername(username);

        final String jwt = jwtUtil.generateToken(userDetails);

        new AuthenticationResponse(jwt);
        return ResponseEntity.ok().header(HttpHeaders.AUTHORIZATION, "Bearer " + jwt).body("jwt: " + jwt);
    }




}
