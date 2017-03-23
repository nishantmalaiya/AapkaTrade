package com.example.pat.aapkatrade.user_dashboard.network;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.pat.aapkatrade.R;
import com.example.pat.aapkatrade.general.AppSharedPreference;
import com.example.pat.aapkatrade.general.progressbar.ProgressBarHandler;
import com.example.pat.aapkatrade.user_dashboard.service_enquirylist.ServiceEnquiryActivity;
import com.example.pat.aapkatrade.user_dashboard.service_enquirylist.ServiceEnquiryAdapter;
import com.example.pat.aapkatrade.user_dashboard.service_enquirylist.ServiceEnquiryData;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import java.util.ArrayList;

public class NetworkActivity extends AppCompatActivity
{

    RecyclerView recyclerViewcompanylist;
    NetworkAdapter networkAdapter;
    ArrayList<NetworkData> networkDatas = new ArrayList<>();
    RelativeLayout relativeCompanylist;
    ProgressBarHandler progress_handler;
    AppSharedPreference app_sharedpreference;
    String reference_id;
    TextView tvTitle;



    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_network);

        progress_handler = new ProgressBarHandler(this);
        setuptoolbar();

        progress_handler = new ProgressBarHandler(this);

        app_sharedpreference = new AppSharedPreference(getApplicationContext());

        reference_id = app_sharedpreference.getsharedpref("ref_no");

        setup_layout();
    }



    private void setuptoolbar()
    {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("My Network");

        tvTitle = (TextView) findViewById(R.id.tvTitle);
        tvTitle.setText("My Network");


        //getSupportActionBar().setIcon(R.drawable.home_logo);
    }


    private void setup_layout()
    {
        relativeCompanylist = (RelativeLayout) findViewById(R.id.relativeCompanylist);

        recyclerViewcompanylist = (RecyclerView) findViewById(R.id.companylist);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());

        recyclerViewcompanylist.setLayoutManager(mLayoutManager);

        get_company_list_data();

        networkAdapter = new NetworkAdapter(NetworkActivity.this, networkDatas, NetworkActivity.this);

        recyclerViewcompanylist.setAdapter(networkAdapter);

        app_sharedpreference = new AppSharedPreference(NetworkActivity.this);
    }



    public void get_company_list_data()
    {

        relativeCompanylist.setVisibility(View.INVISIBLE);
        progress_handler.show();
        networkDatas.clear();
        Ion.with(NetworkActivity.this)
                .load("https://aapkatrade.com/slim/network_list")
                .setHeader("authorization", "xvfdbgfdhbfdhtrh54654h54ygdgerwer3")
                .setBodyParameter("ref_no","aapkatrade11")
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

                                String user_name = jsonObject2.get("name").getAsString();

                                String user_last_name = jsonObject2.get("lastname").getAsString();

                                String user_email= jsonObject2.get("email").getAsString();

                                String user_mobile = jsonObject2.get("mobile").getAsString();

                                String created_date = jsonObject2.get("created_at").getAsString();

                                networkDatas.add(new NetworkData(user_name,user_last_name,user_email,user_mobile,created_date));
                            }

                            networkAdapter.notifyDataSetChanged();

                            progress_handler.hide();
                            // progressView.setVisibility(View.INVISIBLE);
                            relativeCompanylist.setVisibility(View.VISIBLE);
                        }

                    }

                });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.menu_map, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case android.R.id.home:
                finish();
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return super.onOptionsItemSelected(item);
    }

}
