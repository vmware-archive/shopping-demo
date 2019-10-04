package io.projectriffdemo.shopping;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;


import java.util.function.Consumer;

@SpringBootApplication
public class CartIngestApplication {

    public final String STREAMING_HTTP_GATEWAY_NAME = "riff-streaming-http-gateway";
    public final String STREAM_NAME = "cart-events";

    public static void main(String[] args) {
        SpringApplication.run(CartIngestApplication.class, args);
    }

    @Bean
    public Consumer<CartEvent> consumeCartEvent() {
        return e -> {
            System.out.println("Consuming event"+ e);
            RestTemplate restTemplate = new RestTemplate();
            restTemplate.postForLocation("http://"+STREAMING_HTTP_GATEWAY_NAME+".riff-system.svc.cluster.local/default/"+STREAM_NAME, e);
            System.out.println("published");
        };
    }
}
