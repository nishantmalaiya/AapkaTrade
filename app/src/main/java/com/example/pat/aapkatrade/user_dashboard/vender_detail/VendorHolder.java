package com.example.pat.aapkatrade.user_dashboard.vender_detail;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.example.pat.aapkatrade.R;

/**
 * Created by PPC16 on 09-Feb-17.
 */

public class VendorHolder extends RecyclerView.ViewHolder
{


    View view;
    TextView tvUserName,tvCreationDate,tvUserType,tvUserEmail,tvUserMobile;
    RelativeLayout relativeVenderLayout;

    View View1;



    public VendorHolder(View itemView)
    {
        super(itemView);

        View1 = itemView.findViewById(R.id.View1);

        relativeVenderLayout = (RelativeLayout) itemView.findViewById(R.id.relativeVenderLayout);

        tvUserMobile = (TextView) itemView.findViewById(R.id.tvUserMobile);

        tvUserEmail = (TextView) itemView.findViewById(R.id.tvUserEmail);

        tvUserType= (TextView) itemView.findViewById(R.id.tvUserType);

        tvUserName = (TextView) itemView.findViewById(R.id.tvUserName);

        tvCreationDate = (TextView) itemView.findViewById(R.id.tvCreationDate);


        view = itemView;
    }
}