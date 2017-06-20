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

import java.beans.PropertyEditorSupport;

/**
 * A class for adding a {@link PropertyEditorSupport} for a specified {@link Class}.
 * <p>
 * A {@code List} of these can be passed into the constructor of the {@link TdsPropertyRowMapper} to define any
 * special mapping situations that arise when mapping data from the TDS databases to a POJO.
 * </p>
 */
public class CustomEditorDefinition {
    private Class className;
    private PropertyEditorSupport propertyEditorSupport;

    public CustomEditorDefinition(Class className, PropertyEditorSupport propertyEditorSupport) {
        this.className = className;
        this.propertyEditorSupport = propertyEditorSupport;
    }

    /**
     * @return The {@link Class} of the property being mapped.
     */
    public Class getClassName() {
        return className;
    }

    public void setClassName(Class className) {
        this.className = className;
    }

    /**
     * @return A class that extends {@link PropertyEditorSupport} that defines how to map from the database value to the
     * POJO value.
     */
    public PropertyEditorSupport getPropertyEditorSupport() {
        return propertyEditorSupport;
    }

    public void setPropertyEditorSupport(PropertyEditorSupport propertyEditorSupport) {
        this.propertyEditorSupport = propertyEditorSupport;
    }
}
