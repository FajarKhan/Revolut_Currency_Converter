package com.revolut.currencyconverter.ui.adapter;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.revolut.currencyconverter.R;
import com.revolut.currencyconverter.model.RatesListModel;
import com.revolut.currencyconverter.model.RatesModel;
import com.revolut.currencyconverter.utils.SetupRateList;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.revolut.currencyconverter.utils.Constant.BASE_MOVED_CURRENCY_CODE;
import static com.revolut.currencyconverter.utils.Constant.BASE_MOVED_FROM_POSITION;
import static com.revolut.currencyconverter.utils.Constant.BASE_MOVED_RATE;
import static com.revolut.currencyconverter.utils.Constant.BASE_RATE;
import static com.revolut.currencyconverter.utils.Constant.EUR_MOVED_RATE;
import static com.revolut.currencyconverter.utils.Constant.KEY_CURRENCY_NAME;
import static com.revolut.currencyconverter.utils.Constant.KEY_RATE;

public class RatesListAdapter extends RecyclerView.Adapter<RatesListAdapter.ViewHolder> {

    private RecyclerView parentRecycler;
    private LayoutInflater mInflater;
    private Context context;
    private List<RatesListModel> ratesList;
    private RatesModel ratesModel;
    private baseRateChange baseRateChange;
    private RatesListModel baseRate;
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
        baseRate = ratesList.get(0);
        if (ratesListModel != null) {

            if (ratesListModel.getCountryFlag() != 0)
                holder.imgCountryFlag.setImageDrawable(ContextCompat.getDrawable(context, ratesListModel.getCountryFlag()));
            else
                holder.imgCountryFlag.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.empthy_flag_shape));

            if (ratesListModel.getCurrencyRate() != null) {
//                String rates = ratesListModel.getCurrencyRate().substring(0, Math.min(ratesListModel.getCurrencyRate().length(), 8));
//                if (rates.endsWith(".")) {
//                    rates.substring(0, rates.length() - 1);
//                    holder.editTextCurrencyRate.setText(rates);
//                } else
                    holder.editTextCurrencyRate.setText(ratesListModel.getCurrencyRate());
            }

            if (ratesListModel.getCountryCode() != null)
                holder.txtCountryCode.setText(ratesListModel.getCountryCode());
            if (ratesListModel.getCurrencyName() != null)
                holder.txtCountryCurrency.setText(ratesListModel.getCurrencyName());

        }

        String countryCode = holder.txtCountryCode.getText().toString();
        holder.myCustomEditTextListener.updatePosition(countryCode);
        holder.editTextCurrencyRate.setSelection(holder.editTextCurrencyRate.getText().length());
    }


    private void getUpdatedDataBasedOnUserInput(String userInput, boolean isTyping, String countryCode) {
        if (baseRate.getCountryCode().equals(countryCode) && isTyping && hasFocusOnBase) {
            if (userInput.equals("")) {
                BASE_RATE = "0";
            } else if (userInput.startsWith("0") && userInput.length() > 1 && !userInput.equals("0.")) {
                BASE_RATE = userInput.substring(1);
            } else {
                BASE_RATE = userInput;
            }

            //            if (baseRate.getCountryCode().equals(EUR))
            //                EUR_RATE = BASE_RATE;


            ratesList.get(0).setCurrencyRate(BASE_RATE);
            //get new data based on user input
            List<RatesListModel> newRatesList = new SetupRateList().addData(ratesModel, BASE_RATE);
            baseRateChange.OnBaseRateChanged(newRatesList, ratesList);
        }
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position, @NonNull List<Object> payloads) {
        if (payloads.isEmpty()) {
            super.onBindViewHolder(holder, position, payloads);
        } else {
            Bundle o = (Bundle) payloads.get(0);
            if ((o.getString(KEY_CURRENCY_NAME).equals(ratesList.get(position).getCountryCode()))) {
//                String rates = o.getString(KEY_RATE).substring(0, Math.min(o.getString(KEY_RATE).length(), 8));
//                if (rates.endsWith(".")) {
//                    rates.substring(0, rates.length() - 1);
//                    holder.editTextCurrencyRate.setText(rates);

//                }else
                holder.editTextCurrencyRate.setText(o.getString(KEY_RATE));
            }
        }
    }

    @Override
    public int getItemCount() {
        return ratesList.size();
    }


    private void setNewBase(int position) {
        if (position != 0) {
            RatesListModel ratesListModel = ratesList.get(position);
            ratesList.remove(position);
            BASE_MOVED_FROM_POSITION = position;
            BASE_MOVED_RATE = ratesListModel.getCurrencyRate();
            EUR_MOVED_RATE = ratesListModel.getCurrencyRate();
            BASE_MOVED_CURRENCY_CODE = ratesListModel.getCountryCode();
            baseRate = ratesListModel;
            ratesList.add(0, ratesListModel);
            notifyItemMoved(position, 0);
            parentRecycler.scrollToPosition(0);
        }
    }

    public List<RatesListModel> getOldRatesList() {
        return ratesList;
    }

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

        @Override
        public void onClick(View view) {
            setNewBase(getAdapterPosition());
        }

        @Override
        public void onFocusChange(View view, boolean b) {
            if (b) {
                hasFocusOnBase = true;
            } else {
                hasFocusOnBase = false;
            }
        }

        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            setNewBase(getAdapterPosition());
            return false;
        }
    }

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
