package tds.common.logging;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.stereotype.Component;

import static tds.common.logging.EventLogger.LogEvent.APP_SHUTDOWN;

@Component
public class ApplicationShutdownLogger implements ApplicationListener<ContextClosedEvent> {

    private final EventLogger logger;
    private final String appId;
    boolean logged = false;

    @Autowired
    public ApplicationShutdownLogger(final ObjectMapper objectMapper,
                                     final ApplicationContext applicationContext) {
        logger = new EventLogger(objectMapper);
        appId = applicationContext.getId();
    }

    /**
     * We want to log an event to our centralized log service when the application server shuts down.
     * This event is fired multiple times on shutdown, so we add logic to only log the first occurrence.
     */
    @Override
    public void onApplicationEvent(final ContextClosedEvent event) {
        if (!logged) {
            logged = true;
            logger.trace(appId, APP_SHUTDOWN.name(), null, null, null);
        }
    }
}
