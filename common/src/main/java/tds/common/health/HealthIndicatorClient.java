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
        restTemplate.setErrorHandler(new DefaultResponseErrorHandler() {
            @Override
            protected boolean hasError(HttpStatus statusCode) {
                // spring boot health endpoint returns a 503 http status code when health checks fail
                // do not throw an exception in this case
                return super.hasError(statusCode) && !statusCode.equals(HttpStatus.SERVICE_UNAVAILABLE);
            }
        });
        this.restTemplate = restTemplate;
    }

    /**
     * Health status of a dependency that exposes the "health" rest API from Spring Boot’s Actuator Project.
     *
     * @param url Base URL of service
     * @return Service's health
     * @see <a href="http://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/#production-ready">Spring Boot’s Actuator Project</a>
     */
    public Health health(final String url) {
        try {
            // parse url for existing url components
            final UriComponents components = UriComponentsBuilder.fromUriString(url).build();
            final UriComponentsBuilder uriBuilder;
            if (components.getHost() != null) {
                uriBuilder = UriComponentsBuilder.fromUriString(url);
            } else {
                // add default http protocol if missing
                uriBuilder = UriComponentsBuilder.fromHttpUrl(String.format("http://%s", url));
            }
            uriBuilder.pathSegment("health");

            final String restUrl = uriBuilder.build().toString();

            final tds.common.health.Health health = restTemplate.getForObject(restUrl, tds.common.health.Health.class);

            final Health.Builder builder = new Health.Builder(new Status(health.getStatus()));
            health.getDetails().forEach(builder::withDetail);
            return builder.build();
        } catch (IllegalArgumentException ex) {
            // parse url exception
            return Health.down(ex).withDetail("url", url).build();
        } catch (Exception ex) {
            return Health.down(ex).build();
        }
    }
}