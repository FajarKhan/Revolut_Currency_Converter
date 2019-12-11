package com.revolut.currencyconverter;

import android.app.Application;
import android.content.Context;

import com.revolut.currencyconverter.di.AppComponent;
import com.revolut.currencyconverter.di.AppModule;
import com.revolut.currencyconverter.di.DaggerAppComponent;
import com.revolut.currencyconverter.di.UtilsModule;


/**
 * Created by ${Fajar Khan} on 07-12-2019.
 */

public class MyApplication extends Application {
    AppComponent appComponent;
    Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
        appComponent = DaggerAppComponent.builder().appModule(new AppModule(this)).utilsModule(new UtilsModule()).build();
    }

    public AppComponent getAppComponent() {
        return appComponent;
    }

    @Override
    protected void attachBaseContext(Context context) {
        super.attachBaseContext(context);
    }
}
