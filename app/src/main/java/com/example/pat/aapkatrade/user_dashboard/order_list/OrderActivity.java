package com.example.pat.aapkatrade.user_dashboard.order_list;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import com.example.pat.aapkatrade.R;
import com.example.pat.aapkatrade.general.AppSharedPreference;
import com.example.pat.aapkatrade.general.progressbar.ProgressBarHandler;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import java.util.ArrayList;

public class OrderActivity extends AppCompatActivity {

    ArrayList<OrderListData> orderListDatas = new ArrayList<>();
    RecyclerView order_list;
    OrderListAdapter orderListAdapter;
    ProgressBarHandler progress_handler;
    LinearLayout layout_container;
    AppSharedPreference app_sharedpreference;
    String user_id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_order);
        Log.e("hi////", "ghuygubgiugvuyuuihguogyuygukyvgbuk");

        setuptoolbar();

        progress_handler = new ProgressBarHandler(this);


        app_sharedpreference = new AppSharedPreference(this);

        user_id = app_sharedpreference.getsharedpref("userid", "");

        setup_layout();

        get_web_data();


    }


    private void setup_layout() {
        layout_container = (LinearLayout) findViewById(R.id.layout_container);

        order_list = (RecyclerView) findViewById(R.id.order_list);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        order_list.setLayoutManager(mLayoutManager);
    }

    private void setuptoolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(null);
        app_sharedpreference = new AppSharedPreference(OrderActivity.this);
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


    private void get_web_data() {
        orderListDatas.clear();
        progress_handler.show();




        Log.e("hi////", app_sharedpreference.getsharedpref("userid", user_id)+"GGGGGGG"+app_sharedpreference.getsharedpref("usertype","1"));

        Ion.with(OrderActivity.this)
                .load("http://aapkatrade.com/slim/seller_order_list")
                .setHeader("authorization", "xvfdbgfdhbfdhtrh54654h54ygdgerwer3")
                .setBodyParameter("authorization", "xvfdbgfdhbfdhtrh54654h54ygdgerwer3")


                .setBodyParameter("seller_id", app_sharedpreference.getsharedpref("userid", user_id))
                .setBodyParameter("type", app_sharedpreference.getsharedpref("usertype","1"))

                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {




                        System.out.println("jsonObject-------------" + result.toString());


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

                            } else {

                                JsonObject jsonObject1 = jsonObject.getAsJsonObject("result");

                                System.out.println("jsonOblect-------------" + jsonObject1.toString());

                                JsonArray jsonArray = jsonObject1.getAsJsonArray("list");

                                for (int i = 0; i < jsonArray.size(); i++)
                                {
                                    JsonObject jsonObject2 = (JsonObject) jsonArray.get(i);

                                    String order_id = jsonObject2.get("id").getAsString();

                                    String product_name = jsonObject2.get("product_name").getAsString();

                                    String product_price = jsonObject2.get("product_price").getAsString();

                                    String product_qty = jsonObject2.get("product_qty").getAsString();

                                    String address = jsonObject2.get("address").getAsString();

                                    String email = jsonObject2.get("email").getAsString();

                                    String buyersmobile = jsonObject2.get("buyersmobile").getAsString();

                                    String buyersname = jsonObject2.get("buyersname").getAsString();

                                    String company_name = jsonObject2.get("cname").getAsString();

                                    String status = jsonObject2.get("status").getAsString();

                                    String created_at = jsonObject2.get("created_at").getAsString();

                                    orderListDatas.add(new OrderListData(order_id, product_name, product_price, product_qty, address, email, buyersmobile, buyersname, company_name, status, created_at));


                                }

                                orderListAdapter = new OrderListAdapter(getApplicationContext(), orderListDatas);

                                order_list.setAdapter(orderListAdapter);

                                orderListAdapter.notifyDataSetChanged();

                                progress_handler.hide();


                            }

                            //   layout_container.setVisibility(View.VISIBLE);
                        }

                    }
                });

    }

}
