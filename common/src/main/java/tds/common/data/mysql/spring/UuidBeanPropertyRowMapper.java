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

import java.util.UUID;

import tds.common.data.mysql.UuidPropertyEditorSupport;

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
