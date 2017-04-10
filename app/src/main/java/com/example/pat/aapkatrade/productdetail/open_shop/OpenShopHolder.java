package com.example.pat.aapkatrade.productdetail.open_shop;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.pat.aapkatrade.R;

/**
 * Created by PPC16 on 4/10/2017.
 */

public class OpenShopHolder extends RecyclerView.ViewHolder
{

    View view;
    TextView tvdays ,tvopentime, tvclosetime;
    RelativeLayout relativeOpenShop;



    public OpenShopHolder(View itemView)
    {
        super(itemView);

        relativeOpenShop = (RelativeLayout) itemView.findViewById(R.id.relativeOpenShop);

        tvdays = (TextView) itemView.findViewById(R.id.tvdays);

        tvopentime = (TextView) itemView.findViewById(R.id.tvopentime) ;

        tvclosetime = (TextView) itemView.findViewById(R.id.tvclosetime);

        view = itemView;
    }
}