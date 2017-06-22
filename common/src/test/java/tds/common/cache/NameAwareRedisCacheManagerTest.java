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

import org.junit.Before;
import org.junit.Test;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.entry;
import static org.mockito.Mockito.mock;
import static tds.common.cache.CacheType.LONG_TERM;
import static tds.common.cache.CacheType.MEDIUM_TERM;
import static tds.common.cache.CacheType.SHORT_TERM;

public class NameAwareRedisCacheManagerTest {

    private CacheConfigurationProperties cacheProperties;
    private NameAwareRedisCacheManager cacheManager;

    @Before
    public void setup() {
        cacheProperties = new CacheConfigurationProperties();
        cacheManager = new NameAwareRedisCacheManager(mock(RedisTemplate.class), cacheProperties,
            SHORT_TERM, MEDIUM_TERM, LONG_TERM);
    }

    @Test
    public void itShouldConfigureExpirationTimesBasedUponName() throws Exception {
        final Map<String, Long> expirationMap = cacheManager.asExpires(cacheProperties);
        assertThat(expirationMap).containsOnly(
            entry(SHORT_TERM, cacheProperties.getExpireTimeShort()),
            entry(MEDIUM_TERM, cacheProperties.getExpireTimeMedium()),
            entry(LONG_TERM, cacheProperties.getExpireTimeLong()));
    }
}