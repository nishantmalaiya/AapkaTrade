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

public class FilterViewHolder extends RecyclerView.ViewHolder {
    public CheckBox checkBox;
    public TextView tvCity;

    public FilterViewHolder(View view){
        super(view);
        checkBox = (CheckBox) view.findViewById(R.id.checkBox);
        tvCity = (TextView) view.findViewById(R.id.tvCity);
    }
}
