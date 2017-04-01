package com.example.pat.aapkatrade.user_dashboard.order_list;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.pat.aapkatrade.R;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by PPC16 on 17-Jan-17.
 */

public class OrderListHolder extends RecyclerView.ViewHolder
{

    View view;
    TextView tvProductName,tvCompanyName,tvProductPrice,tvUserName,tvMobileNo,tvCreatedDate,tvUserEmail,tvAddress;
    Button buttonDespatched;
    CircleImageView circleImage;

    public OrderListHolder(View itemView)
    {
        super(itemView);

        tvProductName = (TextView) itemView.findViewById(R.id.tvProductName);

        tvCompanyName = (TextView) itemView.findViewById(R.id.tvCompanyName);

        tvProductPrice = (TextView) itemView.findViewById(R.id.tvProductPrice);

        tvUserName = (TextView) itemView.findViewById(R.id.tvUserName);

        tvMobileNo = (TextView) itemView.findViewById(R.id.tvMobileNo);

        tvCreatedDate = (TextView) itemView.findViewById(R.id.tvCreatedDate);

        tvUserEmail = (TextView) itemView.findViewById(R.id.tvUserEmail);

        tvAddress = (TextView) itemView.findViewById(R.id.tvAddress);

        buttonDespatched = (Button) itemView.findViewById(R.id.buttonDespatched);

        circleImage = (CircleImageView) itemView.findViewById(R.id.circleImage);
        view = itemView;
    }
}
