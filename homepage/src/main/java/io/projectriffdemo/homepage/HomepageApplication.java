package io.projectriffdemo.homepage;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import reactor.core.publisher.Flux;
import reactor.util.function.Tuple2;

import java.util.function.Function;

@SpringBootApplication
public class HomepageApplication {

    public static void main(String[] args) {
        SpringApplication.run(HomepageApplication.class, args);
    }

    @Bean
    public Function<Tuple2<Flux<Cart>, Flux<Ads>>, Flux<HomePage>> compileHomepage() {
        return objects -> {
            Flux<Cart> carts = objects.getT1();
            Flux<Ads> ads = objects.getT2();

            return Flux.just(new HomePage());
        };
    }
}
