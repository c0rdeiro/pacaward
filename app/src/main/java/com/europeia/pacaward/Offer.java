package com.europeia.pacaward;

public class Offer {

    private String offerCategory;
    private String brandName;
    private String description;
    private String imageUrl;


    public Offer(String brandName, String description, String imageUrl) {
        this.brandName = brandName;
        this.description = description;
        this.imageUrl = imageUrl;
    }


    public String getOfferCategory() {
        return offerCategory;
    }

    public void setOfferCategory(String offerCategory) {
        this.offerCategory = offerCategory;
    }

    public String getBrandName() {
        return brandName;
    }

    public String getDescription() {
        return description;
    }
    public String getImageUrl() {
        return imageUrl;
    }
}
