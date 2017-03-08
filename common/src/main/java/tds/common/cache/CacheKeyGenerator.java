package tds.common.cache;

import org.springframework.cache.interceptor.KeyGenerator;

import java.lang.reflect.Method;

/**
 * A custom key generator for caching
 */
public class CacheKeyGenerator implements KeyGenerator {
    @Override
    public Object generate(Object target, Method method, Object... params) {
        return this.generate(target.getClass().getName(), method.getName(), params);
    }

    public String generate(String className, String methodName, Object... params) {
        StringBuilder sb = new StringBuilder();
        sb.append(className);
        sb.append(".");
        sb.append(methodName);

        for (Object obj : params) {
            if (obj != null) {
                sb.append(obj.hashCode());
            }

            sb.append(":");
        }

        return sb.toString();
    }
}
