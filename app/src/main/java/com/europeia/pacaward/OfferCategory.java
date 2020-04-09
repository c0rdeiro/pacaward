package com.europeia.pacaward;

import java.util.ArrayList;

class OfferCategory {

        private ArrayList<Offer> offers;
        private String categoryName;

        public OfferCategory(ArrayList<Offer> offers, String categoryName) {
                this.offers = offers;
                this.categoryName = categoryName;
        }

        public void addOffer(Offer offer){
                offers.add(offer);
        }


        public ArrayList<Offer> getOffers() {
                return offers;
        }


        public String getCategoryName() {
                return categoryName;
        }


}
