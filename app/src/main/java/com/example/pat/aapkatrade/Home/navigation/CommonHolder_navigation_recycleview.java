package com.example.pat.aapkatrade.Home.navigation;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.pat.aapkatrade.R;

/**
 * Created by PPC17 on 22-Feb-17.
 */
public class CommonHolder_navigation_recycleview extends RecyclerView.ViewHolder {

    RelativeLayout rl_category_container;
    ImageView imageViewIcon;
    TextView tv_categoryname;

    public CommonHolder_navigation_recycleview(View itemView) {
        super(itemView);
        rl_category_container=(RelativeLayout)itemView.findViewById(R.id.rl_category_container);
        imageViewIcon=(ImageView)itemView.findViewById(R.id.imageViewIcon);
        tv_categoryname=(TextView)itemView.findViewById(R.id.lblListHeader);

    }
}
