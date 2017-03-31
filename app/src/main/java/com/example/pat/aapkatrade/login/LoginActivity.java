package com.example.pat.aapkatrade.login;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.pat.aapkatrade.Home.HomeActivity;
import com.example.pat.aapkatrade.Home.registration.RegistrationActivity;
import com.example.pat.aapkatrade.Home.registration.RegistrationBusinessAssociateActivity;
import com.example.pat.aapkatrade.R;
import com.example.pat.aapkatrade.general.AppSharedPreference;
import com.example.pat.aapkatrade.general.Call_webservice;
import com.example.pat.aapkatrade.general.TaskCompleteReminder;
import com.example.pat.aapkatrade.general.Utils.AndroidUtils;
import com.example.pat.aapkatrade.general.Validation;
import com.google.gson.JsonObject;

import java.util.HashMap;


public class LoginActivity extends AppCompatActivity {

    private TextView login_text, forgot_password;
    private EditText username, password;
    private RelativeLayout rl_login, relativeRegister;
    private Validation vt;
    private AppSharedPreference app_sharedpreference;
    private CoordinatorLayout cl;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        
        context = LoginActivity.this;
        app_sharedpreference = new AppSharedPreference(context);
        setUpToolBar();
        initView();
        putValues();

        relativeRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (app_sharedpreference.shared_pref != null) {
                    if (app_sharedpreference.getsharedpref("usertype", "0").equals("3")) {

                        Intent registerUserActivity = new Intent(context, RegistrationBusinessAssociateActivity.class);
                        startActivity(registerUserActivity);
                    } else if ((app_sharedpreference.getsharedpref("usertype", "0").equals("1")) || app_sharedpreference.getsharedpref("usertype", "0").equals("2")) {
                        Intent registerUserActivity = new Intent(context, RegistrationActivity.class);
                        startActivity(registerUserActivity);
                    }
                } else {
                    Log.e("null_sharedPreferences", "sharedPreferences");
                }

            }


        });

    }

    private void setUpToolBar() {
        ImageView homeIcon = (ImageView) findViewById(R.id.iconHome) ;
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


    private void putValues() {


        rl_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String input_email = username.getText().toString().trim();
                String input_password = password.getText().toString().trim();

                if (Validation.validateEdittext(username)) {

                    if (Validation.isValidEmail(input_email)) {


                        if (Validation.validateEdittext(password)) {


                            if (app_sharedpreference.shared_pref != null) {

                                Log.e("login------------", app_sharedpreference.getsharedpref("usertype", "0"));

                                if (app_sharedpreference.getsharedpref("usertype", "0").equals("3")) {

                                    Log.e("login------------", app_sharedpreference.getsharedpref("usertype", "0"));

                                    String login_url = getResources().getString(R.string.webservice_base_url)+"/businesslogin";

                                    callwebservice_login(login_url, input_email, input_password);


                                } else if (app_sharedpreference.getsharedpref("usertype", "0").equals("2")) {

                                    String login_url = getResources().getString(R.string.webservice_base_url)+"/buyerlogin";

                                    callwebservice_login(login_url, input_email, input_password);


                                } else if (app_sharedpreference.getsharedpref("usertype", "0").equals("1")) {

                                    String login_url = getResources().getString(R.string.webservice_base_url)+"/sellerlogin";

                                    callwebservice_login(login_url, input_email, input_password);

                                }
                            }
                        } else {
                            showMessage("Invalid Password");
                            password.setError("Invalid Password");
                        }

                    } else {
                        showMessage("Invalid Password");
                        password.setError("Invalid Password");
                    }

                } else {
                    username.setError("Invalid Username");
                    showMessage("Invalid Username");
                }

            }
        });

    }

    private void callwebservice_login(String login_url, String input_username, String input_password) {
        // dialog.show();
        HashMap<String, String> webservice_body_parameter = new HashMap<>();
        webservice_body_parameter.put("authorization", "xvfdbgfdhbfdhtrh54654h54ygdgerwer3");
        webservice_body_parameter.put("type", "login");
        webservice_body_parameter.put("email", input_username);
        webservice_body_parameter.put("password", input_password);

        HashMap<String, String> webservice_header_type = new HashMap<>();
        webservice_header_type.put("authorization", "xvfdbgfdhbfdhtrh54654h54ygdgerwer3");

        Call_webservice.call_login_webservice(context, login_url, "login", webservice_body_parameter, webservice_header_type);

        Call_webservice.taskCompleteReminder = new TaskCompleteReminder() {
            @Override
            public void Taskcomplete(JsonObject webservice_returndata) {

                if (webservice_returndata != null) {
                    boolean flag = false;
                    if (app_sharedpreference.getsharedpref("usertype", "0").equals("1")) {
                        Log.e("webservice_returndata", webservice_returndata.toString());
                        if (webservice_returndata.get("error").getAsString().equals("false")) {
                            saveDataInSharedPreference(webservice_returndata, 1);
                            flag = true;
                        } else {
                            showMessage(webservice_returndata.get("message").getAsString());
                        }
                    } else if (app_sharedpreference.getsharedpref("usertype", "0").equals("2")) {
                        Log.e("webservice_returndata", webservice_returndata.toString());
                        if (webservice_returndata.get("error").getAsString().equals("false")) {
                            saveDataInSharedPreference(webservice_returndata, 2);
                            flag = true;
                        } else {
                            showMessage(webservice_returndata.get("message").getAsString());
                        }

                    } else if (app_sharedpreference.getsharedpref("usertype", "0").equals("3")) {

                        Log.e("webservice_returndata", webservice_returndata.toString());

                        if (webservice_returndata.get("error").getAsString().equals("false")) {
                            saveDataInSharedPreference(webservice_returndata, 3);
                            flag = true;
                        } else {
                            showMessage(webservice_returndata.get("message").getAsString());
                        }
                    }
                    if (flag) {
                        Intent Homedashboard = new Intent(context, HomeActivity.class);
                        Homedashboard.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(Homedashboard);
                    }
                }
            }
        };

    }

    private void saveDataInSharedPreference(JsonObject webservice_returndata, int userType) {

        if (userType == 1)
        {
            JsonObject jsonObject = webservice_returndata.getAsJsonObject("all_info");
            Log.e("hi", jsonObject.toString());

            app_sharedpreference.setsharedpref("userid", webservice_returndata.get("user_id").getAsString());
            app_sharedpreference.setsharedpref("name", jsonObject.get("name").getAsString());
            app_sharedpreference.setsharedpref("username", jsonObject.get("name").getAsString());
            app_sharedpreference.setsharedpref("lname", jsonObject.get("lastname").getAsString());
            app_sharedpreference.setsharedpref("shopname", jsonObject.get("shopname").getAsString());
            app_sharedpreference.setsharedpref("business_type", jsonObject.get("business_type").getAsString());
            app_sharedpreference.setsharedpref("emailid", jsonObject.get("email").getAsString());
            app_sharedpreference.setsharedpref("mobile", jsonObject.get("mobile").getAsString());
            app_sharedpreference.setsharedpref("dob", jsonObject.get("dob").getAsString());
            app_sharedpreference.setsharedpref("address", jsonObject.get("address").getAsString());
            app_sharedpreference.setsharedpref("companyname", jsonObject.get("companyname").getAsString());
            app_sharedpreference.setsharedpref("comp_incorporation", jsonObject.get("comp_incorporation").getAsString());
            app_sharedpreference.setsharedpref("tin_number", jsonObject.get("tin_number").getAsString());
            app_sharedpreference.setsharedpref("tan_number", jsonObject.get("tan_number").getAsString());
            app_sharedpreference.setsharedpref("personal_doc", jsonObject.get("personal_doc").getAsString());
            app_sharedpreference.setsharedpref("city_id", jsonObject.get("city_id").getAsString());
            app_sharedpreference.setsharedpref("country_id", jsonObject.get("country_id").getAsString());
            app_sharedpreference.setsharedpref("state_id", jsonObject.get("state_id").getAsString());
            app_sharedpreference.setsharedpref("profile_pic", jsonObject.get("profile_pick").getAsString());
            app_sharedpreference.setsharedpref("order", webservice_returndata.get("order").getAsString());
            app_sharedpreference.setsharedpref("product", webservice_returndata.get("product").getAsString());
            app_sharedpreference.setsharedpref("company", webservice_returndata.get("company").getAsString());

        }
        else if (userType == 2)
        {
            JsonObject jsonObject = webservice_returndata.getAsJsonObject("all_info");
            Log.e("hi", jsonObject.toString());


            app_sharedpreference.setsharedpref("userid", webservice_returndata.get("user_id").getAsString());
            app_sharedpreference.setsharedpref("name", jsonObject.get("name").getAsString());
            app_sharedpreference.setsharedpref("username", jsonObject.get("name").getAsString());
            app_sharedpreference.setsharedpref("lname", jsonObject.get("lastname").getAsString());
            app_sharedpreference.setsharedpref("emailid", jsonObject.get("email").getAsString());
            app_sharedpreference.setsharedpref("mobile", jsonObject.get("mobile").getAsString());
            app_sharedpreference.setsharedpref("dob", jsonObject.get("dob").getAsString());
            app_sharedpreference.setsharedpref("country_id", jsonObject.get("country_id").getAsString());
            app_sharedpreference.setsharedpref("state_id", jsonObject.get("state_id").getAsString());
            app_sharedpreference.setsharedpref("city_id", jsonObject.get("city_id").getAsString());
            app_sharedpreference.setsharedpref("address", jsonObject.get("address").getAsString());
            app_sharedpreference.setsharedpref("platform", jsonObject.get("platform").getAsString());
            app_sharedpreference.setsharedpref("device_id", jsonObject.get("device_id").getAsString());
            app_sharedpreference.setsharedpref("updated_at", jsonObject.get("updated_at").getAsString());
            app_sharedpreference.setsharedpref("status", jsonObject.get("status").getAsString());
            app_sharedpreference.setsharedpref("order", webservice_returndata.get("order").getAsString());
            app_sharedpreference.setsharedpref("createdAt", webservice_returndata.get("createdAt").getAsString());



        }
        else if (userType == 3)
        {


            JsonObject jsonObject = webservice_returndata.getAsJsonObject("all_info");
            Log.e("hi", jsonObject.toString());
            app_sharedpreference.setsharedpref("userid", webservice_returndata.get("user_id").getAsString());
            app_sharedpreference.setsharedpref("business_id", webservice_returndata.get("user_id").getAsString());
            app_sharedpreference.setsharedpref("name", jsonObject.get("name").getAsString());
            app_sharedpreference.setsharedpref("username", jsonObject.get("name").getAsString());
            app_sharedpreference.setsharedpref("lname", jsonObject.get("lastname").getAsString());
            app_sharedpreference.setsharedpref("father_name", jsonObject.get("father_name").getAsString());
            app_sharedpreference.setsharedpref("emailid", jsonObject.get("email").getAsString());
            app_sharedpreference.setsharedpref("mobile", jsonObject.get("mobile").getAsString());
            app_sharedpreference.setsharedpref("qualification", jsonObject.get("qualification").getAsString());
            app_sharedpreference.setsharedpref("total_exp", jsonObject.get("total_exp").getAsString());
            app_sharedpreference.setsharedpref("relevant_exp", jsonObject.get("relevant_exp").getAsString());
            app_sharedpreference.setsharedpref("id_proof", jsonObject.get("id_proof").getAsString());
            app_sharedpreference.setsharedpref("profile_pic", jsonObject.get("photo").getAsString());
            app_sharedpreference.setsharedpref("country_id", jsonObject.get("country_id").getAsString());
            app_sharedpreference.setsharedpref("state_id", jsonObject.get("state_id").getAsString());
            app_sharedpreference.setsharedpref("city_id", jsonObject.get("city_id").getAsString());
            app_sharedpreference.setsharedpref("dob", jsonObject.get("dob").getAsString());
            app_sharedpreference.setsharedpref("type_emp", jsonObject.get("type_emp").getAsString());
            app_sharedpreference.setsharedpref("address", jsonObject.get("address").getAsString());
            app_sharedpreference.setsharedpref("by_ref", jsonObject.get("by_ref").getAsString());
            app_sharedpreference.setsharedpref("ref_no", jsonObject.get("ref_no").getAsString());
            app_sharedpreference.setsharedpref("pincode", jsonObject.get("pincode").getAsString());
            app_sharedpreference.setsharedpref("platform", jsonObject.get("platform").getAsString());
            app_sharedpreference.setsharedpref("device_id", jsonObject.get("device_id").getAsString());
            app_sharedpreference.setsharedpref("term_accepted", jsonObject.get("term_accepted").getAsString());
            app_sharedpreference.setsharedpref("created_at", "");
            app_sharedpreference.setsharedpref("updated_at", "");
            app_sharedpreference.setsharedpref("status", jsonObject.get("status").getAsString());
            app_sharedpreference.setsharedpref("id", "");
            app_sharedpreference.setsharedpref("bank_name", jsonObject.get("bank_name").getAsString());
            app_sharedpreference.setsharedpref("account_no", jsonObject.get("account_no").getAsString());
            app_sharedpreference.setsharedpref("branch_code", jsonObject.get("branch_code").getAsString());
            app_sharedpreference.setsharedpref("branch_name", jsonObject.get("branch_name").getAsString());
            app_sharedpreference.setsharedpref("ifsc_code", jsonObject.get("ifsc_code").getAsString());
            app_sharedpreference.setsharedpref("micr_code", jsonObject.get("micr_code").getAsString());
            app_sharedpreference.setsharedpref("account_holder", jsonObject.get("account_holder").getAsString());
            app_sharedpreference.setsharedpref("register_mobile", jsonObject.get("register_mobile").getAsString());
            app_sharedpreference.setsharedpref("vendor", webservice_returndata.get("vendor").getAsString());
            app_sharedpreference.setsharedpref("network", webservice_returndata.get("network").getAsString());

        }
    }


    private void initView()
    {

        forgot_password = (TextView) findViewById(R.id.tv_forgotpassword);
        login_text = (TextView) findViewById(R.id.tv_login);
        cl = (CoordinatorLayout) findViewById(R.id.coordinator_layout);
        username = (EditText) findViewById(R.id.etusername);
        password = (EditText) findViewById(R.id.etpassword);
        rl_login = (RelativeLayout) findViewById(R.id.rl_login);
        relativeRegister = (RelativeLayout) findViewById(R.id.relativeRegister);
        vt = new Validation();
        forgot_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent forgotpassword_activity = new Intent(context, ForgotPassword.class);
                forgotpassword_activity.putExtra("forgot_index","0");
                startActivity(forgotpassword_activity);
            }
        });



    }

    public void showMessage(String message) {
        Snackbar snackbar = Snackbar
                .make(cl, message, Snackbar.LENGTH_LONG)
                .setAction("", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                    }
                });
        snackbar.show();
    }


}
