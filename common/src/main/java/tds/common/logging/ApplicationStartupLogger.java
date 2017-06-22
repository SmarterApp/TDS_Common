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
            logger.trace(appId, APP_STARTUP.name(), null, null, null);
        }
    }
}
