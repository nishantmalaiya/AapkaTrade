package com.example.pat.aapkatrade.general.Utils.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.example.pat.aapkatrade.R;

import java.util.ArrayList;

/**
 * Created by PPC17 on 13-Apr-17.
 */

public class CustomMultipleCheckBoxAdapter extends BaseAdapter {
    private Context context;
    private ArrayList arrayList;
    private LayoutInflater inflter;


    public CustomMultipleCheckBoxAdapter(Context context, ArrayList arrayList) {
        this.context = context;
        this.arrayList = arrayList;
        this.inflter = LayoutInflater.from(context);
    }



    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override


    public View getView(int i, View view, ViewGroup viewGroup) {
        view = inflter.inflate(R.layout.row_filter_column2, null);

        return view;
    }
}