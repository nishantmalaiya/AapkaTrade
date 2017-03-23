package com.example.pat.aapkatrade.user_dashboard.companylist;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.example.pat.aapkatrade.R;


/**
 * Created by John on 10/31/2016.
 */
public class CompanyListHolder extends RecyclerView.ViewHolder
{

    View view;
    TextView tvCompanyname,tvDate,tvEmail,tvAddress,tvDescription;
    ImageView imgEdtCompanyName,imgDeleteCompany,imgNext;
    RelativeLayout relativecompanyList;
    LinearLayout linearLayoutDetail;
    View View1;




    public CompanyListHolder(View itemView)
    {
        super(itemView);

        View1 = itemView.findViewById(R.id.View1);

        linearLayoutDetail = (LinearLayout)  itemView.findViewById(R.id.linearLayoutDetail);

        relativecompanyList = (RelativeLayout) itemView.findViewById(R.id.relativecompanyList);

        imgNext = (ImageView) itemView.findViewById(R.id.imgNext);

        imgEdtCompanyName = (ImageView) itemView.findViewById(R.id.imgEdit);

        tvCompanyname = (TextView) itemView.findViewById(R.id.tvCompanyname);

        tvDate = (TextView) itemView.findViewById(R.id.tvDate);

        imgDeleteCompany = (ImageView)  itemView.findViewById(R.id.imgDelete);

        tvEmail = (TextView) itemView.findViewById(R.id.tvEmail);

        tvAddress = (TextView) itemView.findViewById(R.id.tvAddress);

        tvDescription = (TextView) itemView.findViewById(R.id.tvDescription) ;


        view = itemView;
    }
}