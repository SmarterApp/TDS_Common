package tds.common.data.mysql.spring;

import org.springframework.beans.BeanWrapper;
import tds.common.data.mysql.UuidPropertyEditorSupport;

import java.util.UUID;

/**
 * Handle mapping MySQL {@code VARBINARY(16)} to {@code UUID}.
 *
 * @deprecated Use the {@link TdsPropertyRowMapper} instead; this class is intended for backwards compatibility.
 */
@Deprecated
public class UuidBeanPropertyRowMapper<T> extends TdsPropertyRowMapper<T> {
    public UuidBeanPropertyRowMapper(Class<T> type) {
        super(type);
    }

    @Override
    protected void initBeanWrapper(BeanWrapper bw) {
        bw.registerCustomEditor(UUID.class, new UuidPropertyEditorSupport());
    }
}
