package com.example.pat.aapkatrade.filter.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.RadioButton;
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
    public RadioButton radioButton;
    public LinearLayout ll_filter_coloumn2_container;

    public FilterColumn2ViewHolder(View view){
        super(view);
        checkFilterValue = (CheckBox) view.findViewById(R.id.check_filter_value);
        filterValue = (TextView) view.findViewById(R.id.filter_value);
        ll_filter_coloumn2_container=(LinearLayout)view.findViewById(R.id.ll_filter_coloumn2_container);
    }
    public FilterColumn2ViewHolder(View view, boolean isRadio){
        super(view);
        checkFilterValue = (CheckBox) view.findViewById(R.id.check_filter_value);
        filterValue = (TextView) view.findViewById(R.id.filter_value);
        ll_filter_coloumn2_container=(LinearLayout)view.findViewById(R.id.ll_filter_coloumn2_container);
        if(isRadio){
            radioButton = (RadioButton) view.findViewById(R.id.radio_button);
            radioButton.setVisibility(View.VISIBLE);
            checkFilterValue.setVisibility(View.GONE);
            filterValue.setVisibility(View.GONE);
        }
    }
}
