package com.example.pat.aapkatrade.general.Utils.adapter;

/**
 * Created by PPC16 on 10-Jan-17.
 */

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.pat.aapkatrade.Home.navigation.entity.CategoryHome;
import com.example.pat.aapkatrade.Home.navigation.entity.SubCategory;
import com.example.pat.aapkatrade.R;
import com.example.pat.aapkatrade.user_dashboard.addcompany.CompanyData;

import java.util.ArrayList;
import java.util.Objects;

public class CustomSimpleListAdapter extends BaseAdapter {
    Context context;
    ArrayList categoriesNames;
    LayoutInflater inflter;


    public CustomSimpleListAdapter(Context applicationContext, ArrayList categoriesNames) {
        this.context = applicationContext;

        this.categoriesNames = categoriesNames;
        inflter = (LayoutInflater.from(applicationContext));
    }

    @Override
    public int getCount() {
        return categoriesNames.size();
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

        if(categoriesNames.get(i) instanceof String) {
            names.setText((CharSequence) categoriesNames.get(i));
        } else if(categoriesNames.get(i) instanceof CategoryHome){
            names.setText(((CategoryHome) categoriesNames.get(i)).getCategoryName());
        }else if(categoriesNames.get(i) instanceof CompanyData){
            names.setText(((CompanyData) categoriesNames.get(i)).getCompanyName());
        } else if(categoriesNames.get(i) instanceof SubCategory){
            names.setText(((SubCategory) categoriesNames.get(i)).subCategoryName);
        }
        return view;
    }
}