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
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.RelativeLayout;

import com.davemorrissey.labs.subscaleview.ImageSource;
import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView;
import com.example.pat.aapkatrade.R;
import com.example.pat.aapkatrade.general.Utils.AndroidUtils;

/**
 * Created by PPC15 on 31-03-2017.
 */

public class ZoomImageDialog extends Dialog {
    private Bitmap bitmap;
    private SubsamplingScaleImageView imageView;

    public ZoomImageDialog(Context context, Bitmap bitmap) {
        super(context);
        this.bitmap=bitmap;
    }


//    public static ZoomImageDialog newInstance(Bitmap bitmap) {
//        ZoomImageDialog frag = new ZoomImageDialog();
//        Bundle args = new Bundle();
//        args.putParcelable("image", bitmap);
//        //this.bitmap=bitmap;
//        frag.setArguments(args);
//
//        return frag;
//    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.zoom_image_dialog_layout);
        initView();
        //setStyle(DialogFragment.STYLE_NORMAL, R.style.dialog_theme);
    }



//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//
//
//
//
//        View view = inflater.inflate(R.layout.zoom_image_dialog_layout, container, false);
//
//
//        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) new RelativeLayout.LayoutParams(
//                AndroidUtils.screenWidth(getActivity()), AndroidUtils.screenHeight(getActivity()));
//
//        view.setLayoutParams(params);
//        initView(view);
//       // imageView.setImage(ImageSource.bitmap(bitmap));
//        return view;
//    }






    private void initView() {
        imageView = (SubsamplingScaleImageView)findViewById(R.id.imageView);
    }
}
