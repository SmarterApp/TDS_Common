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

package tds.common.time;

import org.junit.Test;

import java.time.Instant;

import static org.assertj.core.api.Assertions.assertThat;

public class JodaTimeConverterTest {
    @Test
    public void shouldConvertJodaInstant() {
        assertThat(JodaTimeConverter.convertJodaInstant(null)).isNull();
        org.joda.time.Instant jodaInstant = org.joda.time.Instant.now();
        assertThat(JodaTimeConverter.convertJodaInstant(jodaInstant).toEpochMilli()).isEqualByComparingTo(jodaInstant.getMillis());
    }

    @Test
    public void shouldConvertInstantToJodaInstant() {
        assertThat(JodaTimeConverter.convertInstant(null)).isNull();
        Instant instant = Instant.now();
        assertThat(JodaTimeConverter.convertInstant(instant).getMillis()).isEqualByComparingTo(instant.toEpochMilli());
    }
}
