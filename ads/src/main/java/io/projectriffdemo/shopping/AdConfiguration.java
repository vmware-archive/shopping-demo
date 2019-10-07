package io.projectriffdemo.shopping;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.ReactiveRedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.ReactiveRedisOperations;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class AdConfiguration {
    @Bean
    ReactiveRedisOperations<String, Ad> redisOperations(ReactiveRedisConnectionFactory redisConnectionFactory) {
        Jackson2JsonRedisSerializer<Ad> serializer = new Jackson2JsonRedisSerializer<>(Ad.class);

        RedisSerializationContext.RedisSerializationContextBuilder<String, Ad> builder =
                RedisSerializationContext.newSerializationContext(new StringRedisSerializer());

        RedisSerializationContext<String, Ad> context = builder.value(serializer).build();

        return new ReactiveRedisTemplate<>(redisConnectionFactory, context);
    }

    @Bean
    LettuceConnectionFactory redisConnectionFactory() {
        return new LettuceConnectionFactory("my-redis-master.default.svc.cluster.local", 6379);
    }
}
