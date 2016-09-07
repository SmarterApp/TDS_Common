package tds.common.web.exceptions;

/**
 * Represents when a resource cannot be found
 */
public class NotFoundException extends RuntimeException {

    /**
     * @param message message including formatting
     * @param args arguments to be used in the format processing of the message
     */
    public NotFoundException(String message, Object... args) {
        super(String.format(message, args));
    }

    /**
     * @param message message for the exception
     */
    public NotFoundException(String message) {
        super(message);
    }
}
