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