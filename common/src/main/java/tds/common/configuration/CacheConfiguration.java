package tds.common.configuration;

import com.google.common.cache.CacheBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.guava.GuavaCache;
import org.springframework.cache.guava.GuavaCacheManager;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.util.concurrent.TimeUnit;

import tds.common.cache.CacheKeyGenerator;
import tds.common.cache.CacheType;

/**
 * Spring Configuration file for caching
 */
@Configuration
@EnableCaching
@PropertySource(value="classpath:cache.properties", ignoreResourceNotFound=true)
public class CacheConfiguration extends CachingConfigurerSupport {

    @Value("${tds.cache.expire.time.short:20}")
    private Integer shortTermExpirationInSeconds;

    @Value("${tds.cache.expire.time.medium:600}")
    private Integer mediumTermExpirationInSeconds;

    @Value("${tds.cache.expire.time.long:7200}")
    private Integer longTermExpirationInSeconds;

    @Bean
    public CacheManager cacheManager() {
        return new GuavaCacheManager(CacheType.SHORT_TERM, CacheType.MEDIUM_TERM, CacheType.LONG_TERM);
    }

    @Bean
    public KeyGenerator keyGenerator() {
        return new CacheKeyGenerator();
    }

    @Bean
    public Cache shortTermCache() {
        return new GuavaCache(CacheType.SHORT_TERM, CacheBuilder.newBuilder()
            .expireAfterWrite(shortTermExpirationInSeconds, TimeUnit.SECONDS)
            .build());
    }

    @Bean
    public Cache mediumTermCache() {
        return new GuavaCache(CacheType.MEDIUM_TERM, CacheBuilder.newBuilder()
            .expireAfterWrite(mediumTermExpirationInSeconds, TimeUnit.SECONDS)
            .build());
    }

    @Bean
    public Cache longTermCache() {
        return new GuavaCache(CacheType.LONG_TERM, CacheBuilder.newBuilder()
            .expireAfterWrite(longTermExpirationInSeconds, TimeUnit.SECONDS)
            .build());
    }
}
