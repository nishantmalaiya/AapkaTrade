package com.example.pat.aapkatrade.rateus;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.pat.aapkatrade.Home.HomeActivity;
import com.example.pat.aapkatrade.R;
import com.example.pat.aapkatrade.general.Utils.AndroidUtils;
import com.hedgehog.ratingbar.RatingBar;


public class RateusActivity extends AppCompatActivity
{

    TextView tvProductName,tvCategoriesName,tvReviewHeading;
    RatingBar ratingbar;
    Button butttonExperience,buttonSubmit;
    EditText edtWriteReview;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_rateus);

        setUpToolBar();

        setup_layout();

    }

    private void setup_layout()
    {
        tvProductName = (TextView) findViewById(R.id.tvProductName);

        tvCategoriesName = (TextView) findViewById(R.id.tvCategoriesName);

        ratingbar = (RatingBar) findViewById(R.id.ratingbar);

        butttonExperience= (Button) findViewById(R.id.butttonExperience);

        tvReviewHeading = (TextView) findViewById(R.id.tvReviewHeading);

        edtWriteReview = (EditText) findViewById(R.id.edtWriteReview);

        butttonExperience = (Button) findViewById(R.id.butttonExperience);

        butttonExperience.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

                tvProductName.setVisibility(View.GONE);
                tvCategoriesName.setVisibility(View.GONE);
                ratingbar.setVisibility(View.GONE);


            }
        });

        buttonSubmit = (Button) findViewById(R.id.buttonSubmit);



        buttonSubmit.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {



            }
        });

    }

    private void setUpToolBar()
    {
        ImageView homeIcon = (ImageView) findViewById(R.id.iconHome);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        AndroidUtils.setImageColor(homeIcon, RateusActivity.this, R.color.white);
        homeIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RateusActivity.this, HomeActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle(null);
            getSupportActionBar().setElevation(0);
        }
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
