package tds.common.web.utils;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Utility class for handling URL formatting
 */
public class UrlUtils {
    /**
     * If there is a trailing slash at the end of a supplied URL, remove it.
     *
     * @param url The URL being trimmed
     * @return The url without a trailing slash
     */
    public static String removeTrailingSlash(String url) {
        return checkNotNull(url, "url cannot be null").endsWith("/")
            ? url.substring(0, url.length() - 1)
            : url;
    }
}
