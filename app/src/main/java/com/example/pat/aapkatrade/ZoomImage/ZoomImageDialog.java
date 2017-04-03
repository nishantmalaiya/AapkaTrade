package com.example.pat.aapkatrade.ZoomImage;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.davemorrissey.labs.subscaleview.ImageSource;
import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView;
import com.example.pat.aapkatrade.Home.banner_home.viewpageradapter_home;
import com.example.pat.aapkatrade.R;
import com.example.pat.aapkatrade.general.CustomImageVIew;
import com.example.pat.aapkatrade.general.Utils.AndroidUtils;
import com.example.pat.aapkatrade.general.customimageview.ZoomableImageView;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import me.relex.circleindicator.CircleIndicator;

/**
 * Created by PPC15 on 31-03-2017.
 */

public class ZoomImageDialog extends AppCompatActivity {
    private Bitmap bitmap;
    private SubsamplingScaleImageView imageView;
    Context context;
    ArrayList<String> imageurl=new ArrayList<>();
    ZoomViewPagerAdapter zoomViewPagerAdapter;
    ViewPager viewpager_custom;
    private int dotsCount;
    Timer banner_timer = new Timer();
    int currentPage = 0;
    CircleIndicator circleIndicator;
    public ZoomImageDialog() {




    }

//    public ZoomImageDialog(Context context, Bitmap bitmap) {
//       this.context=context;
//        this.bitmap=bitmap;
//    }






    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.zoom_image_dialog_layout);
       // goto_zoom_imageview.putStringArrayListExtra("imageurl",imageurl);
        imageurl= getIntent().getStringArrayListExtra("imageurl");
        Log.e(AndroidUtils.getTag(this),imageurl.toString());
        initView();

        setupviewpager(imageurl);


    }

    private void setupviewpager(ArrayList<String> imageIdList) {



        zoomViewPagerAdapter  = new ZoomViewPagerAdapter(this, imageIdList);
        viewpager_custom.setAdapter(zoomViewPagerAdapter);

        //viewpager_custom.setCurrentItem(currentPage);
            //setUiPageViewController();

            final Handler handler = new Handler();

            final Runnable update = new Runnable() {
                public void run() {
                    if (currentPage == zoomViewPagerAdapter.getCount() - 1) {
                        currentPage = 0;
                    }
                    viewpager_custom.setCurrentItem(currentPage++, true);
                }
            };


            banner_timer.schedule(new TimerTask() {

                @Override
                public void run() {
                    handler.post(update);
                }
            }, 0, 3000);

        circleIndicator.setViewPager(viewpager_custom);

    }


    private void initView() {
        viewpager_custom=(ViewPager)findViewById(R.id.viewpager_zoom) ;
        circleIndicator=(CircleIndicator)findViewById(R.id.indicator_custom) ;


//       ZoomableImageView mImageView = (ZoomableImageView)findViewById(R.id.action_infolinks_splash);
//        mImageView.setMaxZoom(3f);
//        mImageView.setImageBitmap(bitmap);



    }
//    private void setUiPageViewController() {
//
//        dotsCount = zoomViewPagerAdapter.getCount();
//        dots = new ImageView[dotsCount];
//
//        for (int i = 0; i < dotsCount; i++) {
//            dots[i] = new ImageView(getApplicationContext());
//            dots[i].setImageDrawable(getResources().getDrawable(R.drawable.nonselected_item));
//
//            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
//                    LinearLayout.LayoutParams.WRAP_CONTENT,
//                    LinearLayout.LayoutParams.WRAP_CONTENT
//            );
//
//            params.setMargins(4, 0, 4, 0);
//
//            viewpagerindicator.addView(dots[i], params);
//        }
//
//        dots[0].setImageDrawable(getResources().getDrawable(R.drawable.selecteditem_dot));
//
//
//    }




}
