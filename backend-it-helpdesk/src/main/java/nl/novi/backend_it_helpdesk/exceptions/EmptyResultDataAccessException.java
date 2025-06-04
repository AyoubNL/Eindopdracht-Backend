package nl.novi.backend_it_helpdesk.exceptions;

public class EmptyResultDataAccessException extends RuntimeException {
    public EmptyResultDataAccessException() {
        super();
    }

    public EmptyResultDataAccessException(String message) {
        super(message);
    }
}
