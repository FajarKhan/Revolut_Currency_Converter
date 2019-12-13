package com.revolut.currencyconverter.utils;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DiffUtil;

import com.revolut.currencyconverter.model.RatesListModel;

import java.util.List;

import static com.revolut.currencyconverter.utils.Constant.BASE_MOVED_CURRENCY_CODE;
import static com.revolut.currencyconverter.utils.Constant.KEY_CURRENCY_NAME;
import static com.revolut.currencyconverter.utils.Constant.KEY_RATE;

/*
 * DiffUtils is used to calculate difference between old and new rates
 * */
public class RatesDiffUtilCallback extends DiffUtil.Callback {
    private List<RatesListModel> newRatesList;
    private List<RatesListModel> oldRatesList;

    public RatesDiffUtilCallback(List<RatesListModel> newList, List<RatesListModel> oldList) {
        this.newRatesList = newList;
        this.oldRatesList = oldList;
    }

    @Override
    public int getOldListSize() {
        return oldRatesList != null ? oldRatesList.size() : 0;
    }

    @Override
    public int getNewListSize() {
        return newRatesList != null ? newRatesList.size() : 0;
    }

    /*
     * Check if our rates are equal by comparing country codes
     * */
    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        return newRatesList.get(newItemPosition).getCountryCode().equals(oldRatesList.get(oldItemPosition).getCountryCode());

    }

    /*
     * if country codes are equal :
     * then we will compare with currency rates
     * check if both currency rates are empty
     * if any of above case is false then we will call payload method to change data otherwise leave it as it is
     * */
    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        if (newRatesList.get(newItemPosition).getCurrencyRate().equals("")
                && oldRatesList.get(oldItemPosition).getCurrencyRate().equals("") && !oldRatesList.get(oldItemPosition).getCountryCode().equals(BASE_MOVED_CURRENCY_CODE))
            return false;
        else
            return newRatesList.get(newItemPosition).getCurrencyRate().equals(oldRatesList.get(oldItemPosition).getCurrencyRate());
    }

    /*
     * if rates are not same:
     * we will send updated rate to payload to refresh list
     * */
    @Nullable
    @Override
    public Object getChangePayload(int oldItemPosition, int newItemPosition) {
        RatesListModel newrate = newRatesList.get(newItemPosition);
        RatesListModel oldrate = oldRatesList.get(oldItemPosition);

        Bundle diff = new Bundle();
        if (!newrate.getCurrencyRate().equals(oldrate.getCurrencyRate())) {
            diff.putString(KEY_RATE, newrate.getCurrencyRate());
            diff.putString(KEY_CURRENCY_NAME, newrate.getCountryCode());
        }
        if (diff.size() == 0) return null;
        return diff;
    }
}