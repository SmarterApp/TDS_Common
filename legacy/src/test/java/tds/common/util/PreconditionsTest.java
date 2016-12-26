package tds.common.util;

import org.junit.Test;

public class PreconditionsTest {
    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowIllegalArgumentExceptionWhenArgumentIsNull() {
        Preconditions.checkNotNull(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowIllegalArgumentExceptionWithMessageWhenArgumentIsNull() {
        Preconditions.checkNotNull(null, "x cannot be null");
    }

    @Test
    public void shouldNotThrowIfArgumentsCannotBeNull() {
        Preconditions.checkNotNull("test");
        Preconditions.checkNotNull("test", "test cannot be null");
    }
}
