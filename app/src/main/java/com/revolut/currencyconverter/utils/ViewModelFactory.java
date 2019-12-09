package com.revolut.currencyconverter.utils;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.revolut.currencyconverter.repository.RatesRepository;
import com.revolut.currencyconverter.viewmodel.RatesViewModel;

import javax.inject.Inject;

/**
 * Created by ${Fajar Khan} on 07-12-2019.
 */

public class ViewModelFactory implements ViewModelProvider.Factory {

    private RatesRepository repository;

    @Inject
    public ViewModelFactory(RatesRepository repository) {
        this.repository = repository;
    }


    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(RatesViewModel.class)) {
            return (T) new RatesViewModel(repository);
        }
        throw new IllegalArgumentException("Unknown class name");
    }
}
