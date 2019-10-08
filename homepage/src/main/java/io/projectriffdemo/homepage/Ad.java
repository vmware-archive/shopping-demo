package io.projectriffdemo.homepage;

public class Ad {
    private String itemId;
    private String message;

    public Ad() {
    }

    public Ad(String itemId) {
        this.itemId = itemId;
    }

    public Ad(String itemId, boolean active, String message) {
        this.itemId = itemId;
        this.message = message;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "Ad{" +
                "itemId='" + itemId + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}
