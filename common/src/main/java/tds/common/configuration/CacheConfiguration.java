package tds.common.configuration;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import tds.common.cache.CacheConfigurationProperties;
import tds.common.cache.CacheKeyGenerator;
import tds.common.cache.CacheType;
import tds.common.cache.NameAwareGuavaCacheManager;

/**
 * Spring Configuration file for caching
 */
@Configuration
@EnableCaching
@Import(CacheConfigurationProperties.class)
@ConditionalOnProperty(value = "tds.cache.enabled", havingValue = "true")
public class CacheConfiguration extends CachingConfigurerSupport {

    @Bean
    public KeyGenerator keyGenerator() {
        return new CacheKeyGenerator();
    }

    /**
     * Guava cache manager implementation used if Redis is not available.
     *
     * @return A guava cache manager
     */
    @Bean
    public CacheManager cacheManager(final CacheConfigurationProperties cacheConfigurationProperties) {
        return new NameAwareGuavaCacheManager(cacheConfigurationProperties,
            CacheType.SHORT_TERM, CacheType.MEDIUM_TERM, CacheType.LONG_TERM);
    }
}
