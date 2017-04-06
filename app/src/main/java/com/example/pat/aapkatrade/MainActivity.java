package com.example.pat.aapkatrade;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.pat.aapkatrade.Home.HomeActivity;
import com.example.pat.aapkatrade.general.ConnectivityNotFound;
import com.example.pat.aapkatrade.general.ConnetivityCheck;
import com.example.pat.aapkatrade.general.animation_effects.App_animation;
import com.example.pat.aapkatrade.general.progressbar.Custom_progress_bar;

public class MainActivity extends AppCompatActivity {

    private final int SPLASH_DISPLAY_LENGTH = 3000;
    ConnetivityCheck connetivity_check;
    TextView tv_aapkatrade;
    ImageView left_image, right_image;

    ProgressDialog pd;
    ImageView circle_image;
    Custom_progress_bar custom_progress_bar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        circle_image = (ImageView) findViewById(R.id.circle_image);

        App_animation.circularanimation(circle_image);


        String fontPath = "impact.ttf";

        // text view label
        tv_aapkatrade = (TextView) findViewById(R.id.tv_aapkatrade);
        left_image = (ImageView) findViewById(R.id.left_panel);
        right_image = (ImageView) findViewById(R.id.right_panel);


        //Animation
        App_animation.left_animation(left_image, this);
        App_animation.right_animation(right_image, this);


        // Loading Font Face
        Typeface tf = Typeface.createFromAsset(getAssets(), fontPath);

        // Applying font
        tv_aapkatrade.setTypeface(tf);
        connetivity_check = new ConnetivityCheck();
        custom_progress_bar=new Custom_progress_bar(MainActivity.this);
        custom_progress_bar.show();


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                if (ConnetivityCheck.isNetworkAvailable(MainActivity.this)) {

                    Intent mainIntent = new Intent(MainActivity.this, HomeActivity.class);
                    startActivity(mainIntent);
                    finish();
                    if (pd != null) {
                        pd.dismiss();
                    }
                } else {
                    Intent mainIntent = new Intent(MainActivity.this, ConnectivityNotFound.class);
                    mainIntent.putExtra("callerActivity", MainActivity.class.getName());
                    startActivity(mainIntent);
                    finish();
                    if (pd != null) {
                        pd.dismiss();
                    }
                }
                /* Create an Intent that will start the Menu-Activity. */

            }
        }, SPLASH_DISPLAY_LENGTH);
    }


}
