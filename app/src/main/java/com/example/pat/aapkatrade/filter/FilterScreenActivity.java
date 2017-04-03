package com.example.pat.aapkatrade.filter;
import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.example.pat.aapkatrade.R;
import com.example.pat.aapkatrade.general.AppSharedPreference;
import com.example.pat.aapkatrade.user_dashboard.companylist.CompanyList;
import com.example.pat.aapkatrade.user_dashboard.companylist.CompanyListAdapter;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import java.util.ArrayList;
import java.util.HashMap;


public class FilterScreenActivity extends AppCompatActivity
{


    RecyclerView recyclerViewcompanylist;
    CompanyListAdapter companyListAdapter;
   // ArrayList<CompanyData> companyDatas = new ArrayList<>();
    RelativeLayout relativeCompanylist;
    private AppSharedPreference app_sharedpreference;
    String user_id;
    public final static String TAG_FRAGMENT = "TAG_FRAGMENT";
    TextView tvTitle;
    private Context context;
    public LinearLayoutManager mLayoutManager;
    SwipeRefreshLayout mSwipyRefreshLayout;
    String[] animalName={"Lion","Tiger","Monkey","Dog","Cat","Elephant"};




    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_filter_screen);

        context = FilterScreenActivity.this;

        app_sharedpreference = new AppSharedPreference(getApplicationContext());
        user_id = app_sharedpreference.getsharedpref("userid", "");


        LinearLayout root = (LinearLayout) findViewById(R.id.my_root);

        root.setBackgroundColor(getResources().getColor(R.color.red));

        RecyclerView llay1 = new RecyclerView(FilterScreenActivity.this);



    }

   /* public void get_company_list_data()
    {

        companyDatas.clear();
        Ion.with(FilterScreenActivity.this)
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

                            //mSwipyRefreshLayout.setRefreshing(false);
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


                        }

                    }

                });
    }

*/



}
