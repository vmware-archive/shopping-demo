package io.projectriffdemo.shopping;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.function.Consumer;

@Component
public class CartEventConsumer implements Consumer<CartEvent> {


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

    @Override
    public void accept(CartEvent cartEvent) {
        System.out.println("Publishing event:" + cartEvent);
        restTemplate.postForLocation(streamUrl, cartEvent);
        System.out.println("Published");
    }
}
