package tds.common.data.mysql.spring;

import org.springframework.beans.BeanWrapper;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import tds.common.data.mysql.UuidPropertyEditorSupport;

import java.util.UUID;


public class UuidBeanPropertyRowMapper<T> extends BeanPropertyRowMapper<T> {

    public UuidBeanPropertyRowMapper(Class<T> type) {
        super(type);
    }

    @Override
    protected void initBeanWrapper(BeanWrapper bw) {
        bw.registerCustomEditor(UUID.class, new UuidPropertyEditorSupport());
    }
}
