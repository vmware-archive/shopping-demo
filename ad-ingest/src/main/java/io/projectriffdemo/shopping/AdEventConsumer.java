package io.projectriffdemo.shopping;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.function.Consumer;
import java.util.function.Function;

@Component
public class AdEventConsumer implements Consumer<AdEvent>, Function<AdEvent, String> {

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

    // needs to be a function as the riff java invoker does not support Consumer yet
    @Override
    public String apply(AdEvent adEvent) {
        this.accept(adEvent);
        return "irrelevant";
    }

    @Override
    public void accept(AdEvent adEvent) {
        System.out.println("Publishing event:" + adEvent);
        restTemplate.postForLocation(streamUrl, adEvent);
        System.out.println("Published");
    }
}
