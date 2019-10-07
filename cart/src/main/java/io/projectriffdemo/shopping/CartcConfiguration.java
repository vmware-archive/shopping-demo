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
public class CartcConfiguration {
    @Bean
    ReactiveRedisOperations<String, Cart> redisOperations(ReactiveRedisConnectionFactory redisConnectionFactory) {
        Jackson2JsonRedisSerializer<Cart> serializer = new Jackson2JsonRedisSerializer<>(Cart.class);

        RedisSerializationContext.RedisSerializationContextBuilder<String, Cart> builder =
                RedisSerializationContext.newSerializationContext(new StringRedisSerializer());

        RedisSerializationContext<String, Cart> context = builder.value(serializer).build();

        return new ReactiveRedisTemplate<>(redisConnectionFactory, context);
    }

    @Bean
    LettuceConnectionFactory redisConnectionFactory() {
        return new LettuceConnectionFactory("my-redis-master.default.svc.cluster.local", 6379);
    }
}
