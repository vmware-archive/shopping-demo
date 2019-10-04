package io.projectriffdemo.homepage;

import java.util.List;

public class AdCampaign {
    private String userId;
    private List<Advertisement> ads;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public List<Advertisement> getAds() {
        return ads;
    }

    public void setAds(List<Advertisement> ads) {
        this.ads = ads;
    }

    public AdCampaign(String userId, List<Advertisement> ads) {
        this.userId = userId;
        this.ads = ads;
    }
}
