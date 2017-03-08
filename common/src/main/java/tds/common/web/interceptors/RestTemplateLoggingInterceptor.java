package tds.common.web.interceptors;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Charsets;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * An interceptor for logging REST requests and responses
 */
public class RestTemplateLoggingInterceptor implements ClientHttpRequestInterceptor {
    private static final Logger log = LoggerFactory.getLogger(RestTemplateLoggingInterceptor.class);
    private static final String CONTENT_TYPE_SUBTYPE_JSON = "json";
    private ObjectMapper objectMapper;

    public RestTemplateLoggingInterceptor(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public ClientHttpResponse intercept(final HttpRequest request, final byte[] body, final ClientHttpRequestExecution clientHttpRequestExecution) throws IOException {
        logRequest(request, body);
        ClientHttpResponse response = clientHttpRequestExecution.execute(request, body);
        logResponse(response);
        return response;
    }

    private void logRequest(final HttpRequest request, final byte[] body) {
        final HttpHeaders headers = request.getHeaders();
        String bodyString = new String(body, Charsets.UTF_8);

        if (!bodyString.isEmpty() && headers.getAccept().contains(MediaType.APPLICATION_JSON)) {
            try {
                final Object json = objectMapper.readValue(bodyString, Object.class);
                bodyString = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(json);
            } catch (IOException e) {
                log.debug("Unable to parse the request as JSON. Printing raw request body string...");
            }
        }

        log.debug("=========================================* REQUEST *==========================================");
        log.debug(" Method/URI  :   {} - {}", request.getMethod(), request.getURI());
        log.debug(" Headers     :   {} ", request.getHeaders());
        log.debug(" Body        :   {} ", bodyString);
        log.debug("==============================================================================================");
    }

    private void logResponse(final ClientHttpResponse response) throws IOException {
        StringBuilder inputStringBuilder = new StringBuilder();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(response.getBody(), Charsets.UTF_8));
        String line = bufferedReader.readLine();

        while (line != null) {
            inputStringBuilder.append(line);
            inputStringBuilder.append('\n');
            line = bufferedReader.readLine();
        }

        String bodyString = inputStringBuilder.toString();
        MediaType contentType = response.getHeaders().getContentType();

        if (!bodyString.isEmpty() && contentType.getSubtype().equals(CONTENT_TYPE_SUBTYPE_JSON)) {
            try {
                final Object json = objectMapper.readValue(bodyString, Object.class);
                bodyString = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(json);
            } catch (IOException e) {
                log.debug("Unable to parse the response as JSON. Printing raw response body string...");
            }
        }

        log.debug("=========================================* RESPONSE *=========================================");
        log.debug(" Status Code :   {}", response.getStatusCode());
        log.debug(" Status Text :   {}", response.getStatusText());
        log.debug(" Headers     :   {}", response.getHeaders());
        log.debug(" Body        :   {}", bodyString);
        log.debug("==============================================================================================");
    }
}
