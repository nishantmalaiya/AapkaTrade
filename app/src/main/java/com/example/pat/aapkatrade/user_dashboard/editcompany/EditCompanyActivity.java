package com.example.pat.aapkatrade.user_dashboard.editcompany;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.example.pat.aapkatrade.R;
import com.example.pat.aapkatrade.general.AppSharedPreference;
import com.example.pat.aapkatrade.general.ConnetivityCheck;
import com.example.pat.aapkatrade.general.Validation;
import com.example.pat.aapkatrade.general.progressbar.ProgressBarHandler;
import com.example.pat.aapkatrade.user_dashboard.addcompany.AddCompany;
import com.example.pat.aapkatrade.user_dashboard.companylist.CompanyList;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;



public class EditCompanyActivity extends AppCompatActivity
{

    String company_id,company_name,creation_date, address,description,secondaryEmail;

    EditText etCompanyName,etPEmail,etSEmail,etAddress,etDiscription;
    ProgressDialog dialog;
    LinearLayout linearLayout;
    Snackbar snackbar;
    ProgressBarHandler progress_handler;
    AppSharedPreference app_sharedpreference;
    String user_id,email;
    RelativeLayout relativeSubmit;
    TextView tvTitle;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_edit_company);

        Intent intent = getIntent();

        company_id = intent.getStringExtra("company_id");

        company_name = intent.getStringExtra("company_name");

        creation_date = intent.getStringExtra("company_creation_date");

        address  = intent.getStringExtra("address");

        description = intent.getStringExtra("description");

        secondaryEmail = intent.getStringExtra("secondaryEmail");

        app_sharedpreference = new AppSharedPreference(getApplicationContext());

        user_id = app_sharedpreference.getsharedpref("userid", "");

        email = app_sharedpreference.getsharedpref("emailid", "");

        progress_handler = new ProgressBarHandler(this);

        setuptoolbar();

        setup_layout();

    }


    private void setuptoolbar()
    {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        getSupportActionBar().setTitle(null);

        // getSupportActionBar().setIcon(R.drawable.home_logo);

        tvTitle = (TextView) findViewById(R.id.tvTitle);
        tvTitle.setText("Edit Company");


    }

    private void setup_layout()
    {

        relativeSubmit = (RelativeLayout) findViewById(R.id.relativeSubmit);

        etCompanyName = (EditText) findViewById(R.id.etCompanyName);
        etCompanyName.setText(company_name);


        etPEmail = (EditText) findViewById(R.id.etPEmail);
        etPEmail.setText(email);


        etSEmail= (EditText) findViewById(R.id.etSEmail);
        etSEmail.setText(secondaryEmail);

        etAddress = (EditText) findViewById(R.id.etAddress);
        etAddress.setText(address);

        etDiscription = (EditText) findViewById(R.id.etDiscription);
        etDiscription.setText(description);

        relativeSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                addCompany();
            }
        });

        linearLayout = (LinearLayout) findViewById(R.id.snakBar);



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

                            if(ConnetivityCheck.isNetworkAvailable(EditCompanyActivity.this))
                            {
                                dialog=new ProgressDialog(EditCompanyActivity.this);
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

        Ion.with(EditCompanyActivity.this)
                .load((getResources().getString(R.string.webservice_base_url))+"/editCompany")
                .setHeader("authorization", "xvfdbgfdhbfdhtrh54654h54ygdgerwer3")
                .setBodyParameter("authorization", "xvfdbgfdhbfdhtrh54654h54ygdgerwer3")
                .setBodyParameter("company_name", companyName)
                .setBodyParameter("secondaryEmail",sEmail)
                .setBodyParameter("address", address)
                .setBodyParameter("description", description)
                .setBodyParameter("id", company_id)
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>()
                {


                    @Override
                    public void onCompleted(Exception e, JsonObject result)
                    {

                        if (result == null){

                            progress_handler.hide();

                        }
                        else
                            {
                            JsonObject jsonObject = result.getAsJsonObject();
                            String message = jsonObject.get("message").getAsString();

                                System.out.println("message---------"+message);


                                if (message.equals("You are successfully updated"))
                                {

                                    progress_handler.hide();
                                    etCompanyName.setText("");

                                    etSEmail.setText("");
                                    etAddress.setText("");
                                    etDiscription.setText("");

                                    Toast.makeText(getApplicationContext(),"Company details has been sucessfully updated",Toast.LENGTH_SHORT).show();

                                    Intent companylist = new Intent(EditCompanyActivity.this, CompanyList.class);
                                    startActivity(companylist);
                                    finish();


                                }
                                else
                                {
                                    progress_handler.hide();
                                    Toast.makeText(getApplicationContext(),message,Toast.LENGTH_SHORT).show();
                                }

                               /*snackbar.make(linearLayout, message, Snackbar.LENGTH_SHORT).show();
                                 Intent Homedashboard = new Intent(AddCompany.this, HomeActivity.class);
                                Homedashboard.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(Homedashboard);*/

                        }
                    }
                });
    }







}
