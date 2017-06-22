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

import java.time.Instant;

/**
 * Conversion utilities for joda time to Instant
 */
public class JodaTimeConverter {
    /**
     * Null safe conversion for a {@link org.joda.time.Instant} to a {@link java.time.Instant}
     *
     * @param instant a {@link org.joda.time.Instant}
     * @return {@link java.time.Instant} if not null otherwise null
     */
    public static Instant convertJodaInstant(org.joda.time.Instant instant) {
        return instant == null ? null :
            Instant.ofEpochMilli(instant.getMillis());
    }

    /**
     * Null safe conversion for a {@link java.time.Instant} to a {@link org.joda.time.Instant}
     *
     * @param instant a {@link java.time.Instant}
     * @return {@link org.joda.time.Instant} if not null otherwise null
     */
    public static org.joda.time.Instant convertInstant(Instant instant) {
        return instant == null ? null :
            new org.joda.time.Instant(instant.toEpochMilli());
    }
}
