package com.example.pat.aapkatrade.user_dashboard.add_product;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.pat.aapkatrade.R;

/**
 * Created by PPC16 on 13-Jan-17.
 */

public class SpinnerAdapter  extends BaseAdapter {
    Context context;
    String[] categoriesNames;
    LayoutInflater inflter;

    public SpinnerAdapter(Context applicationContext, String[] categoriesNames) {
        this.context = applicationContext;

        this.categoriesNames = categoriesNames;
        inflter = (LayoutInflater.from(applicationContext));
    }

    @Override
    public int getCount() {
        return categoriesNames.length;
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
        names.setText(categoriesNames[i]);
        return view;
    }
}