package com.example.pat.aapkatrade.login;

import android.content.BroadcastReceiver;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.pat.aapkatrade.Home.HomeActivity;
import com.example.pat.aapkatrade.R;
import com.example.pat.aapkatrade.general.App_config;
import com.example.pat.aapkatrade.general.Call_webservice;
import com.example.pat.aapkatrade.general.TaskCompleteReminder;
import com.google.gson.JsonObject;

import java.util.HashMap;

public class ActivityOTPVerify extends AppCompatActivity {

    Button retryotp, verifyotp;
    TextView toolbar_title_txt, tittleTxt, otpNotRespond, couter_textview;
    public static EditText editText1, editText2, editText3, editText4;
    int count = 00;

    CoordinatorLayout coordinatorLayout;
    BroadcastReceiver receiver;
    LocalBroadcastManager bManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otpverify);

        setuptoolbar();

        setup_layout();

        // bManager = LocalBroadcastManager.getInstance(this);


        // call_auto_retrive_sms(ActivityOTPVerify.this);


    }

    public void update_otp(String message) {

        //String code = parseCode(message).trim();//Parse verification code

        message = message.replace("Your otp is ", "").trim();
        String a = message.substring(0, 1).trim();
        String b = message.substring(1, 2).trim();
        String c = message.substring(2, 3).trim();
        String d = message.substring(3, 4).trim();
        editText1 = (EditText) findViewById(R.id.etotp1);

        editText2 = (EditText) findViewById(R.id.etotp2);

        editText3 = (EditText) findViewById(R.id.etotp3);

        editText4 = (EditText) findViewById(R.id.etotp4);

//        Character b=code.charAt(1);
//        Character c=code.charAt(2);
//        Character d=code.charAt(3);

        editText1.setText(a);
        editText2.setText(b);
        editText3.setText(c);
        editText4.setText(d);
        Log.e("message412354235", message);
    }


    private void setuptoolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(null);

        getSupportActionBar().setIcon(R.drawable.home_logo);

//        toolbar_title_txt = (TextView) findViewById(R.id.title_txt);
//
//        toolbar_title_txt.setText("ENTER OTP");

    }


//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.user, menu);
//        return true;
//    }

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


    private void setup_layout() {


        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinate_otpverify);


        verifyotp = (Button) findViewById(R.id.btn_verify);

        couter_textview = (TextView) findViewById(R.id.couter_textview);

        editText1 = (EditText) findViewById(R.id.etotp1);

        editText2 = (EditText) findViewById(R.id.etotp2);

        editText3 = (EditText) findViewById(R.id.etotp3);

        editText4 = (EditText) findViewById(R.id.etotp4);


        retryotp = (Button) findViewById(R.id.retryButton);

        //tittleTxt = (TextView) findViewById(R.id.tittleTxt);

        otpNotRespond = (TextView) findViewById(R.id.otpNotRespond);


        verifyotp.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

                if (editText1.getText().length() != 0)
                {
                    String otp = editText1.getText().toString().trim() + editText2.getText().toString().trim() + editText3.getText().toString().trim() + editText4.getText().toString().trim();

                    Log.e("otp ", otp);
                    call_verifyotp_webservice(otp);
                }
                else
                {

                    Log.e("otp null", "*****");

                }



            }
        });


        retryotp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                call_otp_retry_webservice();

