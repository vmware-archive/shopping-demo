package io.projectriffdemo.shopping;

import java.util.Objects;

public class UserAd {

    private final String userId;
    private final Ad ad;

    public UserAd(String userId, Ad ad) {
        this.userId = userId;
        this.ad = ad;
    }

    public String getUserId() {
        return userId;
    }

    public Ad getAd() {
        return ad;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserAd userAd = (UserAd) o;
        return Objects.equals(userId, userAd.userId) &&
                Objects.equals(ad, userAd.ad);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, ad);
    }

    @Override
    public String toString() {
        return "UserAd{" +
                "userId='" + userId + '\'' +
                ", ad=" + ad +
                '}';
    }
}
