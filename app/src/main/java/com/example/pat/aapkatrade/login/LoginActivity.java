package com.example.pat.aapkatrade.login;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
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
import java.util.UUID;


public class LoginActivity extends AppCompatActivity
{

    private TextView loginText, forgotPassword;
    private EditText etEmail, password;
    private RelativeLayout relativeLayoutLogin, relativeRegister;
    private AppSharedPreference appSharedpreference;
    private CoordinatorLayout coordinatorLayout;
    private Context context;
    String user_login;



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Intent intent = getIntent();
        Bundle b = intent.getExtras();
        user_login = b.getString("user_login");
        context = LoginActivity.this;
        appSharedpreference = new AppSharedPreference(context);
        setUpToolBar();
        initView();
        putValues();

        relativeRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (appSharedpreference.shared_pref != null) {
                    if (appSharedpreference.getsharedpref("usertype", "0").equals("3")) {

                        Intent registerUserActivity = new Intent(context, RegistrationBusinessAssociateActivity.class);
                        startActivity(registerUserActivity);
                    } else if ((appSharedpreference.getsharedpref("usertype", "0").equals("1")) || appSharedpreference.getsharedpref("usertype", "0").equals("2")) {
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
        ImageView homeIcon = (ImageView) findViewById(R.id.iconHome);
        findViewById(R.id.logoWord).setVisibility(View.INVISIBLE);
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
        AndroidUtils.setBackgroundSolid(toolbar, context, R.color.transparent, 0, GradientDrawable.OVAL);
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

        relativeLayoutLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String input_email = etEmail.getText().toString().trim();
                String input_password = password.getText().toString();

                if (Validation.isValidEmail(input_email)) {

                    if (Validation.validateEdittext(password)) {


                        if (appSharedpreference.shared_pref != null) {

                            Log.e("login------------", appSharedpreference.getsharedpref("usertype", "0"));

                            if (appSharedpreference.getsharedpref("usertype", "0").equals("3")) {

                                
                                Log.e("login------------", appSharedpreference.getsharedpref("usertype", "0"));
                                AndroidUtils.showErrorLog(context, "UserType : BusinessAssociates");

                                String login_url = getResources().getString(R.string.webservice_base_url) + "/businesslogin";

                                callwebservice_login(login_url, input_email, input_password);


                            } else if (appSharedpreference.getsharedpref("usertype", "0").equals("2")) {
                                AndroidUtils.showErrorLog(context, "UserType : Buyer");

                                String login_url = getResources().getString(R.string.webservice_base_url) + "/buyerlogin";

                                callwebservice_login(login_url, input_email, input_password);


                            } else if (appSharedpreference.getsharedpref("usertype", "0").equals("1")) {
                                AndroidUtils.showErrorLog(context, "UserType : Seller");

                                String login_url = getResources().getString(R.string.webservice_base_url) + "/sellerlogin";

                                callwebservice_login(login_url, input_email, input_password);

                            }
                        }
                    } else {
                        showMessage(getResources().getString(R.string.password_validing_text));
                        password.setError(getResources().getString(R.string.password_validing_text));
                    }

                } else {
                    showMessage("Invalid Email Address");
                    etEmail.setError("Invalid Email Address");
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
                    if (appSharedpreference.getsharedpref("usertype", "0").equals("1")) {
                        Log.e("webservice_returndata", webservice_returndata.toString());
                        if (webservice_returndata.get("error").getAsString().equals("false")) {
                            saveDataInSharedPreference(webservice_returndata, 1);
                            flag = true;
                        } else {
                            showMessage(webservice_returndata.get("message").getAsString());
                        }
                    } else if (appSharedpreference.getsharedpref("usertype", "0").equals("2")) {
                        Log.e("webservice_returndata", webservice_returndata.toString());
                        if (webservice_returndata.get("error").getAsString().equals("false")) {
                            saveDataInSharedPreference(webservice_returndata, 2);
                            flag = true;
                        } else {
                            showMessage(webservice_returndata.get("message").getAsString());
                        }

                    } else if (appSharedpreference.getsharedpref("usertype", "0").equals("3")) {

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

        if (userType == 1) {
            JsonObject jsonObject = webservice_returndata.getAsJsonObject("all_info");
            Log.e("hi", jsonObject.toString());

            appSharedpreference.setsharedpref("userid", webservice_returndata.get("user_id").getAsString());
            appSharedpreference.setsharedpref("name", jsonObject.get("name").getAsString());
            appSharedpreference.setsharedpref("username", jsonObject.get("name").getAsString());
            appSharedpreference.setsharedpref("lname", jsonObject.get("lastname").getAsString());
            appSharedpreference.setsharedpref("shopname", jsonObject.get("shopname").getAsString());
            appSharedpreference.setsharedpref("business_type", jsonObject.get("business_type").getAsString());
            appSharedpreference.setsharedpref("emailid", jsonObject.get("email").getAsString());
            appSharedpreference.setsharedpref("mobile", jsonObject.get("mobile").getAsString());
            appSharedpreference.setsharedpref("dob", jsonObject.get("dob").getAsString());
            appSharedpreference.setsharedpref("address", jsonObject.get("address").getAsString());
            appSharedpreference.setsharedpref("companyname", jsonObject.get("companyname").getAsString());
            appSharedpreference.setsharedpref("comp_incorporation", jsonObject.get("comp_incorporation").getAsString());
            appSharedpreference.setsharedpref("tin_number", jsonObject.get("tin_number").getAsString());
            appSharedpreference.setsharedpref("tan_number", jsonObject.get("tan_number").getAsString());
            appSharedpreference.setsharedpref("personal_doc", jsonObject.get("personal_doc").getAsString());
            appSharedpreference.setsharedpref("city_id", jsonObject.get("city_id").getAsString());
            appSharedpreference.setsharedpref("country_id", jsonObject.get("country_id").getAsString());
            appSharedpreference.setsharedpref("state_id", jsonObject.get("state_id").getAsString());
            appSharedpreference.setsharedpref("profile_pic", jsonObject.get("profile_pick").getAsString());
            appSharedpreference.setsharedpref("order", webservice_returndata.get("order").getAsString());
            appSharedpreference.setsharedpref("product", webservice_returndata.get("product").getAsString());
            appSharedpreference.setsharedpref("company", webservice_returndata.get("company").getAsString());

        } else if (userType == 2) {
            JsonObject jsonObject = webservice_returndata.getAsJsonObject("all_info");
            Log.e("hi", jsonObject.toString());


            appSharedpreference.setsharedpref("userid", webservice_returndata.get("user_id").getAsString());
            appSharedpreference.setsharedpref("name", jsonObject.get("name").getAsString());
            appSharedpreference.setsharedpref("username", jsonObject.get("name").getAsString());
            appSharedpreference.setsharedpref("lname", jsonObject.get("lastname").getAsString());
            appSharedpreference.setsharedpref("emailid", jsonObject.get("email").getAsString());
            appSharedpreference.setsharedpref("mobile", jsonObject.get("mobile").getAsString());
            appSharedpreference.setsharedpref("dob", jsonObject.get("dob").getAsString());
            appSharedpreference.setsharedpref("country_id", jsonObject.get("country_id").getAsString());
            appSharedpreference.setsharedpref("state_id", jsonObject.get("state_id").getAsString());
            appSharedpreference.setsharedpref("city_id", jsonObject.get("city_id").getAsString());
            appSharedpreference.setsharedpref("address", jsonObject.get("address").getAsString());
            // appSharedpreference.setsharedpref("platform", jsonObject.get("platform").getAsString());
            appSharedpreference.setsharedpref("device_id", jsonObject.get("device_id").getAsString());
            appSharedpreference.setsharedpref("updated_at", jsonObject.get("updated_at").getAsString());
            appSharedpreference.setsharedpref("status", jsonObject.get("status").getAsString());
            appSharedpreference.setsharedpref("order", webservice_returndata.get("order").getAsString());
            appSharedpreference.setsharedpref("createdAt", webservice_returndata.get("createdAt").getAsString());


        } else if (userType == 3) {


            JsonObject jsonObject = webservice_returndata.getAsJsonObject("all_info");
            Log.e("hi", jsonObject.toString());
            appSharedpreference.setsharedpref("userid", webservice_returndata.get("user_id").getAsString());
            appSharedpreference.setsharedpref("business_id", webservice_returndata.get("user_id").getAsString());
            appSharedpreference.setsharedpref("name", jsonObject.get("name").getAsString());
            appSharedpreference.setsharedpref("username", jsonObject.get("name").getAsString());
            appSharedpreference.setsharedpref("lname", jsonObject.get("lastname").getAsString());
            appSharedpreference.setsharedpref("father_name", jsonObject.get("father_name").getAsString());
            appSharedpreference.setsharedpref("emailid", jsonObject.get("email").getAsString());
            appSharedpreference.setsharedpref("mobile", jsonObject.get("mobile").getAsString());
            appSharedpreference.setsharedpref("qualification", jsonObject.get("qualification").getAsString());
            appSharedpreference.setsharedpref("total_exp", jsonObject.get("total_exp").getAsString());
            appSharedpreference.setsharedpref("relevant_exp", jsonObject.get("relevant_exp").getAsString());
            appSharedpreference.setsharedpref("id_proof", jsonObject.get("id_proof").getAsString());
            appSharedpreference.setsharedpref("profile_pic", jsonObject.get("photo").getAsString());
            appSharedpreference.setsharedpref("country_id", jsonObject.get("country_id").getAsString());
            appSharedpreference.setsharedpref("state_id", jsonObject.get("state_id").getAsString());
            appSharedpreference.setsharedpref("city_id", jsonObject.get("city_id").getAsString());
            appSharedpreference.setsharedpref("dob", jsonObject.get("dob").getAsString());
            appSharedpreference.setsharedpref("type_emp", jsonObject.get("type_emp").getAsString());
            appSharedpreference.setsharedpref("address", jsonObject.get("address").getAsString());
            appSharedpreference.setsharedpref("by_ref", jsonObject.get("by_ref").getAsString());
            appSharedpreference.setsharedpref("ref_no", jsonObject.get("ref_no").getAsString());
            appSharedpreference.setsharedpref("pincode", jsonObject.get("pincode").getAsString());
            appSharedpreference.setsharedpref("platform", jsonObject.get("platform").getAsString());
            appSharedpreference.setsharedpref("device_id", jsonObject.get("device_id").getAsString());
            appSharedpreference.setsharedpref("term_accepted", jsonObject.get("term_accepted").getAsString());
            appSharedpreference.setsharedpref("created_at", "");
            appSharedpreference.setsharedpref("updated_at", "");
            appSharedpreference.setsharedpref("status", jsonObject.get("status").getAsString());
            appSharedpreference.setsharedpref("id", "");
            appSharedpreference.setsharedpref("bank_name", jsonObject.get("bank_name").getAsString());
            appSharedpreference.setsharedpref("account_no", jsonObject.get("account_no").getAsString());
            appSharedpreference.setsharedpref("branch_code", jsonObject.get("branch_code").getAsString());
            appSharedpreference.setsharedpref("branch_name", jsonObject.get("branch_name").getAsString());
            appSharedpreference.setsharedpref("ifsc_code", jsonObject.get("ifsc_code").getAsString());
            appSharedpreference.setsharedpref("micr_code", jsonObject.get("micr_code").getAsString());
            appSharedpreference.setsharedpref("account_holder", jsonObject.get("account_holder").getAsString());
            appSharedpreference.setsharedpref("register_mobile", jsonObject.get("register_mobile").getAsString());
            appSharedpreference.setsharedpref("vendor", webservice_returndata.get("vendor").getAsString());
            appSharedpreference.setsharedpref("network", webservice_returndata.get("network").getAsString());

        }
    }


    private void initView() {

        forgotPassword = (TextView) findViewById(R.id.tv_forgotpassword);
        loginText = (TextView) findViewById(R.id.tv_login);

        loginText.setText(user_login);

        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinator_layout);
        etEmail = (EditText) findViewById(R.id.etEmail);
        password = (EditText) findViewById(R.id.etpassword);
        relativeLayoutLogin = (RelativeLayout) findViewById(R.id.rl_login);
        relativeRegister = (RelativeLayout) findViewById(R.id.relativeRegister);
        forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent forgotpassword_activity = new Intent(context, ForgotPassword.class);
                forgotpassword_activity.putExtra("forgot_index", "0");
                startActivity(forgotpassword_activity);
            }
        });


    }

    public void showMessage(String message)
    {
       AndroidUtils.showSnackBar(coordinatorLayout, message);
    }


}
