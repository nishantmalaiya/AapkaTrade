package com.example.pat.aapkatrade.user_dashboard.my_profile;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.content.ContextCompat;
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
import android.widget.Toast;

import com.example.pat.aapkatrade.Home.HomeActivity;
import com.example.pat.aapkatrade.Home.registration.RegistrationActivity;
import com.example.pat.aapkatrade.Home.registration.entity.Country;
import com.example.pat.aapkatrade.R;
import com.example.pat.aapkatrade.categories_tab.CategoriesListAdapter;
import com.example.pat.aapkatrade.categories_tab.CategoriesListData;
import com.example.pat.aapkatrade.categories_tab.CategoryListActivity;
import com.example.pat.aapkatrade.general.AppSharedPreference;
import com.example.pat.aapkatrade.general.Utils.AndroidUtils;
import com.example.pat.aapkatrade.general.Validation;
import com.example.pat.aapkatrade.general.progressbar.ProgressBarHandler;
import com.example.pat.aapkatrade.general.recycleview_custom.MyRecyclerViewEffect;
import com.example.pat.aapkatrade.user_dashboard.addcompany.AddCompany;
import com.example.pat.aapkatrade.user_dashboard.companylist.CompanyList;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import java.util.Calendar;

import it.carlom.stikkyheader.core.StikkyHeaderBuilder;


public class MyProfileActivity extends AppCompatActivity implements TimePickerDialog.OnTimeSetListener, DatePickerDialog.OnDateSetListener
{

    Button btnSave, btnEdit, btnLogout;
    public static String shared_pref_name = "aapkatrade";
    AppSharedPreference app_sharedpreference;
    EditText etFName, etLName, etEmail, etMobileNo, etAddress;
   // ImageView imgCalender,backbutton;
    ProgressBarHandler p_handler;
    TextView tvDate, tvMyProfileDetailHeading;
    CollapsingToolbarLayout collapsingToolbarLayout;
    Toolbar toolbar;
    AppBarLayout aapbar_layout_myprofile;
    CoordinatorLayout coordinatorlayout_myprofile;
    private Context context;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        context =MyProfileActivity.this;
        setContentView(R.layout.activity_my_profile);

        app_sharedpreference = new AppSharedPreference(this);
        p_handler=new ProgressBarHandler(this);
        setUpToolBar();

