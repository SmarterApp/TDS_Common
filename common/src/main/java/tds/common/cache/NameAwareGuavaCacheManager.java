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

import com.google.common.cache.CacheBuilder;
import org.springframework.cache.Cache;
import org.springframework.cache.guava.GuavaCache;
import org.springframework.cache.guava.GuavaCacheManager;

import java.util.Arrays;

import static java.util.concurrent.TimeUnit.SECONDS;

/**
 * This implementation of a GuavaCacheManager configures internal caches based upon the cache name.
 */
public class NameAwareGuavaCacheManager extends GuavaCacheManager {

    private final CacheConfigurationProperties cacheProperties;

    /**
     * Constructor
     *
     * @param cacheProperties   Configuration cache properties
     * @param cacheNames        The cache names
     */
    public NameAwareGuavaCacheManager(final CacheConfigurationProperties cacheProperties,
                                      final String... cacheNames) {
        this.cacheProperties = cacheProperties;
        this.setCacheNames(Arrays.asList(cacheNames));
    }

    @Override
    protected Cache createGuavaCache(final String name) {
        final CacheBuilder<Object, Object> cacheBuilder = CacheBuilder.newBuilder();
        switch (name) {
            case CacheType.SHORT_TERM:
                cacheBuilder.expireAfterWrite(cacheProperties.getExpireTimeShort(), SECONDS);
                break;
            case CacheType.MEDIUM_TERM:
                cacheBuilder.expireAfterWrite(cacheProperties.getExpireTimeMedium(), SECONDS);
                break;
            case CacheType.LONG_TERM:
                cacheBuilder.expireAfterWrite(cacheProperties.getExpireTimeLong(), SECONDS);
                break;
            default:
                cacheBuilder.expireAfterWrite(cacheProperties.getExpireTimeDefault(), SECONDS);
                break;
        }
        return new GuavaCache(name, cacheBuilder.build(), isAllowNullValues());
    }
}
