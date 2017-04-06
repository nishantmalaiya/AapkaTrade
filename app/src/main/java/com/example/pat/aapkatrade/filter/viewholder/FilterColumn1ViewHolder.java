package com.example.pat.aapkatrade.filter.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.pat.aapkatrade.R;

/**
 * Created by PPC15 on 06-04-2017.
 */

public class FilterColumn1ViewHolder extends RecyclerView.ViewHolder {
    public TextView filterName;

    public FilterColumn1ViewHolder(View itemView) {
        super(itemView);
        filterName = (TextView) itemView.findViewById(R.id.tv_filter_name);
    }
}
