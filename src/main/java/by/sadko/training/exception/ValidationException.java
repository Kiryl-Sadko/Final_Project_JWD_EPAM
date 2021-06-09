package by.sadko.training.exception;

public class ValidationException extends RuntimeException {

    private static final long serialVersionUID = 5038943272618354269L;

    public ValidationException(String message) {
        super(message);
    }

    public ValidationException(String message, Throwable cause) {
        super(message, cause);
    }

    public ValidationException(Throwable cause) {
        super(cause);
    }
}
