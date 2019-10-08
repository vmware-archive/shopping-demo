package io.projectriffdemo.shopping;

import java.util.ArrayList;
import java.util.List;

public class Ads {
    private String userId;
    private List<Ad> ads;

    public Ads() {
        this.ads = new ArrayList<>();
    }

    public Ads(String userId) {
        this.userId = userId;
        this.ads = new ArrayList<>();
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public List<Ad> getAds() {
        return ads;
    }

    public void addAds(Ad ad) {
        this.ads.add(ad);
    }
}
