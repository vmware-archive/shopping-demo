package io.projectriffdemo.shopping;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.function.Consumer;

@Component
public class AdEventConsumer implements Consumer<AdEvent> {

    private final RestTemplate restTemplate;
    private final String streamUrl;

    @Autowired
    public AdEventConsumer(RestTemplate restTemplate,
                           @Value("${http.gateway.serviceName}") String streamingGatewayServiceName,
                           @Value("${http.gateway.namespace}") String streamingGatewayNamespace,
                           @Value("${streaming.stream.namespace:default}") String streamNamespace,
                           @Value("${streaming.stream.name}") String streamName) {

        this.restTemplate = restTemplate;
        this.streamUrl = String.format("http://%s.%s.svc.cluster.local/%s/%s",
                streamingGatewayServiceName,
                streamingGatewayNamespace,
                streamNamespace,
                streamName
        );
    }

    @Override
    public void accept(AdEvent adEvent) {
        System.out.println("Publishing event:" + adEvent);
        restTemplate.postForLocation(streamUrl, adEvent);
        System.out.println("Published");
    }
}
