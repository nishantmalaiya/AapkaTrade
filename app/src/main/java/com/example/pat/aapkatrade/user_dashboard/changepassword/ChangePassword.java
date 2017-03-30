package com.example.pat.aapkatrade.user_dashboard.changepassword;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.pat.aapkatrade.Home.HomeActivity;
import com.example.pat.aapkatrade.Home.registration.RegistrationActivity;
import com.example.pat.aapkatrade.R;
import com.example.pat.aapkatrade.general.AppSharedPreference;
import com.example.pat.aapkatrade.general.ConnectivityNotFound;
import com.example.pat.aapkatrade.general.ConnetivityCheck;
import com.example.pat.aapkatrade.general.Utils.AndroidUtils;
import com.example.pat.aapkatrade.general.Validation;
import com.example.pat.aapkatrade.general.progressbar.ProgressBarHandler;
import com.example.pat.aapkatrade.login.ActivityOTPVerify;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;


public class ChangePassword extends AppCompatActivity
{

    EditText OldPassword, NewPassword, ConfirmPassword;
    TextView saveNewPasswordButton;
    LinearLayout linearChangePassword;
    AppSharedPreference app_sharedpreference;
    String user_id, user_type;
    ProgressBarHandler progress_handler;
    TextView tvTitle;
    private Context context;



    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_change_password);

        context = ChangePassword.this;

        progress_handler = new ProgressBarHandler(this);

        setUpToolBar();

        app_sharedpreference = new AppSharedPreference(getApplicationContext());

        user_id = app_sharedpreference.getsharedpref("userid", "");

        user_type = app_sharedpreference.getsharedpref("usertype","1");

        initView();


    }


    private void showMessage(String message) {
        AndroidUtils.showSnackBar(linearChangePassword, message);
    }


    private void callChangePasswordWebService(String userType) {

        progress_handler.show();

        System.out.println("user_id------------" + user_id +"usertype-----"+userType+ "old password--" + OldPassword.getText().toString() + "Confirm password----" + ConfirmPassword.getText().toString());

        if (ConnetivityCheck.isNetworkAvailable(ChangePassword.this)) {


            Log.e("changePassword",getResources().getString(R.string.webservice_base_url)+"/changePassword");
            Ion.with(ChangePassword.this)
                    .load(getResources().getString(R.string.webservice_base_url)+"/changePassword")
                    .setHeader("authorization", "xvfdbgfdhbfdhtrh54654h54ygdgerwer3")
                    .setBodyParameter("authorization", "xvfdbgfdhbfdhtrh54654h54ygdgerwer3")
                    .setBodyParameter("type", userType)
                    .setBodyParameter("old_password", OldPassword.getText().toString())
                    .setBodyParameter("new_password", NewPassword.getText().toString())
                    .setBodyParameter("confirm_password", ConfirmPassword.getText().toString())
                    .setBodyParameter("id", user_id)
                    .asJsonObject()
                    .setCallback(new FutureCallback<JsonObject>() {
                        @Override
                        public void onCompleted(Exception e, JsonObject result) {

                            //Log.e("change_password_error",result.toString());

                            if (result == null) {
                                Log.e("change_password_error",e.toString());
                                progress_handler.hide();
                            }

                            else
                                {
                                JsonObject jsonObject = result.getAsJsonObject();
                                String message = jsonObject.get("message").getAsString();
                                Log.e("data_change_password", result.toString());
                                progress_handler.hide();
                                showMessage(message);
                                Intent intent = new Intent(ChangePassword.this, HomeActivity.class);
                                intent.putExtra("callerActivity", ChangePassword.class.getName());
                                startActivity(intent);

                            }
                        }

                    });
        } else {
            Intent intent = new Intent(ChangePassword.this, ConnectivityNotFound.class);
            intent.putExtra("callerActivity", ChangePassword.class.getName());
            startActivity(intent);
        }


    }

    private void initView() {

        linearChangePassword = (LinearLayout) findViewById(R.id.linearChangePassword);

        OldPassword = (EditText) findViewById(R.id.etOldPassword);

        NewPassword = (EditText) findViewById(R.id.etNewPassword);

        ConfirmPassword = (EditText) findViewById(R.id.etNewPasswordConfirm);

        saveNewPasswordButton = (TextView) findViewById(R.id.saveNewPasswordButton);

        saveNewPasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!Validation.isEmptyStr(OldPassword.getText().toString())) {

                    if (Validation.isValidPassword(NewPassword.getText().toString())) {

                        if (!Validation.isEmptyStr(ConfirmPassword.getText().toString())) {

                            if (NewPassword.getText().toString().equals(ConfirmPassword.getText().toString())) {

String get_user_type=AndroidUtils.getUserType(user_type);
                                String get_user_type_string;

                                if(get_user_type.contains("1"))
                                {
                                    get_user_type_string="buyer";


                                }
                                else if (get_user_type.contains("2")){

                                    get_user_type_string="sellers";

                                }
                                else {
                                    get_user_type_string="business";
                                }
                                callChangePasswordWebService(get_user_type_string);
                                Log.e("get_user_type_string",get_user_type_string);

                            } else {
                                showMessage("Old and confirm password does not match");
                            }
                        } else {
                            showMessage("Please Enter Confirm Password");

                        }

                    } else {
                        showMessage("Please Enter Minimum 6 digit New Password");
                    }

                } else {
                    showMessage("Please Enter Old Password");
                }

            }
        });
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
        header_name.setText(getResources().getString(R.string.change_password_heading));
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
}
