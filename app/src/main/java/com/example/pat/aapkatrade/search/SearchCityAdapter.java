package com.example.pat.aapkatrade.search;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.pat.aapkatrade.R;

import java.util.ArrayList;

/**
 * Created by PPC21 on 06-Feb-17.
 */

public class SearchCityAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>
{
    private LayoutInflater layoutInflater;

    private ArrayList<String> productDetails=new ArrayList<String>();
    int count;

    Context context;
    ArrayList<common_category_search> common_category_searchlist=new ArrayList<>();
    String type,state;

    //constructor method
    public SearchCityAdapter(Context context, ArrayList<String> product_details) {

        layoutInflater = LayoutInflater.from(context);

        this.productDetails=product_details;
        this.count= product_details.size();
        this.context = context;


    }





    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        CommomHolder_search_state viewHolder1;


        //RecyclerView.ViewHolder viewHolder;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

          View  v = inflater.inflate(R.layout.viewpager_search_state, parent, false);
        viewHolder1 = new CommomHolder_search_state(v);







        return viewHolder1;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {


        CommomHolder_search_state  viewHolder1 = (CommomHolder_search_state) holder;



        viewHolder1.product_name.setText(productDetails.get(position));
        GradientDrawable shape =  new GradientDrawable();
        shape.setCornerRadius( 20 );
        shape.setColor(ContextCompat.getColor(context, R.color.orange));
        viewHolder1.product_name.setBackground(shape);

    }

    @Override
    public long getItemId(int arg0) {
        return arg0;
    }

    @Override
    public int getItemCount() {
        return count;
    }



}