package com.example.pat.aapkatrade.ZoomImage;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;

import com.davemorrissey.labs.subscaleview.ImageSource;
import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView;
import com.example.pat.aapkatrade.R;

/**
 * Created by PPC15 on 31-03-2017.
 */

public class ZoomImageDialog extends Dialog {
    private Bitmap bitmap;
    private SubsamplingScaleImageView imageView;
    public ZoomImageDialog(Context context, Bitmap bitmap) {
        super(context);
        this.bitmap = bitmap;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));
        setContentView(R.layout.zoom_image_dialog_layout);
        initView();
        imageView.setImage(ImageSource.bitmap(bitmap));

    }

    private void initView() {
        imageView = (SubsamplingScaleImageView) findViewById(R.id.imageView);
    }
}
