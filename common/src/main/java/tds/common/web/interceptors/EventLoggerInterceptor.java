package tds.common.web.interceptors;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import tds.common.logging.EventLogger;

import static tds.common.logging.EventLogger.Checkpoint.ENTER;
import static tds.common.logging.EventLogger.Checkpoint.EXIT;
import static tds.common.logging.EventLogger.EventData.HTTP_REQUEST_PARAMETERS;
import static tds.common.logging.EventLogger.EventData.HTTP_SESSION_ID;
import static tds.common.logging.EventLogger.EventData.RESPONSE_CODE;

/**
 * Logs all http requests and responses formatted for logstash centralized logging.
 * <p>
 * Common http fields such as http session and request parameters are logged.
 */
public class EventLoggerInterceptor extends HandlerInterceptorAdapter {

    private final String app;
    private final ObjectMapper objectMapper;
    private EventLogger eventLogger;

    public EventLoggerInterceptor(final String app, final ObjectMapper objectMapper) {
        this.app = app;
        this.objectMapper = objectMapper;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }
        eventLogger = new EventLogger(objectMapper);
        final HttpSession session = request.getSession(false);
        if (null != session) {
            eventLogger.putField(HTTP_SESSION_ID.name(), session.getId());
        }
        eventLogger.putField(HTTP_REQUEST_PARAMETERS.name(), request.getParameterMap());
        eventLogger.setBeginMillis(System.currentTimeMillis());
        eventLogger.info(app, request.getRequestURI(), ENTER.name(), null, null);
        return true;
    }

    @Override
    public void postHandle(
        HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        eventLogger.putField(RESPONSE_CODE.name(), response.getStatus());
        eventLogger.setEndMillis(System.currentTimeMillis());
        eventLogger.info(app, request.getRequestURI(), EXIT.name(), null, null);
    }
}