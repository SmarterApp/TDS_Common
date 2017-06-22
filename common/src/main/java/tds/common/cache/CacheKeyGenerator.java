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
