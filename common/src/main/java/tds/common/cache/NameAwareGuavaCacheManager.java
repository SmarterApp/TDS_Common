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
        }
        return new GuavaCache(name, cacheBuilder.build(), isAllowNullValues());
    }
}
