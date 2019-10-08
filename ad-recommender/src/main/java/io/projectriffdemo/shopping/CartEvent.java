package io.projectriffdemo.shopping;

public class CartEvent {
    private String userId;
    private String itemId;
    private String action;

    public CartEvent() {
    }

    public CartEvent(String userId, String itemId, String action) {
        this.userId = userId;
        this.itemId = itemId;
        this.action = action;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "CartEvent{" +
                "userId='" + userId + '\'' +
                ", itemId='" + itemId + '\'' +
                ", action='" + action + '\'' +
                '}';
    }

}
