package tds.common.data;

/**
 * Database Exception thrown when failing to create a record.  Since this is normally
 * an unrecoverable situation in the application it extends a runtime exception.
 */
public class CreateRecordException extends RuntimeException {

    /**
     * @param message the message to have in the Exception
     */
    public CreateRecordException(String message) {
        super(message);
    }

    /**
     * Constructor that takes the message and an inserts the
     * arguments into the message
     *
     * @param message message
     * @param args    arguments used when formatting the message
     */
    public CreateRecordException(String message, Object... args) {
        super(String.format(message, args));
    }
}
