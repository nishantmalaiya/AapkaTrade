package com.example.pat.aapkatrade.ZoomImage;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.davemorrissey.labs.subscaleview.ImageSource;
import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView;
import com.example.pat.aapkatrade.R;
import com.example.pat.aapkatrade.general.Utils.AndroidUtils;

/**
 * Created by PPC15 on 31-03-2017.
 */

public class ZoomImageDialog extends Fragment {
    private Bitmap bitmap;
    private ImageView imageView;
    private ScaleGestureDetector scaleGestureDetector;
    private Matrix matrix = new Matrix();

    public ZoomImageDialog() {

    }


    public static ZoomImageDialog newInstance(Bitmap bitmap) {
        ZoomImageDialog frag = new ZoomImageDialog();
        Bundle args = new Bundle();
        args.putParcelable("image", bitmap);
        frag.setArguments(args);
        return frag;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.zoom_image_dialog_layout, container, false);
        initView(view);
        imageView = (ImageView) view.findViewById(R.id.imageView1);
        if (bitmap != null)
            imageView.setImageBitmap(bitmap);
        imageView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                scaleGestureDetector.onTouchEvent(event);
                return true;
            }
        });
        return view;
    }


    private void initView(View view) {
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(AndroidUtils.screenWidth(getActivity()), AndroidUtils.screenHeight(getActivity()));
        view.setLayoutParams(params);
        Bundle bundle = getArguments();
        if(bundle!=null){
            if(bundle.get("image")!=null){
                bitmap = (Bitmap) bundle.get("image");
            }
        }
        scaleGestureDetector = new ScaleGestureDetector(getContext(),new ScaleListener());

    }


    private class ScaleListener extends ScaleGestureDetector.
            SimpleOnScaleGestureListener {
        @Override
        public boolean onScale(ScaleGestureDetector detector) {
            float scaleFactor = detector.getScaleFactor();
            scaleFactor = Math.max(0.1f, Math.min(scaleFactor, 5.0f));
            matrix.setScale(scaleFactor, scaleFactor);
            imageView.setImageMatrix(matrix);
            return true;
        }
    }
}
