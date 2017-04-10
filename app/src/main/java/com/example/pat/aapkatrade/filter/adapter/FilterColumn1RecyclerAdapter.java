package com.example.pat.aapkatrade.filter.adapter;

import android.content.Context;
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
    public void onBindViewHolder(final FilterColumn1ViewHolder holder, final int position) {
        holder.filterName.setText(filterNameList.get(position));
        holder.filterName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickListener(position, holder.filterColumn1);
            }
        });

        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickListener(position, holder.filterColumn1);
            }
        });
    }

    private void setBackground(int position, View view) {
        for (int i = 0; i < filterNameList.size(); i++) {
            AndroidUtils.showErrorLog(context, position+"POSITION"+(position == i));
            if (position == i)
                AndroidUtils.setBackgroundSolid(view, context, R.color.green, 0);
//            else
//                AndroidUtils.setBackgroundSolid(view, context, R.color.white, 0);
        }
    }

    private void onClickListener(int position, View view) {
        setBackground(position, view);
        AndroidUtils.showErrorLog(context, "Item No Selected ;  " + position);
        commonInterface.getData(filterNameList.get(position));
    }


    @Override
    public int getItemCount() {
        return filterNameList.size();
    }

}

