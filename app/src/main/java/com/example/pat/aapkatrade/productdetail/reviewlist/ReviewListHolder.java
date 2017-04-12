package com.example.pat.aapkatrade.productdetail.reviewlist;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.example.pat.aapkatrade.R;

/**
 * Created by PPC16 on 4/8/2017.
 */



public class ReviewListHolder extends RecyclerView.ViewHolder
{

    View view;
    TextView title ,message_description, username, deliver_date,tvRating;
    ImageView imgStar;


    public ReviewListHolder(View itemView)
    {
        super(itemView);

        title = (TextView) itemView.findViewById(R.id.tvtitle);

        message_description = (TextView) itemView.findViewById(R.id.tvMessage) ;

        username = (TextView) itemView.findViewById(R.id.tvUserName);

        deliver_date = (TextView) itemView.findViewById(R.id.deliver_date);

        tvRating = (TextView) itemView.findViewById(R.id.tvRating);

        imgStar = (ImageView) itemView.findViewById(R.id.imgStar);

        deliver_date = (TextView) itemView.findViewById(R.id.deliver_date);

        view = itemView;
    }
}