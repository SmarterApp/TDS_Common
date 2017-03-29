package tds.common.web.interceptors;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Charsets;
import com.google.common.io.CharStreams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpRequest;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.UUID;

import tds.common.logging.ScopedClientHttpEventLogger;

/**
 * An interceptor for logging REST requests and responses
 */
public class RestTemplateLoggingInterceptor implements ClientHttpRequestInterceptor {
    private static final Logger log = LoggerFactory.getLogger(RestTemplateLoggingInterceptor.class);
    private static final String CONTENT_TYPE_SUBTYPE_JSON = "json";
    private final ObjectMapper objectMapper;

    public RestTemplateLoggingInterceptor(final ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public ClientHttpResponse intercept(final HttpRequest request,
                                        final byte[] body,
                                        final ClientHttpRequestExecution clientHttpRequestExecution) throws IOException {
        final UUID traceId = UUID.randomUUID();
        ScopedClientHttpEventLogger eventLogger = new ScopedClientHttpEventLogger(objectMapper, traceId);
        logRequest(request, body, traceId);
        eventLogger.logEnter(request);
        ClientHttpResponse response = clientHttpRequestExecution.execute(request, body);
        eventLogger.logExit(request, response);
        logResponse(response, traceId);
        return response;
    }

    private void logRequest(final HttpRequest request, final byte[] body, final UUID traceId) {
        String bodyString = new String(body, Charsets.UTF_8);

        final MediaType contentType = request.getHeaders().getContentType();

        if (!bodyString.isEmpty() && contentType.getSubtype().equals(CONTENT_TYPE_SUBTYPE_JSON)) {
            try {
                final Object json = objectMapper.readValue(bodyString, Object.class);
                bodyString = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(json);
            } catch (IOException e) {
                log.debug("Unable to parse the request as JSON. Printing raw request body string...", e);
            }
        }

        log.debug("\n=========================================* REQUEST *========================================== \n" +
                " Trace ID    :   {} \n" +
                " Method/URI  :   {} - {} \n" +
                " Headers     :   {} \n" +
                " Body        :   {} \n" +
                "==============================================================================================",
            traceId,
            request.getMethod(), request.getURI(),
            request.getHeaders(),
            bodyString.isEmpty() ? "<no body>" : bodyString);
    }

    private void logResponse(final ClientHttpResponse response, final UUID traceId) throws IOException {
        String bodyString = "";

        try (final InputStream in = response.getBody()) {
            bodyString = CharStreams.toString(new InputStreamReader(in, Charsets.UTF_8));
        } catch (IOException e) {
            // An IOException will be thrown when the body is zero-length (e.g. a 404 response)
            log.debug("Unable to open response body", e);
        }

        final MediaType contentType = response.getHeaders().getContentType();

        if (!bodyString.isEmpty() && contentType.getSubtype().equals(CONTENT_TYPE_SUBTYPE_JSON)) {
            try {
                final Object json = objectMapper.readValue(bodyString, Object.class);
                bodyString = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(json);
            } catch (IOException e) {
                log.debug("Unable to parse the response as JSON.", e);
            }
        }

        log.debug("\n=========================================* RESPONSE *========================================= \n" +
                " Trace ID    :   {} \n" +
                " Status Code :   {} \n" +
                " Status Text :   {} \n" +
                " Headers     :   {} \n" +
                " Body        :   {} \n" +
                "==============================================================================================",
            traceId,
            response.getStatusCode(),
            response.getStatusText(),
            response.getHeaders(),
            bodyString.isEmpty() ? "<no body>" : bodyString);
    }
}
