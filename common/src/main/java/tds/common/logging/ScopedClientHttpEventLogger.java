package tds.common.logging;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpResponse;

import java.util.UUID;

import static tds.common.logging.EventLogger.Checkpoint.ENTER;
import static tds.common.logging.EventLogger.Checkpoint.EXIT;
import static tds.common.logging.EventLogger.EventData.HTTP_HEADERS;
import static tds.common.logging.EventLogger.EventData.HTTP_REQUEST_UUID;
import static tds.common.logging.EventLogger.EventData.RESPONSE_CODE;
import static tds.common.logging.EventLogger.EventData.RESPONSE_CODE_TEXT;

/**
 * Logs all http requests and responses formatted for logstash centralized logging.
 * <p>
 * Common http fields such as http session and request parameters are logged.
 */
public class ScopedClientHttpEventLogger extends EventLogger {

    public ScopedClientHttpEventLogger(final ObjectMapper objectMapper, UUID uuid) {
        super(objectMapper);
        putField(HTTP_REQUEST_UUID.name(), uuid);
    }

    public void logEnter(HttpRequest request) {
        putField(HTTP_HEADERS.name(), request.getHeaders());
        info(request.getURI().getPath(), request.getURI().getPath(), ENTER.name(), null, null);
    }

    public void logExit(HttpRequest request, ClientHttpResponse response) {
        try {
            putField(RESPONSE_CODE.name(), response.getStatusCode());
            putField(RESPONSE_CODE_TEXT.name(), response.getStatusText());
        } catch (Exception ignored) {
        }
        info(request.getURI().getPath(), request.getURI().getPath(), EXIT.name(), null, null);
    }
}
