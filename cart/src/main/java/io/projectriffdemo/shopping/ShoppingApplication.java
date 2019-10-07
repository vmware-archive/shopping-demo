package io.projectriffdemo.shopping;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.core.ReactiveRedisOperations;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;

@SpringBootApplication
public class ShoppingApplication {

    @Autowired
    private ReactiveRedisOperations<String, Cart> redisOperations;

    public static void main(String[] args) {
        SpringApplication.run(ShoppingApplication.class, args);
    }

    @Bean
    public Function<Flux<CartEvent>, Flux<Cart>> updateCart() {
        return cartEventFlux -> cartEventFlux
                .log()
                .groupBy(CartEvent::getUserId)
                .concatMap(stringCartEventGroupedFlux -> {
                    return stringCartEventGroupedFlux.reduce(getCart(stringCartEventGroupedFlux.key()), (cart, cartEvent) -> {
                        cart.applyEvent(cartEvent);
                        return cart;
                    })
                .doOnNext(this::writeCart);
                });

    }

    protected Cart getCart(String userId) {
        Cart defaultCart = new Cart(userId);
        return redisOperations.opsForValue().get("userId:"+userId).defaultIfEmpty(defaultCart).onErrorReturn(defaultCart).block();
    }

    protected void writeCart(Cart cart) {
        redisOperations.opsForValue().set("userId:"+cart.getUserId(), cart).subscribe();
    }
}
