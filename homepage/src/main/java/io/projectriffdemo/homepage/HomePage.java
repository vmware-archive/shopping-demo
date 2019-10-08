package io.projectriffdemo.homepage;

public class HomePage {
    private String userId;
    private Cart cart;
    private Ads ads;

    public HomePage() {
    }

    public HomePage(String userId) {
        this.userId = userId;
    }

    public HomePage(String userId, Cart cart, Ads ads) {
        this.userId = userId;
        this.cart = cart;
        this.ads = ads;
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

    public Ads getAds() {
        return ads;
    }

    public void setAds(Ads ads) {
        this.ads = ads;
    }
}
