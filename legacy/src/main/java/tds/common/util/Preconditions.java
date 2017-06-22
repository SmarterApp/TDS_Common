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

package tds.common.util;

/**
 * Inspired by Google Guava's preconditions class
 */
public class Preconditions {
    /**
     * Convenience method to check not null
     *
     * @param reference the object to check for null
     * @param <T>       type for the reference
     * @return the object if not null
     * @throws java.lang.IllegalArgumentException if null
     */
    public static <T> T checkNotNull(T reference) {
        if (reference == null) {
            throw new IllegalArgumentException();
        }
        return reference;
    }

    /**
     * Convenience method to check not null
     *
     * @param reference    the object to check for null
     * @param <T>          type for the reference
     * @param errorMessage the message to include in the exception
     * @return the object if not null
     * @throws java.lang.IllegalArgumentException if null
     */
    public static <T> T checkNotNull(T reference, Object errorMessage) {
        if (reference == null) {
            throw new IllegalArgumentException(String.valueOf(errorMessage));
        }
        return reference;
    }
}
