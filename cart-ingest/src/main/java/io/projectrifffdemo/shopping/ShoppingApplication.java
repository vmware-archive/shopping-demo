package io.projectrifffdemo.shopping;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;


import java.util.function.Consumer;

@SpringBootApplication
public class ShoppingApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShoppingApplication.class, args);
    }

    @Bean
    public Consumer<CartEvent> consumeCartEvent() {
        return e -> {
            System.out.println("Consuming event"+ e);
            RestTemplate restTemplate = new RestTemplate();
            restTemplate.postForLocation("http://riff-streaming-http-gateway.riff-system.svc.cluster.local/default/cart-events", e);
            System.out.println("published");
        };
    }
}
