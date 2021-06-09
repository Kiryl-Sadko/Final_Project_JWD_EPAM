package by.sadko.training.exception;

public class DAOException extends Exception {

    private static final long serialVersionUID = 6004987541731278341L;

    public DAOException() {
        super();
    }

    public DAOException(String message) {
        super(message);
    }

    public DAOException(String message, Throwable cause) {
        super(message, cause);
    }
}
