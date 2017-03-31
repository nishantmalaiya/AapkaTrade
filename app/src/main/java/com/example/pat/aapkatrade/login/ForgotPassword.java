package com.example.pat.aapkatrade.login;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.pat.aapkatrade.Home.DashboardFragment;
import com.example.pat.aapkatrade.Home.HomeActivity;
import com.example.pat.aapkatrade.R;
import com.example.pat.aapkatrade.general.AppSharedPreference;
import com.example.pat.aapkatrade.general.App_config;
import com.example.pat.aapkatrade.general.Call_webservice;
import com.example.pat.aapkatrade.general.Change_Font;
import com.example.pat.aapkatrade.general.TaskCompleteReminder;
import com.example.pat.aapkatrade.general.Utils.AndroidUtils;
import com.example.pat.aapkatrade.general.Validation;
import com.example.pat.aapkatrade.general.progressbar.ProgressBarHandler;
import com.google.gson.JsonObject;

import java.util.HashMap;

public class ForgotPassword extends AppCompatActivity implements View.OnClickListener {
    private TextView tv_forgot_password, tv_forgot_password_description;
    private Button btn_send_otp;
    private EditText et_email_forgot, et_mobile_no;
    private CoordinatorLayout activity_forgot__password;
    private AppSharedPreference app_sharedpreference;
    private String usertype;
    private ProgressBarHandler progressBarHandler;
    private Context context;
    String classname;
    Forgot_password_fragment forgot_password_fragment;
    Reset_password_fragment reset_password_fragment;

    String class_index;

    public ForgotPassword(Context context, int class_index) {
        this.context = context;

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot__password);
        class_index=  getIntent().getStringExtra("forgot_index");
        Log.e("class_index",""+class_index);
        forgot_password_fragment = new Forgot_password_fragment();
        //setUpToolBar();
        setupForgetpassword(class_index);

        initView();

    }

    public ForgotPassword() {
    }

    private void setupForgetpassword(String class_index) {
        if (class_index .contains( "0")) {
            if (forgot_password_fragment == null) {
                forgot_password_fragment = new Forgot_password_fragment();
            }
            String tagName = forgot_password_fragment.getClass().getName();


            replaceFragment(forgot_password_fragment, tagName);


        } else {


            if (reset_password_fragment == null) {
                reset_password_fragment = new Reset_password_fragment();
            }
            String tagName = reset_password_fragment.getClass().getName();


            replaceFragment(reset_password_fragment, tagName);


        }


    }


    private void initView() {
//        app_sharedpreference = new AppSharedPreference(context);
//        progressBarHandler = new ProgressBarHandler(context);
//
//        tv_forgot_password = (TextView) findViewById(R.id.tv_forgot_password);
//        tv_forgot_password_description = (TextView) findViewById(R.id.tv_forgot_password_description);
//
//
//        et_email_forgot = (EditText) findViewById(R.id.et_email_forgot);
//        et_mobile_no = (EditText) findViewById(R.id.et_mobile_no);
//
//        btn_send_otp = (Button) findViewById(R.id.btn_send_otp);
//        btn_send_otp.setOnClickListener(this);

        activity_forgot__password = (CoordinatorLayout) findViewById(R.id.activity_forgot__password);

//        Change_Font.Change_Font_textview(ForgotPassword.this, tv_forgot_password);
//        Change_Font.Change_Font_textview(ForgotPassword.this, tv_forgot_password_description);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.btn_send_otp:


                Validatefields();


                break;
        }

    }

    private void Validatefields() {

        if (Validation.validateEdittext(et_email_forgot)) {
            call_forgotpasswod_webservice();

        } else if (Validation.validateEdittext(et_mobile_no)) {

            call_forgotpasswod_webservice();


        } else {
            showmessage("");

        }


    }

    private void showmessage(String message) {
        AndroidUtils.showSnackBar(activity_forgot__password, message);
    }


    private void call_forgotpasswod_webservice() {
        progressBarHandler.show();


        String webservice_forgot_password = getResources().getString(R.string.webservice_base_url) + "/forget";

        if (app_sharedpreference.shared_pref != null) {
            if (app_sharedpreference.getsharedpref("usertype", "0").equals("3")) {

                usertype = "business";
            } else if ((app_sharedpreference.getsharedpref("usertype", "0").equals("1"))) {
                usertype = "seller";

            } else if (app_sharedpreference.getsharedpref("usertype", "0").equals("2")) {
                usertype = "business";

            }
        } else {
            Log.e("null_sharedPreferences", "sharedPreferences");
        }


        HashMap<String, String> webservice_body_parameter = new HashMap<>();
        webservice_body_parameter.put("authorization", "xvfdbgfdhbfdhtrh54654h54ygdgerwer3");
        webservice_body_parameter.put("type", usertype);
        webservice_body_parameter.put("email", et_email_forgot.getText().toString().trim());
        webservice_body_parameter.put("mobile", et_mobile_no.getText().toString().trim());
        webservice_body_parameter.put("client_id", App_config.getCurrentDeviceId(context));


        HashMap<String, String> webservice_header_type = new HashMap<>();
        webservice_header_type.put("authorization", "xvfdbgfdhbfdhtrh54654h54ygdgerwer3");


        Call_webservice.forgot_password(context, webservice_forgot_password, "forgot_password", webservice_body_parameter, webservice_header_type);
        Call_webservice.taskCompleteReminder = new TaskCompleteReminder() {
            @Override
            public void Taskcomplete(JsonObject data) {
                if (data != null) {
                    String error = data.get("error").getAsString();
                    if (error.contains("false")) {
                        Intent go_to_activity_otp_verify = new Intent(context, ActivityOTPVerify.class);
                        go_to_activity_otp_verify.putExtra("class_name",context.getClass().getName());
                        startActivity(go_to_activity_otp_verify);
                    }
                    String message = data.get("message").getAsString();
                    showmessage(message);
                    progressBarHandler.hide();
                } else {
                    progressBarHandler.hide();
                }
                Log.e("forgot_password", data.toString());
            }
        };
    }

    private void setUpToolBar() {
        ImageView homeIcon = (ImageView) findViewById(R.id.iconHome);
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
        AndroidUtils.setBackgroundSolid(toolbar, context, R.color.transparent, 0);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
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

    private void replaceFragment(Fragment newFragment, String tag) {

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fl_forgot_password_container, newFragment, tag).addToBackStack(tag);
        transaction.commit();
    }


}
