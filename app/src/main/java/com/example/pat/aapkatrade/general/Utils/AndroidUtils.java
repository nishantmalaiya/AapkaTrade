package com.example.pat.aapkatrade.general.Utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.pat.aapkatrade.Home.registration.entity.State;
import com.example.pat.aapkatrade.Home.registration.spinner_adapter.SpStateAdapter;
import com.example.pat.aapkatrade.R;
import com.example.pat.aapkatrade.general.Call_webservice;
import com.example.pat.aapkatrade.general.TaskCompleteReminder;
import com.example.pat.aapkatrade.general.Validation;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.internal.Streams;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

/**
 * Created by PPC09 on 03-Feb-17.
 */

public class AndroidUtils {

    public static String BaseUrl = "http://staging.aapkatrade.com";

    public static void showSnackBar(ViewGroup layout, String message) {
        if (layout != null) {
            Snackbar snackbar = Snackbar
                    .make(layout, message, Snackbar.LENGTH_SHORT)
                    .setAction("", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                        }
                    });
            snackbar.show();
        }
    }


    public static String getEditTextData(EditText et) {
        if (Validation.validateEdittext(et)) {
            return et.getText().toString();
        }
        return "";
    }

    public static String getUserType(String user_type) {
        if (user_type.equals("1")) {
            return "2";
        } else if (user_type.equals("2")) {
            return "1";
        }
        return user_type;
    }

    public static Calendar stringToCalender(String date_yyyy_mm_dd) {
        int day = Integer.parseInt(date_yyyy_mm_dd.split("-")[2]);
        int month = Integer.parseInt(date_yyyy_mm_dd.split("-")[1]);
        int year = Integer.parseInt(date_yyyy_mm_dd.split("-")[0]);
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month - 1, day);
        return calendar;
    }

    public static void setBackgroundSolid(View layout, Context context, int bgColor, int cornerRadius) {
        GradientDrawable shape = new GradientDrawable();
        shape.setCornerRadius(cornerRadius);
        shape.setColor(ContextCompat.getColor(context, bgColor));
        layout.setBackground(shape);
    }


    public static void setBackgroundStroke(View layout, Context context, int bgColor, int cornerRadius, int strokeWidth) {
        GradientDrawable shape = new GradientDrawable();
        shape.setStroke(strokeWidth, ContextCompat.getColor(context, bgColor));
        shape.setCornerRadius(cornerRadius);
        layout.setBackground(shape);
    }

    public static void setImageColor(ImageView imageView, Context context, int color) {
        if (imageView != null)
            imageView.setColorFilter(ContextCompat.getColor(context, color));
    }

    public static int screenHeight(Context ctx) {
        DisplayMetrics displaymetrics = new DisplayMetrics();
        ((Activity) ctx).getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        return displaymetrics.heightPixels;
    }

    public static int screenWidth(Context ctx) {
        DisplayMetrics displaymetrics = new DisplayMetrics();
        ((Activity) ctx).getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        return displaymetrics.widthPixels;
    }

    public static String getTag(Context context) {
        return context.getClass().getSimpleName();
    }

    public static Drawable setImageColor(Context context, int imageDrawable, int color) {
        Drawable mDrawable = ContextCompat.getDrawable(context, imageDrawable);
        mDrawable.setColorFilter(ContextCompat.getColor(context, color), PorterDuff.Mode.MULTIPLY);

        return mDrawable;

    }


}
