package com.revolut.currencyconverter.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RatesResponse implements Parcelable {

    public static final Parcelable.Creator<RatesResponse> CREATOR = new Parcelable.Creator<RatesResponse>() {
        @Override
        public RatesResponse createFromParcel(Parcel source) {
            return new RatesResponse(source);
        }

        @Override
        public RatesResponse[] newArray(int size) {
            return new RatesResponse[size];
        }
    };
    @SerializedName("date")
    @Expose()
    private String date;
    @SerializedName("rates")
    @Expose()
    private RatesModel rates;
    @SerializedName("base")
    @Expose()
    private String base;

    public RatesResponse() {
    }

    protected RatesResponse(Parcel in) {
        this.date = in.readString();
        this.rates = in.readParcelable(RatesModel.class.getClassLoader());
        this.base = in.readString();
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public RatesModel getRates() {
        return rates;
    }

    public void setRates(RatesModel rates) {
        this.rates = rates;
    }

    public String getBase() {
        return base;
    }

    public void setBase(String base) {
        this.base = base;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.date);
        dest.writeParcelable(this.rates, flags);
        dest.writeString(this.base);
    }
}
