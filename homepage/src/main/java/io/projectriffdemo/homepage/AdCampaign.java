package io.projectriffdemo.homepage;

import java.util.List;

public class AdCampaign {
    private String userId;
    private List<Advertisment> ads;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public List<Advertisment> getAds() {
        return ads;
    }

    public void setAds(List<Advertisment> ads) {
        this.ads = ads;
    }

    public AdCampaign(String userId, List<Advertisment> ads) {
        this.userId = userId;
        this.ads = ads;
    }
}
