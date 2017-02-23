package tds.common.time;

import org.junit.Test;

import java.time.Instant;

import static org.assertj.core.api.Assertions.assertThat;

public class JodaTimeConverterTest {
    @Test
    public void shouldConvertJodaInstant() {
        assertThat(JodaTimeConverter.convertJodaInstant(null)).isNull();
        org.joda.time.Instant jodaInstant = org.joda.time.Instant.now();
        assertThat(JodaTimeConverter.convertJodaInstant(jodaInstant).toEpochMilli()).isEqualByComparingTo(jodaInstant.getMillis());
    }

    @Test
    public void shouldConvertInstantToJodaInstant() {
        assertThat(JodaTimeConverter.convertInstant(null)).isNull();
        Instant instant = Instant.now();
        assertThat(JodaTimeConverter.convertInstant(instant).getMillis()).isEqualByComparingTo(instant.toEpochMilli());
    }
}
