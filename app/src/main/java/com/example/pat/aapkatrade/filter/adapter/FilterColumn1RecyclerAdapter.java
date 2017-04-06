package com.example.pat.aapkatrade.filter.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.pat.aapkatrade.R;
import com.example.pat.aapkatrade.filter.viewholder.FilterColumn1ViewHolder;
import com.example.pat.aapkatrade.general.CommonInterface;

import java.util.ArrayList;

public class FilterColumn1RecyclerAdapter extends RecyclerView.Adapter<FilterColumn1ViewHolder> {
    private final LayoutInflater inflater;
    private ArrayList<String> filterNameList;
    private Context context;
    public static CommonInterface commonInterface;

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
    public void onBindViewHolder(FilterColumn1ViewHolder holder, final int position) {
        holder.filterName.setText(filterNameList.get(position));
        holder.filterName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(commonInterface!=null)
                commonInterface.getData(position);
            }
        });
    }


    @Override
    public int getItemCount() {
        return filterNameList.size();
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

