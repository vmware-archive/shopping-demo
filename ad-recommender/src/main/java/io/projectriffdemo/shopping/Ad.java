package io.projectriffdemo.shopping;

import java.util.ArrayList;
import java.util.List;

public class Ad {
    private String itemId;
    private boolean active;
    private List<String> relatesTo;
    private String message;

    public Ad() {
    }

    public Ad(String itemId) {
        this.itemId = itemId;
    }

    public Ad(String itemId, boolean active, String message) {
        this.itemId = itemId;
        this.active = active;
        this.message = message;
        this.relatesTo = new ArrayList<>();
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public List<String> getRelatesTo() {
        return relatesTo;
    }

    public void addRelatesTo(String relatesToItemId) {
        this.relatesTo.add(relatesToItemId);
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    @Override
    public String toString() {
        return "Ad{" +
                "itemId='" + itemId + '\'' +
                ", active=" + active +
                ", relatesTo=" + relatesTo +
                ", message='" + message + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Ad ad = (Ad) o;

        if (active != ad.active) return false;
        if (!itemId.equals(ad.itemId)) return false;
        if (!relatesTo.equals(ad.relatesTo)) return false;
        return message != null ? message.equals(ad.message) : ad.message == null;

    }

    @Override
    public int hashCode() {
        int result = itemId.hashCode();
        result = 31 * result + (active ? 1 : 0);
        result = 31 * result + relatesTo.hashCode();
        result = 31 * result + (message != null ? message.hashCode() : 0);
        return result;
    }
}
