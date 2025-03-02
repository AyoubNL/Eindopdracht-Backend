package nl.novi.backend_it_helpdesk.exceptions;

public class UsernameNotFoundException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public UsernameNotFoundException(String username) {
        super("Username " + username + " not found");
    }
}
