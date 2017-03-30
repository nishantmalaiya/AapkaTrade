package com.example.pat.aapkatrade.categories_tab;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.pat.aapkatrade.Home.HomeActivity;
import com.example.pat.aapkatrade.Home.registration.entity.State;
import com.example.pat.aapkatrade.R;
import com.example.pat.aapkatrade.filter.FilterDialog;
import com.example.pat.aapkatrade.general.AppSharedPreference;
import com.example.pat.aapkatrade.general.CommonInterface;
import com.example.pat.aapkatrade.general.LocationManager_check;
import com.example.pat.aapkatrade.general.TaskCompleteReminder;
import com.example.pat.aapkatrade.general.Utils.AndroidUtils;
import com.example.pat.aapkatrade.general.Validation;
import com.example.pat.aapkatrade.general.progressbar.ProgressBarHandler;
import com.example.pat.aapkatrade.general.recycleview_custom.MyRecyclerViewEffect;
import com.example.pat.aapkatrade.location.MyAsyncTask_location;
import com.example.pat.aapkatrade.location.Mylocation;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import java.util.ArrayList;
import java.util.List;

import it.carlom.stikkyheader.core.StikkyHeaderBuilder;


public class CategoryListActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private CategoriesListAdapter categoriesListAdapter;
    private ArrayList<CategoriesListData> productListDatas = new ArrayList<>();
    private ProgressBarHandler progress_handler;
    private FrameLayout layout_container, layout_container_relativeSearch;
    private MyRecyclerViewEffect myRecyclerViewEffect;
    private String category_id, sub_category_id, user_id;
    private AppSharedPreference app_sharedpreference;
    private Mylocation mylocation;
    private ArrayList<State> productAvailableStateList = new ArrayList<>();
    private Context context;
    private TextView toolbarRightText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_categories_list);
        context = CategoryListActivity.this;
        Intent intent = getIntent();


        Bundle b = intent.getExtras();
        if (b != null) {
            category_id = b.getString("category_id");
        }
        app_sharedpreference = new AppSharedPreference(this);

        user_id = app_sharedpreference.getsharedpref("userid", "");

        setUpToolBar();

        ViewGroup view = (ViewGroup) findViewById(android.R.id.content);

        progress_handler = new ProgressBarHandler(this);

        layout_container = (FrameLayout) view.findViewById(R.id.layout_container);

        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerview);

        findViewById(R.id.home_search).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                LocationManager_check locationManagerCheck = new LocationManager_check(
                        CategoryListActivity.this);
                Location location = null;
                if (locationManagerCheck.isLocationServiceAvailable()) {

                    MyAsyncTask_location myAsyncTask_location = new MyAsyncTask_location(CategoryListActivity.this, "homeactivity");
                    myAsyncTask_location.execute();


                } else {
                    locationManagerCheck.createLocationServiceError(CategoryListActivity.this);
                }


            }
        });

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));

        mRecyclerView.setHasFixedSize(true);

        StikkyHeaderBuilder.stickTo(mRecyclerView)
                .setHeader(R.id.header_simple, view)
                .minHeightHeaderDim(R.dimen.min_header_height)
                .build();

        FilterDialog.commonInterface = new CommonInterface() {
            @Override
            public Object getData(Object object) {
                categoriesListAdapter = new CategoriesListAdapter(CategoryListActivity.this, (List<CategoriesListData>) object);
                myRecyclerViewEffect = new MyRecyclerViewEffect(CategoryListActivity.this);
                mRecyclerView.setAdapter(categoriesListAdapter);
                categoriesListAdapter.notifyDataSetChanged();
                return null;
            }
        };

        get_web_data();


    }

    private void get_web_data() {


        productListDatas.clear();
        State state = new State("-1", "Select State", "0");
        productAvailableStateList.add(state);


        progress_handler.show();
        Ion.with(CategoryListActivity.this)
                .load(getResources().getString(R.string.webservice_base_url)+"/productlist")
                .setHeader("authorization", "xvfdbgfdhbfdhtrh54654h54ygdgerwer3")
                .setBodyParameter("type", "product_list")
                .setBodyParameter("authorization", "xvfdbgfdhbfdhtrh54654h54ygdgerwer3")
                .setBodyParameter("category_id", category_id)
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {

                        if (result == null) {
                            progress_handler.hide();
                            layout_container.setVisibility(View.INVISIBLE);
                        } else {
                            if(Validation.isNumber(result.get("total_result").getAsString()) && Integer.parseInt(result.get("total_result").getAsString())>1){
                                toolbarRightText.setVisibility(View.VISIBLE);
                            }
                            JsonArray statesArray = result.get("states").getAsJsonArray();
                            for(int i = 0; i < statesArray.size(); i++){
                                JsonObject stateObject = (JsonObject) statesArray.get(i);
                                State state = new State(stateObject.get("state_id").getAsString(), stateObject.get("statename").getAsString(), stateObject.get("countprod").getAsString());
                                productAvailableStateList.add(state);
                            }

                            String message = result.get("message").toString().substring(0, result.get("message").toString().length());

                            String message_data = message.replace("\"", "");

                            Log.e("message_product_list", result.toString());

                            if (message_data.equals("No record found")) {
                                progress_handler.hide();
                                layout_container.setVisibility(View.INVISIBLE);

                            } else {
                                JsonArray jsonArray = result.getAsJsonArray("result");

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

                                }

                                categoriesListAdapter = new CategoriesListAdapter(CategoryListActivity.this, productListDatas);
                                myRecyclerViewEffect = new MyRecyclerViewEffect(CategoryListActivity.this);
                                mRecyclerView.setAdapter(categoriesListAdapter);
                                categoriesListAdapter.notifyDataSetChanged();
                                progress_handler.hide();
                            }
                        }

                    }

                });

    }

    private void setUpToolBar() {
        ImageView homeIcon = (ImageView) findViewById(R.id.iconHome);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        AndroidUtils.setImageColor(homeIcon, context, R.color.white);
        homeIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, HomeActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
        toolbarRightText = (TextView) findViewById(R.id.toolbarRightText);
        toolbarRightText.setBackground(ContextCompat.getDrawable(context, R.drawable.ic_filter));
        toolbarRightText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FilterDialog filterDialog = new FilterDialog(context, category_id, productAvailableStateList);
                filterDialog.show();
            }
        });
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle(null);
            getSupportActionBar().setElevation(0);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_map, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return super.onOptionsItemSelected(item);
    }


}

