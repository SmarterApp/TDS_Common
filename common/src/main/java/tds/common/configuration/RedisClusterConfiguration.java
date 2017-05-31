package tds.common.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import tds.common.cache.RedisJsonSerializer;

/**
 * This configuration is responsible for initializing a general RedisTemplate
 * able to serialize/deserialize generic Objects using the application ObjectMapper.
 */
@Configuration
@Import(RedisAutoConfiguration.class)
@ConditionalOnProperty(name = "tds.cache.implementation", havingValue = "redis")
public class RedisClusterConfiguration {

    @Bean
    @Primary
    @ConditionalOnBean(RedisConnectionFactory.class)
    public RedisTemplate<String, Object> redisTemplate(final RedisConnectionFactory redisConnectionFactory,
                                                       final ObjectMapper objectMapper) {
        final RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new RedisJsonSerializer(objectMapper));
        return redisTemplate;
    }

}
