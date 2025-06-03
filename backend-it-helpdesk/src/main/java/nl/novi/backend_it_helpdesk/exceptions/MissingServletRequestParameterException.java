package nl.novi.backend_it_helpdesk.exceptions;

public class MissingServletRequestParameterException extends RuntimeException {

    public MissingServletRequestParameterException() {
    }

    public MissingServletRequestParameterException(String message) {
        super(message);
    }


}
