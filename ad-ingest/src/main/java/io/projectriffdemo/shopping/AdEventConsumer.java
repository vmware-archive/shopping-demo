package io.projectriffdemo.shopping;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.client.RestTemplate;

import java.util.function.Consumer;

public class AdEventConsumer implements Consumer<AdEvent> {

    private final RestTemplate restTemplate;
    private final String streamUrl;

    @Autowired
    public AdEventConsumer(RestTemplate restTemplate,
                           @Value("${streaming.gateway}") String streamingGateway,
                           @Value("${streaming.stream.namespace:default}") String streamNamespace,
                           @Value("${streaming.stream.name}") String streamName) {

        this.restTemplate = restTemplate;
        this.streamUrl = String.format("http://%s.riff-system.svc.cluster.local/%s/%s", streamingGateway, streamNamespace, streamName);
    }

    @Override
    public void accept(AdEvent adEvent) {
        System.out.println("Publishing event:" + adEvent);
        restTemplate.postForLocation(streamUrl, adEvent);
        System.out.println("Published");
    }
}
