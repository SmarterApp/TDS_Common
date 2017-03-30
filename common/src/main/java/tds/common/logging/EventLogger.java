package tds.common.logging;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static java.lang.String.format;
import static java.util.Collections.unmodifiableMap;

/**
 * Centralized event logger.
 */
public class EventLogger {
    public enum Checkpoint {
        ENTER,
        EXIT,
        SERVICE_CALL,
        SERVICE_RETURN,
    }

    public enum EventData {
        APP,
        ELAPSED_TIME,
        CHECKPOINT,
        HTTP_HEADERS,
        HTTP_REQUEST_PARAMETERS,
        HTTP_REQUEST_UUID,
        HTTP_SESSION_ID,
        RESPONSE_CODE,
        RESPONSE_CODE_TEXT,
        RESULT,
        SUB_EVENT,
        UNKNOWN,
    }

    private static final Logger logger = LoggerFactory.getLogger(EventLogger.class);
    private static final Marker marker = MarkerFactory.getMarker("event-logger");
    private final ObjectWriter writer;
    private final Map<String, Long> eventTimes = new HashMap<>();
    private final Map<String, Object> extraFields = new HashMap<>();

    public EventLogger(final ObjectMapper objectMapper) {
        this.writer = objectMapper.writer();
        extraFields.put(EventData.HTTP_REQUEST_UUID.name(), UUID.randomUUID());
    }

    /**
     * Add a supplemental piece of info that will be logged on subsequent log calls.
     */
    public void putField(final String key, final Object value) {
        extraFields.put(key, value);
    }

    /**
     * Get a supplemental piece of info that was placed into the logger.
     */
    public Object getField(final String key) {
        return extraFields.get(key);
    }

    /**
     * Log event occurrences to centralized logging.
     * <p>
     * These events are intended to be sent to a logstash server and searchable using elastic search.
     *
     * @param app        name of the app writing the log
     * @param logEvent   name of the event occurring
     * @param checkpoint an optional checkpoint name
     * @param message    an optional free-format message string to add to the log entry
     * @param data       optional searchable event data to add to the recorded event
     */
    public void info(final String app, final String logEvent, final String checkpoint,
                     final String message, final Map<EventData, Object> data) {
        log(formatMessage(logEvent, message), getFieldMap(app, logEvent, checkpoint, data));
    }

    /**
     * Log event errors to centralized logging.
     * <p>
     * These events are intended to be sent to a logstash server and searchable using elastic search.
     *
     * @param app        name of the app writing the log
     * @param logEvent   name of the event occurring
     * @param checkpoint an optional checkpoint name
     * @param message    an optional free-format message string to add to the log entry
     * @param data       optional searchable event data to add to the recorded event
     */
    public void error(final String app, final String logEvent, final String checkpoint,
                      String message, final Map<EventData, Object> data, final Exception e) {
        error(formatMessage(logEvent, message), getFieldMap(app, logEvent, checkpoint, data), e);
    }

    private void log(final String event, final Map<String, Object> fields) {
        try {
            logger.info(marker, format("EVENT:%s JSON:({\"event_data\": %s})", event, writer.writeValueAsString(fields)));
        } catch (Exception e) {
            logger.warn(marker, format("exception occurred while logging event: %s", event), e);
        }
    }

    private void error(final String event, final Map<String, Object> fields, final Exception e) {
        try {
            logger.error(marker, format("EVENT:%s JSON:({\"event_data\": %s})", event, writer.writeValueAsString(fields)),
                e);
        } catch (Exception inner) {
            logger.error(marker, format("exception occurred while error event: %s", event), inner);
        }
    }

    private void addTimerMetrics(final String event, final String checkpoint, Map<EventData, Object> fields) {
        Long eventTime = eventTimes.get(event);
        if (Checkpoint.ENTER.name().equals(checkpoint)) {
            // entry point - (re)set eventTime and emit nothing
            eventTimes.put(event, System.currentTimeMillis());
        } else {
            if (Checkpoint.EXIT.name().equals(checkpoint)) {
                // exit point - remove timestamp. falls through to default to emit metric.
                eventTimes.remove(event);
            }
            // non entry/exit or missing position marker - emit elapsed metric
            if (null != eventTime) {
                fields.put(EventData.ELAPSED_TIME, System.currentTimeMillis() - eventTime);
            }
        }
    }

    private Map<String, Object> getFieldMap(final String app, final String event, String checkpoint,
                                            final Map<EventData, Object> data) {
        Map<EventData, Object> fields = new HashMap<>();
        if (!StringUtils.isEmpty(app)) {
            fields.put(EventData.APP, app);
        }
        if (!StringUtils.isEmpty(checkpoint)) {
            fields.put(EventData.CHECKPOINT, checkpoint.toLowerCase());
        }
        if (null != data) {
            fields.putAll(data);
        }
        addTimerMetrics(event, checkpoint, fields);

        // Write all data into outputMap, lowercasing key names.
        Map<String, Object> outputMap = new HashMap<>();
        for (Map.Entry<EventData, Object> entry : fields.entrySet()) {
            outputMap.put(entry.getKey().name().toLowerCase(), entry.getValue());
        }
        for (Map.Entry<String, Object> entry : extraFields.entrySet()) {
            outputMap.put(entry.getKey().toLowerCase(), entry.getValue());
        }
        return unmodifiableMap(outputMap);
    }

    private static String formatMessage(final String logEvent, final String message) {
        String result = "";
        if (!StringUtils.isEmpty(logEvent)) {
            result = logEvent.toLowerCase();
        }
        if (!StringUtils.isEmpty(message)) {
            result = result + " - " + message;
        }
        return result;
    }
}
