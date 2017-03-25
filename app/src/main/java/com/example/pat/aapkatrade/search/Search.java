package com.example.pat.aapkatrade.search;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Looper;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.pat.aapkatrade.Home.CommomAdapter;
import com.example.pat.aapkatrade.Home.CommomData;
import com.example.pat.aapkatrade.Home.HomeActivity;
import com.example.pat.aapkatrade.Home.registration.RegistrationActivity;
import com.example.pat.aapkatrade.Home.registration.entity.State;
import com.example.pat.aapkatrade.Home.registration.spinner_adapter.SpStateAdapter;
import com.example.pat.aapkatrade.R;
import com.example.pat.aapkatrade.categories_tab.CategoryListActivity;
import com.example.pat.aapkatrade.general.Adapter_callback_interface;
import com.example.pat.aapkatrade.general.App_config;
import com.example.pat.aapkatrade.general.Call_webservice;
import com.example.pat.aapkatrade.general.TaskCompleteReminder;
import com.example.pat.aapkatrade.general.Utils.AndroidUtils;
import com.example.pat.aapkatrade.general.Utils.adapter.CustomAutocompleteAdapter;
import com.example.pat.aapkatrade.general.Utils.adapter.Webservice_search_autocompleteadapter;
import com.example.pat.aapkatrade.general.progressbar.ProgressBarHandler;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class Search extends AppCompatActivity  implements Adapter_callback_interface {

    AutoCompleteTextView autocomplete_textview_state, autocomplete_textview_product;
    CustomAutocompleteAdapter categoryadapter;
    Context c;
    GridLayoutManager gridLayoutManager;
    public static RecyclerView recyclerView_search, state_names_recycler, category_names_recycler;
    CommomAdapter commomAdapter;
    Spinner state_list_spinner;
    RecyclerView.LayoutManager mLayoutManager_state,mLayoutManager_category;
    common_state_search common_state_search;
    Adapter_callback_interface callback_interface;
    ArrayList<String> state_names = new ArrayList<>();
    ArrayList<String> product_names = new ArrayList<>();
    ArrayList<CommomData> search_productlist = new ArrayList<>();
    ArrayList<common_category_search> common_category_searchlist = new ArrayList<>();
    ArrayList<common_state_search> common_state_searchlist = new ArrayList<>();
    ArrayList<common_city_search> common_city_searchlist = new ArrayList<>();
    Toolbar toolbar;
    Webservice_search_autocompleteadapter product_autocompleteadapter;
    ProgressBarHandler progressBarHandler;
    CoordinatorLayout coordinate_search;
    private Adapter_callback_interface callback_listener;

    private ArrayList<String> stateList = new ArrayList<>();
    SearchResultsAdapter searchResultsAdapter;
    SearchcategoryAdapter searchResults_category_Adapter;
    SearchStateAdapter searchResults_state_Adapter, searchResults_city_Adapter;
    HashMap<String, String> webservice_header_type = new HashMap<>();
    String currentlocation_statename;
    int current_state_index;
    String class_name;

    String selected_categoryid;
    ViewPager viewpager_state;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        c = Search.this;
        Intent i = getIntent();
        currentlocation_statename = i.getStringExtra("state_name");
        Log.e("current_statename_",currentlocation_statename);

        class_name = i.getStringExtra("classname");
        Log.e("class_name",class_name);



        setuptoolbar();

        initview();
        call_state_webservice(currentlocation_statename);


    }


    private void setuptoolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(null);
        getSupportActionBar().setElevation(0);
        ((ImageView) toolbar.findViewById(R.id.img_vew_location)).setColorFilter(ContextCompat.getColor(Search.this, R.color.white));

        // getSupportActionBar().setIcon(R.drawable.home_logo);

    }

    private void initview() {
        progressBarHandler = new ProgressBarHandler(Search.this);
        autocomplete_textview_product = (AutoCompleteTextView) findViewById(R.id.search_autocompletetext_products);
        autocomplete_textview_product.setThreshold(1);
        setup_state_spinner();

        setup_state_Recycleview();
        setup_category_Recycleview();
        setup_search_Recyclewview();


        coordinate_search = (CoordinatorLayout) findViewById(R.id.coordinate_search);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setBackgroundTintList(getResources().getColorStateList(R.color.color_voilet));
        fab.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                // Click action
//                Intent intent = new Intent(MainActivity.this, NewMessageActivity.class);
//                startActivity(intent);
            }
        });









        autocomplete_textview_product.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                String text = s.toString();

                if (text.length() > 0) {


                    String product_search_url = (getResources().getString(R.string.webservice_base_url)) + "/product_search";


                    call_search_suggest_webservice_product(product_search_url, text, state_list_spinner.getSelectedItem().toString());


                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        autocomplete_textview_product.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {


                if (actionId == EditorInfo.IME_ACTION_SEARCH) {


                    if (autocomplete_textview_product.getText().length() != 0) {
                        // Log.e("text_editor",autocomplete_textview_state.getText().toString()+"**********"+autocomplete_textview_state.getText().toString());
                        call_search_webservice(state_list_spinner.getSelectedItem().toString(), autocomplete_textview_product.getText().toString(),"","","","");

                        App_config.hideKeyboard(Search.this);

                    }


                    return true;
                }
                return false;


            }
        });


    }

    private void setup_search_Recyclewview() {

        recyclerView_search = (RecyclerView) findViewById(R.id.recycleview_search);
        gridLayoutManager = new GridLayoutManager(c, 2);
        recyclerView_search.setLayoutManager(gridLayoutManager);
        commomAdapter = new CommomAdapter(Search.this, search_productlist, "grid", "latestupdate");
        recyclerView_search.setAdapter(commomAdapter);

    }

    private void setup_category_Recycleview() {


        category_names_recycler = (RecyclerView) findViewById(R.id.category_names_recycler);
        mLayoutManager_category = new LinearLayoutManager(c, LinearLayoutManager.HORIZONTAL, false);
        category_names_recycler.setLayoutManager(mLayoutManager_category);
        searchResults_category_Adapter = new SearchcategoryAdapter(Search.this, common_category_searchlist, state_list_spinner.getItemAtPosition(current_state_index).toString(),
                autocomplete_textview_product.getText().toString());
        category_names_recycler.setAdapter(searchResults_category_Adapter);


    }

    private void setup_state_Recycleview() {

        state_names_recycler = (RecyclerView) findViewById(R.id.state_names_recycler);
        mLayoutManager_state = new LinearLayoutManager(c, LinearLayoutManager.HORIZONTAL, false);
        state_names_recycler.setLayoutManager(mLayoutManager_state);
        searchResults_state_Adapter = new SearchStateAdapter(Search.this, common_state_searchlist);
        state_names_recycler.setAdapter(searchResults_state_Adapter);


    }

    private void setup_state_spinner() {

        state_list_spinner = (Spinner) findViewById(R.id.spin_select_state);
        stateList = new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.state_list)));



        ArrayAdapter spinnerArrayAdapter = new ArrayAdapter(c, R.layout.white_textcolor_spinner, stateList);
        spinnerArrayAdapter.setDropDownViewResource(R.layout.white_textcolor_spinner);

        state_list_spinner.setAdapter(spinnerArrayAdapter);

    }


    private void call_state_webservice( String a) {

        progressBarHandler.show();
        Log.e("statelist_state", stateList.toString() + "" + c);





        for (int i = 0; i < stateList.size(); i++) {

            if (a.equals(stateList.get(i))) {

                current_state_index = i;
                Log.e("current_state_index", current_state_index + "");
            }
        }
        Log.e("current_state_index2", current_state_index + "");
        state_list_spinner.setSelection(current_state_index);
        progressBarHandler.hide();


    }

    private void call_search_webservice(String location_text, final String product_name1, final String id, String stateid, String cityid, final String type) {

        Log.e("callback_state",id+"***"+type);


        String search_url = (getResources().getString(R.string.webservice_base_url)) + "/search";
        progressBarHandler.show();
        if (type.contains("category")){

            Ion.with(Search.this)
                    .load(search_url)
                    .setHeader("authorization", "xvfdbgfdhbfdhtrh54654h54ygdgerwer3")

                    .setBodyParameter("authorization", "xvfdbgfdhbfdhtrh54654h54ygdgerwer3")
                    .setBodyParameter("name", product_name1)
                    .setBodyParameter("category_id",id)
                    .asJsonObject()
                    .setCallback(new FutureCallback<JsonObject>() {
                        @Override
                        public void onCompleted(Exception e, JsonObject result) {

                            Log.e("first---",result.toString());

                            if (result != null) {

                                set_webservice_data(result,type);

                                Log.e("call 2_sending",product_name1+"**"+id+"");
                                // Log.e("call 2_receiving",result.toString());


                            } else {
                                progressBarHandler.hide();
                            }


//
                        }


                    });


        }
        else if (type.contains("state")){

            Ion.with(Search.this)
                    .load(search_url)
                    .setHeader("authorization", "xvfdbgfdhbfdhtrh54654h54ygdgerwer3")

                    .setBodyParameter("authorization", "xvfdbgfdhbfdhtrh54654h54ygdgerwer3")
                    .setBodyParameter("name", product_name1)
                    .setBodyParameter("state_id ",id)
                    .setBodyParameter("category_id","")
                    .asJsonObject()
                    .setCallback(new FutureCallback<JsonObject>() {
                        @Override
                        public void onCompleted(Exception e, JsonObject result) {

                            Log.e("second---",result.toString());

                            if (result != null) {

                                set_webservice_data(result,type);

                                Log.e("call 2_state",product_name1+"**"+id+"");
                                // Log.e("call 2_receiving",result.toString());


                            } else {
                                progressBarHandler.hide();
                            }


//
                        }


                    });


        }








        else{

            Ion.with(Search.this)
                    .load(search_url)
                    .setHeader("authorization", "xvfdbgfdhbfdhtrh54654h54ygdgerwer3")
                    .setBodyParameter("location", location_text)
                    .setBodyParameter("authorization", "xvfdbgfdhbfdhtrh54654h54ygdgerwer3")
                    .setBodyParameter("name", product_name1)
                    .asJsonObject()
                    .setCallback(new FutureCallback<JsonObject>() {
                        @Override
                        public void onCompleted(Exception e, JsonObject result) {
                            if (result != null) {
                                set_webservice_data(result,"");

                                Log.e("call 3",result.toString());

                            } else {
                                progressBarHandler.hide();
                            }


//
                        }


                    });


        }








    }

    public void set_webservice_data(JsonObject result,String type) {


        Log.e("Arvind_data",result.toString());

        search_productlist.clear();
        common_category_searchlist.clear();
        common_state_searchlist.clear();
        common_city_searchlist.clear();


        JsonObject jsonObject = result.getAsJsonObject();

        String error = jsonObject.get("error").getAsString();
        String message = jsonObject.get("message").getAsString();
        if (message.contains("Failed")) {


            AndroidUtils.showSnackBar(coordinate_search, "No Suggesstion found");
            progressBarHandler.hide();

        } else {
          //  search_productlist=new ArrayList<>();

            Log.e("data2_search", result.toString());
            if (jsonObject.get("result").isJsonNull()) {
                Log.e("data_jsonArray null", result.toString());
            }


            JsonArray jsonarray_result = jsonObject.getAsJsonArray("result");
            Log.e("data_jsonarray", jsonarray_result.toString());


            for (int l = 0; l < jsonarray_result.size(); l++) {

                JsonObject jsonObject_result = (JsonObject) jsonarray_result.get(l);
                String productname = jsonObject_result.get("name").getAsString();
                String productid = jsonObject_result.get("id").getAsString();
                String product_prize = jsonObject_result.get("price").getAsString();
                String imageurl = jsonObject_result.get("image_url").getAsString();
                String productlocation = jsonObject_result.get("city_name").getAsString() + "," + jsonObject_result.get("state_name").getAsString() + "," +
                        jsonObject_result.get("country_name").getAsString();

                search_productlist.add(new CommomData(productid, productname, product_prize, imageurl, productlocation));


            }


            commomAdapter = new CommomAdapter(Search.this, search_productlist, "grid", "latestupdate");
            //recyclerView_search.setAdapter(commomAdapter);
            commomAdapter.notifyDataSetChanged();




            JsonArray jsonarray_category = jsonObject.getAsJsonArray("category");


            for (int l = 0; l < jsonarray_category.size(); l++) {

                JsonObject jsonObject_result = (JsonObject) jsonarray_category.get(l);
                String cat_id = jsonObject_result.get("category_id").getAsString();
                String catname = jsonObject_result.get("catname").getAsString();
                String countprod = jsonObject_result.get("countprod").getAsString();

                common_category_searchlist.add(new common_category_search(cat_id, catname, countprod));


            }

            searchResults_category_Adapter = new SearchcategoryAdapter(Search.this, common_category_searchlist, state_list_spinner.getItemAtPosition(current_state_index).toString(),
                    autocomplete_textview_product.getText().toString());
            Log.e("category_data",common_category_searchlist.toString());
            searchResults_category_Adapter.notifyDataSetChanged();


            JsonArray jsonarray_states = jsonObject.getAsJsonArray("states");


            for (int l = 0; l < jsonarray_states.size(); l++) {

                JsonObject jsonObject_result = (JsonObject) jsonarray_states.get(l);
                String state_id = jsonObject_result.get("state_id").getAsString();
                String statename = jsonObject_result.get("statename").getAsString();
                String countprod = jsonObject_result.get("countprod").getAsString();
            common_state_search=  new common_state_search(state_id, statename, countprod);


                common_state_searchlist.add(common_state_search);


            }

            searchResults_state_Adapter = new SearchStateAdapter(Search.this, common_state_searchlist);
            Log.e("state_data",common_state_searchlist.toString());

            searchResults_state_Adapter.notifyDataSetChanged();






            JsonArray jsonarray_cities = jsonObject.getAsJsonArray("cities");


            for (int l = 0; l < jsonarray_cities.size(); l++) {

                JsonObject jsonObject_result = (JsonObject) jsonarray_cities.get(l);
                String city_id = jsonObject_result.get("city_id").getAsString();
                String ctyname = jsonObject_result.get("ctyname").getAsString();
                String countprod = jsonObject_result.get("countprod").getAsString();

                common_city_searchlist.add(new common_city_search(city_id, ctyname, countprod));


            }




//            state_list_spinner.setSelection(current_state_index);
//            if (type.contains("category"))
//            {
//
//            }
//            else{
//                Log.e("work2",type);
//
//                category_names_recycler.setAdapter(searchResults_category_Adapter);
//            }






//            if(searchResults_state_Adapter!=null)
//            {
//                searchResults_state_Adapter = new SearchStateAdapter(Search.this, common_state_searchlist);
//                state_names_recycler.setAdapter(searchResults_state_Adapter);
//
//                Log.e("searchstateAdapter!","notnull");
//
//            }
//            else {
//                searchResults_state_Adapter = new SearchStateAdapter(Search.this, common_state_searchlist);
//                searchResults_state_Adapter.notifyDataSetChanged();
//                Log.e("searchstateAdapter","null");
//            }


//search recycleview set adapter
           // recyclerView_search.setLayoutManager(gridLayoutManager);

            progressBarHandler.hide();
            findViewById(R.id.search_category_state_container).setVisibility(View.VISIBLE);

        }




    }


    private void call_search_suggest_webservice_product(String product_search_url, String product_search_text, String location_text) {

        Ion.with(Search.this)
                .load(product_search_url)
                .setHeader("authorization", "xvfdbgfdhbfdhtrh54654h54ygdgerwer3")
                .setBodyParameter("location", location_text.trim())
                .setBodyParameter("authorization", "xvfdbgfdhbfdhtrh54654h54ygdgerwer3")
                .setBodyParameter("name", product_search_text.trim())
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {
                        if (result != null) {
                            product_names.clear();
                            product_names = new ArrayList<String>();
                            JsonObject jsonObject = result.getAsJsonObject();

                            String error = jsonObject.get("error").getAsString();
                            String message = jsonObject.get("message").getAsString();
                            if (message.contains("Failed")) {


                                AndroidUtils.showSnackBar(coordinate_search, "No Suggesstion found");

                            }

                            else {

                                Log.e("data2", result.toString());
                                if (jsonObject.get("result").isJsonNull()) {

                                }


                                JsonArray jsonarray_result = jsonObject.getAsJsonArray("result");


                                for (int l = 0; l < jsonarray_result.size(); l++) {

                                    JsonObject jsonObject_result = (JsonObject) jsonarray_result.get(l);
                                    String productname = jsonObject_result.get("name").getAsString();

                                    product_names.add(productname);

                                }


                                if (error.contains("false")) {



                                    product_autocompleteadapter = new Webservice_search_autocompleteadapter(c, product_names);

                                    if (product_names.size() != 0)
                                        autocomplete_textview_product.setAdapter(product_autocompleteadapter);


//


                                }


                            }


                        }


                    }

                });


    }





    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.bottom_home_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                if (class_name.contains("homeactivity"))

                {
                    Intent goto_home = new Intent(Search.this, HomeActivity.class);
                    startActivity(goto_home);
                    finish();

                } else {
                    Intent goto_categorylist = new Intent(Search.this, CategoryListActivity.class);
                    startActivity(goto_categorylist);
                    finish();
                }


                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return super.onOptionsItemSelected(item);
    }




    @Override
    public void onBackPressed() {
        if (class_name.contains("homeactivity"))

        {
            Intent goto_home = new Intent(Search.this, HomeActivity.class);
            startActivity(goto_home);
            finish();

        } else {

        }

    }





    @Override
    public void callback(String id,String type) {


        Log.e("callback_state",id+"***"+type);
        if(type.contains("category")) {
            call_search_webservice(state_list_spinner.getSelectedItem().toString(), autocomplete_textview_product.getText().toString(), id, null, "", "category");

        }
        else {
            call_search_webservice(state_list_spinner.getSelectedItem().toString(), autocomplete_textview_product.getText().toString(), id, null, "", "state");

        }

    }
}









