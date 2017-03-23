package com.example.pat.aapkatrade.categories_tab;

import android.graphics.Paint;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.pat.aapkatrade.R;

/**
 * Created by PPC16 on 16-Jan-17.
 */

public class CategoriesListHolder extends RecyclerView.ViewHolder
{

    View view;
    LinearLayout linearlayout1,linearlayoutMap;
    TextView tvProductName, tvProductPrice,tvProductCrossPrice, tvProductCategoryname;
    ImageView productimage;




    public CategoriesListHolder(View itemView)
    {

        super(itemView);

        linearlayout1 = (LinearLayout) itemView.findViewById(R.id.linearlayout1) ;

        linearlayoutMap = (LinearLayout) itemView.findViewById(R.id.linearlayoutMap);

        tvProductName = (TextView) itemView.findViewById(R.id.tvProductName);

        tvProductPrice = (TextView) itemView.findViewById(R.id.tvProductPrice);

        tvProductCrossPrice = (TextView) itemView.findViewById(R.id.tvProductCrossPrice) ;

        tvProductCrossPrice.setPaintFlags(tvProductCrossPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

        productimage = (ImageView) itemView.findViewById(R.id.productImage);

        tvProductCategoryname = (TextView) itemView.findViewById(R.id.tvProductCategoryname);


        view = itemView;
    }
}