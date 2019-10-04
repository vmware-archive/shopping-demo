package io.projectriffdemo.shopping;

import java.util.Objects;

public class Merchandise {
    private String itemId;
    private String description;
    private int price;

    public Merchandise(String itemId) {
        this.itemId = itemId;
    }

    public Merchandise(String itemId, String description, int price) {
        this.itemId = itemId;
        this.description = description;
        this.price = price;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Merchandise{" +
                "itemId='" + itemId + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Merchandise that = (Merchandise) o;
        return itemId.equals(that.itemId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(itemId);
    }
}
