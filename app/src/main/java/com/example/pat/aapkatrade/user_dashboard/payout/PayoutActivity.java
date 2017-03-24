package com.example.pat.aapkatrade.user_dashboard.payout;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.pat.aapkatrade.Home.HomeActivity;
import com.example.pat.aapkatrade.R;
import com.example.pat.aapkatrade.general.AppSharedPreference;
import com.example.pat.aapkatrade.general.Utils.AndroidUtils;
import com.example.pat.aapkatrade.general.progressbar.ProgressBarHandler;
import com.example.pat.aapkatrade.user_dashboard.address.AddressData;
import com.example.pat.aapkatrade.user_dashboard.address.AddressListAdapter;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.util.ArrayList;
import java.util.Calendar;

public class PayoutActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener
{

    private Context context;
    private int isStartDate = -1;
    private EditText etStartDate, etEndDate;
    private ImageView openStartDateCal, openEndDateCal;
    private String date;
    ArrayList<PayoutData> payoutDatas = new ArrayList<>();
    RecyclerView payoutList;
    PayoutAdapter payoutAdapter;
RelativeLayout relativeAddress;
    private AppSharedPreference app_sharedpreference;
    ProgressBarHandler progressBarHandler;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payout);
        context = PayoutActivity.this;

        setUpToolBar();

        initView();



        setup_layout();


    }

    private void setup_layout()
    {

        payoutList = (RecyclerView) findViewById(R.id.payoutList);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());

        payoutAdapter = new PayoutAdapter(getApplicationContext(), payoutDatas);

        payoutList.setAdapter(payoutAdapter);

        payoutList.setLayoutManager(mLayoutManager);
        openStartDateCal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isStartDate = 0;
                openCalender();
            }
        });
        openEndDateCal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isStartDate = 1;
                openCalender();
            }
        });

        relativeAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callwebservicepayout();
            }
        });


    }

    private void callwebservicepayout() {

        if(etStartDate.getText().length()!=0) {
            if(etEndDate.getText().length()!=0) {

                progressBarHandler.show();

                String payout_url = getResources().getString(R.string.webservice_base_url )+ "/payout";
                Log.e("payout_url",etStartDate.getText().toString().trim()+"*****"+etEndDate.getText().toString().trim());
                Ion.with(context)
                        .load(payout_url)
                        .setHeader("authorization", "xvfdbgfdhbfdhtrh54654h54ygdgerwer3")
                        .setBodyParameter("authorization", "xvfdbgfdhbfdhtrh54654h54ygdgerwer3")
                        .setBodyParameter("user_id", app_sharedpreference.getsharedpref("userid", ""))
                        .setBodyParameter("from_date",etStartDate.getText().toString().trim() )
                        .setBodyParameter("to_date", etEndDate.getText().toString().trim())
                        .asJsonObject()
                        .setCallback(new FutureCallback<JsonObject>() {
                            @Override
                            public void onCompleted(Exception e, JsonObject result) {

                                String error=result.get("error").getAsString();

                                Log.e("error",error+"***"+result.toString());
                                    if (error.contains("false")) {
                                       String total_payout= result.get("total").getAsString();
                                        setup_data(total_payout);
                                        progressBarHandler.hide();

                                        //context.startActivity(new Intent(context, HomeActivity.class));
                                    }


                                else {
                                    showMessage("Webservice Responding Null");
                                }
                            }
                        });

            }
            else{
                progressBarHandler.hide();

                showMessage("! Requried End Date");
            }

        }
        else{
            progressBarHandler.hide();
            showMessage("! Requried Start Date");
        }


    }

    private void initView()
    {
        progressBarHandler=new ProgressBarHandler(context);
        etStartDate = (EditText) findViewById(R.id.etStartDate);
        etEndDate = (EditText) findViewById(R.id.etEndDate);
        openStartDateCal = (ImageView) findViewById(R.id.openStartDateCal);
        openEndDateCal = (ImageView) findViewById(R.id.openEndDateCal);
        relativeAddress=(RelativeLayout)findViewById(R.id.relativeAddress);
        app_sharedpreference = new AppSharedPreference(context);
    }

    private void setUpToolBar() {
        ImageView homeIcon = (ImageView) findViewById(R.id.iconHome) ;
        AppCompatImageView back_imagview = (AppCompatImageView) findViewById(R.id.back_imagview);
        back_imagview.setVisibility(View.VISIBLE);
        back_imagview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        findViewById(R.id.logoWord).setVisibility(View.GONE); ;
        TextView header_name = (TextView) findViewById(R.id.header_name);
        header_name.setVisibility(View.VISIBLE);
        header_name.setText(getResources().getString(R.string.payout_report_heading));
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
    public boolean onCreateOptionsMenu(Menu menu) {
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



    private void openCalender() {
        Calendar now = Calendar.getInstance();
        DatePickerDialog dpd = DatePickerDialog.newInstance(
                PayoutActivity.this,
                now.get(Calendar.YEAR),
                now.get(Calendar.MONTH),
                now.get(Calendar.DAY_OF_MONTH)
        );
        dpd.setMaxDate(now);
        if(isStartDate == 1){
            if(etStartDate.getText()!=null || etStartDate.getText().toString().length()>=8)
            dpd.setMinDate(AndroidUtils.stringToCalender(etStartDate.getText().toString()));
        }
        dpd.show(getFragmentManager(), "DatePickerDialog");
    }


    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        showDate(year, monthOfYear + 1, dayOfMonth);
    }

    private void showDate(int year, int month, int day) {
        date = (new StringBuilder()).append(year).append("-").append(month).append("-").append(day).toString();
        if(isStartDate == 0){
            etStartDate.setText(date);
        } else if(isStartDate == 1){
            etEndDate.setText(date);
        }
    }


    private void setup_data(String total_payout)
    {
        payoutDatas.clear();
        try
        {

            payoutDatas.add(new PayoutData(etStartDate.getText().toString().trim(), total_payout,etEndDate.getText().toString().trim()));
            payoutAdapter = new PayoutAdapter(getApplicationContext(), payoutDatas);

            payoutList.setAdapter(payoutAdapter);
            findViewById(R.id.ll_payout).setVisibility(View.VISIBLE);

        }
        catch (Exception e)
        {

        }
    }
    private void showMessage(String msg) {
        AndroidUtils.showSnackBar((LinearLayout) findViewById(R.id.ll_parent_payout), msg);
    }
}
