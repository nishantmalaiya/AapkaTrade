package com.example.pat.aapkatrade.user_dashboard;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.pat.aapkatrade.R;

/**
 * Created by PPC16 on 10-Jan-17.
 */

public class DashboardHolder extends RecyclerView.ViewHolder
{

    View view;
    TextView tvDashboard, tvquantity;
    ImageView imageView;
    LinearLayout relativeDashboard;
    RelativeLayout relativeImageView;



    public DashboardHolder(View itemView)
    {
        super(itemView);

        relativeDashboard = (LinearLayout) itemView.findViewById(R.id.relativeDashboard);

        relativeImageView   = (RelativeLayout) itemView.findViewById(R.id.relativeImageView);

        tvDashboard = (TextView) itemView.findViewById(R.id.tvDashboard);

        imageView = (ImageView) itemView.findViewById(R.id.imgDashboard);

        tvquantity = (TextView) itemView.findViewById(R.id.tvquantity);

        view = itemView;
    }
}