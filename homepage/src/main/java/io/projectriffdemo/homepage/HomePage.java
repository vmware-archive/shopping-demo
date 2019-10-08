package io.projectriffdemo.homepage;

public class Home {
    private String userId;
    private Cart cart;
    private AdCampaign ads;

    public Home(String userId, Cart cart, AdCampaign ads) {
        this.userId = userId;
        this.cart = cart;
        this.ads = ads;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Home home = (Home) o;

        return userId.equals(home.userId);

    }

    @Override
    public int hashCode() {
        return userId.hashCode();
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Cart getCart() {
        return cart;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }

    public AdCampaign getAds() {
        return ads;
    }

    public void setAds(AdCampaign ads) {
        this.ads = ads;
    }
}
