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

package tds.common.health;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.auto.value.AutoValue;

import java.util.HashMap;
import java.util.Map;

@AutoValue
public abstract class Health {
    @JsonCreator
    public static Health create(@JsonProperty("status") String status) {
        return new AutoValue_Health(status, new HashMap<>());
    }

    public abstract String getStatus();

    @JsonAnyGetter
    public abstract Map<String, Object> getDetails();

    @JsonAnySetter
    public void add(final String key, final Object value) {
        getDetails().put(key, value);
    }
}