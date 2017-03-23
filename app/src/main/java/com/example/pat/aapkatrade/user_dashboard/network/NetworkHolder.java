package com.example.pat.aapkatrade.user_dashboard.network;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.pat.aapkatrade.R;

/**
 * Created by PPC16 on 03-Mar-17.
 */

public class NetworkHolder extends RecyclerView.ViewHolder
{

    View view;
    TextView tvUserName,tvEmail,tvMobile,tvCreatedDate;
    RelativeLayout relativeServiceEnquairy;
    View View1;



    public NetworkHolder(View itemView)
    {
        super(itemView);

        View1 = itemView.findViewById(R.id.View1);

        relativeServiceEnquairy = (RelativeLayout)  itemView.findViewById(R.id.relativeServiceEnquairy);

        tvUserName = (TextView) itemView.findViewById(R.id.tvUserName);

        tvEmail = (TextView) itemView.findViewById(R.id.tvEmail);

        tvMobile = (TextView) itemView.findViewById(R.id.tvMobile);

        tvCreatedDate = (TextView) itemView.findViewById(R.id.tvCreatedDate);

        view = itemView;
    }
}