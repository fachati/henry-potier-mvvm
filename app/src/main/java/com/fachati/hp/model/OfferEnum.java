package com.fachati.hp.model;

/**
 * Created by ismailfachati on 11/03/2017.
 */
public enum OfferEnum {

    PERCENTAGE("percentage"),
    MINUS("minus"),
    SLICE("slice");

    private String offer;

    OfferEnum(String text) {
        this.offer = text;
    }

    public String getText() {
        return this.offer;
    }

    public static OfferEnum fromString(String text) {
        for (OfferEnum b : OfferEnum.values()) {
            if (b.offer.equalsIgnoreCase(text)) {
                return b;
            }
        }
        return null;
    }

}
