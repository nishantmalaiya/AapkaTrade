package com.example.pat.aapkatrade.user_dashboard.service_enquirylist;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.pat.aapkatrade.R;

/**
 * Created by PPC16 on 10-Mar-17.
 */

public class ServiceEnquiryHolder extends RecyclerView.ViewHolder
{

    View view;
    TextView tvProductName,tvPrice,tvUserName,tvEmail,tvMobile,tvCreatedDate,tvDescription,tvMore,tvCategoryName;
    RelativeLayout relativeServiceEnquairy;
    View View1;




    public ServiceEnquiryHolder(View itemView)
    {
        super(itemView);

        View1 = itemView.findViewById(R.id.View1);

        relativeServiceEnquairy = (RelativeLayout)  itemView.findViewById(R.id.relativeServiceEnquairy);

        tvProductName = (TextView) itemView.findViewById(R.id.tvProductName);

        tvPrice = (TextView) itemView.findViewById(R.id.tvPrice);

        tvUserName = (TextView)itemView.findViewById(R.id.tvUserName);

        tvEmail = (TextView) itemView.findViewById(R.id.tvEmail);

        tvMobile = (TextView) itemView.findViewById(R.id.tvMobile);

        tvCreatedDate = (TextView) itemView.findViewById(R.id.tvCreatedDate);

        tvDescription = (TextView) itemView.findViewById(R.id.tvDescription);

        tvMore = (TextView) itemView.findViewById(R.id.tvMore);

        tvCategoryName = (TextView) itemView.findViewById(R.id.tvCategoryName);

        view = itemView;
    }
}