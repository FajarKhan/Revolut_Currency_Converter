package com.revolut.currencyconverter.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.revolut.currencyconverter.model.RatesModel;

import java.lang.reflect.Field;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

import static com.revolut.currencyconverter.utils.Constant.BASE_MOVED_CURRENCY_CODE;
import static com.revolut.currencyconverter.utils.Constant.BASE_MOVED_RATE;
import static com.revolut.currencyconverter.utils.Constant.BASE_RATE;
import static com.revolut.currencyconverter.utils.Constant.EUR;
import static com.revolut.currencyconverter.utils.Constant.EUR_RATE;

public class Utils {
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

    /*
     * method to remove last character when its coma and dot
     * add 0 when character starts with dot
     * */
    public static String formatRate(String value) {
        if (value.length() >= 8) {
            String updatedValue = value.substring(0, 8);
            if (updatedValue.endsWith(",") || updatedValue.endsWith("."))
                updatedValue = updatedValue.substring(0, updatedValue.length() - 1);
            return updatedValue;
        } else if (value.startsWith(".")) {
            StringBuilder stringBuilder = new StringBuilder(value);
            stringBuilder.insert(0, "0");
            return stringBuilder.toString();
        } else {
            return value;
        }
    }
}
