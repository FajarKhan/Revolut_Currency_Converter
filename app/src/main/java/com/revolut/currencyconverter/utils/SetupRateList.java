package com.revolut.currencyconverter.utils;

import com.revolut.currencyconverter.R;
import com.revolut.currencyconverter.model.RatesListModel;
import com.revolut.currencyconverter.model.RatesModel;

import java.util.ArrayList;
import java.util.List;

import static com.revolut.currencyconverter.utils.Constant.AUD;
import static com.revolut.currencyconverter.utils.Constant.AUD_CURRENCY;
import static com.revolut.currencyconverter.utils.Constant.BGN;
import static com.revolut.currencyconverter.utils.Constant.BGN_CURRENCY;
import static com.revolut.currencyconverter.utils.Constant.BRL;
import static com.revolut.currencyconverter.utils.Constant.BRL_CURRENCY;
import static com.revolut.currencyconverter.utils.Constant.CAD;
import static com.revolut.currencyconverter.utils.Constant.CAD_CURRENCY;
import static com.revolut.currencyconverter.utils.Constant.CHF;
import static com.revolut.currencyconverter.utils.Constant.CHF_CURRENCY;
import static com.revolut.currencyconverter.utils.Constant.CNY;
import static com.revolut.currencyconverter.utils.Constant.CNY_CURRENCY;
import static com.revolut.currencyconverter.utils.Constant.CZK;
import static com.revolut.currencyconverter.utils.Constant.CZK_CURRENCY;
import static com.revolut.currencyconverter.utils.Constant.DKK;
import static com.revolut.currencyconverter.utils.Constant.DKK_CURRENCY;
import static com.revolut.currencyconverter.utils.Constant.EUR;
import static com.revolut.currencyconverter.utils.Constant.EUR_CURRENCY;
import static com.revolut.currencyconverter.utils.Constant.GBP;
import static com.revolut.currencyconverter.utils.Constant.GBP_CURRENCY;
import static com.revolut.currencyconverter.utils.Constant.HKD;
import static com.revolut.currencyconverter.utils.Constant.HKD_CURRENCY;
import static com.revolut.currencyconverter.utils.Constant.HRK;
import static com.revolut.currencyconverter.utils.Constant.HRK_CURRENCY;
import static com.revolut.currencyconverter.utils.Constant.HUF;
import static com.revolut.currencyconverter.utils.Constant.HUF_CURRENCY;
import static com.revolut.currencyconverter.utils.Constant.IDR;
import static com.revolut.currencyconverter.utils.Constant.IDR_CURRENCY;
import static com.revolut.currencyconverter.utils.Constant.ILS;
import static com.revolut.currencyconverter.utils.Constant.ILS_CURRENCY;
import static com.revolut.currencyconverter.utils.Constant.INR;
import static com.revolut.currencyconverter.utils.Constant.INR_CURRENCY;
import static com.revolut.currencyconverter.utils.Constant.ISK;
import static com.revolut.currencyconverter.utils.Constant.ISK_CURRENCY;
import static com.revolut.currencyconverter.utils.Constant.JPY;
import static com.revolut.currencyconverter.utils.Constant.JPY_CURRENCY;
import static com.revolut.currencyconverter.utils.Constant.KRW;
import static com.revolut.currencyconverter.utils.Constant.KRW_CURRENCY;
import static com.revolut.currencyconverter.utils.Constant.MXN;
import static com.revolut.currencyconverter.utils.Constant.MXN_CURRENCY;
import static com.revolut.currencyconverter.utils.Constant.MYR;
import static com.revolut.currencyconverter.utils.Constant.MYR_CURRENCY;
import static com.revolut.currencyconverter.utils.Constant.NOK;
import static com.revolut.currencyconverter.utils.Constant.NOK_CURRENCY;
import static com.revolut.currencyconverter.utils.Constant.NZD;
import static com.revolut.currencyconverter.utils.Constant.NZD_CURRENCY;
import static com.revolut.currencyconverter.utils.Constant.PHP;
import static com.revolut.currencyconverter.utils.Constant.PHP_CURRENCY;
import static com.revolut.currencyconverter.utils.Constant.PLN;
import static com.revolut.currencyconverter.utils.Constant.PLN_CURRENCY;
import static com.revolut.currencyconverter.utils.Constant.RON;
import static com.revolut.currencyconverter.utils.Constant.RON_CURRENCY;
import static com.revolut.currencyconverter.utils.Constant.RUB;
import static com.revolut.currencyconverter.utils.Constant.RUB_CURRENCY;
import static com.revolut.currencyconverter.utils.Constant.SEK;
import static com.revolut.currencyconverter.utils.Constant.SEK_CURRENCY;
import static com.revolut.currencyconverter.utils.Constant.SGD;
import static com.revolut.currencyconverter.utils.Constant.SGD_CURRENCY;
import static com.revolut.currencyconverter.utils.Constant.THB;
import static com.revolut.currencyconverter.utils.Constant.THB_CURRENCY;
import static com.revolut.currencyconverter.utils.Constant.TRY;
import static com.revolut.currencyconverter.utils.Constant.TRY_CURRENCY;
import static com.revolut.currencyconverter.utils.Constant.USD;
import static com.revolut.currencyconverter.utils.Constant.USD_CURRENCY;
import static com.revolut.currencyconverter.utils.Constant.ZAR;
import static com.revolut.currencyconverter.utils.Constant.ZAR_CURRENCY;
import static com.revolut.currencyconverter.utils.Utils.getCurrencyRate;
import static com.revolut.currencyconverter.utils.Utils.getEURRating;

