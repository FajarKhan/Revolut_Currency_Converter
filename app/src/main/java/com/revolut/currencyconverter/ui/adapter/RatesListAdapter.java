package com.revolut.currencyconverter.ui.adapter;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.revolut.currencyconverter.R;
import com.revolut.currencyconverter.model.RatesListModel;
import com.revolut.currencyconverter.model.RatesModel;
import com.revolut.currencyconverter.utils.RatesDiffUtilCallback;
import com.revolut.currencyconverter.utils.SetupRateList;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.revolut.currencyconverter.utils.Constant.BASE_MOVED_CURRENCY_CODE;
import static com.revolut.currencyconverter.utils.Constant.BASE_RATE;
import static com.revolut.currencyconverter.utils.Constant.KEY_CURRENCY_NAME;
import static com.revolut.currencyconverter.utils.Constant.KEY_RATE;
import static com.revolut.currencyconverter.utils.Utils.formatRate;

public class RatesListAdapter extends RecyclerView.Adapter<RatesListAdapter.ViewHolder> {

    private RecyclerView parentRecycler;
    private LayoutInflater mInflater;
    private Context context;
    private List<RatesListModel> ratesList;
    private RatesModel ratesModel;
    private baseRateChange baseRateChange;
    private boolean hasFocusOnBase = false;

