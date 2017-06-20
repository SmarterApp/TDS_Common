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


import com.google.common.base.Optional;

import static tds.common.util.Preconditions.checkNotNull;

/**
 * Contains data and errors if any when responding with the data.
 *
 * @param <T> type for the data contained in the response
 */
public class Response<T> {
    private ValidationError error;
    private T data;

    /**
     * @param data  data requested
     * @param error errors present when completing the request
     */
    public Response(T data, ValidationError error) {
        this.error = checkNotNull(error);
        this.data = checkNotNull(data);
    }

    /**
     * Private constructor for frameworks
     */
    private Response() {
    }

    /**
     * @param error errors present when completing the request
     */
    public Response(ValidationError error) {
        this.error = checkNotNull(error);
    }

    /**
     * @param data data requested
     */
    public Response(T data) {
        this.data = checkNotNull(data);
    }

    /**
     * @return error when completing the request.
     */
    public Optional<ValidationError> getError() {
        return Optional.fromNullable(error);
    }

    /**
     * @return True if there is a {@link tds.common.ValidationError}s
     */
    public boolean hasError() {
        return error != null;
    }

    /**
     * @return optional potentially containing the data
     */
    public Optional<T> getData() {
        return Optional.fromNullable(data);
    }
}
