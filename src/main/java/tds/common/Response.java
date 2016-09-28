package tds.common;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;

/**
 * Contains data and errors if any when responding with the data.
 * @param <T> type for the data contained in the response
 */
public class Response<T> {
    private ValidationError[] errors;
    private T data;

    /**
     * @param data data requested
     * @param errors errors present when completing the request
     */
    public Response(T data, ValidationError... errors) {
        this.errors = errors;
        this.data = data;
    }

    /**
     * @param errors errors present when completing the request
     */
    public Response(ValidationError... errors) {
        this.errors = errors;
    }

    /**
     * @param data data requested
     */
    public Response(T data) {
        this.data = data;
    }

    /**
     * @return errors when completing the request.
     */
    public Optional<ValidationError[]> getErrors() {
        if (errors == null) {
            return Optional.empty();
        }
        return Optional.of(errors);
    }

    /**
     * @return optional potentially containing the data
     */
    public Optional<T> getData() {
        if (data == null) {
            return Optional.empty();
        }

        return Optional.of(data);
    }
}
