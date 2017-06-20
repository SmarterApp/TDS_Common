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

package tds.common;

/**
 * Enumeration representing assessment algorithm types
 */
public enum Algorithm {
    ADAPTIVE_2("adaptive2"),
    VIRTUAL("virtual"),
    FIXED_FORM("fixedform");

    private String type;

    Algorithm(String type) {
        this.type = type;
    }

    /**
     * @return the name of the algorithm
     */
    public String getType() {
        return type;
    }

    @Override
    public String toString() {
        return this.getType();
    }

    /**
     * Returns a {@link Algorithm} enum for the algorithm name
     *
     * @param algorithmType Th
     * @return
     */
    public static Algorithm fromType(String algorithmType) {
        if (algorithmType == null) throw new IllegalArgumentException("The algorithm type cannot be null");

        for (Algorithm algorithm : values()) {
            if (algorithmType.equalsIgnoreCase(algorithm.getType())) {
                return algorithm;
            }
        }
        // No Algorithm found for algorithm type
        throw new IllegalArgumentException(String.format("No Algorithm found with the name %s", algorithmType));
    }
}
