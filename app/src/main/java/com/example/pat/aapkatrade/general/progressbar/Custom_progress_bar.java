package com.example.pat.aapkatrade.general.progressbar;

import android.app.Activity;
import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.example.pat.aapkatrade.R;
import com.example.pat.aapkatrade.animation.ProgressBarAnimation;
import com.wang.avi.AVLoadingIndicatorView;

/**
 * Created by PPC17 on 04-Apr-17.
 */

public class Custom_progress_bar {

    private AVLoadingIndicatorView mProgressBar;
    private Context mContext;



    public Custom_progress_bar(Context context)
    {
        mContext = context;

        ViewGroup layout = (ViewGroup) ((Activity) context).findViewById(android.R.id.content).getRootView();

        mProgressBar = new AVLoadingIndicatorView(context, null, android.R.attr.progressBarStyleLarge);
        mProgressBar.setIndicator("BallPulseIndicator");
        mProgressBar.setIndicatorColor(ContextCompat.getColor(mContext,R.color.green));
        show();

        //hide();
    }

    public void show() {
        mProgressBar.show();
        //setVisibility(View.VISIBLE);
    }

    public void hide() {
        mProgressBar.hide();
        //setVisibility(View.INVISIBLE);
    }


}
