package com.example.pat.aapkatrade.user_dashboard.addcompany;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pat.aapkatrade.Home.HomeActivity;
import com.example.pat.aapkatrade.R;
import com.example.pat.aapkatrade.general.AppSharedPreference;
import com.example.pat.aapkatrade.general.ConnetivityCheck;
import com.example.pat.aapkatrade.general.Utils.AndroidUtils;
import com.example.pat.aapkatrade.general.Validation;
import com.example.pat.aapkatrade.general.progressbar.ProgressBarHandler;
import com.example.pat.aapkatrade.user_dashboard.companylist.CompanyList;
import com.example.pat.aapkatrade.user_dashboard.editcompany.EditCompanyActivity;
import com.example.pat.aapkatrade.user_dashboard.my_profile.ProfilePreviewActivity;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;


public class AddCompany extends AppCompatActivity
{

    Button btnSave;
    EditText etCompanyName,etPEmail,etSEmail,etAddress,etDiscription;
    ProgressDialog dialog;
    LinearLayout linearLayout;
    Snackbar snackbar;
    ProgressBarHandler progress_handler;
    AppSharedPreference app_sharedpreference;
    String user_id,email;
    TextView tvTitle;
    RelativeLayout relativeSubmit;
    private Context context;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_add_company);
        context = AddCompany.this;
        app_sharedpreference = new AppSharedPreference(getApplicationContext());
        user_id = app_sharedpreference.getsharedpref("userid", "");
        email = app_sharedpreference.getsharedpref("emailid", "");
        progress_handler = new ProgressBarHandler(this);
        setUpToolBar();
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        initView();
    }

    private void addCompany()
    {

        if(!etCompanyName.getText().toString().equals(""))
        {

            if(!etSEmail.getText().toString().equals(""))
            {

                if(Validation.isValidEmail(etSEmail.getText().toString()))
                {

                    if(!etAddress.getText().toString().equals(""))
                    {

                        if(!etDiscription.getText().toString().equals(""))
                        {

                            if(ConnetivityCheck.isNetworkAvailable(AddCompany.this))
                            {
                                dialog=new ProgressDialog(AddCompany.this);
                                dialog.setMessage("Loading...\nPlease Wait");
                                dialog.setCancelable(false);
                                dialog.setInverseBackgroundForced(false);
                                dialog.show();
                                callAddCompanyWebService(user_id, etCompanyName.getText().toString(),etPEmail.getText().toString(),etSEmail.getText().toString() ,etAddress.getText().toString(),etDiscription.getText().toString());
                                dialog.hide();
                            }
                            else
                            {
                                linearLayout.setVisibility(View.VISIBLE);
                                snackbar = Snackbar.make(linearLayout, "Please Check Your Network Connection", Snackbar.LENGTH_LONG);
                                snackbar.setActionTextColor(getResources().getColor(android.R.color.holo_red_dark));
                                snackbar.setAction("Retry", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        addCompany();
                                    }
                                });
                                snackbar.show();
                            }

                        }
                        else
                        {
                            linearLayout.setVisibility(View.VISIBLE);
                            snackbar = Snackbar.make(linearLayout, "Discription is Empty", Snackbar.LENGTH_LONG);
                            snackbar.setActionTextColor(getResources().getColor(android.R.color.holo_red_dark));
                            snackbar.show();

                        }

                    }
                    else
                    {

                        linearLayout.setVisibility(View.VISIBLE);
                        snackbar = Snackbar.make(linearLayout, "Address is Empty", Snackbar.LENGTH_LONG);
                        snackbar.setActionTextColor(getResources().getColor(android.R.color.holo_red_dark));
                        snackbar.show();
                    }

                }
                else
                {
                    linearLayout.setVisibility(View.VISIBLE);
                    snackbar = Snackbar.make(linearLayout, "Please Enter Valid Email", Snackbar.LENGTH_LONG);
                    snackbar.setActionTextColor(getResources().getColor(android.R.color.holo_red_dark));
                    snackbar.show();
                }

            }
            else
            {

                linearLayout.setVisibility(View.VISIBLE);
                snackbar = Snackbar.make(linearLayout, "Please Enter Secondary Email", Snackbar.LENGTH_LONG);
                snackbar.setActionTextColor(getResources().getColor(android.R.color.holo_red_dark));
                snackbar.show();
            }


        }
        else
        {
            linearLayout.setVisibility(View.VISIBLE);
            snackbar = Snackbar.make(linearLayout, "Please Enter Company Name", Snackbar.LENGTH_LONG);
            snackbar.setActionTextColor(getResources().getColor(android.R.color.holo_red_dark));
            snackbar.show();


        }


    }

    private void callAddCompanyWebService(String userId, final String companyName, String pEmail , String sEmail, String address, String description)
    {

        progress_handler.show();

        Ion.with(AddCompany.this)
                .load(getResources().getString(R.string.webservice_base_url)+"/addCompany")
                .setHeader("authorization", "xvfdbgfdhbfdhtrh54654h54ygdgerwer3")
                .setBodyParameter("authorization", "xvfdbgfdhbfdhtrh54654h54ygdgerwer3")
                .setBodyParameter("user_id", userId)
                .setBodyParameter("company_name", companyName)
                .setBodyParameter("secondaryEmail",sEmail)
                .setBodyParameter("address", address)
                .setBodyParameter("description", description)
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>()
                {


                    @Override
                    public void onCompleted(Exception e, JsonObject result)
                    {

                        if (result == null)
                        {

                            progress_handler.hide();

                        }
                        else
                        {
                            JsonObject jsonObject = result.getAsJsonObject();
                            String message = jsonObject.get("message").getAsString();
                            Log.e("message", message);

                            if (message.equals("You company successfully added"))
                            {

                                progress_handler.hide();
                                etCompanyName.setText("");

                                etSEmail.setText("");
                                etAddress.setText("");
                                etDiscription.setText("");
                                Toast.makeText(getApplicationContext(),"Company Registerted",Toast.LENGTH_SHORT).show();

                                Intent companylist = new Intent(AddCompany.this, CompanyList.class);
                                startActivity(companylist);
                                finish();


                            }
                            else
                            {
                                progress_handler.hide();
                                Toast.makeText(getApplicationContext(),message,Toast.LENGTH_SHORT).show();
                            }

                            /*  snackbar.make(linearLayout, message, Snackbar.LENGTH_SHORT).show();
                            Intent Homedashboard = new Intent(AddCompany.this, HomeActivity.class);
                            Homedashboard.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(Homedashboard);*/



                        }
                    }
                });
    }

    private void initView()
    {
        relativeSubmit = (RelativeLayout) findViewById(R.id.relativeSubmit);

        etCompanyName = (EditText) findViewById(R.id.etCompanyName);

        etPEmail = (EditText) findViewById(R.id.etPEmail);
        etPEmail.setText(email);

        etSEmail= (EditText) findViewById(R.id.etSEmail);

        etAddress = (EditText) findViewById(R.id.etAddress);

        etDiscription = (EditText) findViewById(R.id.etDiscription);

        relativeSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                addCompany();
            }
        });

        linearLayout = (LinearLayout) findViewById(R.id.snakBar);
    }


    private void setUpToolBar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        AppCompatImageView back_imagview = (AppCompatImageView) findViewById(R.id.back_imagview);
        back_imagview.setVisibility(View.VISIBLE);
        back_imagview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        ImageView homeIcon = (ImageView) findViewById(R.id.iconHome);
        ImageView logoWord = (ImageView) findViewById(R.id.logoWord);
        TextView header_name = (TextView) findViewById(R.id.header_name);
        header_name.setVisibility(View.VISIBLE);
        header_name.setText(getResources().getString(R.string.add_company_heading));
        logoWord.setVisibility(View.GONE);
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


}
