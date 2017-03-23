package com.example.pat.aapkatrade.Home;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.pat.aapkatrade.R;


/**
 * Created by Netforce on 7/25/2016.
 */
public class CommomHolder extends RecyclerView.ViewHolder {


    CardView cardview;
    ImageView cimageview;
    TextView tvProductName;


    public CommomHolder(View itemView)
    {
        super(itemView);
        cardview= (CardView) itemView.findViewById(R.id.cardview);
        cimageview=(ImageView)itemView.findViewById(R.id.img_product_image_list) ;

        tvProductName = (TextView) itemView.findViewById(R.id.tvProductName);
    }
}
