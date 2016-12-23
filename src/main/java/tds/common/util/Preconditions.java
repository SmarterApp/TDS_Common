package tds.common.util;

/**
 * Inspired by Google Guava's preconditions class
 */
public class Preconditions {
    /**
     * Convenience method to check not null
     *
     * @param reference the object to check for null
     * @param <T>       type for the reference
     * @return the object if not null
     * @throws java.lang.IllegalArgumentException if null
     */
    public static <T> T checkNotNull(T reference) {
        if (reference == null) {
            throw new IllegalArgumentException();
        }
        return reference;
    }

    /**
     * Convenience method to check not null
     *
     * @param reference    the object to check for null
     * @param <T>          type for the reference
     * @param errorMessage the message to include in the exception
     * @return the object if not null
     * @throws java.lang.IllegalArgumentException if null
     */
    public static <T> T checkNotNull(T reference, Object errorMessage) {
        if (reference == null) {
            throw new NullPointerException(String.valueOf(errorMessage));
        }
        return reference;
    }
}
