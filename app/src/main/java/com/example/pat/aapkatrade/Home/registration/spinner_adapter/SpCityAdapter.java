package com.example.pat.aapkatrade.Home.registration.spinner_adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.pat.aapkatrade.Home.registration.entity.City;
import com.example.pat.aapkatrade.R;

import java.util.ArrayList;

/**
 * Created by PPC16 on 16-Jan-17.
 */

public class SpCityAdapter  extends BaseAdapter {
    Context context;
    private ArrayList<City> cityList = new ArrayList<>();
    LayoutInflater inflter;
    private boolean isMultipleCity = false;

    public SpCityAdapter(Context applicationContext, ArrayList<City> cityList) {
        this.context = applicationContext;
        this.cityList = cityList;
        inflter = (LayoutInflater.from(applicationContext));
    }
    public SpCityAdapter(Context applicationContext, ArrayList<City> cityList, boolean isMultipleCity) {
        this.context = applicationContext;
        this.cityList = cityList;
        inflter = (LayoutInflater.from(applicationContext));
    }


    @Override
    public int getCount() {
        return cityList.size();
    }

    @Override
    public Object getItem(int i) {
        return i;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override


    public View getView(int i, View view, ViewGroup viewGroup) {
        if(isMultipleCity){
            view = inflter.inflate(R.layout.select_city_layout, null);
            CheckBox checkBox = (CheckBox) view.findViewById(R.id.checkBox);
            TextView names = (TextView) view.findViewById(R.id.tvSpCategory);
            Log.e("H123", "State List item : " + i);
            checkBox.setChecked(cityList.get(i).isChecked);
            names.setText(cityList.get(i).cityName);
        }else {
            view = inflter.inflate(R.layout.row_spinner, null);
            TextView names = (TextView) view.findViewById(R.id.tvSpCategory);
            Log.e("HOooooooooo", "State List item : " + i);
            names.setText(cityList.get(i).cityName);
        }

        return view;
    }
}