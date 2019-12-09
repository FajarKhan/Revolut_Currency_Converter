package com.revolut.currencyconverter.ui.rates;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.revolut.currencyconverter.MyApplication;
import com.revolut.currencyconverter.R;
import com.revolut.currencyconverter.model.RatesListModel;
import com.revolut.currencyconverter.model.RatesModel;
import com.revolut.currencyconverter.model.RatesResponse;
import com.revolut.currencyconverter.ui.adapter.RatesListAdapter;
import com.revolut.currencyconverter.utils.ApiResponse;
import com.revolut.currencyconverter.utils.Constant;
import com.revolut.currencyconverter.utils.SetupRateList;
import com.revolut.currencyconverter.utils.ViewDialog;
import com.revolut.currencyconverter.utils.ViewModelFactory;
import com.revolut.currencyconverter.viewmodel.RatesViewModel;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.revolut.currencyconverter.utils.Constant.BASE_MOVED_FROM_POSITION;
import static com.revolut.currencyconverter.utils.Constant.BASE_RATE;
import static com.revolut.currencyconverter.utils.Constant.KEY_CURRENCY_NAME;
import static com.revolut.currencyconverter.utils.Constant.KEY_RATE;

public class RatesActivity extends AppCompatActivity implements RatesListAdapter.baseRateChange {

    @Inject
    ViewModelFactory viewModelFactory;

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.rv_rates)
    RecyclerView rvRates;

    private RatesViewModel viewModel;
    private RatesListAdapter adapter;
    private ViewDialog viewDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rates);
        ButterKnife.bind(this);

        initResources();
        getCurrencyRates();
    }

    /*
     * method to initiate resources
     * */
    private void initResources() {
        viewDialog = new ViewDialog(this);
        ((MyApplication) getApplication()).getAppComponent().doInjection(this);

        viewModel = ViewModelProviders.of(this, viewModelFactory).get(RatesViewModel.class);
        viewModel.ratesResponse().observe(this, this::ratesResponse);
    }


    /*
     * method to call API
     * */
    private void getCurrencyRates() {
        if (!Constant.checkInternetConnection(this)) {
            Toast.makeText(RatesActivity.this, getResources().getString(R.string.network_error), Toast.LENGTH_SHORT).show();
        } else {
            viewModel.getRates("EUR");
        }
    }

    /*
     * method to handle response
     * */
    private void ratesResponse(ApiResponse apiResponse) {

        switch (apiResponse.status) {

            case LOADING:
                viewDialog.showDialog();
                break;

            case SUCCESS:
                viewDialog.hideDialog();
                renderSuccessResponse(apiResponse.data);
                break;

            case ERROR:
                viewDialog.hideDialog();
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
                List<RatesListModel> ratesListModels = new SetupRateList().addData(ratesModel, BASE_RATE);
                setupRateListRecycler(ratesListModels, ratesModel);
            } else {
                Toast.makeText(RatesActivity.this, getResources().getString(R.string.errorString), Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(RatesActivity.this, getResources().getString(R.string.errorString), Toast.LENGTH_SHORT).show();
        }
    }

    private void setupRateListRecycler(List<RatesListModel> list, RatesModel ratesModel) {
        if (adapter == null) {
            rvRates.setLayoutManager(new LinearLayoutManager(this));
            adapter = new RatesListAdapter(list, this, ratesModel, this);
            rvRates.setHasFixedSize(true);
            rvRates.setAdapter(adapter);
        } else {
            setUpdatedRates(list);
        }
    }

    @Override
    public void OnBaseRateChanged(List<RatesListModel> newRatesList) {
        setUpdatedRates(newRatesList);
    }

    private void setUpdatedRates(List<RatesListModel> newRatesList) {
        if (BASE_MOVED_FROM_POSITION != 0) {
            newRatesList.remove(BASE_MOVED_FROM_POSITION);
            newRatesList.add(0, newRatesList.get(0));
        }
        for (int i = 1; i < newRatesList.size(); i++) {
            Bundle diffPayLoad = new Bundle();
            diffPayLoad.putString(KEY_RATE, newRatesList.get(i).getCurrencyRate());
            diffPayLoad.putString(KEY_CURRENCY_NAME, newRatesList.get(i).getCountryCode());
            adapter.notifyItemChanged(i, diffPayLoad);
        }
    }
}
