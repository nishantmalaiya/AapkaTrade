package com.example.pat.aapkatrade.search;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.pat.aapkatrade.R;
import com.example.pat.aapkatrade.general.Adapter_callback_interface;
import com.example.pat.aapkatrade.general.Utils.AndroidUtils;

import java.util.ArrayList;

/**
 * Created by PPC21 on 06-Feb-17.
 */

public class SearchStateAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>
{
    private LayoutInflater layoutInflater;

    private ArrayList<String> productDetails=new ArrayList<String>();
    int count;

    Context context;
    Adapter_callback_interface callback_interface;
    public ArrayList<common_state_search> common_state_searchlist;
    String type,state;

    //constructor method




    public SearchStateAdapter(Search context, ArrayList<common_state_search> common_state_searchlist) {

        layoutInflater = LayoutInflater.from(context);

        this.common_state_searchlist=common_state_searchlist;
        this.count= common_state_searchlist.size();
        this.context = context;
        this.callback_interface = context;

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
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {


        final CommomHolder_search_state  viewHolder1 = (CommomHolder_search_state) holder;

Log.e("common_state.statename",common_state_searchlist.get(position).statename);
        viewHolder1.product_name.setText(common_state_searchlist.get(position).statename);

        AndroidUtils.setBackgroundStroke(viewHolder1.product_name,context,R.color.green,20,5);
        viewHolder1.product_name.setTextColor(context.getResources().getColor(R.color.green));







        viewHolder1.product_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                AndroidUtils.setBackgroundSolid(viewHolder1.product_name,context,R.color.green,20, GradientDrawable.OVAL);



                viewHolder1.product_name.setTextColor(context.getResources().getColor(R.color.white));
                callback_interface.callback(common_state_searchlist.get(position).state_id,"state");


            }
        });




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