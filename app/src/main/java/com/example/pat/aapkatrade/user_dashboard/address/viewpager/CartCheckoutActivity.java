package com.example.pat.aapkatrade.user_dashboard.address.viewpager;

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
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.pat.aapkatrade.Home.banner_home.viewpageradapter_home;
import com.example.pat.aapkatrade.R;
import com.example.pat.aapkatrade.user_dashboard.my_profile.ProfilePreviewActivity;
import com.jaredrummler.materialspinner.MaterialSpinner;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import github.nisrulz.stackedhorizontalprogressbar.StackedHorizontalProgressBar;

public class CartCheckoutActivity extends AppCompatActivity
{

    LinearLayout viewpagerindicator;
    private ArrayList<String> imageIdList;
    int currentPage=0;
    ViewPager vp;
    viewpageradapter_home viewpageradapter;
    private int dotsCount;
    private ImageView[] dots;
    Timer banner_timer=new Timer();
    RelativeLayout relativeBuyNow;
    RadioButton addressRadioButton;
    RelativeLayout relativePayment;
    TextView tvTitle;
    LinearLayout linearLayoutEditDelete;



    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_cart_checkout);

        setuptoolbar();

        setup_layout();

        setupviewpager();

    }


    private void setup_layout()
    {
        linearLayoutEditDelete = (LinearLayout) findViewById(R.id.linearLayoutEditDelete);

        linearLayoutEditDelete.setVisibility(View.GONE);

        relativePayment = (RelativeLayout) findViewById(R.id.relativePayment);

        relativePayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        vp = (ViewPager) findViewById(R.id.cartCheckOutViewPager);

        viewpagerindicator = (LinearLayout) findViewById(R.id.viewpagerindicator);

        addressRadioButton = (RadioButton)  findViewById(R.id.addressRadioButton);

        addressRadioButton.setVisibility(View.INVISIBLE);


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
            public void onPageSelected(int position)
            {
                try
                {
                    for (int i = 0; i < dotsCount; i++)
                    {
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

    private void setUiPageViewController()
    {
        dotsCount = viewpageradapter.getCount();
        dots = new ImageView[dotsCount];

        for (int i = 0; i < dotsCount; i++)
        {
            dots[i] = new ImageView(getApplicationContext());
            dots[i].setImageDrawable(getResources().getDrawable(R.drawable.nonselected_item));

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            params.setMargins(4, 0, 4, 0);

            viewpagerindicator.addView(dots[i], params);
        }

        dots[0].setImageDrawable(getResources().getDrawable(R.drawable.selecteditem_dot));

    }


    private void setuptoolbar()
    {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(null);
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
