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