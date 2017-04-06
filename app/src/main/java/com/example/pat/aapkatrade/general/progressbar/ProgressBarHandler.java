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

/**
 * Created by PPC21 on 01-Feb-17.
 */

public class ProgressBarHandler
{

    private ProgressBar mProgressBar;
    private Context mContext;



    public ProgressBarHandler(Context context)
    {
        mContext = context;

        ViewGroup layout = (ViewGroup) ((Activity) context).findViewById(android.R.id.content).getRootView();

        mProgressBar = new ProgressBar(context, null, android.R.attr.progressBarStyleLarge);
        mProgressBar.setIndeterminate(true);
        mProgressBar.invalidateDrawable(ContextCompat.getDrawable(mContext,R.drawable.progress_bar_animation));
        ProgressBarAnimation mProgressAnimation = new ProgressBarAnimation(mProgressBar, 1000);


        mProgressAnimation.setDuration(1000);
        mProgressBar.startAnimation(mProgressAnimation);

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {

            Drawable wrapDrawable = DrawableCompat.wrap(mProgressBar.getIndeterminateDrawable());
            DrawableCompat.setTint(wrapDrawable, ContextCompat.getColor(mContext, R.color.color_voilet));
            mProgressBar.setIndeterminateDrawable(DrawableCompat.unwrap(wrapDrawable));
        } else {
            mProgressBar.getIndeterminateDrawable().setColorFilter(ContextCompat.getColor(mContext,  R.color.color_voilet), PorterDuff.Mode.SRC_IN);
        }


        RelativeLayout.LayoutParams params = new
                RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);

        RelativeLayout rl = new RelativeLayout(context);

        rl.setGravity(Gravity.CENTER);
        rl.addView(mProgressBar);

        layout.addView(rl, params);

        hide();
    }

    public void show() {
        mProgressBar.setVisibility(View.VISIBLE);
    }

    public void hide() {
        mProgressBar.setVisibility(View.INVISIBLE);
    }


}
