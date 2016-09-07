package tds.common.data.mysql;

import com.fasterxml.uuid.Generators;

import java.beans.PropertyEditorSupport;
import java.util.UUID;

/**
 * Handles property support conversion of VARBINARY UUIDs in MySQL
 */
public class UuidPropertyEditorSupport extends PropertyEditorSupport {
    @Override
    public void setAsText(String text) throws IllegalArgumentException {
        setValue(UUID.fromString(text));
    }

    @Override
    public void setValue(final Object value) {
        super.setValue(value == null || value instanceof byte[]
                ? UuidAdapter.getUUIDFromBytes((byte[]) value)
                : Generators.timeBasedGenerator().generate());
    }

    @Override
    public UUID getValue() {
        return (UUID) super.getValue();
    }

    @Override
    public String getAsText() {
        return getValue().toString();
    }
}

