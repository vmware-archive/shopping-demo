package io.projectriffdemo.shopping;

import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.util.function.Tuple2;

import java.util.Optional;
import java.util.function.Function;

@Component
public class AdRecommenderFunction implements Function<Tuple2<Flux<CartEvent>, Flux<AdEvent>>, Flux<UserAd>> {

    @Override
    public Flux<UserAd> apply(Tuple2<Flux<CartEvent>, Flux<AdEvent>> events) {
        Flux<CartEvent> cartFlux = events.getT1();
        Flux<AdEvent> adFlux = events.getT2();

        return cartFlux
                .join(
                        adFlux,
                        just2(Flux.never()),
                        just2(Flux.never()),
                        this::computeRelevantAd
                )
                .filter(Optional::isPresent)
                .map(Optional::get);
    }

    private Optional<UserAd> computeRelevantAd(CartEvent cartEvent, AdEvent adEvent) {
        Optional<Ad> maybeAd = Optional.empty();
        if (showMainItemMatchingAd(cartEvent, adEvent)) {
            maybeAd = mainItemAd(adEvent);
        }
        if (showRelatedItemMatchingAd(cartEvent, adEvent)) {
            maybeAd = relatedItemAd(adEvent);
        }
        return maybeAd.map(ad -> new UserAd(cartEvent.getUserId(), ad));
    }

    private boolean showMainItemMatchingAd(CartEvent userCartEvent, AdEvent adEvent) {
        String action = userCartEvent.getAction();
        String cartItemId = userCartEvent.getItemId();
        return action.equals("add") && adEvent.getRelatedTo().equals(cartItemId) ||
                action.equals("remove") && adEvent.getItemId().equals(cartItemId);
    }

    private boolean showRelatedItemMatchingAd(CartEvent userCartEvent, AdEvent adEvent) {
        String action = userCartEvent.getAction();
        String cartItemId = userCartEvent.getItemId();
        return action.equals("add") && adEvent.getItemId().equals(cartItemId) ||
                action.equals("remove") && adEvent.getRelatedTo().equals(cartItemId);
    }

    private Optional<Ad> relatedItemAd(AdEvent adEvent) {
        String relatedItem = adEvent.getRelatedTo();
        if (relatedItem.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(new Ad(relatedItem, true, adEvent.getMessage()));
    }

    private Optional<Ad> mainItemAd(AdEvent adEvent) {
        return Optional.of(new Ad(adEvent.getItemId(), true, adEvent.getMessage()));
    }


    private <T, R> Function<T, Flux<R>> just2(final Flux<R> publisher) {
        return t1 -> publisher;
    }
}
