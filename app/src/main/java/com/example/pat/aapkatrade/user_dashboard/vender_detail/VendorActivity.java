package com.example.pat.aapkatrade.user_dashboard.vender_detail;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.pat.aapkatrade.R;
import com.example.pat.aapkatrade.general.AppSharedPreference;
import com.example.pat.aapkatrade.general.progressbar.ProgressBarHandler;
import com.example.pat.aapkatrade.user_dashboard.companylist.CompanyData;
import com.example.pat.aapkatrade.user_dashboard.companylist.CompanyList;
import com.example.pat.aapkatrade.user_dashboard.companylist.CompanyListAdapter;
import com.example.pat.aapkatrade.user_dashboard.order_list.OrderListAdapter;
import com.example.pat.aapkatrade.user_dashboard.order_list.OrderListData;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import java.util.ArrayList;

public class VendorActivity extends AppCompatActivity
{

    private ArrayList<VendorData> venderListDatas = new ArrayList<>();
    private RecyclerView vender_list;
    VendorAdapter vendorAdapter;
    AppSharedPreference app_sharedpreference;
    String user_id;
    ProgressBarHandler progress_handler;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vendor);

        progress_handler = new ProgressBarHandler(this);

        app_sharedpreference = new AppSharedPreference(getApplicationContext());

        user_id = app_sharedpreference.getsharedpref("userid", "");


        setuptoolbar();

        setup_layout();

    }



    private void setup_layout() {
        vender_list = (RecyclerView) findViewById(R.id.vender_list);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());

        vender_list.setLayoutManager(mLayoutManager);

        get_company_list_data();
    }

    public void get_company_list_data()
    {

        progress_handler.show();
        venderListDatas.clear();
        Ion.with(VendorActivity.this)
                .load((getResources().getString(R.string.webservice_base_url))+"/list_vendor")
                .setHeader("authorization", "xvfdbgfdhbfdhtrh54654h54ygdgerwer3")
                .setBodyParameter("authorization", "xvfdbgfdhbfdhtrh54654h54ygdgerwer3")
                .setBodyParameter("user_id", app_sharedpreference.getsharedpref("userid", "0"))
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result)
                    {
                        if (result == null)
                        {

                            progress_handler.hide();
                        }
                        else
                        {
                            Log.e("data===============", result.toString());

                            JsonObject jsonObject = result.getAsJsonObject();

                            JsonArray jsonArray = jsonObject.getAsJsonArray("result");


                            for (int i = 0; i < jsonArray.size(); i++)
                            {
                                JsonObject jsonObject2 = (JsonObject) jsonArray.get(i);

                                System.out.println("jsonArray jsonObject2" + jsonObject2.toString());

                                String vender_id = jsonObject2.get("id").getAsString();

                                String vender_name = jsonObject2.get("name").getAsString();

                                String vender_last_name = jsonObject2.get("lastname").getAsString();

                                String business_type = jsonObject2.get("business_type").getAsString();

                                String mobile = jsonObject2.get("mobile").getAsString();

                                String email = jsonObject2.get("email").getAsString();

                                String creation_date = jsonObject2.get("created_at").getAsString();

                                venderListDatas.add(new VendorData(vender_id, vender_name,vender_last_name,business_type,mobile,email, creation_date));

                            }

                            vendorAdapter = new VendorAdapter(VendorActivity.this, venderListDatas, VendorActivity.this);

                            vender_list.setAdapter(vendorAdapter);

                            vendorAdapter.notifyDataSetChanged();

                            progress_handler.hide();
                            // progressView.setVisibility(View.INVISIBLE);

                        }

                    }

                });

    }



    private void setuptoolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(null);
        // getSupportActionBar().setIcon(R.drawable.home_logo);
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
