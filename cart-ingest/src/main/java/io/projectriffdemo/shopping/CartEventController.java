package io.projectriffdemo.shopping;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.function.Consumer;

@RestController
public class CartEventController {

    private final Consumer<CartEvent> eventConsumer;

    @Autowired
    public CartEventController(Consumer<CartEvent> eventConsumer) {
        this.eventConsumer = eventConsumer;
    }

    @PostMapping
    public void persist(@RequestBody CartEvent event) {
        eventConsumer.accept(event);
    }
}
