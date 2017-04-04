package com.example.pat.aapkatrade.filter.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.CompoundButton;

import com.example.pat.aapkatrade.Home.registration.entity.City;
import com.example.pat.aapkatrade.R;
import com.example.pat.aapkatrade.filter.entity.FilterObject;
import com.example.pat.aapkatrade.filter.viewholder.FilterViewHolder;
import com.example.pat.aapkatrade.general.CommonInterface;

import java.util.ArrayList;
import java.util.HashMap;

public class FilterRecyclerAdapter extends RecyclerView.Adapter<FilterViewHolder> {
    private final LayoutInflater inflater;
    private ArrayList<FilterObject> filterObjectList;
    private Context context;
    public static CommonInterface commonInterface;

    public FilterRecyclerAdapter(Context context, ArrayList<FilterObject> filterObjectList) {
        this.context = context;
        this.filterObjectList = filterObjectList;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public FilterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new FilterViewHolder(inflater.inflate(R.layout.select_city_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(FilterViewHolder holder, final int position) {
        holder.checkBox.setChecked(false);
        holder.tvCity.setText("");
//        Log.e("message_data---", "City NAme : " + cityList.get(position).cityName);

        holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
//                    cityList.get(position).isChecked = true;
//                    Log.e("message_data---", "City ArrayList*** : " + getSelectedCityList(cityList).size());
//                    commonInterface.getData(getSelectedCityList(cityList));
                }
            }
        });
    }


    @Override
    public int getItemCount() {
        return filterObjectList.size();
    }

   /* private ArrayList<City> getSelectedCityList(ArrayList<City> cityArrayList){
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
    }*/


}

