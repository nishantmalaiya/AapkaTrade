package com.example.pat.aapkatrade.general;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.pat.aapkatrade.Home.HomeActivity;
import com.example.pat.aapkatrade.R;

public class ConnectivityNotFound extends AppCompatActivity {
    Button reConnectButton;
    private String callerActivity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connectivity_not_found);
        initView();
        if(getIntent()!=null){
            if(getIntent().getStringExtra("callerActivity")!=null){
                callerActivity = getIntent().getStringExtra("callerActivity");
            }
        }
        reConnectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ConnetivityCheck.isNetworkAvailable(ConnectivityNotFound.this)){
//                    startActivity(new Intent(ConnectivityNotFound.this, HomeActivity.class));
                    try {
                        startActivity(new Intent(ConnectivityNotFound.this, Class.forName(callerActivity)));
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                } else {
                    Intent intent=new Intent(Settings.ACTION_SETTINGS);
                    startActivity(intent);
                }
            }
        });
    }

    private void initView() {
        reConnectButton = (Button) findViewById(R.id.reConnectButton);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
