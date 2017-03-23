package com.example.pat.aapkatrade.general;

import android.app.Activity;
import android.graphics.Typeface;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by PPC21 on 21-Jan-17.
 */

public class Change_Font {

    public static  String fontPath = "OpenSans_Regular.ttf";


    public static void Change_Font_textview(Activity activity,TextView tv)
    {

        // text view label


        // Loading Font Face
        Typeface tf = Typeface.createFromAsset(activity.getAssets(), fontPath);

        // Applying font
        tv.setTypeface(tf);
    }


    public static void Change_Font_edittext(Activity activity,EditText et)
    {


        // text view label


        // Loading Font Face
        Typeface tf = Typeface.createFromAsset(activity.getAssets(), fontPath);

        // Applying font
        et.setTypeface(tf);
    }





}
