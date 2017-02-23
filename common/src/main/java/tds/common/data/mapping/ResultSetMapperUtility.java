package tds.common.data.mapping;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

public class ResultSetMapperUtility {
    /**
     * Map a {@link java.sql.Timestamp} to an {@link java.time.Instant}.
     *
     * @param rs          The {@link java.sql.ResultSet} being processed.
     * @param columnLabel The name of the column that should be mapped.
     * @return An {@link java.time.Instant} representation of the {@link java.sql.Timestamp} if one exists; otherwise null.
     * @throws java.sql.SQLException in the event an error occurs processing the {@code RecordSet}.
     */
    public static Instant mapTimestampToInstant(ResultSet rs, String columnLabel) throws SQLException {
        Timestamp ts = rs.getTimestamp(columnLabel);
        return ts == null
            ? null
            : ts.toLocalDateTime().toInstant(ZoneOffset.UTC);
    }

    /**
     * Map a {@link java.time.Instant} to a {@link java.sql.Timestamp} in UTC.
     *
     * @param time The {@link java.time.Instant} to convert
     * @return A {@link java.sql.Timestamp} representation of the {@link java.time.Instant}
     */
    public static Timestamp mapInstantToTimestamp(Instant time) {
        return time == null
            ? null
            : Timestamp.valueOf(LocalDateTime.ofInstant(time, ZoneOffset.UTC));
    }

    /**
     * Map a {@link org.joda.time.Instant} to a {@link java.sql.Timestamp}
     *
     * @param time the {@link org.joda.time.Instant} to convert
     * @return the converted instant
     */
    public static Timestamp mapJodaInstantToTimestamp(org.joda.time.Instant time) {
        if (time == null) return null;
        Timestamp ts = new Timestamp(time.getMillis());
        return ts;
    }

    /**
     * Map a {@link java.sql.Timestamp} to a {@link org.joda.time.Instant}
     *
     * @param rs          {@link java.sql.ResultSet} containing the data to convert
     * @param columnLabel the column label used to fetch the timestamp from the result set
     * @return converted {@link org.joda.time.Instant}
     * @throws SQLException in the event an error occurs accessing the result set
     */
    public static org.joda.time.Instant mapTimestampToJodaInstant(ResultSet rs, String columnLabel) throws SQLException {
        Timestamp ts = rs.getTimestamp(columnLabel);
        return ts == null ? null :
            new org.joda.time.Instant(ts.getTime());
    }
}
