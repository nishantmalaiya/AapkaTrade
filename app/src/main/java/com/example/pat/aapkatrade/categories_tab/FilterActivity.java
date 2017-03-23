package com.example.pat.aapkatrade.categories_tab;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;

import com.example.pat.aapkatrade.R;

public class FilterActivity extends AppCompatActivity
{


    RecyclerView cityList;
    RelativeLayout relativeLayout1,relativeLayout2,relativeDistanceLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_filter);

        setuptoolbar();

        setup_layout();

    }

    private void setup_layout()
    {

        relativeLayout1 = (RelativeLayout) findViewById(R.id.relativeLayout1);

        relativeLayout2 = (RelativeLayout) findViewById(R.id.relativeLayout2);

        relativeDistanceLayout = (RelativeLayout) findViewById(R.id.relativeDistanceLayout);

        cityList = (RecyclerView) findViewById(R.id.cityList);

        relativeLayout2.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

                relativeDistanceLayout.setVisibility(View.VISIBLE);
                cityList.setVisibility(View.INVISIBLE);

            }
        });

        relativeLayout1.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

                relativeDistanceLayout.setVisibility(View.INVISIBLE);
                cityList.setVisibility(View.VISIBLE);
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

        getSupportActionBar().setIcon(R.drawable.home_logo);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.user, menu);
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
