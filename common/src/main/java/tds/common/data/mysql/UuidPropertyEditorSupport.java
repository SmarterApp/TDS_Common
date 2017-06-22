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

