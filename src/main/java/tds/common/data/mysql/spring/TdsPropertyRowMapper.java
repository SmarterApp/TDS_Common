package tds.common.data.mysql.spring;

import org.springframework.beans.BeanWrapper;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import tds.common.data.mysql.InstantPropertyEditorSupport;
import tds.common.data.mysql.UuidPropertyEditorSupport;

import java.beans.PropertyEditorSupport;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Handle MySQL-specific conversions of data stored in the TDS databases.
 */
public class TdsPropertyRowMapper<T> extends BeanPropertyRowMapper<T> {
    private List<CustomEditorDefinition> customEditorDefinitions;

    /**
     * Standard constructor wires up the common/universal mapping conditions that occur within the TDS databases.
     *
     * @param type The type being mapped.
     */
    public TdsPropertyRowMapper(Class<T> type) {
        super(type);

        customEditorDefinitions = new ArrayList<>();
        customEditorDefinitions.add(new CustomEditorDefinition(UUID.class, new UuidPropertyEditorSupport()));
        customEditorDefinitions.add(new CustomEditorDefinition(Instant.class, new InstantPropertyEditorSupport()));
    }

    /**
     * This constructor allows consumers to pass in any class that extends {@link PropertyEditorSupport}, thus allowing
     * the caller to handle special database -> POJO mapping conditions without having to modify and re-compile the
     * TDS_Common project.
     *
     * @param type The type being mapped.
     * @param customEditorDefinitions A collection of {@link CustomEditorDefinition}s that represent the special
     *                                mapping conditions to apply.
     */
    public TdsPropertyRowMapper(Class<T> type, List<CustomEditorDefinition> customEditorDefinitions) {
        super(type);

        this.customEditorDefinitions = customEditorDefinitions;
    }

    @Override
    protected void initBeanWrapper(BeanWrapper bw) {
        for(CustomEditorDefinition def : this.customEditorDefinitions) {
            bw.registerCustomEditor(def.getClassName(), def.getPropertyEditorSupport());
        }
    }
}
