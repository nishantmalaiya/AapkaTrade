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
import android.widget.ArrayAdapter;
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

import com.example.pat.aapkatrade.search.Search;
import com.example.pat.aapkatrade.search.common_category_search;
import com.example.pat.aapkatrade.search.common_state_search;

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
    private TextView applyFilter, clearAll;
    private ArrayList<State> productAvailableStateList = new ArrayList<>();
    private ArrayList<City> productAvailableCityList = new ArrayList<>();
    private Spinner spState;
    JsonObject resultData;
    private JsonArray cityArray;
    private RecyclerView cityRecyclerView;

    private ArrayList<City> getSelectedCityList = new ArrayList<>();
    public static CommonInterface commonInterface;
    private Intent intent;
    String search_name;
    public ArrayList<common_category_search> common_category_searchlist;

    public static TaskCompleteReminder taskCompleteReminder = null;
    String classname ;
    Spinner category_list,state_list;
    ArrayList<String> categorynames=new ArrayList<>();
    ArrayList<String> categoryids=new ArrayList<>();
    ArrayList<String> statenames=new ArrayList<>();
    ArrayList<String> stateids=new ArrayList<>();


    common_state_search common_state_search;


    public FilterDialog(Context context, String categoryId) {
        super(context);
        this.context = context;
        this.categoryId = categoryId;

    }

    public FilterDialog(Context context, String categoryId, ArrayList<State> productAvailableStateList) {
        super(context);
        this.context = context;
        this.categoryId = categoryId;
        this.productAvailableStateList = productAvailableStateList;
        Log.e("message_data-categoryId", categoryId);
        Log.e("message_data--statesize", String.valueOf(productAvailableStateList.size()));


    public FilterDialog(Context context, String search_name, ArrayList<common_category_search> common_category_searchlist, String classname) {
        super(context);
        this.context = context;
        this.search_name = search_name;
        this.common_category_searchlist = common_category_searchlist;
        this.classname=classname;

        Log.e("classname", classname);

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

                getDataByCity();

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

    private void initView() {
        app_sharedpreference = new AppSharedPreference(context);
        progress_handler = new ProgressBarHandler(context);
        dialogue_toolbar = (RelativeLayout) findViewById(R.id.dialogue_toolbar);
        AndroidUtils.setBackgroundSolid(dialogue_toolbar, context, R.color.green, 8);
        imgCLose = (Button) findViewById(R.id.imgCLose);
        categoryFilter = (LinearLayout) findViewById(R.id.categoryFilter);

        category_list=(Spinner)findViewById(R.id.spCategory);
        state_list=(Spinner)findViewById(R.id.spStateCategory);
        if (classname.contains("search")) {
            if (categoryFilter.getVisibility() !=View.VISIBLE) {
                categoryFilter.setVisibility(View.VISIBLE);

            }
        } else {
            categoryFilter.setVisibility(View.GONE);
        }
        applyFilter = (TextView) findViewById(R.id.applyFilter);
        spState = (Spinner) findViewById(R.id.spStateCategory);
        cityRecyclerView = (RecyclerView) findViewById(R.id.selectCityList);
    }

    private void callWebService() {
        if (Validation.isEmptyStr(stateId)) {
            getDataByState();
        }
        if(classname.contains("search"))
        {
            setup_search_functionality();
        }

    }

    private void setup_search_functionality() {

setup_category_spinner();
        Log.e("search_name_filter",search_name);
        Log.e("common_category_search",""+common_category_searchlist);



    private void getDataByState() {
        Log.e("message_data---", "called with category id " + categoryId + " stateId " + stateId);

        Ion.with(context)
                .load(context.getResources().getString(R.string.webservice_base_url) + "/productlist")
                .setHeader("authorization", "xvfdbgfdhbfdhtrh54654h54ygdgerwer3")
                .setBodyParameter("category_id", categoryId)
                .setBodyParameter("state_id", stateId)

    private void setup_category_spinner() {

        for(int i=0;i<common_category_searchlist.size();i++)
        {String cat_id=common_category_searchlist.get(i).cat_id;
            categoryids.add(cat_id);
            String cat_name=common_category_searchlist.get(i).catname;
            categorynames.add(cat_name);
        }

        ArrayAdapter spinnerArrayAdapter = new ArrayAdapter(context,android.R.layout.simple_spinner_item, categorynames);
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);

        category_list.setAdapter(spinnerArrayAdapter);

        category_list.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                call_category_to_state_webservice(context,categoryids.get(position),search_name);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    }

    private void call_category_to_state_webservice(Context c,String category_id,String search_name) {

        statenames.clear();
        stateids.clear();
        Log.e("message_data---", "calledd with category id "+category_id);
        Log.e("message_data---URL", context.getResources().getString(R.string.webservice_base_url) + "/search");
        progress_handler.show();
        Ion.with(c)
                .load(context.getResources().getString(R.string.webservice_base_url) + "/search")
                .setHeader("authorization", "xvfdbgfdhbfdhtrh54654h54ygdgerwer3")
//                .setBodyParameter("authorization", "xvfdbgfdhbfdhtrh54654h54ygdgerwer3")
                .setBodyParameter("category_id", category_id)
                .setBodyParameter("name", search_name)

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
                            JsonObject jsonObject = result.getAsJsonObject();
                            JsonArray jsonarray_states = jsonObject.getAsJsonArray("states");
                            for (int l = 0; l < jsonarray_states.size(); l++) {
                                JsonObject jsonObject_result = (JsonObject) jsonarray_states.get(l);
                                String state_id = jsonObject_result.get("state_id").getAsString();
                                String statename = jsonObject_result.get("statename").getAsString();
                                String countprod = jsonObject_result.get("countprod").getAsString();
                                statenames.add(statename);
                                stateids.add(state_id);
                            }
                            ArrayAdapter spinnerArrayAdapter = new ArrayAdapter(context,android.R.layout.simple_spinner_item, statenames);
                            spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
                            state_list.setAdapter(spinnerArrayAdapter);
                            state_list.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                    call_state_to_city_webservice();

                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> parent) {

                                }
                            });







                           Log.e("result_state_list",result.toString());

                           // dismiss();




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

                });






    }

    private void call_state_to_city_webservice() {



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
        Log.e("message_data---", "called with category id " + categoryId + " stateId " + stateId+" city array \n "+makeCityIdJSonArray(getSelectedCityList).toString());

        Ion.with(context)
                .load(context.getResources().getString(R.string.webservice_base_url) + "/productlist")
                .setHeader("authorization", "xvfdbgfdhbfdhtrh54654h54ygdgerwer3")
                .setBodyParameter("category_id", categoryId)
                .setBodyParameter("state_id", stateId)
                .setBodyParameter("city_id", makeCityIdJSonArray(getSelectedCityList).toString())
                .setBodyParameter("apply", "1")
                .asString()
                .setCallback(new FutureCallback<String>() {
                    @Override
                    public void onCompleted(Exception e, String result) {
                        Log.e("message_data---", "returned filter data"+result);
                    }
                });
//                .asJsonObject()
//                .setCallback(new FutureCallback<JsonObject>() {
//                    @Override
//                    public void onCompleted(Exception e, JsonObject result) {
//                        if (result == null) {
//                            Log.e("message_data---", "null found");
//                        } else {
//                            Log.e("result by city", result.toString());
//                            JsonArray jsonArray = result.getAsJsonArray("result");
//                            ArrayList<CategoriesListData> productListDatas = new ArrayList<>();
//
//                            for (int i = 0; i < jsonArray.size(); i++) {
//                                JsonObject jsonObject2 = (JsonObject) jsonArray.get(i);
//
//                                String product_id = jsonObject2.get("id").getAsString();
//
//                                String product_name = jsonObject2.get("name").getAsString();
//
//                                String product_price = jsonObject2.get("price").getAsString();
//
//                                String product_cross_price = jsonObject2.get("cross_price").getAsString();
//
//                                String product_image = jsonObject2.get("image_url").getAsString();
//                                String productlocation = jsonObject2.get("city_name").getAsString() + "," + jsonObject2.get("state_name").getAsString() + "," +
//                                        jsonObject2.get("country_name").getAsString();
//                                productListDatas.add(new CategoriesListData(product_id, product_name, product_price, product_cross_price, product_image, productlocation));
//                                commonInterface.getData(productListDatas);
//                            }
//                        }
//                    }
//
//                });
    }


    private void setUpStateSpinner(final ArrayList<State> stateList) {
        SpStateAdapter spStateAdapter = new SpStateAdapter(context, stateList);
        spState.setAdapter(spStateAdapter);
        spState.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position > 0) {
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
