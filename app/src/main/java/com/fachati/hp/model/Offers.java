package com.fachati.hp.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Arrays;

/**
 * Created by fachati on 08/03/17.
 */

public class Offers implements Parcelable{

    public Offer[] offers;


    protected Offers(Parcel in) {
        offers = in.createTypedArray(Offer.CREATOR);
    }

    public static final Creator<Offers> CREATOR = new Creator<Offers>() {
        @Override
        public Offers createFromParcel(Parcel in) {
            return new Offers(in);
        }

        @Override
        public Offers[] newArray(int size) {
            return new Offers[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeTypedArray(offers, i);
    }

    @Override
    public String toString() {
        return "Offers{" +
                "offers=" + Arrays.toString(offers) +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Offers)) return false;

        Offers offers1 = (Offers) o;

        // Probably incorrect - comparing Object[] arrays with Arrays.equals
        return Arrays.equals(offers, offers1.offers);

    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(offers);
    }

    public Offer[] getOffers() {
        return offers;
    }
}
