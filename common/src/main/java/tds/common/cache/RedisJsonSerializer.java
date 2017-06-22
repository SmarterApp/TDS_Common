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

package tds.common.cache;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;

import java.io.IOException;

/**
 * This Redis serializer uses an ObjectMapper to serialize objects.
 */
public class RedisJsonSerializer implements RedisSerializer<Object> {

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
