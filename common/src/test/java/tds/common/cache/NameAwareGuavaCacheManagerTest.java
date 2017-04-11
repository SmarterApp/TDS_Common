package tds.common.cache;

import org.junit.Before;
import org.junit.Test;
import org.springframework.cache.Cache;

import java.util.concurrent.Callable;

import static java.lang.Thread.sleep;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class NameAwareGuavaCacheManagerTest {

    private CacheConfigurationProperties cacheProperties;
    private NameAwareGuavaCacheManager cacheManager;

    @Before
    public void setup() {
        cacheProperties = new CacheConfigurationProperties();
        cacheProperties.setExpireTimeShort(1);
        cacheManager = new NameAwareGuavaCacheManager(cacheProperties, CacheType.SHORT_TERM);
    }

    @Test
    public void itShouldConfigureTheCacheBasedUponName() throws Exception {
        final String key = "myKey";
        final Cache shortTermCache = cacheManager.getCache(CacheType.SHORT_TERM);
        final Callable<String> valueLoader = mock(Callable.class);
        when(valueLoader.call()).thenReturn("a value");
        for (int i = 0; i < 10; i++) {
            shortTermCache.get(key, valueLoader);
        }
        sleep(1000);
        shortTermCache.get(key, valueLoader);

        verify(valueLoader, times(2)).call();
    }

}