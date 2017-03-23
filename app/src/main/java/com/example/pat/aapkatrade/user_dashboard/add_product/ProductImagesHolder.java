package com.example.pat.aapkatrade.user_dashboard.add_product;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.pat.aapkatrade.R;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by PPC16 on 21-Feb-17.
 */

public class ProductImagesHolder extends RecyclerView.ViewHolder
{

    View view;
    CircleImageView previewImage;
    ImageView cancelImage;

    public ProductImagesHolder(View itemView)
    {
        super(itemView);

        previewImage = (CircleImageView) itemView.findViewById(R.id.previewImage);
        cancelImage = (ImageView) itemView.findViewById(R.id.cancelImage);
        view = itemView;
    }
}
