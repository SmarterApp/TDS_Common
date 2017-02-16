package tds.common.web.resources;

import tds.common.ValidationError;

/**
 * Contains errors (if any) when responding without data (e.g. when the response would be a 204 - No Content).
 */
public class NoContentResponseResource {
    private ValidationError[] errors;
    
    /**
     * Private constructor for frameworks
     */
    private NoContentResponseResource() {}

    /**
     * @param errors errors present when completing the request
     */
    public NoContentResponseResource(ValidationError... errors) {
        this.errors = errors;
    }

    /**
     * @return errors when completing the request.
     */
    public ValidationError[] getErrors() {
        return errors;
    }
}
