package com.example.pat.aapkatrade.filter.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.pat.aapkatrade.R;
import com.example.pat.aapkatrade.filter.entity.FilterObject;
import com.example.pat.aapkatrade.filter.viewholder.FilterColumn1ViewHolder;
import com.example.pat.aapkatrade.filter.viewholder.FilterColumn2ViewHolder;
import com.example.pat.aapkatrade.general.CommonInterface;

import java.util.ArrayList;

public class FilterColumn2RecyclerAdapter extends RecyclerView.Adapter<FilterColumn2ViewHolder> {
    private final LayoutInflater inflater;
    private ArrayList<FilterObject> filterValueList;
    private Context context;
    public static CommonInterface commonInterface;

    public FilterColumn2RecyclerAdapter(Context context, ArrayList<FilterObject> filterValueList) {
        this.context = context;
        this.filterValueList = filterValueList;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public FilterColumn2ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new FilterColumn2ViewHolder(inflater.inflate(R.layout.row_filter_column2, parent, false));
    }

    @Override
    public void onBindViewHolder(FilterColumn2ViewHolder holder, final int position) {
        holder.checkFilterValue.setChecked(false);
        holder.filterValue.setText(filterValueList.get(position).name.value.toString());
//        holder.filterName.setText(filterNameList.get(position));
//        holder.filterName.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                commonInterface.getData(position);
//            }
//        });
    }


    @Override
    public int getItemCount() {
        return filterValueList.size();
    }
}

