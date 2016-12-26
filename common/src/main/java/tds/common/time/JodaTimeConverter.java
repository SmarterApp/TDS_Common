package tds.common.time;

import java.time.Instant;

/**
 * Conversion utilities for joda time to Instant
 */
public class JodaTimeConverter {
    /**
     * Null safe conversion for a {@link org.joda.time.Instant} to a {@link java.time.Instant}
     *
     * @param instant a {@link org.joda.time.Instant}
     * @return {@link java.time.Instant} if not null otherwise null
     */
    public static Instant convertJodaInstant(org.joda.time.Instant instant) {
        return instant == null ? null :
            Instant.ofEpochMilli(instant.getMillis());
    }

    /**
     * Null safe conversion for a {@link java.time.Instant} to a {@link org.joda.time.Instant}
     *
     * @param instant a {@link java.time.Instant}
     * @return {@link org.joda.time.Instant} if not null otherwise null
     */
    public static org.joda.time.Instant convertInstant(Instant instant) {
        return instant == null ? null :
            new org.joda.time.Instant(instant.toEpochMilli());
    }
}
