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