        setup_layout();

    }

    private void setup_layout() {

        coordinatorlayout_myprofile=(CoordinatorLayout)findViewById(R.id.coordinate_myprofile) ;
        //setupnewlayout();
        //imgCalender = (ImageView) findViewById(R.id.imgCalender);
      //  backbutton=(ImageView)findViewById(R.id.back_imagview) ;
        tvMyProfileDetailHeading = (TextView) findViewById(R.id.tvMyProfileDetailHeading);

        etFName = (EditText) findViewById(R.id.etFName);
        String fname = app_sharedpreference.getsharedpref("name", "");



        tvMyProfileDetailHeading.setText("Hello, " + fname + " To Update your account information.");
        etFName.setText(fname);
        etFName.setSelection(etFName.getText().length());

        etLName = (EditText) findViewById(R.id.etLName);
        String lname = app_sharedpreference.getsharedpref("lname", "");
        etLName.setText(lname);
        etLName.setSelection(etLName.getText().length());

        etEmail = (EditText) findViewById(R.id.etEmail);
        String email = app_sharedpreference.getsharedpref("emailid", "");
        etEmail.setText(email);
        etEmail.setSelection(etEmail.getText().length());

        etMobileNo = (EditText) findViewById(R.id.etMobileNo);
        String mobile = app_sharedpreference.getsharedpref("mobile", "");
        etMobileNo.setText(mobile);
        etMobileNo.setSelection(etMobileNo.getText().length());

        etAddress = (EditText) findViewById(R.id.etAddress);
        String address = app_sharedpreference.getsharedpref("address", "");
        etAddress.setText(address);
        // etAddress.setSelection(etAddress.getText().length());

        tvDate = (TextView) findViewById(R.id.tvDate);
        String dob = app_sharedpreference.getsharedpref("dob", "");

        System.out.println("dob--------------" + dob);

        tvDate.setText(dob);

        etEmail.setKeyListener(null);

        etMobileNo.setKeyListener(null);

        btnSave = (Button) findViewById(R.id.btnSave);

        //  btnEdit = (Button) findViewById(R.id.btnEdit);

        //btnLogout = (Button) findViewById(R.id.btnlogout);
       /* backbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
*/
        /*   imgCalender.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

                Calendar now = Calendar.getInstance();
                DatePickerDialog dpd = DatePickerDialog.newInstance(
                        MyProfileActivity.this,
                        now.get(Calendar.YEAR),
                        now.get(Calendar.MONTH),
                        now.get(Calendar.DAY_OF_MONTH)
                );
                dpd.show(getFragmentManager(), "DatePickerDialog");
            }
         });*/


        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String et = etFName.getText().toString();

                System.out.println("et-----------" + et);

                if (!Validation.isEmptyStr(etFName.getText().toString())) {

                    if (!Validation.isEmptyStr(etEmail.getText().toString())) {

                        if (Validation.isValidEmail(etEmail.getText().toString())) {

                            if (!Validation.isEmptyStr(etMobileNo.getText().toString())) {

                                if (!Validation.isEmptyStr(etAddress.getText().toString())) {

                                    edit_profile_webservice();

                                } else {
                                    Toast.makeText(getApplicationContext(), "Please Enter Address", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(getApplicationContext(), "Please Enter Mobile Number", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(getApplicationContext(), "Please Enter Valid Email Address", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(getApplicationContext(), "Please Enter Email Address", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Please Enter First Name", Toast.LENGTH_SHORT).show();
                }

            }
        });


//        btnEdit.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                Intent i = new Intent(MyProfileActivity.this, AddCompany.class);
//                startActivity(i);
//
//            }
//        });

//        btnLogout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//Log.e("btnlogout","btnlogout");
//                save_shared_pref("notlogin", "notlogin", "notlogin", "", "", "", "", "", "", "", "", "");
//
//
//                Intent Homedashboard = new Intent(MyProfileActivity.this, HomeActivity.class);
//                Homedashboard.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                startActivity(Homedashboard);
//
//            }
//        });


    }

    private void setupnewlayout() {

        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);

        collapsingToolbarLayout.setTitle("Motorcycle");
        collapsingToolbarLayout.setExpandedTitleColor(getResources().getColor(R.color.transparent)); // transperent color = #00000000
        collapsingToolbarLayout.setCollapsedTitleTextColor(getResources().getColor(R.color.white));

//        StikkyHeaderBuilder.stickTo(mRecyclerView)
//                .setHeader(R.id.header_simple, view)
//                .minHeightHeaderDim(R.dimen.min_header_height)
//                .build();

    }

    private void edit_profile_webservice()
    {
        p_handler.show();

        Ion.with(MyProfileActivity.this)
                .load((getResources().getString(R.string.webservice_base_url))+"/my_account")
                .setHeader("authorization", "xvfdbgfdhbfdhtrh54654h54ygdgerwer3")
                .setBodyParameter("authorization", "xvfdbgfdhbfdhtrh54654h54ygdgerwer3")
                .setBodyParameter("name", etFName.getText().toString())
                .setBodyParameter("lastname", etLName.getText().toString())
                .setBodyParameter("mobile", etMobileNo.getText().toString())
                .setBodyParameter("email", etEmail.getText().toString())
                .setBodyParameter("address", etAddress.getText().toString())
                .setBodyParameter("user_id", app_sharedpreference.getsharedpref("userid", ""))
                .setBodyParameter("user_type", AndroidUtils.getUserType(app_sharedpreference.getsharedpref("usertype", "")))
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {


                        if (result == null)
                        {
                            p_handler.hide();
                            System.out.println("result-----------NULLLLLLL");


                        }
                        else
                        {
                            String error = result.get("error").getAsString();
                            if(error.contains("false"))
                            {

                                JsonObject jsonObject = result.getAsJsonObject();

                                String message = jsonObject.get("message").getAsString();

                                if(message.equals("No any changes to update!"))
                                {

                                    showmessage(message);
                                    p_handler.hide();

                                }
                                else
                                {

                                    JsonArray jsonResultArray = jsonObject.getAsJsonArray("result");

                                    for (int i = 0; i < jsonResultArray.size(); i++)
                                    {

                                        JsonObject jsonObject1 = (JsonObject) jsonResultArray.get(i);
                                        String update_name = jsonObject1.get("name").getAsString();
                                        String update_lastname = jsonObject1.get("lastname").getAsString();
                                        String update_mobile = jsonObject1.get("mobile").getAsString();
                                        String update_address = jsonObject1.get("address").getAsString();

                                        app_sharedpreference.setsharedpref("name",update_name);
                                        app_sharedpreference.setsharedpref("lname",update_lastname);
                                        app_sharedpreference.setsharedpref("mobile", update_mobile);
                                        app_sharedpreference.setsharedpref("address",update_address);

                                        System.out.println("Username Data-----------"+update_name);

                                        showmessage(message);
                                        p_handler.hide();

                                    }


                                }

                            }
                            else
                            {
                                JsonObject jsonObject = result.getAsJsonObject();
                                String message = jsonObject.get("message").getAsString();
                                showmessage(message);
                                p_handler.hide();

                            }

                        }
                    }
                });

    }

    private void showmessage(String message)
    {

        AndroidUtils.showSnackBar(coordinatorlayout_myprofile,message);

    }

    private void setUpToolBar() {
        ImageView homeIcon = (ImageView) findViewById(R.id.iconHome);
        ImageView back_imagview = (ImageView) findViewById(R.id.back_imagview);
        back_imagview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
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
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle(null);
            getSupportActionBar().setElevation(0);
        }



    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.bottom_home_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
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


    public void save_shared_pref(String user_id, String user_name, String email_id, String lname, String dob, String address, String mobile, String order_quantity, String product_quantity, String company_quantity, String vendor_quantity, String network_quantity)
    {

        app_sharedpreference.setsharedpref("userid", user_id);
        app_sharedpreference.setsharedpref("username", user_name);
        app_sharedpreference.setsharedpref("emailid", email_id);
        app_sharedpreference.setsharedpref("lname", lname);
        app_sharedpreference.setsharedpref("dob", dob);
        app_sharedpreference.setsharedpref("address", address);
        app_sharedpreference.setsharedpref("mobile", mobile);
        app_sharedpreference.setsharedpref("order_quantity", order_quantity);
        app_sharedpreference.setsharedpref("product_quantity", product_quantity);
        app_sharedpreference.setsharedpref("company_quantity", company_quantity);
        app_sharedpreference.setsharedpref("vendor_quantity", vendor_quantity);
        app_sharedpreference.setsharedpref("network_quantity", network_quantity);

    }


    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {

        showDate(year, monthOfYear + 1, dayOfMonth);

    }

    @Override
    public void onTimeSet(TimePickerDialog view, int hourOfDay, int minute, int second) {

    }

    private void showDate(int year, int month, int day) {

        tvDate.setTextColor(ContextCompat.getColor(MyProfileActivity.this, R.color.black));
        tvDate.setText(new StringBuilder().append(year).append("-").append(month).append("-").append(day));
    }


}
