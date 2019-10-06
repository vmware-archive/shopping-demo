package io.projectriffdemo.shopping;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import reactor.core.publisher.Flux;
import reactor.util.function.Tuple2;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;

@SpringBootApplication
public class ShoppingApplication {

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
                    });

                });

    }

    // TODO read from redis
    protected Cart getCart(String userId) {
        return new Cart(userId);
    }
}
