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
    private Function<Tuple2<Flux<CartEvent>, Flux<AdEvent>>, Flux<Ads>> function;

    @Test
    public void showAddWhenItemRemoved() {
        Flux<CartEvent> cartEvents = Flux.just(
                new CartEvent("1", "i1", "add"),
                new CartEvent("1", "i2", "add"),
                new CartEvent("1", "i1", "remove")
        );
        Flux<AdEvent> adEvents = Flux.just(
                new AdEvent("i1", "add", "", "i1 is great!")
        );

        Ads ads = new Ads();
        ads.addAds(new Ad("i1", true, "i1 is great!"));

        StepVerifier.create(function.apply(Tuples.of(cartEvents, adEvents)))
                .expectNext(ads)
                .verifyComplete();
    }

    @Test
    public void showRelatedAddWhenItemAdded() {
        Flux<CartEvent> cartEvents = Flux.just(
                new CartEvent("1", "drums", "add")
        );
        Flux<AdEvent> adEvents = Flux.just(
                new AdEvent("guitar", "add", "drums", "guitars are great!")
        );

        Ads ads = new Ads();
        ads.addAds(new Ad("guitar", true, "guitars are great!"));

        StepVerifier.create(function.apply(Tuples.of(cartEvents, adEvents)))
                .expectNext(ads)
                .verifyComplete();
    }

}
