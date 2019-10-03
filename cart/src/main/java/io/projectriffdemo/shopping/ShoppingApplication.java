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
        return cartEvents -> {
            Set<Cart> carts = new HashSet<>();
            cartEvents.
                    log().
                    map(cartEvent -> {
                        Cart cart = getCart(cartEvent.getUserId());
                        cart.applyEvent(cartEvent);
                        saveCart(cart);
                        return cart;
                    }).subscribe(carts::add);
            return Flux.fromIterable(carts);
        };
    }

    private Map<String, Cart> cartCache = new ConcurrentHashMap<>();

    private void saveCart(Cart cart) {
        this.cartCache.put(cart.getUserId(), cart);
    }

    protected Cart getCart(String userId) {
        return cartCache.getOrDefault(userId, new Cart(userId));
    }
}
