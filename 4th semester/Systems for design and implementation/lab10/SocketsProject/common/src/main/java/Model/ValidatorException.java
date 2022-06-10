package Model;

/**
 * A validator exception class. Throws the exception which the given message
 */
public class ValidatorException extends Exception {
    public ValidatorException(String message) {
        super(message);
    }

    public ValidatorException(String message, Throwable cause) {
        super(message, cause);
    }

    public ValidatorException(Throwable cause) {
        super(cause);
    }
}