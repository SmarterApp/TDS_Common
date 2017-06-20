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
            Timestamp ts = (Timestamp) value;
            super.setValue(ts.toLocalDateTime().toInstant(ZoneOffset.UTC));
        } else {
            super.setValue(value);
        }
    }

    @Override
    public Instant getValue() {
        return (Instant) super.getValue();
    }
}
