package io.projectriffdemo.shopping;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.function.Consumer;
import java.util.function.Function;

@Component
public class CartEventConsumer implements Consumer<CartEvent>, Function<CartEvent, String> {

    private final RestTemplate restTemplate;
    private final String streamUrl;

    @Autowired
    public CartEventConsumer(RestTemplate restTemplate,
                             @Value("${streaming.gateway}") String streamingGateway,
                             @Value("${streaming.stream.namespace:default}") String streamNamespace,
                             @Value("${streaming.stream.name}") String streamName) {

        this.restTemplate = restTemplate;
        this.streamUrl = String.format("http://%s.riff-system.svc.cluster.local/%s/%s", streamingGateway, streamNamespace, streamName);
    }

    // needs to be a function as the riff java invoker does not support Consumer yet
    @Override
    public String apply(CartEvent cartEvent) {
        this.accept(cartEvent);
        return "irrelevant";
    }

    @Override
    public void accept(CartEvent cartEvent) {
        System.out.println("Publishing event:" + cartEvent);
        restTemplate.postForLocation(streamUrl, cartEvent);
        System.out.println("Published");
    }
}
