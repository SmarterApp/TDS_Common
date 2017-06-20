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

package tds.common.web.resources;

import tds.common.ValidationError;

/**
 * Contains errors (if any) when responding without data (e.g. when the response would be a 204 - No Content).
 */
public class NoContentResponseResource {
    private ValidationError[] errors;
    
    /**
     * Private constructor for frameworks
     */
    private NoContentResponseResource() {}

    /**
     * @param errors errors present when completing the request
     */
    public NoContentResponseResource(ValidationError... errors) {
        this.errors = errors;
    }

    /**
     * @return errors when completing the request.
     */
    public ValidationError[] getErrors() {
        return errors;
    }
}
