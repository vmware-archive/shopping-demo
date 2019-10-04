package io.projectriffdemo.shopping;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.function.context.FunctionCatalog;
import org.springframework.cloud.function.context.test.FunctionalSpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;

@RunWith(SpringRunner.class)
@FunctionalSpringBootTest
public class ShoppingApplicationTests {

    @Autowired
    private FunctionCatalog catalog;

    @Test
    public void updateCartOneCustomerAddItem() {
        Function<Flux<CartEvent>, Flux<Cart>> function = catalog.lookup(Function.class, "updateCart");
        Flux<CartEvent> in = Flux.just(
                new CartEvent("1", "i1", "add"),
                new CartEvent("1", "i2", "add")
        );

        Cart cartexpected1 = new Cart("1");
        cartexpected1.addItem(new Merchandise("i1"));

        Cart cartexpected2 = new Cart("1");
        cartexpected2.addItem(new Merchandise("i1"));
        cartexpected2.addItem(new Merchandise("i2"));

        StepVerifier.create(function.apply(in))
                .expectNext(cartexpected1)
                .expectNext(cartexpected2)
                .verifyComplete();
    }

    @Test
    public void updateCartOneCustomerRemoveItem() {
        Function<Flux<CartEvent>, Flux<Cart>> function = catalog.lookup(Function.class, "updateCart");
        Flux<CartEvent> in = Flux.just(
                new CartEvent("1", "i1", "add"),
                new CartEvent("1", "i2", "add"),
                new CartEvent("1", "i1", "remove")
        );

        Cart cartexpected1 = new Cart("1");
        cartexpected1.addItem(new Merchandise("i1"));

        Cart cartexpected2 = new Cart("1");
        cartexpected2.addItem(new Merchandise("i1"));
        cartexpected2.addItem(new Merchandise("i2"));

        Cart cartexpected3 = new Cart("1");
        cartexpected3.addItem(new Merchandise("i2"));

        StepVerifier.create(function.apply(in))
                .expectNext(cartexpected1)
                .expectNext(cartexpected2)
                .expectNext(cartexpected3)
                .verifyComplete();
    }

    @Test
    public void updateCartOneCustomerEmptyCart() {
        Function<Flux<CartEvent>, Flux<Cart>> function = catalog.lookup(Function.class, "updateCart");
        Flux<CartEvent> in = Flux.just(
                new CartEvent("1", "i1", "add"),
                new CartEvent("1", "i1", "remove")
        );

        Cart cartexpected1 = new Cart("1");
        cartexpected1.addItem(new Merchandise("i1"));

        Cart cartexpected2 = new Cart("1");

        StepVerifier.create(function.apply(in))
                .expectNext(cartexpected1)
                .expectNext(cartexpected2)
                .verifyComplete();
    }

    @Test
    public void updateCartTwoCustomers() {
        Function<Flux<CartEvent>, Flux<Cart>> function = catalog.lookup(Function.class, "updateCart");
        Flux<CartEvent> in = Flux.just(
                new CartEvent("1", "i1", "add"),
                new CartEvent("2", "i1", "add"),
                new CartEvent("1", "i2", "add"),
                new CartEvent("1", "i1", "remove")
        );

        Cart cartexpected1 = new Cart("1");
        cartexpected1.addItem(new Merchandise("i1"));

        Cart cartexpected2 = new Cart("2");
        cartexpected2.addItem(new Merchandise("i1"));

        Cart cartexpected3 = new Cart("1");
        cartexpected3.addItem(new Merchandise("i1"));
        cartexpected3.addItem(new Merchandise("i2"));

        Cart cartexpected4 = new Cart("1");
        cartexpected4.addItem(new Merchandise("i2"));

        StepVerifier.create(function.apply(in))
                .expectNext(cartexpected1)
                .expectNext(cartexpected2)
                .expectNext(cartexpected3)
                .expectNext(cartexpected4)
                .verifyComplete();
    }
}
