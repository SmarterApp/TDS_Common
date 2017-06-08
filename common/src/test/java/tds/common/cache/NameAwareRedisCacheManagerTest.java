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