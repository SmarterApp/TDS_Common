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

package tds.common.data;

/**
 * Database Exception thrown when failing to create a record.  Since this is normally
 * an unrecoverable situation in the application it extends a runtime exception.
 */
public class CreateRecordException extends RuntimeException {

    /**
     * @param message the message to have in the Exception
     */
    public CreateRecordException(String message) {
        super(message);
    }

    /**
     * Constructor that takes the message and an inserts the
     * arguments into the message
     *
     * @param message message
     * @param args    arguments used when formatting the message
     */
    public CreateRecordException(String message, Object... args) {
        super(String.format(message, args));
    }
}
