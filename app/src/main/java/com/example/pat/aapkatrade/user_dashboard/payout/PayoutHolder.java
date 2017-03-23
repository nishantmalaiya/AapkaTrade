package com.example.pat.aapkatrade.user_dashboard.payout;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.pat.aapkatrade.R;

/**
 * Created by PPC16 on 06-Mar-17.
 */

public class PayoutHolder extends RecyclerView.ViewHolder
{


    TextView tvFromDate,tvToDate,tvRupees;



    public PayoutHolder(View itemView)
    {
        super(itemView);


        tvFromDate=(TextView)itemView.findViewById(R.id.tvFromDate);
        tvToDate=(TextView)itemView.findViewById(R.id.tvToDate);
        tvRupees=(TextView)itemView.findViewById(R.id.tvRupees);


    }
}
