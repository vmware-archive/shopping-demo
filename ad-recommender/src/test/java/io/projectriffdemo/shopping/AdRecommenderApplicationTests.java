package io.projectriffdemo.shopping;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;
import reactor.util.function.Tuple2;
import reactor.util.function.Tuples;

import java.util.function.Function;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AdRecommenderApplicationTests {

    @Autowired
    private Function<Tuple2<Flux<CartEvent>, Flux<AdEvent>>, Flux<UserAd>> function;

    @Test
    public void showAdWhenItemRemoved() {
        Flux<CartEvent> cartEvents = Flux.just(
                new CartEvent("user1", "item1", "add"),
                new CartEvent("user1", "item2", "add"),
                new CartEvent("user1", "item1", "remove")
        );
        Flux<AdEvent> adEvents = Flux.just(new AdEvent("item1", "add", "", "item1 is great!"));

        StepVerifier.create(function.apply(Tuples.of(cartEvents, adEvents)))
                .expectNext(new UserAd("user1", new Ad("item1", true, "item1 is great!")))
                .verifyComplete();
    }

    @Test
    public void showRelatedAdWhenItemAdded() {
        Flux<CartEvent> cartEvents = Flux.just(
                new CartEvent("user1", "drums", "add")
        );
        Flux<AdEvent> adEvents = Flux.just(new AdEvent("guitar", "add", "drums", "guitars are great!"));

        StepVerifier.create(function.apply(Tuples.of(cartEvents, adEvents)))
                .expectNext(new UserAd("user1", new Ad("guitar", true, "guitars are great!")))
                .verifyComplete();
    }

    @Test
    public void doesNotShowAdsMatchingAddedItem() {
        Flux<CartEvent> cartEvents = Flux.just(
                new CartEvent("user1", "drums", "add")
        );
        Flux<AdEvent> adEvents = Flux.just(new AdEvent("drums", "add", "guitars", "drums are great!"));

        StepVerifier.create(function.apply(Tuples.of(cartEvents, adEvents)))
                .verifyComplete();
    }

}
