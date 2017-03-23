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

/**
 * Created by PPC21 on 06-Feb-17.
 */

public class Webservice_search_autocompleteadapter extends BaseAdapter implements Filterable {
    Context context;
    ArrayList<String> names_data;
    LayoutInflater inflter;

    private ArrayList<String> originalData;
    private ArrayList<String> filteredData;
    private ValueFilter valueFilter;

    public Webservice_search_autocompleteadapter(Context applicationContext, ArrayList<String> names_data) {
        this.context = applicationContext;

        this.names_data = names_data;

        inflter = (LayoutInflater.from(applicationContext));
    }

    @Override
    public int getCount() {
        return names_data.size();
    }

    @Override
    public Object getItem(int i) {
        return names_data.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override


    public View getView(int i, View view, ViewGroup viewGroup) {
        view = inflter.inflate(R.layout.row_spinner, null);
        TextView names = (TextView) view.findViewById(R.id.tvSpCategory);
        Log.e("names", names_data.get(i));
        names.setText(names_data.get(i));

        return view;
    }

    @Override
    public Filter getFilter() {
        if (valueFilter == null) {
            valueFilter = new ValueFilter();
        }
        return valueFilter;
    }


    public class ValueFilter extends Filter {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();

            if (constraint != null && constraint.length() > 0) {
                ArrayList<String> filterList = new ArrayList<>();
                for (int i = 0; i < names_data.size(); i++) {
                    String contact = names_data.get(i);
                    if ((contact.toUpperCase()).contains(constraint.toString().toUpperCase())
                            ) {
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
            }
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            originalData = (ArrayList<String>) results.values;
            notifyDataSetChanged();
        }

    }







}