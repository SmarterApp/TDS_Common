package tds.common.data.mysql;

import java.beans.PropertyEditorSupport;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.ZoneOffset;

/**
 * Handles property support conversion of TIMESTAMP from MySQL to UTC Instant.
 */
public class InstantPropertyEditorSupport extends PropertyEditorSupport {
    @Override
    public void setValue(final Object value) {
        if (value instanceof Timestamp) {
            Timestamp ts = (Timestamp)value;
            super.setValue(ts.toLocalDateTime().toInstant(ZoneOffset.UTC));
        } else {
            super.setValue(value);
        }
    }

    @Override
    public Instant getValue() {
        return (Instant)super.getValue();
    }
}
