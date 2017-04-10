package com.example.pat.aapkatrade.user_dashboard.add_product.Dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.pat.aapkatrade.R;
import com.example.pat.aapkatrade.general.Utils.AndroidUtils;
import com.example.pat.aapkatrade.user_dashboard.add_product.SpinnerAdapter;

import java.util.ArrayList;

/**
 * Created by PPC17 on 07-Apr-17.
 */

public class Timing_dialog extends Dialog {


    Context context;
    public Timing_dialog( Context context, ArrayList<String> opening_time_list, ArrayList<String> closing_time_list) {
        super(context);
        this.context=context;
    }
View view_monday,view_tuesday,view_wednesday,view_thursday,view_friday,view_saturday,view_sunday;


    Spinner  spin_monday_open,spin_monday_close,spin_tuesday_open,spin_tuesday_close,spin_wednesday_open,spin_wednesday_close,
            spin_thrusday_open,spin_thrusday_close,spin_friday_open,spin_friday_close,spin_saturday_open,spin_saturday_close,
            spin_sunday_open,spin_sunday_close;
    SpinnerAdapter spinnerAdapter;

String demo[]={"Opening","Closing"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getWindow() != null)
            getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        setContentView(R.layout.dialog_timing);


        initView();



    }

    private void initView() {

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

    }

    private void Spinner_adapter() {
        ArrayAdapter<String> open_close_spinner_adapter = new ArrayAdapter<String>(context,R.layout.spinner_dialog_layout, demo);
        open_close_spinner_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin_monday_open.setAdapter(open_close_spinner_adapter);


        spin_monday_open.setAdapter(open_close_spinner_adapter);
        spin_monday_close.setAdapter(open_close_spinner_adapter);
        spin_tuesday_open.setAdapter(open_close_spinner_adapter);
        spin_tuesday_close.setAdapter(open_close_spinner_adapter);
        spin_wednesday_open.setAdapter(open_close_spinner_adapter);
        spin_wednesday_close.setAdapter(open_close_spinner_adapter);
        spin_thrusday_open.setAdapter(open_close_spinner_adapter);
        spin_thrusday_close.setAdapter(open_close_spinner_adapter);
        spin_friday_open.setAdapter(open_close_spinner_adapter);
        spin_friday_close.setAdapter(open_close_spinner_adapter);
        spin_saturday_open.setAdapter(open_close_spinner_adapter);
        spin_saturday_close.setAdapter(open_close_spinner_adapter);
        spin_sunday_open.setAdapter(open_close_spinner_adapter);
        spin_sunday_close.setAdapter(open_close_spinner_adapter);

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
