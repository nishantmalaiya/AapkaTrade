package com.example.pat.aapkatrade.general.animation_effects;

import android.content.Context;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;

import com.example.pat.aapkatrade.R;

/**
 * Created by PPC21 on 04-Feb-17.
 */

public class App_animation {

    public static void circularanimation(View v) {
        RotateAnimation rotate = new RotateAnimation(0, 180, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        rotate.setDuration(5000);
        rotate.setInterpolator(new LinearInterpolator());
        v.startAnimation(rotate);
    }


    public static void left_animation(View v, Context c) {
        v.startAnimation(AnimationUtils.loadAnimation(c, R.anim.slide_left));
    }

    public static void right_animation(View v, Context c) {
        v.startAnimation(AnimationUtils.loadAnimation(c, R.anim.slide_right));
    }

    public static void setScaleAnimation(View view) {
        ScaleAnimation anim = new ScaleAnimation(0.0f, 1.0f, 0.0f, 1.0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        anim.setDuration(1000);
        view.startAnimation(anim);
    }




}
