package com.example.pat.aapkatrade.user_dashboard.my_profile;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.pat.aapkatrade.Home.HomeActivity;
import com.example.pat.aapkatrade.R;
import com.example.pat.aapkatrade.general.AppSharedPreference;
import com.example.pat.aapkatrade.user_dashboard.address.add_address.AddAddressActivity;
import com.example.pat.aapkatrade.user_dashboard.changepassword.ChangePassword;

public class ProfilePreviewActivity extends AppCompatActivity
{

    TextView tvTitle,textViewName,tvMobile,tvEmail,tvUserType;
    LinearLayout linearLayoutLagout,linearLayoutResetpassword,linearLayoutAddress;
    AppSharedPreference app_sharedpreference;
    ImageView btnEdit;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_profile_preview);

        app_sharedpreference = new AppSharedPreference(this);

        setup_layout();

        setuptoolbar();

    }

    private void setup_layout()
    {
        btnEdit = (ImageView) findViewById(R.id.btnEdit);

        linearLayoutLagout = (LinearLayout) findViewById(R.id.linearLayoutLagout);

        linearLayoutResetpassword = (LinearLayout) findViewById(R.id.linearLayoutResetpassword);

        linearLayoutAddress = (LinearLayout) findViewById(R.id.linearLayoutAddress);


        btnEdit.setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick(View v)
            {
                  Intent profile_edit = new Intent(ProfilePreviewActivity.this, MyProfileActivity.class);
                  startActivity(profile_edit);
            }


        });

        linearLayoutLagout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                save_shared_pref("notlogin", "notlogin", "notlogin");
                Intent Homedashboard = new Intent(ProfilePreviewActivity.this, HomeActivity.class);
                Homedashboard.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(Homedashboard);


            }
        });


        linearLayoutResetpassword.setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick(View v) {
                Intent change_password = new Intent(ProfilePreviewActivity.this, ChangePassword.class);
                startActivity(change_password);
            }
        });

        linearLayoutAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent address = new Intent(ProfilePreviewActivity.this, AddAddressActivity.class);
                startActivity(address);
            }
        });

        textViewName = (TextView) findViewById(R.id.textViewName);

        tvMobile = (TextView) findViewById(R.id.tvMobile);

        tvEmail = (TextView) findViewById(R.id.tvEmail);
        tvUserType = (TextView) findViewById(R.id.tvUserType);

        if (app_sharedpreference.getsharedpref("username", "notlogin") != null)
        {
            String Username = app_sharedpreference.getsharedpref("name", "not");
            String Emailid = app_sharedpreference.getsharedpref("emailid", "not");

            String userType = app_sharedpreference.getsharedpref("usertype","0");

            System.out.println("UserName-----------"+Username);

            textViewName.setText(Username);
            tvEmail.setText(Emailid);

            if (userType.equals("1"))
            {
                tvUserType.setText("Welcome Seller");

            }
            else if (userType.equals("2"))
            {
                tvUserType.setText("Welcome Buyer");

            }
            else if (userType.equals("3"))
            {
                tvUserType.setText("Welcome Bussiness Associate");

            }
        }

    }


    private void setuptoolbar()
    {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if(getSupportActionBar()!=null) {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle(null);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.empty_menu, menu);
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


    public void save_shared_pref(String user_id, String user_name, String email_id)
    {
        app_sharedpreference.setsharedpref("userid", user_id);
        app_sharedpreference.setsharedpref("username", user_name);
        app_sharedpreference.setsharedpref("emailid", email_id);

    }
}
