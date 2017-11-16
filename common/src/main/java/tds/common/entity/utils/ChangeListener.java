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

package tds.common.entity.utils;

import tds.common.EntityUpdate;

/**
 * An interface for listening for and responding to changes on an entity
 */
public interface ChangeListener<T> {
    /**
     * Compare an original instance of an object's state to an updated instance of an object's state and execute any
     * business rules/logic that are a consequence of the change in state.
     *
     */
    void accept(EntityUpdate<T> entityUpdate);
}
