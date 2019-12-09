package com.revolut.currencyconverter.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RatesModel implements Parcelable {

    public static final Parcelable.Creator<RatesModel> CREATOR = new Parcelable.Creator<RatesModel>() {
        @Override
        public RatesModel createFromParcel(Parcel source) {
            return new RatesModel(source);
        }

        @Override
        public RatesModel[] newArray(int size) {
            return new RatesModel[size];
        }
    };
    @SerializedName("CHF")
    @Expose()
    private String CHF;
    @SerializedName("HRK")
    @Expose()
    private String HRK;
    @SerializedName("MXN")
    @Expose()
    private String MXN;
    @SerializedName("ZAR")
    @Expose()
    private String ZAR;
    @SerializedName("INR")
    @Expose()
    private String INR;
    @SerializedName("CNY")
    @Expose()
    private String CNY;
    @SerializedName("THB")
    @Expose()
    private String THB;
    @SerializedName("AUD")
    @Expose()
    private String AUD;
    @SerializedName("ILS")
    @Expose()
    private String ILS;
    @SerializedName("KRW")
    @Expose()
    private String KRW;
    @SerializedName("JPY")
    @Expose()
    private String JPY;
    @SerializedName("PLN")
    @Expose()
    private String PLN;
    @SerializedName("GBP")
    @Expose()
    private String GBP;
    @SerializedName("IDR")
    @Expose()
    private String IDR;
    @SerializedName("HUF")
    @Expose()
    private String HUF;
    @SerializedName("PHP")
    @Expose()
    private String PHP;
    @SerializedName("TRY")
    @Expose()
    private String TRY;
    @SerializedName("RUB")
    @Expose()
    private String RUB;
    @SerializedName("HKD")
    @Expose()
    private String HKD;
    @SerializedName("ISK")
    @Expose()
    private String ISK;
    @SerializedName("DKK")
    @Expose()
    private String DKK;
    @SerializedName("CAD")
    @Expose()
    private String CAD;
    @SerializedName("MYR")
    @Expose()
    private String MYR;
    @SerializedName("USD")
    @Expose()
    private String USD;
    @SerializedName("BGN")
    @Expose()
    private String BGN;
    @SerializedName("NOK")
    @Expose()
    private String NOK;
    @SerializedName("RON")
    @Expose()
    private String RON;
    @SerializedName("SGD")
    @Expose()
    private String SGD;
    @SerializedName("CZK")
    @Expose()
    private String CZK;
    @SerializedName("SEK")
    @Expose()
    private String SEK;
    @SerializedName("NZD")
    @Expose()
    private String NZD;
    @SerializedName("BRL")
    @Expose()
    private String BRL;

    public RatesModel() {
    }

    protected RatesModel(Parcel in) {
        this.CHF = in.readString();
        this.HRK = in.readString();
        this.MXN = in.readString();
        this.ZAR = in.readString();
        this.INR = in.readString();
        this.CNY = in.readString();
        this.THB = in.readString();
        this.AUD = in.readString();
        this.ILS = in.readString();
        this.KRW = in.readString();
        this.JPY = in.readString();
        this.PLN = in.readString();
        this.GBP = in.readString();
        this.IDR = in.readString();
        this.HUF = in.readString();
        this.PHP = in.readString();
        this.TRY = in.readString();
        this.RUB = in.readString();
        this.HKD = in.readString();
        this.ISK = in.readString();
        this.DKK = in.readString();
        this.CAD = in.readString();
        this.MYR = in.readString();
        this.USD = in.readString();
        this.BGN = in.readString();
        this.NOK = in.readString();
        this.RON = in.readString();
        this.SGD = in.readString();
        this.CZK = in.readString();
        this.SEK = in.readString();
        this.NZD = in.readString();
        this.BRL = in.readString();
    }

    public String getCHF() {
        return CHF;
    }

    public void setCHF(String CHF) {
        this.CHF = CHF;
    }

    public String getHRK() {
        return HRK;
    }

    public void setHRK(String HRK) {
        this.HRK = HRK;
    }

    public String getMXN() {
        return MXN;
    }

    public void setMXN(String MXN) {
        this.MXN = MXN;
    }

    public String getZAR() {
        return ZAR;
    }

    public void setZAR(String ZAR) {
        this.ZAR = ZAR;
    }

    public String getINR() {
        return INR;
    }

    public void setINR(String INR) {
        this.INR = INR;
    }

    public String getCNY() {
        return CNY;
    }

    public void setCNY(String CNY) {
        this.CNY = CNY;
    }

    public String getTHB() {
        return THB;
    }

    public void setTHB(String THB) {
        this.THB = THB;
    }

    public String getAUD() {
        return AUD;
    }

    public void setAUD(String AUD) {
        this.AUD = AUD;
    }

    public String getILS() {
        return ILS;
    }

    public void setILS(String ILS) {
        this.ILS = ILS;
    }

    public String getKRW() {
        return KRW;
    }

    public void setKRW(String KRW) {
        this.KRW = KRW;
    }

    public String getJPY() {
        return JPY;
    }

    public void setJPY(String JPY) {
        this.JPY = JPY;
    }

    public String getPLN() {
        return PLN;
    }

    public void setPLN(String PLN) {
        this.PLN = PLN;
    }

    public String getGBP() {
        return GBP;
    }

    public void setGBP(String GBP) {
        this.GBP = GBP;
    }

    public String getIDR() {
        return IDR;
    }

    public void setIDR(String IDR) {
        this.IDR = IDR;
    }

    public String getHUF() {
        return HUF;
    }

    public void setHUF(String HUF) {
        this.HUF = HUF;
    }

    public String getPHP() {
        return PHP;
    }

    public void setPHP(String PHP) {
        this.PHP = PHP;
    }

    public String getTRY() {
        return TRY;
    }

    public void setTRY(String TRY) {
        this.TRY = TRY;
    }

    public String getRUB() {
        return RUB;
    }

    public void setRUB(String RUB) {
        this.RUB = RUB;
    }

    public String getHKD() {
        return HKD;
    }

    public void setHKD(String HKD) {
        this.HKD = HKD;
    }

    public String getISK() {
        return ISK;
    }

    public void setISK(String ISK) {
        this.ISK = ISK;
    }

    public String getDKK() {
        return DKK;
    }

    public void setDKK(String DKK) {
        this.DKK = DKK;
    }

    public String getCAD() {
        return CAD;
    }

    public void setCAD(String CAD) {
        this.CAD = CAD;
    }

    public String getMYR() {
        return MYR;
    }

    public void setMYR(String MYR) {
        this.MYR = MYR;
    }

    public String getUSD() {
        return USD;
    }

    public void setUSD(String USD) {
        this.USD = USD;
    }

    public String getBGN() {
        return BGN;
    }

    public void setBGN(String BGN) {
        this.BGN = BGN;
    }

    public String getNOK() {
        return NOK;
    }

    public void setNOK(String NOK) {
        this.NOK = NOK;
    }

    public String getRON() {
        return RON;
    }

    public void setRON(String RON) {
        this.RON = RON;
    }

    public String getSGD() {
        return SGD;
    }

    public void setSGD(String SGD) {
        this.SGD = SGD;
    }

    public String getCZK() {
        return CZK;
    }

    public void setCZK(String CZK) {
        this.CZK = CZK;
    }

    public String getSEK() {
        return SEK;
    }

    public void setSEK(String SEK) {
        this.SEK = SEK;
    }

    public String getNZD() {
        return NZD;
    }

    public void setNZD(String NZD) {
        this.NZD = NZD;
    }

    public String getBRL() {
        return BRL;
    }

    public void setBRL(String BRL) {
        this.BRL = BRL;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.CHF);
        dest.writeString(this.HRK);
        dest.writeString(this.MXN);
        dest.writeString(this.ZAR);
        dest.writeString(this.INR);
        dest.writeString(this.CNY);
        dest.writeString(this.THB);
        dest.writeString(this.AUD);
        dest.writeString(this.ILS);
        dest.writeString(this.KRW);
        dest.writeString(this.JPY);
        dest.writeString(this.PLN);
        dest.writeString(this.GBP);
        dest.writeString(this.IDR);
        dest.writeString(this.HUF);
        dest.writeString(this.PHP);
        dest.writeString(this.TRY);
        dest.writeString(this.RUB);
        dest.writeString(this.HKD);
        dest.writeString(this.ISK);
        dest.writeString(this.DKK);
        dest.writeString(this.CAD);
        dest.writeString(this.MYR);
        dest.writeString(this.USD);
        dest.writeString(this.BGN);
        dest.writeString(this.NOK);
        dest.writeString(this.RON);
        dest.writeString(this.SGD);
        dest.writeString(this.CZK);
        dest.writeString(this.SEK);
        dest.writeString(this.NZD);
        dest.writeString(this.BRL);
    }
}
