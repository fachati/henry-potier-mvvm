package com.fachati.hp.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by fachati on 08/03/17.
 */

public class Offer implements Parcelable {

    public String type;
    public String value;
    public String sliceValue;

    public Offer(){

    }

    protected Offer(Parcel in) {
        type = in.readString();
        value = in.readString();
        sliceValue = in.readString();
    }

    public static final Creator<Offer> CREATOR = new Creator<Offer>() {
        @Override
        public Offer createFromParcel(Parcel in) {
            return new Offer(in);
        }

        @Override
        public Offer[] newArray(int size) {
            return new Offer[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(type);
        parcel.writeString(value);
        parcel.writeString(sliceValue);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Offer)) return false;

        Offer offer = (Offer) o;

        if (!type.equals(offer.type)) return false;
        if (!value.equals(offer.value)) return false;
        return sliceValue.equals(offer.sliceValue);

    }

    @Override
    public int hashCode() {
        int result = type.hashCode();
        result = 31 * result + value.hashCode();
        result = 31 * result + sliceValue.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Offer{" +
                "type='" + type + '\'' +
                ", value='" + value + '\'' +
                ", sliceValue='" + sliceValue + '\'' +
                '}';
    }
}
