package com.revolut.currencyconverter.utils;

import com.revolut.currencyconverter.model.RatesResponse;

import io.reactivex.annotations.NonNull;
import io.reactivex.annotations.Nullable;

import static com.revolut.currencyconverter.utils.Status.ERROR;
import static com.revolut.currencyconverter.utils.Status.LOADING;
import static com.revolut.currencyconverter.utils.Status.SUCCESS;


/**
 * Created by ${Fajar Khan} on 07-12-2019.
 */

public class ApiResponse {

    public final Status status;

    @Nullable
    public final RatesResponse data;

    @Nullable
    public final Throwable error;

    private ApiResponse(Status status, @Nullable RatesResponse data, @Nullable Throwable error) {
        this.status = status;
        this.data = data;
        this.error = error;
    }

    public static ApiResponse loading() {
        return new ApiResponse(LOADING, null, null);
    }

    public static ApiResponse success(@NonNull RatesResponse data) {
        return new ApiResponse(SUCCESS, data, null);
    }

    public static ApiResponse error(@NonNull Throwable error) {
        return new ApiResponse(ERROR, null, error);
    }

}
