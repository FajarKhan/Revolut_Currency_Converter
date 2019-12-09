package com.revolut.currencyconverter.model;

import android.os.Parcel;
import android.os.Parcelable;

public class RatesListModel implements Parcelable {
    public static final Parcelable.Creator<RatesListModel> CREATOR = new Parcelable.Creator<RatesListModel>() {
        @Override
        public RatesListModel createFromParcel(Parcel source) {
            return new RatesListModel(source);
        }

        @Override
        public RatesListModel[] newArray(int size) {
            return new RatesListModel[size];
        }
    };

    private String countryCode;

    private String currencyName;

    private int countryFlag;

    private String currencyRate;

    public RatesListModel(String countryCode, String currencyName, int countryFlag, String currencyRate) {
        this.countryCode = countryCode;
        this.currencyName = currencyName;
        this.countryFlag = countryFlag;
        this.currencyRate = currencyRate;
    }

    protected RatesListModel(Parcel in) {
        this.countryCode = in.readString();
        this.currencyName = in.readString();
        this.countryFlag = in.readInt();
        this.currencyRate = in.readString();
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getCurrencyName() {
        return currencyName;
    }

    public void setCurrencyName(String currencyName) {
        this.currencyName = currencyName;
    }

    public int getCountryFlag() {
        return countryFlag;
    }

    public void setCountryFlag(int countryFlag) {
        this.countryFlag = countryFlag;
    }

    public String getCurrencyRate() {
        return currencyRate;
    }

    public void setCurrencyRate(String currencyRate) {
        this.currencyRate = currencyRate;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.countryCode);
        dest.writeString(this.currencyName);
        dest.writeInt(this.countryFlag);
        dest.writeString(this.currencyRate);
    }
}
