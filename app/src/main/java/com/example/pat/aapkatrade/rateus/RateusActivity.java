package com.example.pat.aapkatrade.rateus;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.pat.aapkatrade.R;


public class RateusActivity extends AppCompatActivity
{

    Button butttonExperience;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_rateus);


        butttonExperience = (Button) findViewById(R.id.butttonExperience);

        butttonExperience.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

            }
        });


    }





}
