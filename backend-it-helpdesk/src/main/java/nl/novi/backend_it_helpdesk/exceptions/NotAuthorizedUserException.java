package nl.novi.backend_it_helpdesk.exceptions;

public class NotAuthorizedUserException extends RuntimeException {

    public NotAuthorizedUserException() {}

    public NotAuthorizedUserException(String message) {
        super(message);
    }


}
