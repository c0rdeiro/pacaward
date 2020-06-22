package com.europeia.pacaward;

import java.io.Serializable;

public class Offer implements Serializable {

    private String offerCategory;
    private String id;
    private String brandName;
    private String title;
    private String imageUrl;


    public Offer(String id, String brandName, String title, String imageUrl) {
        this.id = id;
        this.brandName = brandName;
        this.title = title;
        this.imageUrl = imageUrl;
    }


    public String getOfferCategory() {
        return offerCategory;
    }

    public void setOfferCategory(String offerCategory) {
        this.offerCategory = offerCategory;
    }
    public String getId(){return id;}
    public String getBrandName() {
        return brandName;
    }

    public String getTitle() {
        return title;
    }
    public String getImageUrl() {
        return imageUrl;
    }
}
