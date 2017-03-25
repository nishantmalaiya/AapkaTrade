package com.example.pat.aapkatrade.general;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.example.pat.aapkatrade.R;
import com.example.pat.aapkatrade.general.progressbar.ProgressBarHandler;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by PPC21 on 20-Jan-17.
 */

public class CheckPermission {


    public static final int MULTIPLE_PERMISSIONS = 10; // code you want.
    public  static ProgressBar mProgressBar;

    public static   String[] permissions= new String[]{
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA,
            Manifest.permission.ACCESS_WIFI_STATE,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.READ_SMS
            ,Manifest.permission.RECEIVE_SMS
            ,Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.ACCESS_NETWORK_STATE};


    public static   boolean checkPermissions(Activity c) {

        call_progress_bar(c);
        show();

        int result;

        List<String> listPermissionsNeeded = new ArrayList<>();
        for (String p:permissions) {
            result = ContextCompat.checkSelfPermission(c,p);
            if (result != PackageManager.PERMISSION_GRANTED) {
                listPermissionsNeeded.add(p);
            }
        }
        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(c, listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]),MULTIPLE_PERMISSIONS );
            hide();
            return false;
        }
        else{
            hide();
        }

        return true;
    }
    public static void call_progress_bar(Activity activity)
    {

        ViewGroup layout = (ViewGroup) activity.findViewById(android.R.id.content).getRootView();

        mProgressBar = new ProgressBar(activity, null, android.R.attr.progressBarStyleLarge);
        mProgressBar.setIndeterminate(true);
        mProgressBar.invalidateDrawable(activity.getResources().getDrawable(R.drawable.progress_bar_animation));

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {

            Drawable wrapDrawable = DrawableCompat.wrap(mProgressBar.getIndeterminateDrawable());
            DrawableCompat.setTint(wrapDrawable, ContextCompat.getColor(activity, R.color.color_voilet));
            mProgressBar.setIndeterminateDrawable(DrawableCompat.unwrap(wrapDrawable));
        } else {
            mProgressBar.getIndeterminateDrawable().setColorFilter(ContextCompat.getColor(activity,  R.color.color_voilet), PorterDuff.Mode.SRC_IN);
        }


        RelativeLayout.LayoutParams params = new
                RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);

        RelativeLayout rl = new RelativeLayout(activity);

        rl.setGravity(Gravity.CENTER);
        rl.addView(mProgressBar);

        layout.addView(rl, params);

        hide();

    }

    public static void show() {
        mProgressBar.setVisibility(View.VISIBLE);


        Log.e("show_working","show_working");
    }

    public static  void hide() {
        Log.e("hide_working","hide_working");
        mProgressBar.setVisibility(View.INVISIBLE);
    }










}
