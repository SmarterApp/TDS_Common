package tds.common.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.handler.MappedInterceptor;

import tds.common.web.interceptors.EventLoggerInterceptor;

@Configuration
@ComponentScan(basePackages = "tds.common.logging")
public class EventLoggerConfiguration {
  private final ApplicationContext applicationContext;
  private final ObjectMapper objectMapper;

  @Autowired
  public EventLoggerConfiguration(final ApplicationContext applicationContext,
                                  final ObjectMapper objectMapper) {
    this.applicationContext = applicationContext;
    this.objectMapper = objectMapper;
  }

  /**
   * Log incoming http requests formatted for logstash centralized logging
   *
   * @return http interceptor that delegates calls to {@link EventLoggerInterceptor}
   */
  @Bean
  public MappedInterceptor eventInterceptor()
  {
    return new MappedInterceptor(null, new EventLoggerInterceptor(applicationContext.getId(), objectMapper));
  }
}
