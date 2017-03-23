package com.example.pat.aapkatrade.user_dashboard.product_list.listproduct_detail;
import android.content.Intent;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.pat.aapkatrade.Home.banner_home.viewpageradapter_home;
import com.example.pat.aapkatrade.R;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;


public class ListProductDetailActivity extends AppCompatActivity
{

    LinearLayout viewpagerindicator;
    private ArrayList<String> imageIdList;
    int currentPage=0;
    ViewPager vp;
    viewpageradapter_home viewpageradapter;
    int dotsCount;
    ImageView[] dots;
    Timer banner_timer=new Timer();
    String product_name, price,cross_price, product_image,category_name,description,delivery_distance,delivery_area_name;
    TextView tvProductName_d,tvCategoryName_d,tvProPrice_d,tvDeliveryDays_d,tvDeliveryArea_d,tvDiscription_d,tvSpecificationHeading,tvSpecification;
    RelativeLayout relativeSpecification;



    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_list_product_detail);

        Bundle p = getIntent().getExtras();

        product_name = p.getString("product_name","");

        price = p.getString("product_price","");

        cross_price = p.getString("product_cross_price","");

        product_image = p.getString("product_image","");

        category_name = p.getString("category_name","");

        description = p.getString("description","");

        delivery_distance = p.getString("delivery_distance","");

        delivery_area_name = p.getString("delivery_area_name","");

        System.out.println("p--------------"+product_name+price+cross_price+product_image+category_name+description+delivery_distance+delivery_distance);

        setuptoolbar();

        setup_layout();

        setupviewpager();


    }


    private void setUiPageViewController()
    {

        dotsCount = viewpageradapter.getCount();
        dots = new ImageView[dotsCount];

        for (int i = 0; i < dotsCount; i++) {
            dots[i] = new ImageView(getApplicationContext());
            dots[i].setImageDrawable(getResources().getDrawable(R.drawable.nonselected_item));

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );

            params.setMargins(4, 0, 4, 0);

            viewpagerindicator.addView(dots[i], params);
        }

        dots[0].setImageDrawable(getResources().getDrawable(R.drawable.selecteditem_dot));


    }


    private void setupviewpager()
    {

        imageIdList = new ArrayList<>();
        imageIdList.add("R.drawable.banner_home");
        imageIdList.add("R.drawable.banner_home");
        imageIdList.add("R.drawable.banner_home");
        imageIdList.add("R.drawable.banner_home");


        viewpageradapter  = new viewpageradapter_home(getApplicationContext(), imageIdList);
        vp.setAdapter(viewpageradapter);
        vp.setCurrentItem(currentPage);
        setUiPageViewController();

        final Handler handler = new Handler();

        final Runnable update = new Runnable() {
            public void run() {
                if (currentPage == viewpageradapter.getCount() - 1) {
                    currentPage = 0;
                }
                vp.setCurrentItem(currentPage++, true);
            }
        };


        banner_timer.schedule(new TimerTask() {

            @Override
            public void run() {
                handler.post(update);
            }
        }, 0, 3000);



        vp.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                try {
                    for (int i = 0; i < dotsCount; i++) {
                        dots[i].setImageDrawable(getResources().getDrawable(R.drawable.nonselected_item));
                    }

                    dots[position].setImageDrawable(getResources().getDrawable(R.drawable.selecteditem_dot));
                }
                catch (Exception e)
                {
                }
            }


            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }



    private void setup_layout()
    {

        vp = (ViewPager)  findViewById(R.id.viewpager_custom) ;

        viewpagerindicator=(LinearLayout)findViewById(R.id.viewpagerindicator);

        tvProductName_d = (TextView) findViewById(R.id.tvProductName);

        tvProductName_d.setText(product_name);

        tvCategoryName_d = (TextView) findViewById(R.id.tvCategoryName);
        tvCategoryName_d.setText(category_name);

        tvProPrice_d= (TextView) findViewById(R.id.tvProPrice);
        tvProPrice_d.setText("\u20B9"+price);

        tvDeliveryDays_d= (TextView) findViewById(R.id.tvDeliveryDays);
        tvDeliveryDays_d.setText(delivery_distance);

        tvDeliveryArea_d = (TextView) findViewById(R.id.tvDeliveryArea);
        tvDeliveryArea_d.setText(delivery_area_name);

        tvDiscription_d = (TextView) findViewById(R.id.tvDiscription);
        tvDiscription_d.setText(description);

        tvSpecificationHeading = (TextView) findViewById(R.id.tvSpecificationHeading);
        tvSpecificationHeading.setVisibility(View.INVISIBLE);

        tvSpecification = (TextView) findViewById(R.id.tvSpecification);
        tvSpecification.setVisibility(View.INVISIBLE);

        relativeSpecification = (RelativeLayout) findViewById(R.id.relativeSpecification);
        relativeSpecification.setVisibility(View.GONE);




    }



   /* private void initView()
    {
        String[] ITEMS = {"1", "2", "3", "4", "5", "6"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, ITEMS);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner = (MaterialSpinner) findViewById(R.id.spinner_qty);
        spinner.setAdapter(adapter);
        buyProductButton = (TextView) findViewById(R.id.buyProductButton);
    }*/




    private void setuptoolbar()
    {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayShowTitleEnabled(false);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        getSupportActionBar().setTitle(null);

        // getSupportActionBar().setIcon(R.drawable.home_logo);


    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.empty_menu, menu);
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





