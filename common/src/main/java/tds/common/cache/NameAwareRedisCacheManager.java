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
