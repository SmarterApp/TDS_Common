package tds.common;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ResponseTest {
    @Test
    public void shouldNotHaveErrors() {
        Response<String> response = new Response<>("test");
        assertThat(response.hasError()).isFalse();
        assertThat(response.getError().isPresent()).isFalse();
        assertThat(response.getData().get()).isEqualTo("test");
    }

    @Test
    public void shouldHaveErrors() {
        ValidationError error = new ValidationError("error", "testing error");
        Response<String> response = new Response<String>(error);
        assertThat(response.hasError()).isTrue();
        assertThat(response.getError().get()).isEqualTo(error);
        assertThat(response.getData().isPresent()).isFalse();
    }
}