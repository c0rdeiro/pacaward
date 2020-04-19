package com.europeia.pacaward;

public class Card {
    private String id;
    private String lastNumbers;
    private String expMonth;
    private String expYear;
    private String type;


    public Card(String id, String lastNumbers, String expMonth, String expYear, String type) {
        this.id = id;
        this.lastNumbers = lastNumbers;
        this.expMonth = expMonth;
        this.expYear = expYear;
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public String getDate(){
        return String.format("Exp: %s/%s",this.expMonth,this.expYear.substring(expYear.length()-2));
    }

    public String getLastNumbers() {
        return lastNumbers;
    }



    public String getType() {
        return type;
    }
}
