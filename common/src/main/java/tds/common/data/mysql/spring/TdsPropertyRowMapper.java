/***************************************************************************************************
 * Copyright 2017 Regents of the University of California. Licensed under the Educational
 * Community License, Version 2.0 (the “license”); you may not use this file except in
 * compliance with the License. You may obtain a copy of the license at
 *
 * https://opensource.org/licenses/ECL-2.0
 *
 * Unless required under applicable law or agreed to in writing, software distributed under the
 * License is distributed in an “AS IS” BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for specific language governing permissions
 * and limitations under the license.
 **************************************************************************************************/

package tds.common.data.mysql.spring;

import org.springframework.beans.BeanWrapper;
import org.springframework.jdbc.core.BeanPropertyRowMapper;

import java.beans.PropertyEditorSupport;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import tds.common.data.mysql.InstantPropertyEditorSupport;
import tds.common.data.mysql.UuidPropertyEditorSupport;

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
     * @param type                    The type being mapped.
     * @param customEditorDefinitions A collection of {@link CustomEditorDefinition}s that represent the special
     *                                mapping conditions to apply.
     */
    public TdsPropertyRowMapper(Class<T> type, List<CustomEditorDefinition> customEditorDefinitions) {
        super(type);

        this.customEditorDefinitions = customEditorDefinitions;
    }

    @Override
    protected void initBeanWrapper(BeanWrapper bw) {
        for (CustomEditorDefinition def : this.customEditorDefinitions) {
            bw.registerCustomEditor(def.getClassName(), def.getPropertyEditorSupport());
        }
    }
}
