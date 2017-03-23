package com.example.pat.aapkatrade.service_enquiry;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.pat.aapkatrade.R;
import com.example.pat.aapkatrade.general.Utils.AndroidUtils;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.util.Calendar;

public class ServiceEnquiry extends Dialog implements DatePickerDialog.OnDateSetListener
{


    private EditText firstName, quantity, price, mobile, email, etEndDate, etStartDate, description;
    private TextInputLayout input_layout_start_date, input_layout_end_date;
    private int isStartDate;
    private String date, productName, categoryName;
    private Context context;
    private TextView submit, tvCategoryName, tvProductname;
    private Button imgCLose;
    private RelativeLayout dialogue_toolbar, startDateLayout, endDateLayout;
    private ImageView openStartDateCal, openEndDateCal;



    public ServiceEnquiry(Context context, String productName, String categoryName) {
        super(context);
        this.context = context;
        this.productName = productName;
        this.categoryName = categoryName;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        if(getWindow()!=null)
        getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        setContentView(R.layout.fragment_service_enquiry);


        initView();
        tvProductname.setText(new StringBuilder().append("Product Name : ").append(productName));
        tvCategoryName.setText(new StringBuilder().append("Category Name : ").append(categoryName));
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });


        imgCLose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        openStartDateCal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("etStartDate", "clicked");

                isStartDate = 0;
                openCalender();
            }
        });

        openEndDateCal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("etStartDate", "clicked");
                isStartDate = 1;
                openCalender();
            }
        });


    }

    private void initView()
    {
        dialogue_toolbar = (RelativeLayout) findViewById(R.id.dialogue_toolbar);
        startDateLayout = (RelativeLayout) findViewById(R.id.startDateLayout);
        endDateLayout = (RelativeLayout) findViewById(R.id.endDateLayout);
        imgCLose = (Button) findViewById(R.id.imgCLose);
        firstName = (EditText) findViewById(R.id.etFirstName);
        quantity = (EditText) findViewById(R.id.et_layout_quantity);
        price = (EditText) findViewById(R.id.et_layout_price);
        mobile = (EditText) findViewById(R.id.et_layout_mobile);
        email = (EditText) findViewById(R.id.et_layout_email);
        etEndDate = (EditText) findViewById(R.id.etEndDate);
        etStartDate = (EditText) findViewById(R.id.etStartDate);
        input_layout_start_date = (TextInputLayout) findViewById(R.id.input_layout_start_date);
        input_layout_end_date = (TextInputLayout) findViewById(R.id.input_layout_end_date);
        description = (EditText) findViewById(R.id.et_layout_description);
        submit = (TextView) findViewById(R.id.buttonSubmit);
        AndroidUtils.setBackgroundSolid(submit, context, R.color.orange, 8);
        AndroidUtils.setBackgroundSolid(dialogue_toolbar, context, R.color.green, 15);
        openStartDateCal = (ImageView) findViewById(R.id.openStartDateCal);
        openEndDateCal = (ImageView) findViewById(R.id.openEndDateCal);
        tvProductname = (TextView) findViewById(R.id.tvProductname);
        tvCategoryName = (TextView) findViewById(R.id.tvCategoryName);
    }


    private void openCalender()
    {
        Log.e("openCalender", "openCalender");
        Calendar now = Calendar.getInstance();
        DatePickerDialog dpd = DatePickerDialog.newInstance(
                this,
                now.get(Calendar.YEAR),
                now.get(Calendar.MONTH),
                now.get(Calendar.DAY_OF_MONTH)
        );
        dpd.setMaxDate(now);
        if (isStartDate == 1) {
            if (etStartDate.getText() != null || etStartDate.getText().toString().length() >= 8)
                dpd.setMinDate(AndroidUtils.stringToCalender(etStartDate.getText().toString()));
        }
        final Activity activity = (Activity) context;
        dpd.show(activity.getFragmentManager(), "DatePickerDialog");


    }


    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        showDate(year, monthOfYear + 1, dayOfMonth);
    }

    private void showDate(int year, int month, int day) {
        date = (new StringBuilder()).append(year).append("-").append(month).append("-").append(day).toString();
        if (isStartDate == 0) {
            etStartDate.setText(date);
        } else if (isStartDate == 1) {
            etEndDate.setText(date);
        }
    }
}
