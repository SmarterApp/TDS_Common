/***************************************************************************************************
 * Copyright 2017 Regents of the University of California. Licensed under the Educational
 * Community License, Version 2.0 (the “license”); you may not use this file except in
 * compliance with the License. You may obtain a copy of the license at
 *
 * https://opensource.org/licenses/ECL-2.0
 *
 * Unless required under applicable law or agreed to in writing, software distributed under the
 * License is distributed in an “AS IS” BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for specific language governing permissions
 * and limitations under the license.
 **************************************************************************************************/

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

import tds.common.logging.EventLogger;

import static tds.common.logging.EventLogger.Checkpoint.SERVICE_CALL;
import static tds.common.logging.EventLogger.Checkpoint.SERVICE_RETURN;
import static tds.common.logging.EventLogger.EventData.HTTP_HEADERS;
import static tds.common.logging.EventLogger.EventData.RESPONSE_CODE;
import static tds.common.logging.EventLogger.EventData.RESPONSE_CODE_TEXT;
import static tds.common.logging.EventLogger.EventData.TRACE_ID;

/**
 * An interceptor for logging REST requests and responses
 */
public class RestTemplateLoggingInterceptor implements ClientHttpRequestInterceptor {
    private static final Logger log = LoggerFactory.getLogger(RestTemplateLoggingInterceptor.class);
    private static final String CONTENT_TYPE_SUBTYPE_JSON = "json";
    private final ObjectMapper objectMapper;
    private final String appId;

    public RestTemplateLoggingInterceptor(final ObjectMapper objectMapper, final String appId) {
        this.objectMapper = objectMapper;
        this.appId = appId;
    }

    @Override
    public ClientHttpResponse intercept(final HttpRequest request,
                                        final byte[] body,
                                        final ClientHttpRequestExecution clientHttpRequestExecution) throws IOException {
        EventLogger eventLogger = new EventLogger(objectMapper);
        final UUID traceId = UUID.randomUUID();
        logRequest(request, body, traceId, eventLogger);
        ClientHttpResponse response = clientHttpRequestExecution.execute(request, body);
        logResponse(request, response, traceId, eventLogger);
        return response;
    }

    private void logRequest(final HttpRequest request, final byte[] body, final UUID traceId, final EventLogger eventLogger) {
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

        eventLogger.putField(TRACE_ID.name(), traceId);
        eventLogger.putField(HTTP_HEADERS.name(), request.getHeaders());
        eventLogger.setBeginMillis(System.currentTimeMillis());
        eventLogger.trace(appId, request.getURI().getPath(), SERVICE_CALL.name(), null, null);
    }

    private void logResponse(final HttpRequest request, final ClientHttpResponse response, final UUID traceId, final EventLogger eventLogger) throws IOException {
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

        try {
            eventLogger.putField(RESPONSE_CODE.name(), response.getStatusCode().value());
            eventLogger.putField(RESPONSE_CODE_TEXT.name(), response.getStatusText());
        } catch (Exception ignored) {
        }
        eventLogger.setEndMillis(System.currentTimeMillis());
        eventLogger.trace(appId, request.getURI().getPath(), SERVICE_RETURN.name(), null, null);
    }
}
