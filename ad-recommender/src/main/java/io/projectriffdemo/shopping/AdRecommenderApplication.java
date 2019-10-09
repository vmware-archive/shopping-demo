package io.projectriffdemo.shopping;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import reactor.core.publisher.Flux;
import reactor.util.function.Tuple2;

import java.util.Optional;
import java.util.function.Function;

@SpringBootApplication
public class AdRecommenderApplication {

    public static void main(String[] args) {
        SpringApplication.run(AdRecommenderApplication.class, args);
    }

    @Bean
    public Function<Tuple2<Flux<CartEvent>, Flux<AdEvent>>, Flux<UserAd>> adRecommender() {
        return events -> {
            Flux<CartEvent> cartFlux = events.getT1().log("cart events");
            Flux<AdEvent> adFlux = events.getT2().log("cart events");

            return cartFlux
                    .join(
                            adFlux,
                            just2(Flux.never()),
                            just2(Flux.never()),
                            this::computeRelevantAd
                    )
                    .log("maybe relevant ads")
                    .filter(Optional::isPresent)
                    .log("relevant ads")
                    .map(Optional::get)
                    .log("definitely relevant ads");
        };
    }

    private Optional<UserAd> computeRelevantAd(CartEvent cartEvent, AdEvent adEvent) {
        Optional<Ad> maybeAd = Optional.empty();
        if (showMainItemMatchingAd(cartEvent, adEvent)) {
            maybeAd = mainItemAd(adEvent);
        }
        return maybeAd.map(ad -> new UserAd(cartEvent.getUserId(), ad));
    }

    private boolean showMainItemMatchingAd(CartEvent userCartEvent, AdEvent adEvent) {
        String action = userCartEvent.getAction();
        String cartItemId = userCartEvent.getItemId();
        return action.equals("add") && adEvent.getRelatedTo().equals(cartItemId) ||
                action.equals("remove") && adEvent.getItemId().equals(cartItemId);
    }

    private Optional<Ad> mainItemAd(AdEvent adEvent) {
        return Optional.of(new Ad(adEvent.getItemId(), true, adEvent.getMessage()));
    }


    private <T, R> Function<T, Flux<R>> just2(final Flux<R> publisher) {
        return t1 -> publisher;
    }
}
