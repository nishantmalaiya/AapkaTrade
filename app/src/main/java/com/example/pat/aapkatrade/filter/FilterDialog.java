package com.example.pat.aapkatrade.filter;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
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
import com.example.pat.aapkatrade.filter.adapter.CityRecyclerAdapter;
import com.example.pat.aapkatrade.general.AppSharedPreference;
import com.example.pat.aapkatrade.general.CommonInterface;
import com.example.pat.aapkatrade.general.TaskCompleteReminder;
import com.example.pat.aapkatrade.general.Utils.AndroidUtils;
import com.example.pat.aapkatrade.general.Validation;
import com.example.pat.aapkatrade.general.progressbar.ProgressBarHandler;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
    private int selectedStatePosition = 0;
    private TextView applyFilter, clearAll;
    private ArrayList<State> productAvailableStateList = new ArrayList<>();
    private ArrayList<City> productAvailableCityList = new ArrayList<>();
    private Spinner spState;
    JsonObject resultData;
    private JsonArray cityArray;
    private RecyclerView cityRecyclerView;
    private ArrayList<City> getSelectedCityList = new ArrayList<>();
    public static CommonInterface commonInterface;

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
                if (selectedStatePosition <= 0) {
                    Log.e("message_data-if", "filter dismissed " + selectedStatePosition);
                    dismiss();
                } else {
                    Log.e("message_data-else", "filter webservice called");
                    getDataByCity();
                    dismiss();
                }
            }
        });

        imgCLose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        clearAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedStatePosition > 0) {
                    spState.setSelection(0);
                    unCheckCityList(productAvailableCityList);
                    RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(context);
                    CityRecyclerAdapter cityRecyclerAdapter = new CityRecyclerAdapter(context, productAvailableCityList);
                    cityRecyclerView.setAdapter(cityRecyclerAdapter);
                    cityRecyclerView.setLayoutManager(mLayoutManager);

                }
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
        spState = (Spinner) findViewById(R.id.spStateCategory);
        cityRecyclerView = (RecyclerView) findViewById(R.id.selectCityList);
        clearAll = (TextView) findViewById(R.id.clearAll);
    }

    private void setUpData() {

        CityRecyclerAdapter.commonInterface = new CommonInterface() {
            @Override
            public Object getData(Object object) {
                getSelectedCityList = (ArrayList<City>) object;
                Log.e("getSelectedCityList", getSelectedCityList.toString());
                return null;
            }
        };

        setUpStateSpinner(productAvailableStateList);
    }

    private void showMessage(String msg) {
        AndroidUtils.showSnackBar((LinearLayout) findViewById(R.id.dialog_filter_root), msg);
    }


    private void unCheckCityList(ArrayList<City> cityArrayList) {
        if (cityArrayList != null) {
            if (cityArrayList.size() > 0) {
                for (int i = 0; i < cityArrayList.size(); i++) {
                    if (cityArrayList.get(i).isChecked) {
                        cityArrayList.get(i).isChecked = false;
                    }
                }
            }
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
                            productAvailableCityList = new ArrayList<>();
                            for (int i = 0; i < cityArray.size(); i++) {
                                JsonObject jsonObject = (JsonObject) cityArray.get(i);
                                City city = new City(jsonObject.get("city_id").getAsString(), jsonObject.get("ctyname").getAsString());
                                productAvailableCityList.add(city);
                                Log.e("message_data---", "CityName :  " + city.cityName);
                            }
                            if (productAvailableCityList.size() > 0) {
                                setUpCityAdapter();
                            }
                        }
                    }

                });
    }


    private void getDataByCity() {
        Log.e("message_data---", "called with category id " + categoryId + " stateId " + stateId + " city array \n " + makeCityIdJSonArray(getSelectedCityList).toString());

        Ion.with(context)
                .load(context.getResources().getString(R.string.webservice_base_url) + "/productlist")
                .setHeader("authorization", "xvfdbgfdhbfdhtrh54654h54ygdgerwer3")
                .setBodyParameter("category_id", categoryId)
                .setBodyParameter("state_id", stateId)
                .setBodyParameter("city_id", makeCityIdJSonArray(getSelectedCityList).toString())
                .setBodyParameter("apply", "1")
//                .asString()
//                .setCallback(new FutureCallback<String>() {
//                    @Override
//                    public void onCompleted(Exception e, String result) {
//                        Log.e("message_data---", "returned filter data"+result);
//                    }
//                });
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {


                        if (result == null) {
                            Log.e("message_data---", "null found");
                        } else {
                            Log.e("result by city", result.toString());
                            JsonArray jsonArray = result.getAsJsonArray("result");
                            ArrayList<CategoriesListData> productListDatas = new ArrayList<>();

                            for (int i = 0; i < jsonArray.size(); i++) {
                                JsonObject jsonObject2 = (JsonObject) jsonArray.get(i);

                                String product_id = jsonObject2.get("id").getAsString();

                                String product_name = jsonObject2.get("name").getAsString();

                                String product_price = jsonObject2.get("price").getAsString();

                                String product_cross_price = jsonObject2.get("cross_price").getAsString();

                                String product_image = jsonObject2.get("image_url").getAsString();
                                String productlocation = jsonObject2.get("city_name").getAsString() + "," + jsonObject2.get("state_name").getAsString() + "," +
                                        jsonObject2.get("country_name").getAsString();
                                productListDatas.add(new CategoriesListData(product_id, product_name, product_price, product_cross_price, product_image, productlocation));
                                commonInterface.getData(productListDatas);
                            }
                        }
                    }

                });
    }


    private void setUpStateSpinner(final ArrayList<State> stateList) {
        SpStateAdapter spStateAdapter = new SpStateAdapter(context, stateList);
        spState.setAdapter(spStateAdapter);
        spState.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position > 0) {
                    selectedStatePosition = position;
                    stateId = stateList.get(position).stateId;
                    getDataByState();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void setUpCityAdapter() {
        Log.e("message_data---", "CityRecyclerAdapter called with productAvailableCityListSize : " + productAvailableCityList.size());
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(context);
        CityRecyclerAdapter cityRecyclerAdapter = new CityRecyclerAdapter(context, productAvailableCityList);
        cityRecyclerView.setAdapter(cityRecyclerAdapter);
        cityRecyclerView.setLayoutManager(mLayoutManager);
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
}
