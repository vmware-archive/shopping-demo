package io.projectriffdemo.shopping;

public class AdEvent {

    private String itemId;
    private String action;
    private String message;

    public AdEvent() {
    }

    public AdEvent(String itemId, String action, String message) {
        this.itemId = itemId;
        this.action = action;
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

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    @Override
    public String toString() {
        return "AdEvent{" +
                "itemId='" + itemId + '\'' +
                ", action='" + action + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}
