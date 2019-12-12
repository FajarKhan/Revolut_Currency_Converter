package com.revolut.currencyconverter.ui.rates;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.facebook.shimmer.ShimmerFrameLayout;
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

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.revolut.currencyconverter.utils.Constant.EUR;
import static com.revolut.currencyconverter.utils.Constant.KEY_CURRENCY_NAME;
import static com.revolut.currencyconverter.utils.Constant.KEY_RATE;

public class RatesActivity extends AppCompatActivity implements RatesListAdapter.baseRateChange {

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
    }


    /*
     * method to call API
     * */
    private void getCurrencyRates() {
        if (!Utils.checkInternetConnection(this)) {
            lavNoData.setVisibility(View.VISIBLE);
            rvRates.setVisibility(View.GONE);
            Toast.makeText(RatesActivity.this, getResources().getString(R.string.network_error), Toast.LENGTH_SHORT).show();
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

            case LOADING:
                shimmerLayout.setVisibility(View.VISIBLE);
                shimmerLayout.startShimmer();
                break;

            case SUCCESS:
                shimmerLayout.setVisibility(View.GONE);
                shimmerLayout.stopShimmer();
                lavNoData.setVisibility(View.GONE);
                rvRates.setVisibility(View.VISIBLE);
                renderSuccessResponse(apiResponse.data);
                break;

            case ERROR:
                shimmerLayout.setVisibility(View.GONE);
                shimmerLayout.stopShimmer();
                lavNoData.setVisibility(View.VISIBLE);
                rvRates.setVisibility(View.GONE);
                Toast.makeText(RatesActivity.this, getResources().getString(R.string.errorString), Toast.LENGTH_SHORT).show();
                break;

            default:
                break;
        }
    }

    /*
     * method to handle success response
     * */
    private void renderSuccessResponse(RatesResponse response) {
        if (response != null) {
            Log.d("response=", response.toString());
            RatesModel ratesModel = response.getRates();
            if (ratesModel != null) {
                List<RatesListModel> ratesListModels = new SetupRateList().addData(ratesModel);
                setupRateListRecycler(ratesListModels, ratesModel);
            } else {
                Toast.makeText(RatesActivity.this, getResources().getString(R.string.errorString), Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(RatesActivity.this, getResources().getString(R.string.errorString), Toast.LENGTH_SHORT).show();
        }
    }

    /*
     * method to setup RecyclerView
     * */
    private void setupRateListRecycler(List<RatesListModel> list, RatesModel ratesModel) {
        // don't initialize if our list already exist
        if (adapter == null) {
            rvRates.setLayoutManager(new LinearLayoutManager(this));
            adapter = new RatesListAdapter(list, this, ratesModel, this);
            rvRates.setHasFixedSize(true);
            rvRates.setAdapter(adapter);
        } else {
            setUpdatedRates(list, adapter.getOldRatesList());
        }
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
        // we compare old list and updated rate list based on user input or from server and sent to RecycleView using payload strategy
        for (int i = 1; i < oldRatesList.size(); i++) {
            for (int j = 0; j < newRatesList.size(); j++) {
                if (oldRatesList.get(i).getCountryCode().equals(newRatesList.get(j).getCountryCode())) {
                    Bundle diffPayLoad = new Bundle();
                    diffPayLoad.putString(KEY_RATE, newRatesList.get(j).getCurrencyRate());
                    diffPayLoad.putString(KEY_CURRENCY_NAME, newRatesList.get(j).getCountryCode());
                    adapter.notifyItemChanged(i, diffPayLoad);
                }
            }
        }
    }
}
