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
  private static final String INCLUDE_ALL_PATTERN = "/**";
  private static final String EXCLUDE_HEALTH_PATTERN = "/**/health*";

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
    return new MappedInterceptor(new String[] {INCLUDE_ALL_PATTERN}, new String[] {EXCLUDE_HEALTH_PATTERN}, new EventLoggerInterceptor(applicationContext.getId(), objectMapper));
  }
}
