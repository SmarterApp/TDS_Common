package tds.common.web.interceptors;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

import java.io.IOException;

import tds.common.logging.EventLogger;

import static tds.common.logging.EventLogger.Checkpoint.SERVICE_CALL;
import static tds.common.logging.EventLogger.Checkpoint.SERVICE_RETURN;
import static tds.common.logging.EventLogger.EventData.HTTP_HEADERS;
import static tds.common.logging.EventLogger.EventData.RESPONSE_CODE;
import static tds.common.logging.EventLogger.EventData.RESPONSE_CODE_TEXT;

/**
 * An interceptor for logging REST requests and responses
 */
public class RestTemplateEventLoggingInterceptor implements ClientHttpRequestInterceptor {

    private ObjectMapper objectMapper;
    private String appId;

    public RestTemplateEventLoggingInterceptor(final ObjectMapper objectMapper, String appId) {
        this.objectMapper = objectMapper;
        this.appId = appId;
    }

    @Override
    public ClientHttpResponse intercept(final HttpRequest request,
                                        final byte[] body,
                                        final ClientHttpRequestExecution clientHttpRequestExecution) throws IOException {
        EventLogger eventLogger = new EventLogger(objectMapper);
        logRequest(eventLogger, request);
        ClientHttpResponse response = clientHttpRequestExecution.execute(request, body);
        logResponse(eventLogger, request, response);
        return response;
    }

    private void logRequest(EventLogger eventLogger, HttpRequest request) {
        eventLogger.putField(HTTP_HEADERS.name(), request.getHeaders());
        eventLogger.setBeginMillis(System.currentTimeMillis());
        eventLogger.info(appId, request.getURI().getPath(), SERVICE_CALL.name(), null, null);
    }

    private void logResponse(EventLogger eventLogger, HttpRequest request, ClientHttpResponse response) {
        try {
            eventLogger.putField(RESPONSE_CODE.name(), response.getStatusCode());
            eventLogger.putField(RESPONSE_CODE_TEXT.name(), response.getStatusText());
        } catch (Exception ignored) {
        }
        eventLogger.setEndMillis(System.currentTimeMillis());
        eventLogger.info(appId, request.getURI().getPath(), SERVICE_RETURN.name(), null, null);
    }
}
