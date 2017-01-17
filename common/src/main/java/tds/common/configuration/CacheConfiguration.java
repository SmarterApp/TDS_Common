package tds.common.configuration;

import com.google.common.cache.CacheBuilder;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.guava.GuavaCache;
import org.springframework.cache.guava.GuavaCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

/**
 * Spring Configuration file for caching
 */
@Configuration
@EnableCaching
public class CacheConfiguration {
    @Bean
    public CacheManager cacheManager() {
        return new GuavaCacheManager(CacheType.SHORT_TERM, CacheType.MEDIUM_TERM, CacheType.LONG_TERM);
    }

    @Bean
    @Qualifier(CacheType.SHORT_TERM)
    public Cache shortTermCache() {
        return new GuavaCache(CacheType.SHORT_TERM, CacheBuilder.newBuilder()
            .expireAfterWrite(20, TimeUnit.SECONDS)
            .build());
    }

    @Bean
    @Qualifier(CacheType.MEDIUM_TERM)
    public Cache mediumTermCache() {
        return new GuavaCache(CacheType.MEDIUM_TERM, CacheBuilder.newBuilder()
            .expireAfterWrite(600, TimeUnit.SECONDS)
            .build());
    }

    @Bean
    @Qualifier(CacheType.LONG_TERM)
    public Cache longTermCache() {
        return new GuavaCache(CacheType.LONG_TERM, CacheBuilder.newBuilder()
            .expireAfterWrite(7200, TimeUnit.SECONDS)
            .build());
    }
}
