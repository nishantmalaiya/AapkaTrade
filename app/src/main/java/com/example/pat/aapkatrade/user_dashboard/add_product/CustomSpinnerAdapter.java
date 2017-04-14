package com.example.pat.aapkatrade.user_dashboard.add_product;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.pat.aapkatrade.R;
import com.example.pat.aapkatrade.general.entity.KeyValue;

import java.util.ArrayList;

/**
 * Created by PPC21 on 24-Jan-17.
 */

public class CustomSpinnerAdapter extends BaseAdapter {
    Context context;
    ArrayList Names,id;
    LayoutInflater inflter;

    public CustomSpinnerAdapter(Context applicationContext, ArrayList Names,ArrayList id) {
        this.context = applicationContext;

        this.Names = Names;
        this.id=id;
        inflter = (LayoutInflater.from(applicationContext));
    }

    public CustomSpinnerAdapter(Context applicationContext, ArrayList Names) {
        this.context = applicationContext;

        this.Names = Names;

        inflter = (LayoutInflater.from(applicationContext));
    }



    @Override
    public int getCount() {
        return Names.size();
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
        view = inflter.inflate(R.layout.row_spinner, null);
        TextView names = (TextView) view.findViewById(R.id.tvSpCategory);
        if(Names.get(i) instanceof KeyValue){
            names.setText(((KeyValue) Names.get(i)).value.toString());
            names.setTextColor(ContextCompat.getColor(context,R.color.black));
        } else{
            names.setText(Names.get(i).toString());
        }
        return view;
    }
}