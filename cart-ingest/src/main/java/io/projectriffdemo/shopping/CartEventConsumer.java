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
                             @Value("${http.gateway.serviceName}") String gatewayServiceName,
                             @Value("${http.gateway.namespace}") String gatewayNamespace,
                             @Value("${streaming.stream.namespace:default}") String streamNamespace,
                             @Value("${streaming.stream.name}") String streamName) {

        this.restTemplate = restTemplate;
        this.streamUrl = String.format("http://%s.%s.svc.cluster.local/%s/%s",
                gatewayServiceName,
                gatewayNamespace,
                streamNamespace,
                streamName
        );
    }

    @Override
    public void accept(CartEvent cartEvent) {
        System.out.println("Publishing event:" + cartEvent);
        restTemplate.postForLocation(streamUrl, cartEvent);
        System.out.println("Published");
    }
}
