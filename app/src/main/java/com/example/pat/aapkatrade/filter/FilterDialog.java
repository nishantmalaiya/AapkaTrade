package com.example.pat.aapkatrade.filter;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.pat.aapkatrade.Home.registration.entity.City;
import com.example.pat.aapkatrade.Home.registration.entity.State;
import com.example.pat.aapkatrade.Home.registration.spinner_adapter.SpCityAdapter;
import com.example.pat.aapkatrade.Home.registration.spinner_adapter.SpStateAdapter;
import com.example.pat.aapkatrade.R;
import com.example.pat.aapkatrade.categories_tab.CategoriesListData;
import com.example.pat.aapkatrade.general.AppSharedPreference;
import com.example.pat.aapkatrade.general.TaskCompleteReminder;
import com.example.pat.aapkatrade.general.Utils.AndroidUtils;
import com.example.pat.aapkatrade.general.Validation;
import com.example.pat.aapkatrade.general.progressbar.ProgressBarHandler;
import com.google.gson.JsonArray;
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
    private String categoryId, stateId;
    private TextView applyFilter, clearAll;
    private ArrayList<State> productAvailableStateList = new ArrayList<>();
    private ArrayList<City> productAvailableCityList = new ArrayList<>();
    private Spinner spState;
    JsonObject resultData;
    private JsonArray cityArray;
    private RecyclerView cityRecyclerView;

    public static TaskCompleteReminder taskCompleteReminder = null;

    public FilterDialog(Context context) {
        super(context);
        this.context = context;
    }

    public FilterDialog(Context context, String categoryId, ArrayList<State> productAvailableStateList) {
        super(context);
        this.context = context;
        this.categoryId = categoryId;
        this.productAvailableStateList = productAvailableStateList;
        Log.e("message_data-categoryId", categoryId);
        Log.e("message_data--statesize", String.valueOf(productAvailableStateList.size()));
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
//                getDataByCategory();
                dismiss();
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
        setUpStateSpinner(productAvailableStateList);
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
        spState = (Spinner) findViewById(R.id.spStateCategory);
        cityRecyclerView = (RecyclerView) findViewById(R.id.selectCityList);
    }


    private void callWebService(){
        if(Validation.isEmptyStr(stateId)){
            getDataByState();
        } else {
//            if(Validation.isEmptyStr(cityId))
        }
    }


    private void getDataByState() {
        Log.e("message_data---", "called with category id " + categoryId + " stateId " + stateId);

        Ion.with(context)
                .load(context.getResources().getString(R.string.webservice_base_url) + "/productlist")
                .setHeader("authorization", "xvfdbgfdhbfdhtrh54654h54ygdgerwer3")
                .setBodyParameter("category_id", categoryId)
                .setBodyParameter("state_id", stateId)
                .setBodyParameter("apply", "1")
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {
                        if (result == null) {
                            Log.e("message_data---", "null found");
                        } else {
                            Log.e("result", result.toString());
                            resultData = result;
                            JsonArray cityArray = result.get("cities").getAsJsonArray();
                            City city = new City("-1", "Select City");
                            productAvailableCityList.add(city);
                            for(int i = 0; i < cityArray.size(); i++){
                                JsonObject jsonObject = (JsonObject) cityArray.get(i);
                                city = new City(jsonObject.get("city_id").getAsString(), jsonObject.get("ctyname").getAsString());
                                productAvailableCityList.add(city);
                            }
                            if(productAvailableCityList.size()>1){
                                setUpCityAdapter();
                            }
                        }
                    }

                });
    }


    private void getDataByCity() {
        Log.e("message_data---", "called with category id " + categoryId + " stateId " + stateId );

        Ion.with(context)
                .load(context.getResources().getString(R.string.webservice_base_url) + "/productlist")
                .setHeader("authorization", "xvfdbgfdhbfdhtrh54654h54ygdgerwer3")
                .setBodyParameter("category_id", categoryId)
                .setBodyParameter("state_id", stateId)
//                .setBodyParameter("city_id", cityId)
                .setBodyParameter("apply", "1")
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {
                        if (result == null) {
                            Log.e("message_data---", "null found");
                        } else {
//                            taskCompleteReminder.Taskcomplete(result);

                        }
                    }

                });
    }


    private void setUpStateSpinner(final ArrayList<State> stateList){
        SpStateAdapter spStateAdapter = new SpStateAdapter(context, stateList);
        spState.setAdapter(spStateAdapter);
        spState.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position > 0) {
                    stateId = stateList.get(position).stateId;
                    getDataByState();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void setUpCityAdapter(){
        SpCityAdapter spCityAdapter = new SpCityAdapter(context, productAvailableCityList, true);
//        cityRecyclerView.setAdapter(spCityAdapter);
    }

}
