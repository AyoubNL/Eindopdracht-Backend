package nl.novi.backend_it_helpdesk.exceptions;

public class UsernameNotFoundException extends RuntimeException {

    public UsernameNotFoundException(String username) {
        super("Username " + username + " not found");
    }
}
