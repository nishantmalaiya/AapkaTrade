package com.example.pat.aapkatrade.user_dashboard.product_list;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.pat.aapkatrade.Home.HomeActivity;
import com.example.pat.aapkatrade.R;
import com.example.pat.aapkatrade.general.AppSharedPreference;
import com.example.pat.aapkatrade.general.Utils.AndroidUtils;
import com.example.pat.aapkatrade.user_dashboard.add_product.ProductImagesData;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import java.util.ArrayList;

public class ProductListActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener
{

    private RecyclerView product_list;
    private ProductListAdapter productListAdapter;
    private AppSharedPreference app_sharedpreference;
    private String user_id;
    private LinearLayout layout_container;
    private ArrayList<ProductListData> productListDatas = new ArrayList<>();
    private LinearLayoutManager mLayoutManager, linearLayoutManager;
    private boolean isLoading = false;
    private int mPageSize = 6;
    private SwipeRefreshLayout mSwipyRefreshLayout;
    private int page = 1;
    private Context context;
    ArrayList<String> product_image_list = new ArrayList<>();




    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_list_product);

        context = ProductListActivity.this;

        app_sharedpreference = new AppSharedPreference(this);

        user_id = app_sharedpreference.getsharedpref("userid", "");

        setUpToolBar();

        setup_layout();

        get_web_data();



    }

    private void setup_layout()
    {
        mSwipyRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe);

        mSwipyRefreshLayout.setRefreshing(false);

        mSwipyRefreshLayout.setOnRefreshListener(this);

        product_list = (RecyclerView) findViewById(R.id.product_list_recycler_view);

        layout_container = (LinearLayout) findViewById(R.id.layout_container);

        //  get_web_data2(0);

        linearLayoutManager = new LinearLayoutManager(getApplicationContext());

        mLayoutManager = new LinearLayoutManager(getApplicationContext());

        product_list.setLayoutManager(mLayoutManager);

        productListAdapter = new ProductListAdapter(ProductListActivity.this, productListDatas);

        product_list.setAdapter(productListAdapter);


        product_list.setOnScrollListener(new RecyclerView.OnScrollListener()
        {

            public void onScrollStateChanged(RecyclerView view, int scrollState)
            {
                super.onScrollStateChanged(product_list, scrollState);
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
                        page = page + 1;
                        get_web_data2(page);
                    }
                    else
                    {
                        //loadingProgress.setVisibility(View.GONE);
                    }
                }
            }

        });


      /*  product_list.setOnScrollListener(new RecyclerView.OnScrollListener() {

            public void onScrollStateChanged(RecyclerView view, int scrollState) {
                super.onScrollStateChanged(product_list, scrollState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                int totalItemCount = mLayoutManager.getItemCount();

                int lastVisibleItemCount = mLayoutManager.findLastVisibleItemPosition();

                System.out.println("totalItemCount-----------" + totalItemCount + "lastVisibleItemCount----------" + lastVisibleItemCount);

                if (totalItemCount > 0) {

                    if ((totalItemCount - 1) == lastVisibleItemCount) {
                        page = page + 1;
                        get_web_data2(page);
                    } else {
                        //loadingProgress.setVisibility(View.GONE);
                    }

                }

            }

        });*/
    }


    private int returnPageIndex(int sizeOfList)
    {
        int index = sizeOfList / mPageSize;
        return index;
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
        header_name.setText(getResources().getString(R.string.product_list_heading));
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

    public void get_web_data2(int page_number)
    {
        Ion.with(ProductListActivity.this)
                .load(getResources().getString(R.string.webservice_base_url)+"/productlist")
                .setHeader("authorization", "xvfdbgfdhbfdhtrh54654h54ygdgerwer3")
                .setBodyParameter("type", "product_list")
                .setBodyParameter("authorization", "xvfdbgfdhbfdhtrh54654h54ygdgerwer3")
                .setBodyParameter("seller_id", user_id)
                .setBodyParameter("page", String.valueOf(page_number))
                .setBodyParameter("apply", "1")
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override

                    public void onCompleted(Exception e, JsonObject result) {
                        System.out.println("userid----------------" + user_id);

                        // System.out.println("jsonObject---------123----"+user_id + result.toString().substring(0, 3574));

                        // System.out.println("jsonObject---------123----" + result.toString().substring(3574, result.toString().length()-1));

                        if (result == null)
                        {
                            // progress_handler.hide();
                            // layout_container.setVisibility(View.INVISIBLE);
                            //  mSwipyRefreshLayout.setRefreshing(false);
                        }
                        else
                        {

                            JsonObject jsonObject = result.getAsJsonObject();

                            String message = jsonObject.get("message").toString().substring(0, jsonObject.get("message").toString().length());

                            String message_data = message.replace("\"", "");

                            System.out.println("message_data==================" + message_data);

                            if (message_data.equals("No record found"))
                            {
                                //progress_handler.hide();

                                // layout_container.setVisibility(View.INVISIBLE);
                            }
                            else
                            {

                                JsonArray jsonArray = jsonObject.getAsJsonArray("result");

                                for (int i = 0; i < jsonArray.size(); i++)
                                {

                                    JsonObject jsonObject2 = (JsonObject) jsonArray.get(i);

                                    String product_id = jsonObject2.get("id").getAsString();

                                    String product_name = jsonObject2.get("name").getAsString();

                                    String product_price = jsonObject2.get("price").getAsString();

                                    String product_cross_price = jsonObject2.get("cross_price").getAsString();

                                    String product_image = jsonObject2.get("image_url").getAsString();

                                    String category_name = jsonObject2.get("category_name").getAsString();

                                    String state = jsonObject2.get("state_name").getAsString();

                                    String description = jsonObject2.get("short_des").getAsString();

                                    String delivery_distance = jsonObject2.get("deliverday").getAsString();

                                    String delivery_area_name = jsonObject2.get("deliveryArea").getAsString();

                                    String company_id = jsonObject2.get("company_id").getAsString();

                                    String distanec_id = jsonObject2.get("distance_id").getAsString();

                                    String country_id= jsonObject2.get("country_id").getAsString();

                                    String state_id = jsonObject2.get("state_id").getAsString();

                                    String city_id = jsonObject2.get("city_id").getAsString();

                                    String category_id =jsonObject2.get("category_id").getAsString();

                                    String sub_category_id = jsonObject2.get("sub_cat_id").getAsString();

                                    String unit_id = jsonObject2.get("unit_id").getAsString();

                                    JsonArray product_array = jsonObject2.getAsJsonArray("product_images");

                                   /* product_image_list.clear();

                                    for (int j=0; j<product_array.size(); j++)
                                    {
                                        JsonObject json_image = (JsonObject) product_array.get(j);
                                        String product_images = json_image.get("image_url").getAsString();
                                        product_image_list.add(product_images);
                                    }
*/
                                    productListDatas.add(new ProductListData(app_sharedpreference.getsharedpref("userid"), product_id, product_name, product_price, product_cross_price, product_image, category_name, state, description, delivery_distance, delivery_area_name,company_id,distanec_id,country_id,state_id,city_id,category_id,sub_category_id,unit_id,product_image_list));


                                }

                                productListAdapter.notifyDataSetChanged();

                                //  mSwipyRefreshLayout.setRefreshing(false);
                                // progress_handler.hide();
                            }

                            //   layout_container.setVisibility(View.VISIBLE);
                        }

                    }
                });


    }

    private void get_web_data()
    {
        // layout_container.setVisibility(View.INVISIBLE);
        mSwipyRefreshLayout.setRefreshing(true);

        productListDatas.clear();

        Ion.with(ProductListActivity.this)
                .load(getResources().getString(R.string.webservice_base_url)+"/productlist")
                .setHeader("authorization", "xvfdbgfdhbfdhtrh54654h54ygdgerwer3")
                .setBodyParameter("type", "product_list")
                .setBodyParameter("authorization", "xvfdbgfdhbfdhtrh54654h54ygdgerwer3")
                .setBodyParameter("seller_id", user_id)
                .setBodyParameter("page", "0")
                .setBodyParameter("apply", "1")
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>()
                {

                    @Override
                    public void onCompleted(Exception e, JsonObject result)
                    {

                        System.out.println("result----------------"+result.toString());

                        if (result == null) {
                            //progress_handler.hide();
                            layout_container.setVisibility(View.INVISIBLE);
                            mSwipyRefreshLayout.setRefreshing(false);
                        }
                        else
                        {
                            System.out.println("message_data==================" + result);
                            JsonObject jsonObject = result.getAsJsonObject();

                            String message = jsonObject.get("message").toString().substring(0, jsonObject.get("message").toString().length());

                            String message_data = message.replace("\"", "");

                            System.out.println("message_data==================" + message_data);

                            if (message_data.equals("No record found"))
                            {

                                layout_container.setVisibility(View.INVISIBLE);
                                mSwipyRefreshLayout.setRefreshing(false);

                            }
                            else
                            {

                                JsonArray jsonArray = jsonObject.getAsJsonArray("result");

                                System.out.println("jsonArray----------------" + jsonArray.toString());

                                for (int i = 0; i < jsonArray.size(); i++)
                                {
                                    JsonObject jsonObject2 = (JsonObject) jsonArray.get(i);

                                    String product_id = jsonObject2.get("id").getAsString();

                                    String product_name = jsonObject2.get("name").getAsString();

                                    String product_price = jsonObject2.get("price").getAsString();

                                    String product_cross_price = jsonObject2.get("cross_price").getAsString();

                                    String product_image = jsonObject2.get("image_url").getAsString();

                                    String category_name = jsonObject2.get("category_name").getAsString();

                                    String state = jsonObject2.get("state_name").getAsString();

                                    String description = jsonObject2.get("short_des").getAsString();

                                    String delivery_distance = jsonObject2.get("deliverday").getAsString();

                                    String delivery_area_name = jsonObject2.get("deliveryArea").getAsString();

                                    String company_id = jsonObject2.get("company_id").getAsString();

                                    String distanec_id = jsonObject2.get("distance_id").getAsString();

                                    String country_id= jsonObject2.get("country_id").getAsString();

                                    String state_id = jsonObject2.get("state_id").getAsString();

                                    String city_id = jsonObject2.get("city_id").getAsString();

                                    String category_id =jsonObject2.get("category_id").getAsString();

                                    String sub_category_id = jsonObject2.get("sub_cat_id").getAsString();

                                    String unit_id = jsonObject2.get("unit_id").getAsString();

                                   /* JsonArray product_array = jsonObject2.getAsJsonArray("product_images");

                                    product_image_list.clear();
                                    for (int j=0; j<product_array.size(); j++)
                                    {
                                        JsonObject json_image = (JsonObject) product_array.get(j);
                                        String product_images = json_image.get("image_url").getAsString();

                                        product_image_list.add(product_images);
                                    }
*/
                                    System.out.println("product_array------------"+product_image_list.toString());

                                    productListDatas.add(new ProductListData(app_sharedpreference.getsharedpref("userid"), product_id, product_name, product_price, product_cross_price, product_image, category_name, state, description, delivery_distance, delivery_area_name,company_id,distanec_id,country_id,state_id,city_id,category_id,sub_category_id,unit_id,product_image_list));


                                }
                                productListAdapter.notifyDataSetChanged();

                                mSwipyRefreshLayout.setRefreshing(false);
                            }
                        }
                    }
                });
    }

    @Override
    public void onRefresh()
    {
        get_web_data();
    }


    @Override
    protected void onSaveInstanceState(Bundle outState)
    {
        super.onSaveInstanceState(outState);
        // Only if you need to restore open/close state when
        // the orientation is changed
        if (productListAdapter != null)
        {
            productListAdapter.saveStates(outState);
        }
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState)
    {
        super.onRestoreInstanceState(savedInstanceState);
        // Only if you need to restore open/close state when
        // the orientation is changed
        if (productListAdapter != null)
        {
            productListAdapter.restoreStates(savedInstanceState);
        }

    }


}
