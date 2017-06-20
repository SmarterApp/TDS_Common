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

package tds.common.data.mysql;

import java.nio.ByteBuffer;
import java.util.UUID;

/**
 * Handles common conversion techniques when dealing with MySQL VARBINARY type.
 */
public class UuidAdapter {
    /**
     * Handles converting a UUID to bytes to interact.  Leverage when dealing with MySQL VARBINARY type.
     *
     * @param uuid the UUID to convert
     * @return byte representation of the UUID
     */
    public static byte[] getBytesFromUUID(UUID uuid) {
        if (uuid == null) {
            return null;
        }

        ByteBuffer bb = ByteBuffer.wrap(new byte[16]);
        bb.putLong(uuid.getMostSignificantBits());
        bb.putLong(uuid.getLeastSignificantBits());

        return bb.array();
    }

    /**
     * Handles converting bytes to a UUID.  Leverage when dealing with MySQL VARBINARY type.
     *
     * @param bytes bytes to convert to UUID
     * @return UUID from the bytes
     */
    public static UUID getUUIDFromBytes(byte[] bytes) {
        if (bytes == null) {
            return null;
        }

        ByteBuffer byteBuffer = ByteBuffer.wrap(bytes);
        Long high = byteBuffer.getLong();
        Long low = byteBuffer.getLong();

        return new UUID(high, low);
    }
}
