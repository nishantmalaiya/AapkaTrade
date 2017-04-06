package com.example.pat.aapkatrade.filter.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextClock;
import android.widget.TextView;
import android.widget.VideoView;

import com.example.pat.aapkatrade.R;

/**
 * Created by PPC15 on 29-03-2017.
 */

public class FilterColumn2ViewHolder extends RecyclerView.ViewHolder {
    public CheckBox checkFilterValue;
    public TextView filterValue;

    public FilterColumn2ViewHolder(View view){
        super(view);
        checkFilterValue = (CheckBox) view.findViewById(R.id.check_filter_value);
        filterValue = (TextView) view.findViewById(R.id.filter_value);
    }
}
