package tds.common.health;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.Status;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.DefaultResponseErrorHandler;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

/**
 * Rest client to Spring Boot’s Actuator Project health check endpoint.
 *
 * @see <a href="http://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/#production-ready">Spring Boot’s Actuator Project</a>
 */
public class HealthIndicatorClient {
    private final RestTemplate restTemplate;

    @Autowired
    public HealthIndicatorClient(final RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
        restTemplate.setErrorHandler(new DefaultResponseErrorHandler() {
            @Override
            protected boolean hasError(HttpStatus statusCode) {
                return statusCode.series() == HttpStatus.Series.CLIENT_ERROR;
            }
        });
    }

    /**
     * Health status of a dependency that exposes the "health" rest API from Spring Boot’s Actuator Project.
     *
     * @see <a href="http://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/#production-ready">Spring Boot’s Actuator Project</a>
     *
     * @param url Base URL of service
     * @return Service's health
     */
    public Health health(final String url) {
        try {
            // parse url for existing url components
            final UriComponents components = UriComponentsBuilder.fromUriString(url).build();
            final UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromUriString(url)
                    .pathSegment("health");
            if (components.getHost() == null) {
                uriBuilder.scheme("http");
            }
            final String restUrl = uriBuilder.build().toString();

            final tds.common.health.Health health = restTemplate.getForObject(restUrl, tds.common.health.Health.class);

            final Health.Builder builder = new Health.Builder(new Status(health.status()));
            health.details().forEach(builder::withDetail);
            return builder.build();
        } catch (Exception ex) {
            return Health.down(ex).build();
        }
    }
}