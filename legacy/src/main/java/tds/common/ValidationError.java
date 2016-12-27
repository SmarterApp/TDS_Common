package tds.common;

import static tds.common.util.Preconditions.checkNotNull;

/**
 * Generic Error object
 */
public class ValidationError {
    private final String code;
    private final String message;

    /**
     * @param code    error code for the error type
     * @param message error message to describe why error occurred
     */
    public ValidationError(String code, String message) {
        this.code = checkNotNull(code);
        this.message = message;
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
}
