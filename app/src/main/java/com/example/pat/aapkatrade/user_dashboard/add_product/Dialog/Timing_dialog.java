package com.example.pat.aapkatrade.user_dashboard.add_product.Dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;

import com.example.pat.aapkatrade.Home.HomeActivity;
import com.example.pat.aapkatrade.MainActivity;
import com.example.pat.aapkatrade.R;
import com.example.pat.aapkatrade.general.ConnectivityNotFound;
import com.example.pat.aapkatrade.general.ConnetivityCheck;
import com.example.pat.aapkatrade.general.Utils.AndroidUtils;
import com.example.pat.aapkatrade.general.progressbar.ProgressBarHandler;
import com.example.pat.aapkatrade.user_dashboard.add_product.SpinnerAdapter;

import java.util.ArrayList;

/**
 * Created by PPC17 on 07-Apr-17.
 */

public class Timing_dialog extends AppCompatActivity {

    ArrayList<String> opening_time_list;
    ArrayList<String> closing_time_list;
    Context context;
    ProgressBarHandler progressbar;
    private final int SPLASH_DISPLAY_LENGTH = 3000;
//    public Timing_dialog( Context context, ArrayList<String> opening_time_list, ArrayList<String> closing_time_list) {
//
//        this.context=context;
//        this.opening_time_list=opening_time_list;
//        this.closing_time_list=closing_time_list;
//        progressbar=new ProgressBarHandler(context);
//        progressbar.show();
//    }
View view_monday,view_tuesday,view_wednesday,view_thursday,view_friday,view_saturday,view_sunday;


    Spinner  spin_monday_open,spin_monday_close,spin_tuesday_open,spin_tuesday_close,spin_wednesday_open,spin_wednesday_close,
            spin_thrusday_open,spin_thrusday_close,spin_friday_open,spin_friday_close,spin_saturday_open,spin_saturday_close,
            spin_sunday_open,spin_sunday_close;
    SpinnerAdapter spinnerAdapter;
    ImageView dialog_close;

String demo[]={"Opening","Closing"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_timing);
        opening_time_list=getIntent().getStringArrayListExtra("opening_time_list");
        closing_time_list=getIntent().getStringArrayListExtra("closing_time_list");
        context=this;
        progressbar=new ProgressBarHandler(context);
        progressbar.show();




        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                initView();

