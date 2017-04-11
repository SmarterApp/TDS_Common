package tds.common.cache;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;

import java.io.IOException;

/**
 * This Redis serializer uses an ObjectMapper to serialize objects.
 */
public class RedisJsonSerializer implements RedisSerializer<Object> {
    private final static Logger LOG = LoggerFactory.getLogger(RedisJsonSerializer.class);

    private final ObjectMapper objectMapper;

    /**
     * Constructor
     * NOTE: We clone and modify the application-wide ObjectMapper and tell it to embed
     * class information in the serialized values because we cannot pass the expected
     * class into the ObjectMapper.readValue method.
     *
     * @param objectMapper The application ObjectMapper
     */
    public RedisJsonSerializer(final ObjectMapper objectMapper) {
        this.objectMapper = objectMapper.copy();
        this.objectMapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL, JsonTypeInfo.As.PROPERTY);
    }

    @Override
    public byte[] serialize(final Object obj) throws SerializationException {
        try {
            LOG.info("Serializing obj {} as {}", obj, objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(obj));
            return objectMapper.writeValueAsBytes(obj);
        } catch (final JsonProcessingException e) {
            throw new SerializationException("Redis is unable to serialize object", e);
        }
    }

    @Override
    public Object deserialize(final byte[] bytes) throws SerializationException {
        if (bytes == null) {
            return null;
        }

        try {
            return objectMapper.readValue(bytes, Object.class);
        } catch (final IOException e) {
            throw new SerializationException("Redis is unable to deserialize object", e);
        }
    }
}
