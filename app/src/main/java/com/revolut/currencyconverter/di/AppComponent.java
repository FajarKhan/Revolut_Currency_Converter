package com.revolut.currencyconverter.di;


import com.revolut.currencyconverter.ui.rates.RatesActivity;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by ${Fajar Khan} on 07-12-2019.
 */

@Component(modules = {AppModule.class, UtilsModule.class})
@Singleton
public interface AppComponent {

    void doInjection(RatesActivity ratesActivity);

}
