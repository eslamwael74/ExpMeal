package com.eslamwael74.inq.expmeal.Utils;

import android.app.Activity;
import android.app.Dialog;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

import com.eslamwael74.inq.expmeal.R;

/**
 * Created by eslam on 2/21/2018.
 */

public class UtilsFunctions {

    //------------------------show ProgressBar-------------------------------------//

    public static Dialog dialog;

    public static void showProgressbar(Activity context) {
        if (dialog != null)
            dialog.cancel();
        dialog = new Dialog(context);
        dialog.setContentView(R.layout.progressbar_layout);
        dialog.getWindow().setBackgroundDrawableResource(R.color.transparent);
        WindowManager.LayoutParams layoutParam = new WindowManager.LayoutParams();
        Window w = dialog.getWindow();
        layoutParam.copyFrom(w.getAttributes());
        layoutParam.width = WindowManager.LayoutParams.MATCH_PARENT;
        layoutParam.height = WindowManager.LayoutParams.WRAP_CONTENT;
        w.setAttributes(layoutParam);
        dialog.setCancelable(false);
        dialog.show();
    }

    //------------------------close ProgressBar-------------------------------------//
    public static void closeProgressbar() {
        try {
            dialog.dismiss();
        } catch (Exception e) {

        }
    }

    //-------------------------Hide KeyBoard ----------------------------//
    public static void hideKeyboard(Activity context) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = context.getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(context);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }


}
