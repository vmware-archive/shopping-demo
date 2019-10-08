package io.projectriffdemo.shopping;

public class AdEvent {

    private String itemId;
    private String action;
    private String relatedTo;
    private String message;

    public AdEvent() {
    }

    public AdEvent(String itemId, String action, String relatedTo, String message) {
        this.itemId = itemId;
        this.action = action;
        this.relatedTo = relatedTo;
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

    public String getRelatedTo() {
        return relatedTo;
    }

    public void setRelatedTo(String relatedTo) {
        this.relatedTo = relatedTo;
    }

    @Override
    public String toString() {
        return "AdEvent{" +
                "itemId='" + itemId + '\'' +
                ", action='" + action + '\'' +
                ", relatedTo='" + relatedTo + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}
