package com.example.pat.aapkatrade.filter;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.pat.aapkatrade.Home.HomeActivity;
import com.example.pat.aapkatrade.R;
import com.example.pat.aapkatrade.categories_tab.CategoriesListData;
import com.example.pat.aapkatrade.categories_tab.CategoryListActivity;
import com.example.pat.aapkatrade.general.AppSharedPreference;
import com.example.pat.aapkatrade.general.TaskCompleteReminder;
import com.example.pat.aapkatrade.general.Utils.AndroidUtils;
import com.example.pat.aapkatrade.general.progressbar.ProgressBarHandler;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import java.util.ArrayList;

/**
 * Created by PPC09 on 25-Mar-17.
 */


public class FilterDialog extends Dialog {
    private AppSharedPreference app_sharedpreference;
    private ProgressBarHandler progress_handler;
    private Context context;
    private ArrayList<CategoriesListData> productListData = new ArrayList<>();
    private Button imgCLose;
    private RelativeLayout dialogue_toolbar;
    private LinearLayout categoryFilter;
    private String categoryId, stateId, cityId;
    private TextView applyFilter, clearAll;
    private Intent intent;

    public static TaskCompleteReminder taskCompleteReminder = null;

    public FilterDialog(Context context) {
        super(context);
        this.context = context;
    }

    public FilterDialog(Context context, String categoryId) {
        super(context);
        this.context = context;
        this.categoryId = categoryId;
        Log.e("message_data---1", categoryId);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        setContentView(R.layout.dialog_filter);
        initView();
        setUpData();

        applyFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDataByCategory();
//
            }
        });

        imgCLose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FilterDialog.this.hide();
            }
        });
    }

    private void setUpData() {

    }

    private void showMessage(String msg) {
        AndroidUtils.showSnackBar((LinearLayout) findViewById(R.id.dialog_filter_root), msg);
    }

    private void initView() {
        app_sharedpreference = new AppSharedPreference(context);
        progress_handler = new ProgressBarHandler(context);
        dialogue_toolbar = (RelativeLayout) findViewById(R.id.dialogue_toolbar);
        AndroidUtils.setBackgroundSolid(dialogue_toolbar, context, R.color.green, 8);
        imgCLose = (Button) findViewById(R.id.imgCLose);
        categoryFilter = (LinearLayout) findViewById(R.id.categoryFilter);
        categoryFilter.setVisibility(View.GONE);
        applyFilter = (TextView) findViewById(R.id.applyFilter);

    }

    private void getDataByCategory() {
        Log.e("message_data---", "calledd with category id "+categoryId);
        Log.e("message_data---URL", context.getResources().getString(R.string.webservice_base_url) + "/productlist");
        progress_handler.show();
        Ion.with(context)
                .load(context.getResources().getString(R.string.webservice_base_url) + "/productlist")
                .setHeader("authorization", "xvfdbgfdhbfdhtrh54654h54ygdgerwer3")
//                .setBodyParameter("authorization", "xvfdbgfdhbfdhtrh54654h54ygdgerwer3")
                .setBodyParameter("category_id", categoryId)
                .setBodyParameter("apply", "1")
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {
//                        Log.e("message_data---", result==null?e.toString():result);

                        progress_handler.hide();

                        if (result == null) {
                            Log.e("message_data---", "null found");

                        } else {

                            taskCompleteReminder.Taskcomplete(result);
                            JsonObject jsonObject = result.getAsJsonObject();

                            String message = jsonObject.get("message").toString().substring(0, jsonObject.get("message").toString().length());

                            String message_data = message.replace("\"", "");

dismiss();






                        }





                    }

                });
        Log.e("message_data---", "calledd closed");

    }

}
