package com.revolut.currencyconverter.ui.rates;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SimpleItemAnimator;

import com.airbnb.lottie.LottieAnimationView;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.material.button.MaterialButton;
import com.revolut.currencyconverter.MyApplication;
import com.revolut.currencyconverter.R;
import com.revolut.currencyconverter.model.RatesListModel;
import com.revolut.currencyconverter.model.RatesModel;
import com.revolut.currencyconverter.model.RatesResponse;
import com.revolut.currencyconverter.ui.adapter.RatesListAdapter;
import com.revolut.currencyconverter.utils.ApiResponse;
import com.revolut.currencyconverter.utils.SetupRateList;
import com.revolut.currencyconverter.utils.Utils;
import com.revolut.currencyconverter.utils.ViewModelFactory;
import com.revolut.currencyconverter.viewmodel.RatesViewModel;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.revolut.currencyconverter.utils.Constant.EUR;

public class RatesActivity extends AppCompatActivity implements RatesListAdapter.baseRateChange, View.OnClickListener {

    @Inject
    ViewModelFactory viewModelFactory;

    @BindView(R.id.rv_rates)
    RecyclerView rvRates;
    @BindView(R.id.shimmer_layout)
    ShimmerFrameLayout shimmerLayout;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.lav_loader)
    LottieAnimationView lavNoData;
    @BindView(R.id.txt_error_title)
    TextView txtErrorTitle;
    @BindView(R.id.txt_error_sub_title)
    TextView txtErrorSubTitle;
    @BindView(R.id.btn_try_again)
    MaterialButton btnTryAgain;
    @BindView(R.id.cl_error_layout)
    ConstraintLayout clErrorLayout;

    private RatesViewModel viewModel;
    private RatesListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rates);
        ButterKnife.bind(this);

        initResources();
        getCurrencyRates();
    }

    /*
     * method to initiate resources and viewModels
     * */
    private void initResources() {
        toolbar.setTitle(getString(R.string.rates_activity_title));
        ((MyApplication) getApplication()).getAppComponent().doInjection(this);

        viewModel = ViewModelProviders.of(this, viewModelFactory).get(RatesViewModel.class);
        viewModel.ratesResponse().observe(this, this::ratesResponse);

        btnTryAgain.setOnClickListener(this);
    }


    /*
     * method to call API
     * */
    private void getCurrencyRates() {
        if (!Utils.checkInternetConnection(this)) {
            setErrorData(false, getResources().getString(R.string.network_error));
        } else {
            //get Rates based on EUR currency only once ie do not call on every orientation changes
            if (viewModel.ratesResponse().getValue() == null)
                viewModel.getRates(EUR);
        }
    }

    /*
     * method to handle response
     * */
    private void ratesResponse(ApiResponse apiResponse) {

        switch (apiResponse.status) {
            // calls if API is in loading state
            case LOADING:
                shimmerLayout.setVisibility(View.VISIBLE);
                shimmerLayout.startShimmer();
                rvRates.setVisibility(View.GONE);
                break;

            // calls on successful API response
            case SUCCESS:
                shimmerLayout.setVisibility(View.GONE);
                shimmerLayout.stopShimmer();
                setErrorData(true, "");
                renderSuccessResponse(apiResponse.data);
                break;

            // calls on failure API response
            case ERROR:
                shimmerLayout.setVisibility(View.GONE);
                shimmerLayout.stopShimmer();
                setErrorData(false, getResources().getString(R.string.errorString));
                break;

            default:
                break;
        }
    }

    /*
     * method to handle success/error response
     * */
    private void renderSuccessResponse(RatesResponse response) {
        if (response != null) {
            Log.d("response=", response.toString());
            RatesModel ratesModel = response.getRates();
            if (ratesModel != null) {
                List<RatesListModel> ratesListModels = new SetupRateList().addData(ratesModel);
                setupRateListRecycler(ratesListModels, ratesModel);
            } else {
                setErrorData(false, getResources().getString(R.string.errorString));
            }
        } else {
            setErrorData(false, getResources().getString(R.string.errorString));
        }
    }

    /*
     * method to setup RecyclerView
     * */
    private void setupRateListRecycler(List<RatesListModel> list, RatesModel ratesModel) {
        // don't need initialize if our list already exist
        if (adapter == null) {
            rvRates.setLayoutManager(new LinearLayoutManager(this));
            adapter = new RatesListAdapter(list, this, ratesModel, this);
            rvRates.setHasFixedSize(true);
            rvRates.setAdapter(adapter);
            RecyclerView.ItemAnimator animator = rvRates.getItemAnimator();
            if (animator instanceof SimpleItemAnimator)
                ((SimpleItemAnimator) animator).setSupportsChangeAnimations(false);
        } else
            setUpdatedRates(list, adapter.getOldRatesList());
    }

    /*
     * this interface will call every time when user input any base rate
     * */
    @Override
    public void OnBaseRateChanged(List<RatesListModel> newRatesList, List<RatesListModel> oldRatesList) {
        setUpdatedRates(newRatesList, oldRatesList);
    }

    /*
     * this method will call every time :
     * 1- when user input any base rate
     * 2- get updated rates every 1 sec
     * */
    private void setUpdatedRates(List<RatesListModel> newRatesList, List<RatesListModel> oldRatesList) {
        // we compare old list and updated rate list based on user input or from server and sent to RecycleView using DiffUtils
        List<RatesListModel> list = new ArrayList<>();
        for (int i = 0; i < oldRatesList.size(); i++) {
            for (int j = 0; j < newRatesList.size(); j++) {
                if (oldRatesList.get(i).getCountryCode().equals(newRatesList.get(j).getCountryCode())) {
                    list.add(i, newRatesList.get(j));
                }
            }
        }
        //Calling DifUtils with old and new list
        adapter.onNewData(list);
    }

    /*
     * method to handle view based on success/ failure response
     * stop rates update API if there was any failure in API
     * */
    private void setErrorData(Boolean isSuccessful, String errorText) {
        if (isSuccessful) {
            clErrorLayout.setVisibility(View.GONE);
            rvRates.setVisibility(View.VISIBLE);
        } else {
            viewModel.stopRatesUpdates();
            clErrorLayout.setVisibility(View.VISIBLE);
            txtErrorTitle.setText(errorText);
            txtErrorSubTitle.setText(getResources().getString(R.string.txt_error_sub_title));
            rvRates.setVisibility(View.GONE);
        }
    }

    @Override
    public void onClick(View view) {
        /*
         * calls every time when user click on try again button
         * */
        if (view.getId() == R.id.btn_try_again) {
            if (!Utils.checkInternetConnection(this))
                setErrorData(false, getResources().getString(R.string.network_error));
            else {
                setErrorData(true, "");
                viewModel.getRates(EUR);
            }
        }
    }
}