                /* Create an Intent that will start the Menu-Activity. */

            }
        }, SPLASH_DISPLAY_LENGTH);







    }

    private void initView() {


        dialog_close=(ImageView)findViewById(R.id.dialog_close);


        view_monday=(View)findViewById(R.id.view_monday);
        view_tuesday=(View)findViewById(R.id.view_tuesday);

        view_wednesday=(View)findViewById(R.id.view_wednesday);
        view_thursday=(View)findViewById(R.id.view_thursday);

        view_friday=(View)findViewById(R.id.view_friday);
        view_saturday=(View)findViewById(R.id.view_saturday);
        view_sunday=(View)findViewById(R.id.view_sunday);


        apply_circle_color();

        //initialize spinner open close
        spin_monday_open=(Spinner)findViewById(R.id.spinner_monday_open);
        spin_monday_close=(Spinner)findViewById(R.id.spinner_monday_close);
        spin_tuesday_open=(Spinner)findViewById(R.id.spinner_tuesday_open);
        spin_tuesday_close=(Spinner)findViewById(R.id.spinner_tuesday_close);
        spin_wednesday_open=(Spinner)findViewById(R.id.spinner_wednesday_open);
        spin_wednesday_close=(Spinner)findViewById(R.id.spinner_wednesday_close);
        spin_thrusday_open=(Spinner)findViewById(R.id.spinner_thursday_open);
        spin_thrusday_close=(Spinner)findViewById(R.id.spinner_thursday_close);
        spin_friday_open=(Spinner)findViewById(R.id.spinner_friday_open);
        spin_friday_close=(Spinner)findViewById(R.id.spinner_friday_close);
        spin_saturday_open=(Spinner)findViewById(R.id.spinner_saturday_open);
        spin_saturday_close=(Spinner)findViewById(R.id.spinner_saturday_close);
        spin_sunday_open=(Spinner)findViewById(R.id.spinner_sunday_open);
        spin_sunday_close=(Spinner)findViewById(R.id.spinner_sunday_close);

        apply_spinner_background();


       // set_spinner_adapter
        spinnerAdapter=new SpinnerAdapter(context,demo);
        Spinner_adapter();

        dialog_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               finish();
            }
        });



    }

    private void Spinner_adapter() {
        ArrayAdapter<String> open_close_spinner_adapter = new ArrayAdapter<String>(context,R.layout.spinner_dialog_layout, opening_time_list);
        open_close_spinner_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin_monday_open.setAdapter(open_close_spinner_adapter);
        ArrayAdapter<String> close_spinner_adapter = new ArrayAdapter<String>(context,R.layout.spinner_dialog_layout, closing_time_list);
        close_spinner_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin_monday_close.setAdapter(close_spinner_adapter);



        spin_tuesday_open.setAdapter(open_close_spinner_adapter);
        spin_tuesday_close.setAdapter(close_spinner_adapter);
        spin_wednesday_open.setAdapter(open_close_spinner_adapter);
        spin_wednesday_close.setAdapter(close_spinner_adapter);
        spin_thrusday_open.setAdapter(open_close_spinner_adapter);
        spin_thrusday_close.setAdapter(close_spinner_adapter);
        spin_friday_open.setAdapter(open_close_spinner_adapter);
        spin_friday_close.setAdapter(close_spinner_adapter);
        spin_saturday_open.setAdapter(open_close_spinner_adapter);
        spin_saturday_close.setAdapter(close_spinner_adapter);
        spin_sunday_open.setAdapter(open_close_spinner_adapter);
        spin_sunday_close.setAdapter(close_spinner_adapter);
        progressbar.hide();
    }

    private void apply_spinner_background() {

        AndroidUtils.setBackgroundStroke(spin_monday_open,context,R.color.gradient_dialog1,5,2,android.R.color.transparent);
        AndroidUtils.setBackgroundStroke(spin_monday_close,context,R.color.gradient_dialog1,5,2,android.R.color.transparent);
        AndroidUtils.setBackgroundStroke(spin_tuesday_open,context,R.color.gradient_dialog2,5,2,android.R.color.transparent);
        AndroidUtils.setBackgroundStroke(spin_tuesday_close,context,R.color.gradient_dialog1,5,2,android.R.color.transparent);
        AndroidUtils.setBackgroundStroke(spin_wednesday_open,context,R.color.gradient_dialog3,5,2,android.R.color.transparent);
        AndroidUtils.setBackgroundStroke(spin_wednesday_close,context,R.color.gradient_dialog3,5,2,android.R.color.transparent);
        AndroidUtils.setBackgroundStroke(spin_thrusday_open,context,R.color.gradient_dialog4,5,2,android.R.color.transparent);
        AndroidUtils.setBackgroundStroke(spin_thrusday_close,context,R.color.gradient_dialog4,5,2,android.R.color.transparent);
        AndroidUtils.setBackgroundStroke(spin_friday_open,context,R.color.gradient_dialog5,5,2,android.R.color.transparent);
        AndroidUtils.setBackgroundStroke(spin_friday_close,context,R.color.gradient_dialog5,5,2,android.R.color.transparent);
        AndroidUtils.setBackgroundStroke(spin_saturday_open,context,R.color.gradient_dialog6,5,2,android.R.color.transparent);
        AndroidUtils.setBackgroundStroke(spin_saturday_close,context,R.color.gradient_dialog6,5,2,android.R.color.transparent);
        AndroidUtils.setBackgroundStroke(spin_sunday_open,context,R.color.gradient_dialog7,5,2,android.R.color.transparent);
        AndroidUtils.setBackgroundStroke(spin_sunday_close,context,R.color.gradient_dialog7,5,2,android.R.color.transparent);



    }

    private void apply_circle_color() {


        AndroidUtils.setBackgroundSolid(view_monday,context, R.color.gradient_dialog1, 1, GradientDrawable.OVAL,2,R.color.stroke_bg);
        AndroidUtils.setBackgroundSolid(view_tuesday,context, R.color.gradient_dialog2, 1, GradientDrawable.OVAL,2,R.color.stroke_bg);

        AndroidUtils.setBackgroundSolid(view_wednesday,context, R.color.gradient_dialog3, 1, GradientDrawable.OVAL,2,R.color.stroke_bg);
        AndroidUtils.setBackgroundSolid(view_thursday,context, R.color.gradient_dialog4, 1, GradientDrawable.OVAL,2,R.color.stroke_bg);
        AndroidUtils.setBackgroundSolid(view_friday,context, R.color.gradient_dialog5, 1, GradientDrawable.OVAL,2,R.color.stroke_bg);
        AndroidUtils.setBackgroundSolid(view_saturday,context, R.color.gradient_dialog6, 1, GradientDrawable.OVAL,2,R.color.stroke_bg);
        AndroidUtils.setBackgroundSolid(view_sunday,context, R.color.gradient_dialog7, 1, GradientDrawable.OVAL,2,R.color.stroke_bg);

    }
}
