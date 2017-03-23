package com.example.pat.aapkatrade.user_dashboard.companylist;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
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
import com.example.pat.aapkatrade.user_dashboard.User_DashboardFragment;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import java.util.ArrayList;


public class CompanyList extends AppCompatActivity
{

    RecyclerView recyclerViewcompanylist;
    CompanyListAdapter companyListAdapter;
    ArrayList<CompanyData> companyDatas = new ArrayList<>();
    RelativeLayout relativeCompanylist;
    ProgressBarHandler progress_handler;
    private AppSharedPreference app_sharedpreference;
    String user_id;
    private final static String TAG_FRAGMENT = "TAG_FRAGMENT";
    TextView tvTitle;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_company_list);

        progress_handler = new ProgressBarHandler(this);

        app_sharedpreference = new AppSharedPreference(getApplicationContext());

        user_id = app_sharedpreference.getsharedpref("userid", "");

        setuptoolbar();

        setup_layout();


    }

    public void get_company_list_data()
    {
        relativeCompanylist.setVisibility(View.INVISIBLE);
        progress_handler.show();
        companyDatas.clear();
        Ion.with(CompanyList.this)
                .load("http://aapkatrade.com/slim/listCompany")
                .setHeader("authorization", "xvfdbgfdhbfdhtrh54654h54ygdgerwer3")
                .setBodyParameter("type", "company")
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

                            System.out.println("jsonArray11111111111111111" + jsonArray.toString());

                            for (int i = 0; i < jsonArray.size(); i++)
                            {
                                JsonObject jsonObject2 = (JsonObject) jsonArray.get(i);

                                System.out.println("jsonArray jsonObject2" + jsonObject2.toString());

                                String country_id = jsonObject2.get("companyId").getAsString();

                                String name = jsonObject2.get("name").getAsString();

                                String creation_date = jsonObject2.get("created").getAsString();

                                String sEmail = jsonObject2.get("secondaryEmail").getAsString();

                                String address = jsonObject2.get("address").getAsString();

                                String description = jsonObject2.get("description").getAsString();

                                System.out.println("ferhgjerk" + country_id + name + creation_date);

                                companyDatas.add(new CompanyData(country_id, name, creation_date,false,address,description,sEmail));


                            }
                            companyListAdapter = new CompanyListAdapter(CompanyList.this, companyDatas, CompanyList.this);

                            recyclerViewcompanylist.setAdapter(companyListAdapter);

                            companyListAdapter.notifyDataSetChanged();

                            progress_handler.hide();
                            // progressView.setVisibility(View.INVISIBLE);
                            relativeCompanylist.setVisibility(View.VISIBLE);

                        }

                    }

                });
    }

    private void setup_layout()
    {
        relativeCompanylist = (RelativeLayout) findViewById(R.id.relativeCompanylist);

        recyclerViewcompanylist = (RecyclerView) findViewById(R.id.companylist);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());

        recyclerViewcompanylist.setLayoutManager(mLayoutManager);
        app_sharedpreference = new AppSharedPreference(CompanyList.this);

        get_company_list_data();
    }

    private void setuptoolbar()
    {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if(getSupportActionBar()!=null) {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle(null);
        }

        tvTitle = (TextView) findViewById(R.id.tvTitle);
        tvTitle.setText("Company List");

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
