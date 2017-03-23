package com.example.pat.aapkatrade.Home.registration.spinner_adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
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

    public SpCityAdapter(Context applicationContext, ArrayList<City> cityList) {
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
        view = inflter.inflate(R.layout.row_spinner, null);
        TextView names = (TextView) view.findViewById(R.id.tvSpCategory);
        Log.e("HOooooooooo", "State List item : " + i);
        names.setText(cityList.get(i).cityName);
        return view;
    }
}