    public RatesListAdapter(List<RatesListModel> ratesList, Context context, RatesModel ratesModel, baseRateChange baseRateChange) {
        this.mInflater = LayoutInflater.from(context);
        this.context = context;
        this.ratesList = ratesList;
        this.ratesModel = ratesModel;
        this.baseRateChange = baseRateChange;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_rates_list_layout, parent, false);
        // adding our EditText for text change listener
        ViewHolder vh = new ViewHolder(view, new CustomEditTextListener());
        return vh;
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        parentRecycler = recyclerView;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        RatesListModel ratesListModel = ratesList.get(position);
        //set currency data
        if (ratesListModel != null) {
            if (ratesListModel.getCountryFlag() != 0)
                holder.imgCountryFlag.setImageDrawable(ContextCompat.getDrawable(context, ratesListModel.getCountryFlag()));
            else
                holder.imgCountryFlag.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.empthy_flag_shape));

            if (ratesListModel.getCurrencyRate() != null)
                holder.editTextCurrencyRate.setText(ratesListModel.getCurrencyRate());
            if (ratesListModel.getCountryCode() != null)
                holder.txtCountryCode.setText(ratesListModel.getCountryCode());
            if (ratesListModel.getCurrencyName() != null)
                holder.txtCountryCurrency.setText(ratesListModel.getCurrencyName());
        }

        //setup EditText
        String countryCode = holder.txtCountryCode.getText().toString();
        holder.myCustomEditTextListener.updatePosition(countryCode);
        holder.editTextCurrencyRate.setSelection(holder.editTextCurrencyRate.getText().length());
    }

    /*
     * calls every time when we update data using payload
     * */
    @Override
    public void onBindViewHolder(ViewHolder holder, int position, @NonNull List<Object> payloads) {
        if (payloads.isEmpty()) {
            super.onBindViewHolder(holder, position, payloads);
        } else {
            Bundle o = (Bundle) payloads.get(0);
            //set updated currency value if the country code is same
            if (o.getString(KEY_CURRENCY_NAME).equals(ratesList.get(position).getCountryCode())
                    && !(o.getString(KEY_CURRENCY_NAME).equals(BASE_MOVED_CURRENCY_CODE))) {
                if (o.getString(KEY_RATE) != null)
                    holder.editTextCurrencyRate.setText(formatRate(Objects.requireNonNull(o.getString(KEY_RATE))));
            }
        }
    }

    @Override
    public int getItemCount() {
        return ratesList.size();
    }

    /*
     * method to handle user input :
     * set 0 if user input empty
     * remove 0 when start with 0
     * */
    private void getUpdatedDataBasedOnUserInput(String userInput, boolean isTyping, String countryCode) {
        if (BASE_MOVED_CURRENCY_CODE.equals(countryCode) && isTyping && hasFocusOnBase) {
            if (userInput.equals("")) {
                BASE_RATE = "0";
            } else if (userInput.startsWith("0") && userInput.length() > 1 && !userInput.equals("0.")) {
                BASE_RATE = userInput.substring(1);
            } else {
                BASE_RATE = userInput;
            }
            //set typed data as base rate
            ratesList.get(0).setCurrencyRate(BASE_RATE);
            //get new data based on user input using interface call
            List<RatesListModel> newRatesList = new SetupRateList().addData(ratesModel);
            baseRateChange.OnBaseRateChanged(newRatesList, ratesList);
        }
    }

    /*
     * calls every time user select any other currency for conversion
     * */
    private void setNewBase(int position, String value) {
        if (position != 0 && !BASE_MOVED_CURRENCY_CODE.equals(ratesList.get(position).getCountryCode())) {
            //get user selected currency
            List<RatesListModel> updatedRates = new ArrayList<>(ratesList);
            // remove selected currency from list
            RatesListModel ratesListModel = ratesList.get(position);
            updatedRates.remove(position);
            //add selected currency to top of the list
            updatedRates.add(0, ratesListModel);

            //add 0 when old base start with dot
            if (BASE_RATE.startsWith("."))
                updatedRates.get(1).setCurrencyRate(formatRate(BASE_RATE));
            // set data for future reference
            BASE_RATE = value;
            BASE_MOVED_CURRENCY_CODE = ratesListModel.getCountryCode();
            //scroll automatically to top when user tap on any currency
            parentRecycler.scrollToPosition(0);
            //sent new list for DiffUtil calculations
            onNewData(updatedRates);
        }
    }

    public List<RatesListModel> getOldRatesList() {
        return ratesList;
    }

    /*
     * method to call DiffUtils class based on new and old rate list
     * */
    public void onNewData(List<RatesListModel> newData) {
        DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(new RatesDiffUtilCallback(newData, ratesList));
        diffResult.dispatchUpdatesTo(this);
        this.ratesList.clear();
        this.ratesList.addAll(newData);
    }

    /*
     * interface to handle user input
     * */
    public interface baseRateChange {
        void OnBaseRateChanged(List<RatesListModel> newRatesList, List<RatesListModel> oldRatesList);
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnFocusChangeListener, View.OnTouchListener {
        public CustomEditTextListener myCustomEditTextListener;
        @BindView(R.id.img_country_flag)
        ImageView imgCountryFlag;
        @BindView(R.id.txt_country_code)
        TextView txtCountryCode;
        @BindView(R.id.txt_country_currency)
        TextView txtCountryCurrency;
        @BindView(R.id.edit_text_currency_rate)
        EditText editTextCurrencyRate;
        @BindView(R.id.cl_base_layout)
        ConstraintLayout clBaseLayout;

        public ViewHolder(View itemView, CustomEditTextListener CustomEditTextListener) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
            editTextCurrencyRate.setOnFocusChangeListener(this);
            editTextCurrencyRate.setOnTouchListener(this);
            this.myCustomEditTextListener = CustomEditTextListener;
            editTextCurrencyRate.addTextChangedListener(myCustomEditTextListener);
        }

        /*
         * calls every time when user click any currency from list
         * */
        @Override
        public void onClick(View view) {
            setNewBase(getAdapterPosition(), editTextCurrencyRate.getText().toString());
            editTextCurrencyRate.requestFocus();
            InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
            assert imm != null;
            imm.showSoftInput(editTextCurrencyRate, InputMethodManager.SHOW_IMPLICIT);
            editTextCurrencyRate.setSelection(editTextCurrencyRate.getText().length());
        }

        @Override
        public void onFocusChange(View view, boolean b) {
            if (b)
                hasFocusOnBase = true;
            else
                hasFocusOnBase = false;

        }

        /*
         * calls every time when user click any currency from list
         * */
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            setNewBase(getAdapterPosition(), editTextCurrencyRate.getText().toString());
            return false;
        }
    }

    /*
     * calls every time user start type any currency rates
     * */
    private class CustomEditTextListener implements TextWatcher {
        String countryCode;

        public void updatePosition(String countryCode) {
            this.countryCode = countryCode;
        }

        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            // calls whenever user types new base rate
            boolean isTyping = Math.abs(i3 - i2) == 1;
            getUpdatedDataBasedOnUserInput(charSequence.toString(), isTyping, countryCode);
        }

        @Override
        public void afterTextChanged(Editable editable) {
        }
    }
}
