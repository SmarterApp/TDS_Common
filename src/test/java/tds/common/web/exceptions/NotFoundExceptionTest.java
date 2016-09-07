package tds.common.web.exceptions;

import org.junit.Test;
import tds.common.web.exceptions.NotFoundException;

import static org.assertj.core.api.Assertions.*;

public class NotFoundExceptionTest {
    @Test
    public void shouldBeAbleToSetMessage() {
        NotFoundException exception = new NotFoundException("Test Message");
        assertThat(exception.getMessage()).isEqualTo("Test Message");
    }

    @Test
    public void shouldBeAbleToSetMessageWithFormat() {
        NotFoundException exception = new NotFoundException("Test Message is %s", "cool");
        assertThat(exception.getMessage()).isEqualTo("Test Message is cool");
    }
}
