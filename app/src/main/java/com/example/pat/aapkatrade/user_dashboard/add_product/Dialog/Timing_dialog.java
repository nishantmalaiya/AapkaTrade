package com.example.pat.aapkatrade.user_dashboard.add_product.Dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;

import com.example.pat.aapkatrade.R;

import java.util.ArrayList;

/**
 * Created by PPC17 on 07-Apr-17.
 */

public class Timing_dialog extends Dialog {
    public Timing_dialog( Context context, ArrayList<String> opening_time_list, ArrayList<String> closing_time_list) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getWindow() != null)
            getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        setContentView(R.layout.dialog_timing);


        initView();



    }

    private void initView() {




    }
}
