package tds.common.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.http.client.BufferingClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import tds.common.web.interceptors.RestTemplateEventLoggingInterceptor;
import tds.common.web.interceptors.RestTemplateLoggingInterceptor;

@Configuration
@Import(JacksonObjectMapperConfiguration.class)
public class RestTemplateConfiguration {

    @Autowired
    private ApplicationContext applicationContext;

    @Bean
    @ConditionalOnMissingBean(RestTemplate.class)
    public RestTemplate restTemplate(final RestTemplateBuilder builder, final ObjectMapper objectMapper) {
        return builder
            .requestFactory(new BufferingClientHttpRequestFactory(new SimpleClientHttpRequestFactory()))
            .additionalInterceptors(
                new RestTemplateEventLoggingInterceptor(objectMapper, applicationContext.getId()),
                new RestTemplateLoggingInterceptor(objectMapper))
            .build();
    }
}
