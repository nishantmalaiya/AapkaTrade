package com.example.pat.aapkatrade.general.Utils.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.example.pat.aapkatrade.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by PPC21 on 06-Feb-17.
 */

public class CustomAutocompleteAdapter extends BaseAdapter implements Filterable {
    Context context;
    ArrayList<String> names_data;
    LayoutInflater inflter;

    private ArrayList<String> originalData;

    private ValueFilter valueFilter;

    public CustomAutocompleteAdapter(Context applicationContext, ArrayList<String> names_data) {
        this.context = applicationContext;

        this.names_data = names_data;

        inflter = (LayoutInflater.from(applicationContext));
    }

    @Override
    public int getCount() {
        if(originalData!=null && originalData.size()< 0)
        return names_data.size();
        else return originalData.size();
    }

    @Override
    public Object getItem(int i) {
        if(originalData.size()==0)
            return names_data.get(i);
        else return originalData.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override


    public View getView(int i, View view, ViewGroup viewGroup) {
        if(view==null)
        {
            view = inflter.inflate(R.layout.row_spinner, null);
        }
        TextView names = (TextView) view.findViewById(R.id.tvSpCategory);
        Log.e("names", names_data.get(i));

        if(originalData.size()==0)
        {
            names.setText(names_data.get(i));
            Log.e("names_data.get(i)",names_data.get(i).toString());
        }

        else  {
            names.setText(originalData.get(i));
            Log.e("originalData.get(i)",originalData.get(i)+"***"+originalData.size());
        }



        return view;
    }

    @Override
    public Filter getFilter() {
        if (valueFilter == null) {
            valueFilter = new ValueFilter();
        }
        Log.e("valueFilter",valueFilter.toString());
        return valueFilter;
    }


    public class ValueFilter extends Filter {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            Log.e("constraint", String.valueOf(constraint)+"***");

            FilterResults results = new FilterResults();

            if (constraint != null && constraint.length() > 0) {

                ArrayList<String> filterList = new ArrayList<>();
                for (int i = 0; i < names_data.size(); i++) {
                    Log.e("results>0",names_data.size()+"");
                    String contact = names_data.get(i);
                    Log.e("contact",names_data.get(i));
                    if ((contact.toUpperCase()).contains(constraint.toString().toUpperCase()))
                    {
                        filterList.add(names_data.get(i));
                    }

                }
                results.count = filterList.size();
                results.values = filterList;
                Log.e("results>0",results.values.toString());
            } else {
                results.count = names_data.size();
                results.values = names_data;
                Log.e("results<0",results.values.toString());
//                Log.e("contact--------",constraint.toString());

            }
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            originalData = (ArrayList<String>) results.values;
            List<String> filterList = (ArrayList<String>) results.values;

            //notifyDataSetChanged();




            if (results != null && results.count > 0) {
                //filterList.clear();
                Log.e("originalData",filterList.toString());
//                for (String names : originalData) {
//                    filterList.add(names);
//                    Log.e("originalData",filterList.toString());
//                    notifyDataSetChanged();
//                }
//                Log.e("originalData2",filterList.toString());
            }
        }

    }







}
