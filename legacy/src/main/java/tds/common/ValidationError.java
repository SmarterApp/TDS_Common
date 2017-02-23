package tds.common;

import com.google.common.base.Optional;

import static tds.common.util.Preconditions.checkNotNull;

/**
 * Generic Error object
 */
public class ValidationError {
    private String code;
    private String message;
    private String translatedMessage;

    /**
     * @param code    error code for the error type
     * @param message error message to describe why error occurred
     */
    public ValidationError(String code, String message) {
        this(code, message, null);
    }

    /**
     * @param code              error code for the error type
     * @param message           error message to describe why error occurred
     * @param translatedMessage the translated error message
     */
    public ValidationError(String code, String message, String translatedMessage) {
        this.code = checkNotNull(code);
        this.message = checkNotNull(message);
        this.translatedMessage = translatedMessage;
    }

    /**
     * Private constructor for frameworks
     */
    private ValidationError() {
    }

    /**
     * @return error code
     */
    public String getCode() {
        return code;
    }

    /**
     * @return description why error occurred
     */
    public String getMessage() {
        return message;
    }

    /**
     * @return optional containing the
     */
    public Optional<String> getTranslatedMessage() {
        return Optional.fromNullable(translatedMessage);
    }
}
