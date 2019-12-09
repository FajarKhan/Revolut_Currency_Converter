package com.revolut.currencyconverter.utils;

import com.google.gson.JsonElement;
import com.revolut.currencyconverter.model.RatesResponse;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by ${Fajar Khan} on 07-12-2019.
 */


public interface ApiCallInterface {

    @GET(Urls.RATES)
    Observable<RatesResponse> getRates(@Query(value="base", encoded=true) String countryCode);
}
