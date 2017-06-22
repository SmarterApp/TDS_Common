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

import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.core.RedisOperations;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * This implementation of a RedisCacheManager configures internal caches based upon the cache name.
 */
public class NameAwareRedisCacheManager extends RedisCacheManager {

    public NameAwareRedisCacheManager(final RedisOperations redisOperations,
                                      final CacheConfigurationProperties cacheProperties,
                                      final String... cacheNames) {
        super(redisOperations);
        super.setExpires(asExpires(cacheProperties));
        super.setDefaultExpiration(cacheProperties.getExpireTimeDefault());
        super.setCacheNames(Arrays.asList(cacheNames));
    }

    Map<String, Long> asExpires(final CacheConfigurationProperties properties) {
        final Map<String, Long> expireMap = new HashMap<>();
        expireMap.put(CacheType.SHORT_TERM, properties.getExpireTimeShort());
        expireMap.put(CacheType.MEDIUM_TERM, properties.getExpireTimeMedium());
        expireMap.put(CacheType.LONG_TERM, properties.getExpireTimeLong());
        return expireMap;
    }
}
