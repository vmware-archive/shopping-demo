package io.projectrifffdemo.shopping;

public class CartEvent {
    private String userId;
    private String itemId;
    private String action;

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
