package io.projectriffdemo.shopping;

public class AdEvent {

    private String adId;
    private String description;

    public String getAdId() {
        return adId;
    }

    public void setAdId(String adId) {
        this.adId = adId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "AdEvent{" +
                "adId='" + adId + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
