package tds.common.logging;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import static tds.common.logging.EventLogger.LogEvent.APP_STARTUP;

@Component
public class ApplicationStartupLogger implements ApplicationListener<ContextRefreshedEvent> {

    private final EventLogger logger;
    private final String appId;
    boolean logged = false;

    @Autowired
    public ApplicationStartupLogger(final ObjectMapper objectMapper,
                                    final ApplicationContext applicationContext) {
        logger = new EventLogger(objectMapper);
        appId = applicationContext.getId();
    }

    /**
     * We want to log an event to our centralized log service when the application server starts up.
     * This event is fired multiple times on startup, so we add logic to only log the first occurrence.
     */
    @Override
    public void onApplicationEvent(final ContextRefreshedEvent event) {
        if (!logged) {
            logged = true;
            logger.info(appId, APP_STARTUP.name(), null, null, null);
        }
    }
}