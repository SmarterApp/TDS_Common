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

package tds.common.configuration;

import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import tds.common.cache.CacheConfigurationProperties;
import tds.common.cache.CacheKeyGenerator;
import tds.common.cache.NameAwareGuavaCacheManager;
import tds.common.cache.NameAwareRedisCacheManager;

import static tds.common.cache.CacheType.LONG_TERM;
import static tds.common.cache.CacheType.MEDIUM_TERM;
import static tds.common.cache.CacheType.SHORT_TERM;

/**
 * Spring Configuration file for caching.
 * If caching is enabled the implementation will switch between:<ol>
 *     <li>Guava - if Redis is not configured</li>
 *     <li>Redis - if configured</li>
 * </ol>
 */
@Configuration
@EnableCaching
@EnableConfigurationProperties(CacheConfigurationProperties.class)
@ConditionalOnProperty(value = "tds.cache.enabled", havingValue = "true", matchIfMissing = true)
public class CacheConfiguration extends CachingConfigurerSupport {

    @Bean
    public KeyGenerator keyGenerator() {
        return new CacheKeyGenerator();
    }

    /**
     * Guava cache manager implementation used if Redis is not available.
     *
     * @param cacheConfigurationProperties cache configuration properties
     * @return A guava cache manager
     */
    @Bean(name = "cacheManager")
    @ConditionalOnProperty(name = "tds.cache.implementation", havingValue = "guava", matchIfMissing = true)
    public CacheManager guavaCacheManager(final CacheConfigurationProperties cacheConfigurationProperties) {
        return new NameAwareGuavaCacheManager(cacheConfigurationProperties,
            SHORT_TERM, MEDIUM_TERM, LONG_TERM);
    }

    /**
     * Redis cache manager implementation providing distributed caching.
     *
     * @param cacheConfigurationProperties  cache configuration properties
     * @param redisTemplate                 Redis communication template
     * @return A redis cache manager
     */
    @Bean(name = "cacheManager")
    @ConditionalOnBean(RedisTemplate.class)
    public CacheManager redisCacheManager(final CacheConfigurationProperties cacheConfigurationProperties,
                                          final RedisTemplate<String, Object> redisTemplate) {
        return new NameAwareRedisCacheManager(redisTemplate, cacheConfigurationProperties,
            SHORT_TERM, MEDIUM_TERM, LONG_TERM);
    }
}