public class SetupRateList {

    /*
     * method to setup currency list
     * */
    public List<RatesListModel> addData(RatesModel ratesModel) {
        List<RatesListModel> ratesListModels = new ArrayList<>();
        try {
            ratesListModels.add(new RatesListModel(EUR, EUR_CURRENCY, R.drawable.ic_eu_flag, getEURRating(ratesModel)));
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
        ratesListModels.add(new RatesListModel(USD, USD_CURRENCY, R.drawable.ic_us_flag, getCurrencyRate(ratesModel.getUSD(), USD, ratesModel)));
        ratesListModels.add(new RatesListModel(CAD, CAD_CURRENCY, R.drawable.ic_canada_flag, getCurrencyRate(ratesModel.getCAD(), CAD, ratesModel)));
        ratesListModels.add(new RatesListModel(SEK, SEK_CURRENCY, R.drawable.ic_sweden_flag, getCurrencyRate(ratesModel.getSEK(), SEK, ratesModel)));
        ratesListModels.add(new RatesListModel(CHF, CHF_CURRENCY, 0, getCurrencyRate(ratesModel.getCHF(), CHF, ratesModel)));
        ratesListModels.add(new RatesListModel(HRK, HRK_CURRENCY, 0, getCurrencyRate(ratesModel.getHRK(), HRK, ratesModel)));
        ratesListModels.add(new RatesListModel(MXN, MXN_CURRENCY, 0, getCurrencyRate(ratesModel.getMXN(), MXN, ratesModel)));
        ratesListModels.add(new RatesListModel(ZAR, ZAR_CURRENCY, 0, getCurrencyRate(ratesModel.getZAR(), ZAR, ratesModel)));
        ratesListModels.add(new RatesListModel(INR, INR_CURRENCY, 0, getCurrencyRate(ratesModel.getINR(), INR, ratesModel)));
        ratesListModels.add(new RatesListModel(CNY, CNY_CURRENCY, 0, getCurrencyRate(ratesModel.getCNY(), CNY, ratesModel)));
        ratesListModels.add(new RatesListModel(THB, THB_CURRENCY, 0, getCurrencyRate(ratesModel.getTHB(), THB, ratesModel)));
        ratesListModels.add(new RatesListModel(AUD, AUD_CURRENCY, 0, getCurrencyRate(ratesModel.getAUD(), AUD, ratesModel)));
        ratesListModels.add(new RatesListModel(ILS, ILS_CURRENCY, 0, getCurrencyRate(ratesModel.getILS(), ILS, ratesModel)));
        ratesListModels.add(new RatesListModel(KRW, KRW_CURRENCY, 0, getCurrencyRate(ratesModel.getKRW(), KRW, ratesModel)));
        ratesListModels.add(new RatesListModel(JPY, JPY_CURRENCY, 0, getCurrencyRate(ratesModel.getJPY(), JPY, ratesModel)));
        ratesListModels.add(new RatesListModel(PLN, PLN_CURRENCY, 0, getCurrencyRate(ratesModel.getPLN(), PLN, ratesModel)));
        ratesListModels.add(new RatesListModel(GBP, GBP_CURRENCY, 0, getCurrencyRate(ratesModel.getGBP(), GBP, ratesModel)));
        ratesListModels.add(new RatesListModel(IDR, IDR_CURRENCY, 0, getCurrencyRate(ratesModel.getIDR(), IDR, ratesModel)));
        ratesListModels.add(new RatesListModel(HUF, HUF_CURRENCY, 0, getCurrencyRate(ratesModel.getHUF(), HUF, ratesModel)));
        ratesListModels.add(new RatesListModel(PHP, PHP_CURRENCY, 0, getCurrencyRate(ratesModel.getPHP(), PHP, ratesModel)));
        ratesListModels.add(new RatesListModel(TRY, TRY_CURRENCY, 0, getCurrencyRate(ratesModel.getTRY(), TRY, ratesModel)));
        ratesListModels.add(new RatesListModel(RUB, RUB_CURRENCY, 0, getCurrencyRate(ratesModel.getRUB(), RUB, ratesModel)));
        ratesListModels.add(new RatesListModel(HKD, HKD_CURRENCY, 0, getCurrencyRate(ratesModel.getHKD(), HKD, ratesModel)));
        ratesListModels.add(new RatesListModel(ISK, ISK_CURRENCY, 0, getCurrencyRate(ratesModel.getISK(), ISK, ratesModel)));
        ratesListModels.add(new RatesListModel(DKK, DKK_CURRENCY, 0, getCurrencyRate(ratesModel.getDKK(), DKK, ratesModel)));
        ratesListModels.add(new RatesListModel(MYR, MYR_CURRENCY, 0, getCurrencyRate(ratesModel.getMYR(), MYR, ratesModel)));
        ratesListModels.add(new RatesListModel(BGN, BGN_CURRENCY, 0, getCurrencyRate(ratesModel.getBGN(), BGN, ratesModel)));
        ratesListModels.add(new RatesListModel(NOK, NOK_CURRENCY, 0, getCurrencyRate(ratesModel.getNOK(), NOK, ratesModel)));
        ratesListModels.add(new RatesListModel(RON, RON_CURRENCY, 0, getCurrencyRate(ratesModel.getRON(), RON, ratesModel)));
        ratesListModels.add(new RatesListModel(SGD, SGD_CURRENCY, 0, getCurrencyRate(ratesModel.getSGD(), SGD, ratesModel)));
        ratesListModels.add(new RatesListModel(CZK, CZK_CURRENCY, 0, getCurrencyRate(ratesModel.getCZK(), CZK, ratesModel)));
        ratesListModels.add(new RatesListModel(NZD, NZD_CURRENCY, 0, getCurrencyRate(ratesModel.getNZD(), NZD, ratesModel)));
        ratesListModels.add(new RatesListModel(BRL, BRL_CURRENCY, 0, getCurrencyRate(ratesModel.getBRL(), BRL, ratesModel)));
        return ratesListModels;
    }
}
