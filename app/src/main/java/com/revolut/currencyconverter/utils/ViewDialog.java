package com.revolut.currencyconverter.utils;

import android.app.Activity;
import android.app.Dialog;
import android.view.Window;

import com.revolut.currencyconverter.R;

public class ViewDialog {
    Activity activity;
    Dialog dialog;

    public ViewDialog(Activity activity) {
        this.activity = activity;
    }

    public void showDialog() {
        if (dialog == null) {
            dialog = new Dialog(activity);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setCancelable(false);
            dialog.setContentView(R.layout.app_progressbar);
            dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

            dialog.show();
        }
    }

//    public void showNoInternetDialogue() {
//        new MaterialAlertDialogBuilder(activity)
//                .setView(R.layout.no_internet_layout)
//                .setTitle("No Internet")
//                .setMessage("Oops! Your not connected to internet. Don't Worry we've saved your data for you!")
//                .setPositiveButton("How cool! take me there.", (dialogInterface, i) -> {
//                    dialogInterface.cancel();
//                })
//                .setCancelable(false)
//                .show();
//    }

    public void hideDialog() {
        if (dialog != null)
            dialog.dismiss();
    }

}

