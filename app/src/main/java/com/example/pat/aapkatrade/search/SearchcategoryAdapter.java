package com.example.pat.aapkatrade.search;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.pat.aapkatrade.Home.CommomAdapter;
import com.example.pat.aapkatrade.Home.CommomData;
import com.example.pat.aapkatrade.R;
import com.example.pat.aapkatrade.general.Adapter_callback_interface;
import com.example.pat.aapkatrade.general.Utils.AndroidUtils;
import com.example.pat.aapkatrade.general.progressbar.ProgressBarHandler;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import java.util.ArrayList;

import static com.example.pat.aapkatrade.search.Search.recyclerView_search;

/**
 * Created by PPC21 on 06-Feb-17.
 */

public class SearchcategoryAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>
{
    private LayoutInflater layoutInflater;

    private ArrayList<String> productDetails=new ArrayList<String>();
    int count;

    Context context;

    Adapter_callback_interface callback_interface;
    ArrayList<common_category_search> common_category_searchlist;
    String type,state,selected_location,search_product;
    CommomAdapter commomAdapter;

    ArrayList<CommomData> search_productlist = new ArrayList<>();
    ProgressBarHandler progressBarHandler;

    //constructor method
    public SearchcategoryAdapter(Context context, ArrayList<common_category_search> common_category_searchlist, String  selected_location, String search_product) {

        layoutInflater = LayoutInflater.from(context);

        this.common_category_searchlist=common_category_searchlist;
        this.count= common_category_searchlist.size();
        this.context = context;
        this.selected_location=selected_location;
        this.search_product=search_product;
        this.notifyDataSetChanged();


        this.callback_interface = ((Adapter_callback_interface) context);
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



        viewHolder1.product_name.setText(common_category_searchlist.get(position).catname);

        AndroidUtils.setBackgroundStroke(viewHolder1.product_name,context,R.color.green,20,5);
        viewHolder1.product_name.setTextColor(context.getResources().getColor(R.color.green));




        viewHolder1.product_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {




                AndroidUtils.setBackgroundSolid(viewHolder1.product_name,context,R.color.green,20);



                viewHolder1.product_name.setTextColor(context.getResources().getColor(R.color.white));
                Log.e("category_id",common_category_searchlist.get(position).cat_id);

                callback_interface.callback(common_category_searchlist.get(position).cat_id,"category");

//




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
