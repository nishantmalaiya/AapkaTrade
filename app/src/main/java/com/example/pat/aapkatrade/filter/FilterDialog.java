package com.example.pat.aapkatrade.filter;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.pat.aapkatrade.Home.registration.entity.City;
import com.example.pat.aapkatrade.R;
import com.example.pat.aapkatrade.filter.entity.FilterObject;
import com.example.pat.aapkatrade.general.AppSharedPreference;
import com.example.pat.aapkatrade.general.Utils.AndroidUtils;
import com.example.pat.aapkatrade.general.progressbar.ProgressBarHandler;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by PPC09 on 25-Mar-17.
 */


public class FilterDialog extends Dialog {
    private AppSharedPreference app_sharedpreference;
    private ProgressBarHandler progress_handler;
    private Context context;
    private Button imgCLose;
    private RelativeLayout dialogue_toolbar;
    private LinearLayout categoryFilter;
    private String categoryId;
    private TextView applyFilter, clearAll;
    private ArrayList<String> filterNameArrayList = new ArrayList<>();
    private ArrayList<FilterObject> filterValueArrayList = new ArrayList<>();
    private HashMap<String, ArrayList<FilterObject>> filterHashMap = null;


    public FilterDialog(Context context, String category_id, HashMap<String, ArrayList<FilterObject>> filterHashMap) {
        super(context);
        this.context = context;
        this.categoryId = categoryId;
        this.filterHashMap = filterHashMap;
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

            }
        });

        imgCLose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                decodeData(filterHashMap);
                dismiss();
            }
        });

        clearAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
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
        clearAll = (TextView) findViewById(R.id.clearAll);
    }

    private void setUpData() {

    }

    private void showMessage(String msg) {
        AndroidUtils.showSnackBar((LinearLayout) findViewById(R.id.dialog_filter_root), msg);
    }


    private JsonArray makeCityIdJSonArray(ArrayList<City> cityArrayList) {
        JsonArray jsonArray = new JsonArray();
        for (City city : cityArrayList) {
            JsonObject cityJsonObject = new JsonObject();
            cityJsonObject.addProperty("city_id", city.cityId);
            jsonArray.add(cityJsonObject);
        }
        return jsonArray;
    }


    private void decodeData(HashMap<String, ArrayList<FilterObject>> filterHashMap) {
        for (String key : filterHashMap.keySet()) {
            filterNameArrayList.add(key);
            filterValueArrayList = filterHashMap.get(key);
            for (int i = 0; i < filterValueArrayList.size(); i++) {
                AndroidUtils.showErrorLog(context, "decoded Key : " + filterValueArrayList.get(i).id.value + " decoded Value : " + filterValueArrayList.get(i).name.value);
            }
        }
    }

}
