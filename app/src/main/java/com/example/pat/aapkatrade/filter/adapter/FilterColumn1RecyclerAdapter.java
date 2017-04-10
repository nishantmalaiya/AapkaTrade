package com.example.pat.aapkatrade.filter.adapter;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.pat.aapkatrade.R;
import com.example.pat.aapkatrade.filter.viewholder.FilterColumn1ViewHolder;
import com.example.pat.aapkatrade.general.CommonInterface;
import com.example.pat.aapkatrade.general.Utils.AndroidUtils;

import java.util.ArrayList;

public class FilterColumn1RecyclerAdapter extends RecyclerView.Adapter<FilterColumn1ViewHolder> {
    private final LayoutInflater inflater;
    private ArrayList<String> filterNameList;
    private Context context;
    private ArrayList<View> viewArrayList = new ArrayList<>();
    public static CommonInterface commonInterface = new CommonInterface() {
        @Override
        public Object getData(Object object) {
            return null;
        }
    };

    public FilterColumn1RecyclerAdapter(Context context, ArrayList<String> filterNameList) {
        this.context = context;
        this.filterNameList = filterNameList;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public FilterColumn1ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new FilterColumn1ViewHolder(inflater.inflate(R.layout.row_filter_column1, parent, false));
    }

    @Override
    public void onBindViewHolder(final FilterColumn1ViewHolder holder, int position) {
        if(!viewArrayList.contains(holder.filterColumn1)){
            viewArrayList.add(holder.filterColumn1);
        }
        holder.filterName.setText(filterNameList.get(position));
        holder.filterName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickListener(holder.getAdapterPosition());
            }
        });
        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickListener(holder.getAdapterPosition());
            }
        });
    }

    private void setBackground(int position) {
        for (int i = 0; i < filterNameList.size(); i++) {
            AndroidUtils.showErrorLog(context, position + "POSITION" + (position == i));
            if (position == i)
                AndroidUtils.setBackgroundSolid(viewArrayList.get(position), context, R.color.selected_filter_col1_bg, 0, GradientDrawable.RECTANGLE);
            else
                AndroidUtils.setBackgroundSolid(viewArrayList.get(i), context, R.color.filter_col1_bg, 0, GradientDrawable.RECTANGLE);
        }
    }

    private void onClickListener(int position) {
        setBackground(position);
        AndroidUtils.showErrorLog(context, "Item No Selected ;  " + position);
        commonInterface.getData(filterNameList.get(position));
    }


    @Override
    public int getItemCount() {
        return filterNameList.size();
    }

}

