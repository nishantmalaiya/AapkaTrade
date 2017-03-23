package com.example.pat.aapkatrade.productdetail;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;


import com.example.pat.aapkatrade.R;

/**
 * Created by PPC16 on 10-Feb-17.
 */

public class ProductViewPagerHolder extends RecyclerView.ViewHolder {

    View view;
    ImageView imageView;
    LinearLayout linearlayout;

    public ProductViewPagerHolder(View itemView)
    {

        super(itemView);

        linearlayout = (LinearLayout) itemView.findViewById(R.id.linearlayout);

        imageView = (ImageView) itemView.findViewById(R.id.img_pager_item);


        view = itemView;


    }
}