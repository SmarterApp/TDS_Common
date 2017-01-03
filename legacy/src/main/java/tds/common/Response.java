package tds.common;


import com.google.common.base.Optional;

import static tds.common.util.Preconditions.checkNotNull;

/**
 * Contains data and errors if any when responding with the data.
 *
 * @param <T> type for the data contained in the response
 */
public class Response<T> {
    private ValidationError[] errors;
    private T data;

    /**
     * @param data   data requested
     * @param errors errors present when completing the request
     */
    public Response(T data, ValidationError... errors) {
        this.errors = checkNotNull(errors);
        this.data = checkNotNull(data);
    }

    /**
     * @param errors errors present when completing the request
     */
    public Response(ValidationError... errors) {
        this.errors = checkNotNull(errors);
    }

    /**
     * @param data data requested
     */
    public Response(T data) {
        this.data = checkNotNull(data);
    }

    /**
     * @return errors when completing the request.
     */
    public ValidationError[] getErrors() {
        if (errors == null) {
            return new ValidationError[]{};
        }
        return errors;
    }

    /**
     * @return True if the {@link tds.common.ValidationError}s array contains any elements; otherwise false
     */
    public boolean hasErrors() {
        return errors.length > 0;
    }

    /**
     * @return optional potentially containing the data
     */
    public Optional<T> getData() {
        if (data == null) {
            return Optional.absent();
        }

        return Optional.of(data);
    }
}
