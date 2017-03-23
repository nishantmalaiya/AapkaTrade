package com.example.pat.aapkatrade.search;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.pat.aapkatrade.R;

/**
 * Created by PPC17 on 15-Mar-17.
 */


class CommomHolder_search_state extends RecyclerView.ViewHolder {
    TextView product_name;
    public CommomHolder_search_state(View itemView) {
        super(itemView);
        product_name=(TextView)itemView.findViewById(R.id.tv_pager_search_state);


    }
}
