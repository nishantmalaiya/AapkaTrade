package com.example.pat.aapkatrade.rateus;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.pat.aapkatrade.Home.HomeActivity;
import com.example.pat.aapkatrade.R;
import com.example.pat.aapkatrade.general.AppSharedPreference;
import com.example.pat.aapkatrade.general.ConnectivityNotFound;
import com.example.pat.aapkatrade.general.ConnetivityCheck;
import com.example.pat.aapkatrade.general.Utils.AndroidUtils;
import com.example.pat.aapkatrade.general.progressbar.ProgressBarHandler;
import com.example.pat.aapkatrade.user_dashboard.changepassword.ChangePassword;
import com.google.gson.JsonObject;
import com.hedgehog.ratingbar.RatingBar;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;


public class RateusActivity extends AppCompatActivity
{

    TextView tvProductName,tvCategoriesName,tvReviewHeading;
    RatingBar ratingbar;
    Button butttonExperience,buttonSubmit;
    EditText edtWriteTitleReview,edtWriteMessage;
    private Context context;
    AppSharedPreference app_sharedpreference;
    String user_id,product_id;
    ProgressBarHandler progress_handler;
    CoordinatorLayout coordinationRateus;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_rateus);


        Intent intent = getIntent();

        Bundle b = intent.getExtras();

        product_id = b.getString("product_id");

        System.out.println("product_id------------"+product_id.toString());


        progress_handler = new ProgressBarHandler(this);

        app_sharedpreference = new AppSharedPreference(getApplicationContext());

        user_id = app_sharedpreference.getsharedpref("userid", "");

        context = RateusActivity.this;

        setUpToolBar();

        setup_layout();

    }

    private void setup_layout()
    {

        coordinationRateus = (CoordinatorLayout) findViewById(R.id.coordinationRateus);

        tvProductName = (TextView) findViewById(R.id.tvProductName);

        tvCategoriesName = (TextView) findViewById(R.id.tvCategoriesName);

        ratingbar = (RatingBar) findViewById(R.id.ratingbar);

        butttonExperience= (Button) findViewById(R.id.butttonExperience);

        tvReviewHeading = (TextView) findViewById(R.id.tvReviewHeading);

        edtWriteTitleReview = (EditText) findViewById(R.id.edtWriteReview);

        edtWriteMessage = (EditText) findViewById(R.id.edtWriteMessage);

        butttonExperience = (Button) findViewById(R.id.butttonExperience);

        buttonSubmit = (Button) findViewById(R.id.buttonSubmit);

        buttonSubmit.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

                if (edtWriteTitleReview.getText().toString().length()>10)
                {
                    if (edtWriteMessage.getText().toString().length()>10)
                    {
                        callChangePasswordWebService();
                    }
                    else
                    {
                        AndroidUtils.showSnackBar(coordinationRateus,"Please Enter Minimum 10 String length Message");
                    }
                }
                else
                {
                    AndroidUtils.showSnackBar(coordinationRateus,"Please Enter Minimum 10 String length Title");
                }

            }
        });


    }


    private void setUpToolBar()
    {
        ImageView homeIcon = (ImageView) findViewById(R.id.iconHome) ;
        AppCompatImageView back_imagview = (AppCompatImageView) findViewById(R.id.back_imagview);
        back_imagview.setVisibility(View.VISIBLE);
        back_imagview.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                finish();
            }
        });
        findViewById(R.id.logoWord).setVisibility(View.GONE); ;
        TextView header_name = (TextView) findViewById(R.id.header_name);
        header_name.setVisibility(View.VISIBLE);
        header_name.setText(getResources().getString(R.string.write_and_review));
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
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
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
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



    private void callChangePasswordWebService()
    {

        progress_handler.show();

        if (ConnetivityCheck.isNetworkAvailable(RateusActivity.this))
        {

            Ion.with(RateusActivity.this)
                    .load(getResources().getString(R.string.webservice_base_url)+"/write_review")
                    .setHeader("authorization", "xvfdbgfdhbfdhtrh54654h54ygdgerwer3")
                    .setBodyParameter("authorization", "xvfdbgfdhbfdhtrh54654h54ygdgerwer3")
                    .setBodyParameter("user_id", user_id)
                    .setBodyParameter("message", edtWriteMessage.getText().toString())
                    .setBodyParameter("product_id", product_id)
                    .setBodyParameter("rating","4")
                    .setBodyParameter("title", edtWriteTitleReview.getText().toString())
                    .asJsonObject()
                    .setCallback(new FutureCallback<JsonObject>()
                    {
                        @Override
                        public void onCompleted(Exception e, JsonObject result)
                        {

                            System.out.println("result______________"+result);

                            if (result == null)
                            {
                                Log.e("change_password_error",e.toString());
                                progress_handler.hide();
                            }

                            else
                            {
                                JsonObject jsonObject = result.getAsJsonObject();
                                String message = jsonObject.get("message").getAsString();
                                Log.e("data_change_password", result.toString());

                                if (message.toString().equals("Your reviews already submitted!"))
                                {
                                    progress_handler.hide();
                                    //showMessage(message);

                                }
                                else
                                {
                                    progress_handler.hide();
                                    //showMessage(message);

                                }

                            }
                        }

                    });
        }
        else
        {
            Intent intent = new Intent(RateusActivity.this, ConnectivityNotFound.class);
            intent.putExtra("callerActivity", ChangePassword.class.getName());
            startActivity(intent);
        }


    }


}
