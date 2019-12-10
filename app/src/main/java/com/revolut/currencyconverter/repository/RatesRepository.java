package com.revolut.currencyconverter.repository;

import com.revolut.currencyconverter.model.RatesResponse;
import com.revolut.currencyconverter.utils.ApiCallInterface;

import io.reactivex.Observable;

/**
 * Created by ${Fajar Khan} on 07-12-2019.
 */

public class RatesRepository {

    private ApiCallInterface apiCallInterface;

    public RatesRepository(ApiCallInterface apiCallInterface) {
        this.apiCallInterface = apiCallInterface;
    }

    /*
     * method to call Rates api
     * */
    public Observable<RatesResponse> executeRates(String countryCode) {
        return apiCallInterface.getRates(countryCode);
    }

}
