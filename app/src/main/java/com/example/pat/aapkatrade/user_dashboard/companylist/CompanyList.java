package com.example.pat.aapkatrade.user_dashboard.companylist;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.example.pat.aapkatrade.Home.HomeActivity;
import com.example.pat.aapkatrade.R;
import com.example.pat.aapkatrade.general.AppSharedPreference;
import com.example.pat.aapkatrade.general.Utils.AndroidUtils;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import java.util.ArrayList;




public class CompanyList extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener
{

    RecyclerView recyclerViewcompanylist;
    CompanyListAdapter companyListAdapter;
    ArrayList<CompanyData> companyDatas = new ArrayList<>();
    RelativeLayout relativeCompanylist;
    private AppSharedPreference app_sharedpreference;
    String user_id;
    public final static String TAG_FRAGMENT = "TAG_FRAGMENT";
    TextView tvTitle;
    private Context context;
    public LinearLayoutManager mLayoutManager;
    SwipeRefreshLayout mSwipyRefreshLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_company_list);

        context = CompanyList.this;

        app_sharedpreference = new AppSharedPreference(getApplicationContext());

        user_id = app_sharedpreference.getsharedpref("userid", "");

        setUpToolBar();

        setup_layout();


    }

    public void get_company_list_data()
    {
        relativeCompanylist.setVisibility(View.INVISIBLE);
        mSwipyRefreshLayout.setRefreshing(true);
        companyDatas.clear();
        Ion.with(CompanyList.this)
                .load(getResources().getString(R.string.webservice_base_url)+"/listCompany")
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

                            mSwipyRefreshLayout.setRefreshing(false);
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

                            mSwipyRefreshLayout.setRefreshing(false);
                            // progressView.setVisibility(View.INVISIBLE);
                            relativeCompanylist.setVisibility(View.VISIBLE);

                        }

                    }

                });
    }

    private void setup_layout()
    {

        mSwipyRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe);

        mSwipyRefreshLayout.setRefreshing(false);

        mSwipyRefreshLayout.setOnRefreshListener(CompanyList.this);


        relativeCompanylist = (RelativeLayout) findViewById(R.id.relativeCompanylist);

        recyclerViewcompanylist = (RecyclerView) findViewById(R.id.companylist);

         mLayoutManager = new LinearLayoutManager(getApplicationContext());


        recyclerViewcompanylist.setLayoutManager(mLayoutManager);
        app_sharedpreference = new AppSharedPreference(CompanyList.this);

        get_company_list_data();


      /*  recyclerViewcompanylist.setOnScrollListener(new RecyclerView.OnScrollListener()
        {

            public void onScrollStateChanged(RecyclerView view, int scrollState)
            {
                super.onScrollStateChanged(recyclerViewcompanylist, scrollState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy)
            {
                super.onScrolled(recyclerView, dx, dy);

                int totalItemCount = mLayoutManager.getItemCount();

                int firstVisibleItem = mLayoutManager.findFirstVisibleItemPosition();

                int lastVisibleItemCount = mLayoutManager.findLastVisibleItemPosition();

                if (totalItemCount > 0)
                {
                    if ((totalItemCount - 1) == lastVisibleItemCount)
                    {
                       *//* page = page + 1;
                        get_web_data2(page);*//*
                        get_company_list_more_data();

                    }
                    else
                    {
                        //loadingProgress.setVisibility(View.GONE);
                    }
                }
            }

        });
      */

    }

    private void setUpToolBar()
    {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        AppCompatImageView back_imagview = (AppCompatImageView) findViewById(R.id.back_imagview);
        back_imagview.setVisibility(View.VISIBLE);
        back_imagview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        ImageView homeIcon = (ImageView) findViewById(R.id.iconHome);
        ImageView logoWord = (ImageView) findViewById(R.id.logoWord);
        TextView header_name = (TextView) findViewById(R.id.header_name);
        header_name.setVisibility(View.VISIBLE);
        header_name.setText(getResources().getString(R.string.compant_list_heading));
        logoWord.setVisibility(View.GONE);
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


        if (getSupportActionBar() != null)
        {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
            getSupportActionBar().setTitle(null);
            getSupportActionBar().setElevation(0);
        }




    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.bottom_home_menu, menu);
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


    @Override
    public void onRefresh()
    {
        get_company_list_data();
    }

  public void  get_company_list_more_data()
  {
      Ion.with(CompanyList.this)
              .load(getResources().getString(R.string.webservice_base_url)+"/listCompany")
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
                          //progress_handler.hide();
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


                         /*  companyListAdapter = new CompanyListAdapter(CompanyList.this, companyDatas, CompanyList.this);
                          recyclerViewcompanylist.setAdapter(companyListAdapter);
                        */

                          companyListAdapter.notifyDataSetChanged();

                          //progress_handler.hide();
                          // progressView.setVisibility(View.INVISIBLE);
                          //relativeCompanylist.setVisibility(View.VISIBLE);

                      }

                  }

              });

  }




}
