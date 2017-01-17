package tds.common.cache;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.lang.reflect.Method;

import tds.common.configuration.CacheConfiguration;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = CacheConfiguration.class)
public class CacheKeyGeneratorTest {

    @Autowired
    KeyGenerator cacheKeyGenerator;

    @Test
    public void shouldGenerateUniqueKeysForDifferentMethodName() throws NoSuchMethodException {
        final Object obj = new Object();
        final Method method1 = String.class.getMethod("toString");
        final Integer param1 = 4;
        final Float param2 = 7f;

        String retCacheKey1 = (String) cacheKeyGenerator.generate(obj, method1, param1, param2);

        assertThat(retCacheKey1).isNotNull();

        Method method2 = String.class.getMethod("hashCode");
        String retCacheKey2 = (String) cacheKeyGenerator.generate(obj, method2, param1, param2);

        assertThat(retCacheKey1).isNotEqualTo(retCacheKey2);
    }

    @Test
    public void shouldGenerateUniqueKeysForDifferentClassName() throws NoSuchMethodException {
        final Object obj1 = new Object();
        final Method method = String.class.getMethod("toString");
        final Integer param1 = 4;
        final Float param2 = 7f;

        String retCacheKey1 = (String) cacheKeyGenerator.generate(obj1, method, param1, param2);

        assertThat(retCacheKey1).isNotNull();

        final String obj2 = "Test";
        String retCacheKey2 = (String) cacheKeyGenerator.generate(obj2, method, param1, param2);

        assertThat(retCacheKey1).isNotEqualTo(retCacheKey2);
    }

    @Test
    public void shouldGenerateUniqueKeysForDifferentParamTypes() throws NoSuchMethodException {
        final Object obj = new Object();
        final Method method = String.class.getMethod("toString");
        final Integer param1 = 4;
        final Float param2a = 7f;

        String retCacheKey1 = (String) cacheKeyGenerator.generate(obj, method, param1, param2a);

        assertThat(retCacheKey1).isNotNull();

        Double param2b = 7d;
        String retCacheKey2 = (String) cacheKeyGenerator.generate(obj, method, param1, param2b);

        assertThat(retCacheKey1).isNotEqualTo(retCacheKey2);
    }

    @Test
    public void shouldGenerateUniqueKeysForDifferentParamVals() throws NoSuchMethodException {
        final Object obj = new Object();
        final Method method = String.class.getMethod("toString");
        final Integer param1 = 4;
        final Float param2a = 7f;

        String retCacheKey1 = (String) cacheKeyGenerator.generate(obj, method, param1, param2a);

        assertThat(retCacheKey1).isNotNull();


        final Float param2b = 9f;

        String retCacheKey2 = (String) cacheKeyGenerator.generate(obj, method, param1, param2b);

        assertThat(retCacheKey1).isNotEqualTo(retCacheKey2);
    }

    @Test
    public void shouldGenerateSameKeysForSameArgs() throws NoSuchMethodException {
        final Object obj = new Object();
        final Method method = String.class.getMethod("toString");
        final Integer param1 = 4;
        final Float param2 = 7f;

        String retCacheKey1 = (String) cacheKeyGenerator.generate(obj, method, param1, param2);

        assertThat(retCacheKey1).isNotNull();
        String retCacheKey2 = (String) cacheKeyGenerator.generate(obj, method, param1, param2);

        assertThat(retCacheKey1).isEqualTo(retCacheKey2);
    }

    @Test
    public void shouldGenerateKeyWithClassMethodNameAndParams() throws NoSuchMethodException {
        final String methodName = "toString";
        final Object obj = new Object();
        final Method method = String.class.getMethod(methodName);
        final Integer param1 = 4;
        final Float param2 = 7f;

        String retCacheKey = (String) cacheKeyGenerator.generate(obj, method, param1, param2);
        assertThat(retCacheKey).contains(methodName);
        assertThat(retCacheKey).contains(obj.getClass().getName());
        assertThat(retCacheKey).contains(String.valueOf(param1.hashCode()));
        assertThat(retCacheKey).contains(String.valueOf(param2.hashCode()));

    }
}
