package tds.common;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ResponseTest {
    @Test
    public void shouldNotHaveErrors() {
        Response<String> response = new Response<>("test");
        assertThat(response.hasErrors()).isFalse();
        assertThat(response.getErrors()).isEmpty();
        assertThat(response.getData().get()).isEqualTo("test");
    }

    @Test
    public void shouldHaveErrors() {
        ValidationError error = new ValidationError("error", "testing error");
        Response<String> response = new Response<String>(error);
        assertThat(response.hasErrors()).isTrue();
        assertThat(response.getErrors()).containsExactly(error);
        assertThat(response.getData().isPresent()).isFalse();
    }
}