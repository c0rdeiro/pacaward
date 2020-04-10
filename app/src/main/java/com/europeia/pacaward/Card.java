package com.europeia.pacaward;

public class Card {

    private String lastNumbers;
    private String expMonth;
    private String expYear;
    private String type;

    public Card(String lastNumbers, String expMonth, String expYear, String type) {
        this.lastNumbers = lastNumbers;
        this.expMonth = expMonth;
        this.expYear = expYear;
        this.type = type;
    }

    public String getLastNumbers() {
        return lastNumbers;
    }

    public String getExpMonth() {
        return expMonth;
    }

    public String getExpYear() {
        return expYear;
    }

    public String getType() {
        return type;
    }
}
