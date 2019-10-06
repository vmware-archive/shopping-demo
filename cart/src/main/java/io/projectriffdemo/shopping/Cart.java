package io.projectriffdemo.shopping;

import reactor.core.publisher.Flux;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Cart {
    private String userId;
    private List<Merchandise> items;

    public Cart() {
    }

    public Cart(String userId) {
        this.userId = userId;
        this.items = new ArrayList<>();
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public List<Merchandise> getItems() {
        return items;
    }

    public void setItems(List<Merchandise> items) {
        this.items = items;
    }

    public void addItem(Merchandise item) {
        if (this.items == null) {
            this.items = new ArrayList<>();
        }
        this.items.add(item);
    }

    public void removeItem(String itemId) {
        List<Merchandise> newList = new ArrayList<>();
        Flux<Merchandise> merchandiseFlux = Flux.fromIterable(this.items);
        merchandiseFlux.filter(merchandise -> !merchandise.getItemId().equals(itemId))
                .subscribe(newList::add);
        this.items = newList;
    }

    public void applyEvent(CartEvent event) {
        if (event.getAction() == null) {
            throw new IllegalStateException("unknown action 'null' for cart");
        }
        String action = event.getAction().toLowerCase();
        switch (action) {
            case "add":
                Merchandise item = new Merchandise(event.getItemId());
                item.setPrice(1); // TODO lookup
                item.setDescription(""); // TODO lookup
                this.addItem(item);
                break;
            case "remove":
                this.removeItem(event.getItemId());
                break;
            default:
                throw new IllegalStateException("unknown action " + event.getAction() + " for cart");
        }
    }

    @Override
    public String toString() {
        return "Cart{" +
                "userId='" + userId + '\'' +
                ", items=" + items +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cart cart = (Cart) o;
        return userId.equals(cart.userId) && items.equals(cart.items);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, items);
    }
}
