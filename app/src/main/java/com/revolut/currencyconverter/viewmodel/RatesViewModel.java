package com.revolut.currencyconverter.viewmodel;


import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.revolut.currencyconverter.repository.RatesRepository;
import com.revolut.currencyconverter.utils.ApiResponse;

import java.util.concurrent.TimeUnit;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by ${Fajar Khan} on 07-12-2019.
 */

public class RatesViewModel extends ViewModel {

    private final CompositeDisposable disposables = new CompositeDisposable();
    private final MutableLiveData<ApiResponse> responseLiveData = new MutableLiveData<>();
    private RatesRepository repository;


    public RatesViewModel(RatesRepository repository) {
        this.repository = repository;
    }

    public MutableLiveData<ApiResponse> ratesResponse() {
        return responseLiveData;
    }

    /*
     * method to call rates api with currencyCode which will call every 1 sec
     * */
    public void getRates(String countryCode) {

        disposables.add(repository.executeRates(countryCode)
                .subscribeOn(Schedulers.io())
                .delay(1000, TimeUnit.MILLISECONDS)
                .repeat()
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(d -> responseLiveData.setValue(ApiResponse.loading()))
                .subscribe(
                        result -> responseLiveData.setValue(ApiResponse.success(result)),
                        throwable -> responseLiveData.setValue(ApiResponse.error(throwable))
                ));
    }

    @Override
    protected void onCleared() {
        disposables.clear();
    }
}
