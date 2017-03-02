package tds.common;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class AlgorithmTest {
    @Test
    public void shouldConvertToEnum() {
        assertThat(Algorithm.fromType("adaptive2")).isEqualTo(Algorithm.ADAPTIVE_2);
        assertThat(Algorithm.fromType("virtual")).isEqualTo(Algorithm.VIRTUAL);
        assertThat(Algorithm.fromType("fixedform")).isEqualTo(Algorithm.FIXED_FORM);
    }

    @Test (expected = IllegalArgumentException.class)
    public void shouldThrowIfStringCannotBeConvertedToEnum() {
        Algorithm.fromType("Bogus");
    }
}