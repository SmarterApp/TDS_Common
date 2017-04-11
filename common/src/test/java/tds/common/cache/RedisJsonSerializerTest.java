package tds.common.cache;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class RedisJsonSerializerTest {

    private final ObjectMapper objectMapper = new ObjectMapper();

    private RedisJsonSerializer serializer;

    @Before
    public void setup() {
        serializer = new RedisJsonSerializer(objectMapper);
    }

    @Test
    public void itShouldSerializeAndDeserializeAnObject() {
        final CacheConfigurationProperties properties = new CacheConfigurationProperties();
        properties.setExpireTimeShort(123);

        final byte[] serializedBytes = serializer.serialize(properties);
        final CacheConfigurationProperties deserialized = (CacheConfigurationProperties) serializer.deserialize(serializedBytes);
        assertThat(deserialized.getExpireTimeShort()).isEqualTo(properties.getExpireTimeShort());
    }
}