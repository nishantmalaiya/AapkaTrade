package com.example.pat.aapkatrade.categories_tab.PurticularDataActivity;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatImageView;
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

import com.example.pat.aapkatrade.Home.HomeActivity;
import com.example.pat.aapkatrade.R;
import com.example.pat.aapkatrade.categories_tab.CategoriesListAdapter;
import com.example.pat.aapkatrade.categories_tab.CategoriesListData;
import com.example.pat.aapkatrade.general.CheckPermission;
import com.example.pat.aapkatrade.general.LocationManager_check;
import com.example.pat.aapkatrade.general.Utils.AndroidUtils;
import com.example.pat.aapkatrade.general.progressbar.ProgressBarHandler;
import com.example.pat.aapkatrade.general.recycleview_custom.MyRecyclerViewEffect;
import com.example.pat.aapkatrade.location.AddressEnum;
import com.example.pat.aapkatrade.location.GeoCoderAddress;
import com.example.pat.aapkatrade.location.Mylocation;
import com.example.pat.aapkatrade.search.Search;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import java.util.ArrayList;

import it.carlom.stikkyheader.core.StikkyHeaderBuilder;


public class PurticularActivity extends AppCompatActivity
{

    RecyclerView mRecyclerView;
    CategoriesListAdapter categoriesListAdapter;
    ArrayList<CategoriesListData> productListDatas = new ArrayList<>();
    ProgressBarHandler progress_handler;
    FrameLayout layout_container, layout_container_relativeSearch;
    MyRecyclerViewEffect myRecyclerViewEffect;
    JsonObject home_data;
    String url;
    Mylocation mylocation;
    private Context context;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {

        super.onCreate(savedInstanceState);
        context = PurticularActivity.this;

        Intent intent = getIntent();

         url = intent.getStringExtra("url");

         System.out.println("url---------------"+url);

//        Intent i = this.getIntent();
//        ArrayList commomDatas_latestpost =  i.getParcelableArrayListExtra("commomDatas_latestpost");
//        for(int j = 0; j < commomDatas_latestpost.size(); j++){
//            CommomData commomData = (CommomData) commomDatas_latestpost.get(j);
//            Log.e("getData1", commomData.toString());
//
//        }
//
//        productListDatas = commomDatas_latestpost;

        setContentView(R.layout.activity_categories_list);

        setUpToolBar();

        ViewGroup view = (ViewGroup) findViewById(android.R.id.content);

        progress_handler = new ProgressBarHandler(this);

        layout_container = (FrameLayout) view.findViewById(R.id.layout_container);

        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerview);

        findViewById(R.id.home_search).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                boolean permission_status = CheckPermission.checkPermissions(PurticularActivity.this);


                if (permission_status)

                { mylocation = new Mylocation(PurticularActivity.this);
                    LocationManager_check locationManagerCheck = new LocationManager_check(
                            PurticularActivity.this);
                    Location location = null;
                    if (locationManagerCheck.isLocationServiceAvailable())
                    {


                            double latitude = mylocation.getLatitude();
                            double longitude = mylocation.getLongitude();
                            GeoCoderAddress geoCoderAddress_statename = new GeoCoderAddress(PurticularActivity.this, latitude, longitude);
                            String state_name = geoCoderAddress_statename.get_state_name().get(AddressEnum.STATE);
                        if(state_name!=null) {
                            Intent goto_search = new Intent(PurticularActivity.this, Search.class);
                            goto_search.putExtra("latitude", latitude);
                            goto_search.putExtra("longitude", longitude);
                            goto_search.putExtra("state_name", state_name);
                            startActivity(goto_search);
                            finish();
                        }

                        else{
                            Log.e("statenotfound",""+"statenotfound");
                        }
                    }
                    else
                    {
                        locationManagerCheck.createLocationServiceError(PurticularActivity.this);
                    }

                }














            }
        });

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));

        mRecyclerView.setHasFixedSize(true);

        StikkyHeaderBuilder.stickTo(mRecyclerView)
                .setHeader(R.id.header_simple, view)
                .minHeightHeaderDim(R.dimen.min_header_height)
                .build();

        get_web_data();

    }

    private void get_web_data() {
        productListDatas.clear();
        progress_handler.show();

        Ion.with(PurticularActivity.this)
                .load(url)
                .setHeader("authorization", "xvfdbgfdhbfdhtrh54654h54ygdgerwer3")
                .setBodyParameter("authorization", "xvfdbgfdhbfdhtrh54654h54ygdgerwer3")
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {

                        if (result == null) {
                            progress_handler.hide();
                            layout_container.setVisibility(View.INVISIBLE);
                        } else {
                            JsonObject jsonObject = result.getAsJsonObject();

                            String message = jsonObject.get("message").toString().substring(0, jsonObject.get("message").toString().length());

                            String message_data = message.replace("\"", "");

                            System.out.println("message_data==================" + message_data);

                            if (message_data.equals("No record found")) {

                                progress_handler.hide();
                                layout_container.setVisibility(View.INVISIBLE);

                            }
                            else
                            {
                                JsonArray jsonArray = jsonObject.getAsJsonArray("result");

                                for (int i = 0; i < jsonArray.size(); i++) {
                                    JsonObject jsonObject2 = (JsonObject) jsonArray.get(i);

                                    String product_id = jsonObject2.get("id").getAsString();

                                    String product_name = jsonObject2.get("prodname").getAsString();

                                    String product_price = jsonObject2.get("price").getAsString();

                                    String product_cross_price = jsonObject2.get("cross_price").getAsString();

                                    String productlocation=jsonObject2.get("city_name").getAsString()+","+jsonObject2.get("state_name").getAsString()+","+
                                            jsonObject2.get("country_name").getAsString();
                                    String product_image = jsonObject2.get("image_url").getAsString();

                                    productListDatas.add(new CategoriesListData(product_id, product_name, product_price, product_cross_price, product_image,productlocation));

                                    }
                                    categoriesListAdapter = new CategoriesListAdapter(PurticularActivity.this, productListDatas);
                                    myRecyclerViewEffect = new MyRecyclerViewEffect(PurticularActivity.this);
                                    mRecyclerView.setAdapter(categoriesListAdapter);

                                }
////                                categoriesListAdapter = new CategoriesListAdapter(PurticularActivity.this, productListDatas);
////                                myRecyclerViewEffect = new MyRecyclerViewEffect(PurticularActivity.this);
////                                mRecyclerView.setAdapter(categoriesListAdapter);
//
//                                categoriesListAdapter.notifyDataSetChanged();

                                progress_handler.hide();
                            }

                        }



                });

    }

    private void setUpToolBar() {
        ImageView homeIcon = (ImageView) findViewById(R.id.iconHome) ;
        AppCompatImageView back_imagview = (AppCompatImageView) findViewById(R.id.back_imagview);
        back_imagview.setVisibility(View.VISIBLE);
        back_imagview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
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
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
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
