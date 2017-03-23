package com.example.pat.aapkatrade.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.FrameLayout;

import com.example.pat.aapkatrade.R;
import com.example.pat.aapkatrade.general.AppSharedPreference;
import com.example.pat.aapkatrade.user_dashboard.User_DashboardFragment;


/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class LoginDashboard extends AppCompatActivity {
    FrameLayout fl_seller, fl_buyer, fl_business_assoc;
    AppSharedPreference app_sharedpreference;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_logindashboard);
        app_sharedpreference=new AppSharedPreference(this);
        Initview();

        fl_seller.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(app_sharedpreference.shared_pref!= null)
                    app_sharedpreference.setsharedpref("usertype","1");

                Intent i = new Intent(LoginDashboard.this, LoginActivity.class);
                startActivity(i);
                overridePendingTransition(R.anim.left_to_right, R.anim.right_to_left);
            }
        });

        fl_buyer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(app_sharedpreference.shared_pref!= null)
                    app_sharedpreference.setsharedpref("usertype","2");
                Intent i = new Intent(LoginDashboard.this, LoginActivity.class);
                startActivity(i);
                overridePendingTransition(R.anim.left_to_right, R.anim.right_to_left);
            }
        });


        fl_business_assoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(app_sharedpreference.shared_pref!= null)
                    app_sharedpreference.setsharedpref("usertype","3");
                Intent i = new Intent(LoginDashboard.this, LoginActivity.class);
                startActivity(i);



                overridePendingTransition(R.anim.left_to_right, R.anim.right_to_left);
            }
        });


    }

    private void Initview() {

        fl_seller = (FrameLayout) findViewById(R.id.fl_seller);
        fl_buyer = (FrameLayout) findViewById(R.id.fl_buyer);
        fl_business_assoc = (FrameLayout) findViewById(R.id.fl_business_assoc);
        fl_seller = (FrameLayout) findViewById(R.id.fl_seller);

        fl_seller.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(LoginDashboard.this, User_DashboardFragment.class);


                startActivity(i);
            }
        });


    }


}
