package tds.common.health;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.boot.actuate.health.Health;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.springframework.boot.actuate.health.Status.DOWN;
import static org.springframework.boot.actuate.health.Status.UP;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.anything;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

public class HealthIndicatorTest {
    static RestTemplate restTemplate;
    static HealthIndicatorClient client;
    static MockRestServiceServer mockServer;

    static String successBody = statusBody(UP.getCode());
    static String failedBody = statusBody(DOWN.getCode());

    static String statusBody(String text) {
        return String.format("{\"status\" : \"%s\",  \"details\" : \"value\"}", text);
    }

    @BeforeClass
    public static void before() {
        restTemplate = new RestTemplate();
        client = new HealthIndicatorClient(restTemplate);
    }

    @Before
    public void setUp() {
        mockServer = MockRestServiceServer.createServer(restTemplate);
    }

    @Test
    public void runningServiceShouldReturnUpStatus() {
        mockServer.expect(anything()).andRespond(
            withSuccess(successBody, MediaType.APPLICATION_JSON));

        Health health = client.health("localhost");

        assertThat(health.getStatus(), is(UP));
    }

    @Test
    public void failedServiceShouldReturnFailedStatus() {
        mockServer.expect(anything()).andRespond(
            withSuccess(failedBody, MediaType.APPLICATION_JSON));

        Health health = client.health("localhost");

        assertThat(health.getStatus(), is(DOWN));
    }
}
