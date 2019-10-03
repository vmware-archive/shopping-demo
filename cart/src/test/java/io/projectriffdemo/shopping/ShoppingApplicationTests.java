package io.projectriffdemo.shopping;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ShoppingApplicationTests {

    @Test
    public void contextLoads() {
    }


    @Test
    public void updateCart() {
        MyFunction myFunction = new MyFunction();
        Flux<CartEvent> in = Flux.just(
                new CartEvent("1", "i1", "add"),
                new CartEvent("1", "i2", "add"),
                new CartEvent("1", "i3", "add"),
                new CartEvent("1", "i1", "remove")
        );
        Flux<Cart> out = myFunction.apply(in);
        System.out.println("SWAP:result");
        out.subscribe(System.out::println);
    }

    @Test
    public void getCart() {
    }
}

class MyFunction implements Function<Flux<CartEvent>, Flux<Cart>> {
    private Map<String, Cart> cartCache = new ConcurrentHashMap<>();

    private void saveCart(Cart cart) {
        this.cartCache.put(cart.getUserId(), cart);
    }

    protected Cart getCart(String userId) {
        return cartCache.getOrDefault(userId, new Cart(userId));
    }

    @Override
    public Flux<Cart> apply(Flux<CartEvent> cartEventFlux) {
        return cartEventFlux.
                log().
                flatMap(cartEvent -> {
                    Cart cart = getCart(cartEvent.getUserId());
                    cart.applyEvent(cartEvent);
                    saveCart(cart);
                    return Flux.just(cart);
                });


        //return null;
    }
}
//class MyFunction implements Function<Flux<CartEvent>, Flux<Cart>> {
//
//    private Map<String, Cart> cartCache = new ConcurrentHashMap<>();
//
//    private void saveCart(Cart cart) {
//        this.cartCache.put(cart.getUserId(), cart);
//    }
//
//    protected Cart getCart(String userId) {
//        return cartCache.getOrDefault(userId, new Cart(userId));
//    }
//
//    @Override
//    public Flux<Cart> apply(Flux<CartEvent> cartEventFlux) {
//        Set<Cart> carts = new HashSet<>();
//        cartEventFlux.
//                log().
//                map(cartEvent -> {
//                    Cart cart = getCart(cartEvent.getUserId());
//                    cart.applyEvent(cartEvent);
//                    saveCart(cart);
//                    return cart;
//                }).subscribe(carts::add);
//        return Flux.fromIterable(carts);
//    }
//}
