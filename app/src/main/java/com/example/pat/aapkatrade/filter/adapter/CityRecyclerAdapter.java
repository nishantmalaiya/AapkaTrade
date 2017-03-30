package com.example.pat.aapkatrade.filter.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.pat.aapkatrade.Home.registration.entity.City;
import com.example.pat.aapkatrade.R;
import com.example.pat.aapkatrade.filter.viewholder.CityViewHolder;
import com.example.pat.aapkatrade.general.CommonInterface;
import com.example.pat.aapkatrade.user_dashboard.address.AddressData;
import com.example.pat.aapkatrade.user_dashboard.address.AddressListViewHolder;
import com.example.pat.aapkatrade.user_dashboard.address.add_address.AddAddressActivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CityRecyclerAdapter extends RecyclerView.Adapter<CityViewHolder> {

    private final LayoutInflater inflater;
    private ArrayList<City> cityList = new ArrayList<>();
    private Context context;
    public static CommonInterface commonInterface;

    public CityRecyclerAdapter(Context context, ArrayList<City> cityList) {
        this.cityList = cityList;
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public CityViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new CityViewHolder(inflater.inflate(R.layout.select_city_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(CityViewHolder holder, final int position) {
        holder.checkBox.setChecked(false);
        holder.tvCity.setText(cityList.get(position).cityName);
        Log.e("message_data---", "City NAme : " + cityList.get(position).cityName);

        holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    cityList.get(position).isChecked = true;
                    Log.e("message_data---", "City ArrayList*** : " + getSelectedCityList(cityList).size());
                    commonInterface.getData(getSelectedCityList(cityList));
                }
            }
        });
    }


    @Override
    public int getItemCount() {
        return cityList.size();
    }

    private ArrayList<City> getSelectedCityList(ArrayList<City> cityArrayList){
        ArrayList<City> tempArrayList = new ArrayList<>();
        int count = 0;
        if (cityArrayList!=null){
            for (int i = 0; i < cityArrayList.size(); i++){
                if (cityArrayList.get(i).isChecked){
                    tempArrayList.add(cityArrayList.get(i));
                } else {
                    count++;
                }
            }
            Log.e("message_data---", "cityArrayList.size() "+cityArrayList.size()+ " tempArrayList.size() "+tempArrayList.size()+"count"+ count);
            if(count == cityArrayList.size()){
                return cityArrayList;
            }else {
                return tempArrayList;
            }
        }
        return null;
    }


}

