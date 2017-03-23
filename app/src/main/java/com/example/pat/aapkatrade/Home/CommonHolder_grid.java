package com.example.pat.aapkatrade.Home;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.pat.aapkatrade.R;

/**
 * Created by PPC21 on 30-Jan-17.
 */

public class CommonHolder_grid extends RecyclerView.ViewHolder {



    TextView tvProductName,tvproductprize;
    ImageView product_imageview;
    RatingBar ratingbar;
    View view_grid_left,view_grid_right;
    RelativeLayout rl_grid_row_parent;



    public CommonHolder_grid(View itemView)
    {
        super(itemView);
        product_imageview=(ImageView)itemView.findViewById(R.id.img_product_image) ;
        rl_grid_row_parent=(RelativeLayout)itemView.findViewById(R.id.rl_grid_row_parent);

        tvProductName = (TextView) itemView.findViewById(R.id.tv_productname);
        tvproductprize=(TextView) itemView.findViewById(R.id.tv_productprize);
        view_grid_left= itemView.findViewById(R.id.view_left);
        view_grid_right= itemView.findViewById(R.id.view_right);

        //ratingbar=(RatingBar)itemView.findViewById(R.id.ratingBar_products);
    }
}