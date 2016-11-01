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
     * @param rs The {@link java.sql.ResultSet} being processed.
     * @param columnLabel The name of the column that should be mapped.
     * @return An {@link java.time.Instant} representation of the {@link java.sql.Timestamp} if one exists; otherwise null.
     * @throws java.sql.SQLException in the event an error occurs processing the {@code RecordSet}.
     */
    public static Instant mapTimeStampToInstant(ResultSet rs, String columnLabel) throws SQLException {
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

}