package com.europeia.pacaward;

public class Transaction {

    private String brandName;
    private String logoUrl;
    private String location;
    private String date;
    private String amountSaved;
    private String amountSpent;
    private String currency;

    public Transaction(String brandName, String logoUrl, String location, String date, String amountSpent, String currency) {
        this.brandName = brandName;
        this.logoUrl = logoUrl;
        this.location = location;
        this.date = date;
        this.amountSpent = amountSpent;
    }

    public String getBrandName() {
        return brandName;
    }

    public String getLogoUrl() {
        return logoUrl;
    }

    public String getLocation() {
        return location;
    }

    public String getDate() {
        return date;
    }

    public String getAmountSpent() {
        return amountSpent;
    }

}
