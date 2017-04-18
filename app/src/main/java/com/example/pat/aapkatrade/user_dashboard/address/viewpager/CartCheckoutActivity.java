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

    RelativeLayout relativePayment;
    String fname,lname,mobile,state_id,address;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_cart_checkout);

        setuptoolbar();

        Bundle bundle = getIntent().getExtras();

        fname = bundle.getString("fname");

        lname = bundle.getString("lname");

        mobile= bundle.getString("mobile");

        state_id = bundle.getString("state_id");

        address = bundle.getString("address");

        setup_layout();




    }


    private void setup_layout()
    {

        relativePayment = (RelativeLayout) findViewById(R.id.relativePayment);

        relativePayment.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

            }
        });



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
