package io.projectriffdemo.shopping;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.core.ReactiveRedisOperations;
import reactor.core.publisher.Flux;

import java.util.function.Function;

@SpringBootApplication
public class AdsApplication {

    @Autowired
    private ReactiveRedisOperations<String, Ad> redisOperations;

    public static void main(String[] args) {
        SpringApplication.run(AdsApplication.class, args);
    }

    @Bean
    public Function<Flux<AdEvent>, Flux<Ad>> updateAd() {
        return AdEventFlux -> AdEventFlux
                .log()
                .groupBy(AdEvent::getItemId)
                .concatMap(stringAdEventGroupedFlux -> {
                    return stringAdEventGroupedFlux.reduce(getAd(stringAdEventGroupedFlux.key()), (Ad, AdEvent) -> {
                        Ad.applyEvent(AdEvent);
                        return Ad;
                    })
                .doOnNext(this::writeAd);
                });

    }

    protected Ad getAd(String itemId) {
        Ad defaultAd = new Ad(itemId);
        return redisOperations.opsForValue().get("itemId:"+itemId).defaultIfEmpty(defaultAd).onErrorReturn(defaultAd).block();
    }

    protected void writeAd(Ad Ad) {
        redisOperations.opsForValue().set("itemId:"+Ad.getItemId(), Ad).subscribe();
    }

}