//                Intent otp = new Intent(ActivityOTPVerify.this, HomeActivity.class);
//                otp.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK |Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                startActivity(otp);
                overridePendingTransition(R.anim.enter, R.anim.exit);

            }
        });


        editText1.addTextChangedListener(new TextWatcher() {

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // TODO Auto-generated method stub
                if (editText1.getText().toString().length() == 1)     //size as per your requirement
                {
                    editText2.requestFocus();
                }
            }

            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
                // TODO Auto-generated method stub

            }

            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub
            }

        });

        editText2.addTextChangedListener(new TextWatcher() {

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // TODO Auto-generated method stub
                if (editText2.getText().toString().length() == 1)     //size as per your requirement
                {
                    editText3.requestFocus();
                }
            }

            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
                // TODO Auto-generated method stub

            }

            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub
            }

        });


        editText3.addTextChangedListener(new TextWatcher() {

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // TODO Auto-generated method stub
                if (editText3.getText().toString().length() == 1)     //size as per your requirement
                {
                    editText4.requestFocus();
                }
            }

            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
                // TODO Auto-generated method stub

            }

            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub
            }

        });


        new CountDownTimer(30000, 1000) {

            public void onTick(long millisUntilFinished) {
                couter_textview.setText("00: " + millisUntilFinished / 1000);
                //here you can have your logic to set text to edittext
            }

            public void onFinish() {
                couter_textview.setText("00:00");
            }

        }.start();


    }

    private void call_verifyotp_webservice(String otp) {
        String getCurrentDeviceId = App_config.getCurrentDeviceId(ActivityOTPVerify.this);

        // dialog.show();
        HashMap<String, String> webservice_body_parameter = new HashMap<>();
        webservice_body_parameter.put("authorization", "xvfdbgfdhbfdhtrh54654h54ygdgerwer3");
        webservice_body_parameter.put("client_id", getCurrentDeviceId);
        webservice_body_parameter.put("otp", otp);


        HashMap<String, String> webservice_header_type = new HashMap<>();
        webservice_header_type.put("authorization", "xvfdbgfdhbfdhtrh54654h54ygdgerwer3");


        String verifyotp_url = "http://aapkatrade.com/slim/varify_otp";
        Call_webservice.verify_otp(ActivityOTPVerify.this, verifyotp_url, "resend_otp", webservice_body_parameter, webservice_header_type);

        Call_webservice.taskCompleteReminder = new TaskCompleteReminder() {
            @Override
            public void Taskcomplete(JsonObject webservice_returndata) {

//                Log.e("data2",webservice_returndata.toString());

                if (webservice_returndata != null) {
                    Log.e("webservice_returndata", webservice_returndata.toString());
                    JsonObject jsonObject = webservice_returndata.getAsJsonObject();

                    String error = jsonObject.get("error").getAsString();
                    String message = jsonObject.get("message").getAsString();
                    if (error.equals("false")) {
                        String user_id = jsonObject.get("user_id").getAsString();


                        showMessage(message);


                        Intent Homedashboard = new Intent(ActivityOTPVerify.this, HomeActivity.class);
                        Homedashboard.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(Homedashboard);

                    } else {
                        showMessage(message);
                    }


                }

            }
        };


    }


    private void call_otp_retry_webservice() {


        // dialog.show();
        HashMap<String, String> webservice_body_parameter = new HashMap<>();
        webservice_body_parameter.put("authorization", "xvfdbgfdhbfdhtrh54654h54ygdgerwer3");
        webservice_body_parameter.put("client_id", "564735473442373");


        HashMap<String, String> webservice_header_type = new HashMap<>();
        webservice_header_type.put("authorization", "xvfdbgfdhbfdhtrh54654h54ygdgerwer3");


        String otp_url = "http://aapkatrade.com/slim/resend_otp";
        Call_webservice.resend_otp(ActivityOTPVerify.this, otp_url, "resend_otp", webservice_body_parameter, webservice_header_type);

        Call_webservice.taskCompleteReminder = new TaskCompleteReminder() {
            @Override
            public void Taskcomplete(JsonObject webservice_returndata) {

                Log.e("data2", webservice_returndata.toString());

                if (webservice_returndata != null) {
                    Log.e("webservice_returndata", webservice_returndata.toString());
                    JsonObject jsonObject = webservice_returndata.getAsJsonObject();

                    String error = jsonObject.get("error").getAsString();
                    String message = jsonObject.get("message").getAsString();
                    if (error.equals("false")) {
                        String user_id = jsonObject.get("user_id").getAsString();


                        showMessage(message);


                        Intent Homedashboard = new Intent(ActivityOTPVerify.this, HomeActivity.class);
                        Homedashboard.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(Homedashboard);

                    } else {
                        showMessage(message);
                    }


                }

            }
        };


    }


    public void showMessage(String message) {

        Snackbar snackbar = Snackbar
                .make(coordinatorLayout, message, Snackbar.LENGTH_LONG)
                .setAction("", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
//                    Snackbar snackbar1 = Snackbar.make(cl, "", Snackbar.LENGTH_SHORT);
//                    snackbar1.show();
                    }
                });

        snackbar.show();
    }


//    @Override
//    protected void onStart() {
//        super.onStart();
//        smsVerifyCatcher.onStart();
//    }
//
//    @Override
//    protected void onStop() {
//        super.onStop();
//        smsVerifyCatcher.onStop();
//    }


}
