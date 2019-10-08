package io.projectriffdemo.shopping;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import reactor.core.publisher.Flux;
import reactor.util.function.Tuple2;

import java.util.function.Function;

@SpringBootApplication
public class AdRecommenderApplication {

    public static void main(String[] args) {
        SpringApplication.run(AdRecommenderApplication.class, args);
    }

    // should really be a consumer
    public Function<Tuple2<Flux<CartEvent>, Flux<Ad>>, Flux<Ads>> adRecommender() {
        return objects -> {
            Flux<CartEvent> cartFlux = objects.getT1();
            Flux<Ad> adFlux = objects.getT2();

            //cartFlux.join(adFlux)
            return Flux.just(new Ads());
        };
    }
}
