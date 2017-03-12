package com.fachati.hp.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by fachati on 08/03/17.
 */

public class Offer implements Parcelable {

    public String type;
    public int value;
    public int sliceValue;

    public Offer(){

    }


    protected Offer(Parcel in) {
        type = in.readString();
        value = in.readInt();
        sliceValue = in.readInt();
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
    public String toString() {
        return "Offer{" +
                "type='" + type + '\'' +
                ", value='" + value + '\'' +
                ", sliceValue='" + sliceValue + '\'' +
                '}';
    }

    public String getType() {
        return type;
    }

    public int getValue() {
        return value;
    }

    public int getSliceValue() {
        return sliceValue;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(type);
        parcel.writeInt(value);
        parcel.writeInt(sliceValue);
    }
}
