package com.revolut.currencyconverter.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.revolut.currencyconverter.model.RatesModel;

import java.lang.reflect.Field;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;


public class Constant {
    // country code
    public static String EUR = "EUR";
    public static String CHF = "CHF";
    public static String HRK = "HRK";
    public static String MXN = "MXN";
    public static String ZAR = "ZAR";
    public static String INR = "INR";
    public static String CNY = "CNY";
    public static String THB = "THB";
    public static String AUD = "AUD";
    public static String ILS = "ILS";
    public static String KRW = "KRW";
    public static String JPY = "JPY";
    public static String PLN = "PLN";
    public static String GBP = "GBP";
    public static String IDR = "IDR";
    public static String HUF = "HUF";
    public static String PHP = "PHP";
    public static String TRY = "TRY";
    public static String RUB = "RUB";
    public static String HKD = "HKD";
    public static String ISK = "ISK";
    public static String DKK = "DKK";
    public static String CAD = "CAD";
    public static String MYR = "MYR";
    public static String USD = "USD";
    public static String BGN = "BGN";
    public static String NOK = "NOK";
    public static String RON = "RON";
    public static String SGD = "SGD";
    public static String CZK = "CZK";
    public static String SEK = "SEK";
    public static String NZD = "NZD";
    public static String BRL = "BRL";

    // country currency name
    public static String EUR_CURRENCY = "Euro";
    public static String CHF_CURRENCY = "Swiss Franc";
    public static String HRK_CURRENCY = "Croatian Kuna";
    public static String MXN_CURRENCY = "Mexican Peso";
    public static String ZAR_CURRENCY = "South African Rand";
    public static String INR_CURRENCY = "Indian rupee";
    public static String CNY_CURRENCY = "Chinese Yuan";
    public static String THB_CURRENCY = "Thai Baht";
    public static String AUD_CURRENCY = "Australian Dollar";
    public static String ILS_CURRENCY = "Israeli New Shekel";
    public static String KRW_CURRENCY = "South Korean won";
    public static String JPY_CURRENCY = "Japanese Yen";
    public static String PLN_CURRENCY = "Poland złoty";
    public static String GBP_CURRENCY = "Pound sterling";
    public static String IDR_CURRENCY = "Indonesian Rupiah";
    public static String HUF_CURRENCY = "Hungarian Forint";
    public static String PHP_CURRENCY = "Philippine peso";
    public static String TRY_CURRENCY = "Turkish lira";
    public static String RUB_CURRENCY = "Russian Ruble";
    public static String HKD_CURRENCY = "Hong Kong Dollar";
    public static String ISK_CURRENCY = "Icelandic Króna";
    public static String DKK_CURRENCY = "Danish Krone";
    public static String CAD_CURRENCY = "Canadian Dollar";
    public static String MYR_CURRENCY = "Malaysian Ringgit";
    public static String USD_CURRENCY = "United States Dollar";
    public static String BGN_CURRENCY = "Bulgarian Lev";
    public static String NOK_CURRENCY = "Norwegian Krone";
    public static String RON_CURRENCY = "Romanian Leu";
    public static String SGD_CURRENCY = "Singapore Dollar";
    public static String CZK_CURRENCY = "Czech Koruna";
    public static String SEK_CURRENCY = "Swedish Krona";
    public static String NZD_CURRENCY = "New Zealand Dollar";
    public static String BRL_CURRENCY = "Brazilian Real";

    /*
     * reference names
     * */
    public static String BASE_RATE = "100";
    public static String EUR_RATE = "100";
    public static String BASE_MOVED_RATE = "BASE_MOVED_RATE";
    public static String BASE_MOVED_CURRENCY_CODE = "EUR";
    public static String KEY_RATE = "key_rate";
    public static String KEY_CURRENCY_NAME = "key_currency_name";

    /*
     * method to check user internet connection
     * */
    public static boolean checkInternetConnection(Context context) {
        ConnectivityManager connectivity = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity == null) {
            return false;
        } else {
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if (info != null) {
                for (int i = 0; i < info.length; i++) {
                    if (info[i].getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /*
     * method to set currency based on base rate
     * */
    public static String getCurrencyRate(String currency, String countryCode) {
        // remove coma from from string for calculations
        replaceComa();

        // return empty if typed data is 0 and 0.1 if . respectively
        if (BASE_RATE.equals(".")) BASE_RATE = "0.";
        if (BASE_RATE.equals("0"))
            return "";

            /*
             * calculating currency rate using formula when
             *  1- current base is EUR : user typed rate * EUR rate
             *  2- current base is not EUR : user typed rate * (current currency/base rate) * EUR rate
             * */
        else if (BASE_MOVED_CURRENCY_CODE.equals(EUR))
            return (getFormattedText(Float.parseFloat(BASE_RATE) * Float.parseFloat(currency)));
        else
            return (getFormattedText((Float.parseFloat(BASE_RATE) *
                    (Float.parseFloat(currency) / Float.parseFloat(BASE_MOVED_RATE))) * Float.parseFloat(EUR_RATE)));
    }

    /*
     * method to set EUR currency based on base rate
     * */
    public static String getEURRating(RatesModel ratesModel) throws NoSuchFieldException, IllegalAccessException {
        // remove coma from from string for calculations
        replaceComa();

        // return empty if typed data is 0 and 0.1 if . respectively
        if (BASE_RATE.equals(".")) BASE_RATE = "0.";
        if (BASE_RATE.equals("0"))
            return "";

            /*
             * calculating EUR rate using formula when
             *  1- current base is EUR : return EUR rate
             *  2- current base is not EUR : user typed rate * current currency value
             * */
        else if (BASE_MOVED_CURRENCY_CODE.equals(EUR)) {
            return BASE_RATE;
        } else {
            Field selectedCurrency = RatesModel.class.getDeclaredField(BASE_MOVED_CURRENCY_CODE);
            selectedCurrency.setAccessible(true);
            String selectedCurrencyValue = (String) selectedCurrency.get(ratesModel);
            if (selectedCurrencyValue != null)
                return (getFormattedText((Float.parseFloat(BASE_RATE) / Float.parseFloat(selectedCurrencyValue))));
            else
                return "";
        }
    }

    /*
     * method to format currency value by adding coma separation in thousand and setting 2 decimal value
     * */
    public static String getFormattedText(Float value) {
        DecimalFormat formatter = (DecimalFormat) NumberFormat.getInstance(Locale.US);
        formatter.applyPattern("#,###");
        formatter.setMaximumFractionDigits(2);
        return formatter.format(value);
    }

    /*
     * method to remove coma from value for calculation
     * */
    public static void replaceComa() {
        BASE_MOVED_RATE = BASE_MOVED_RATE.replaceAll(",", "");
        BASE_RATE = BASE_RATE.replaceAll(",", "");
    }
}
