package com.example.pat.aapkatrade.user_dashboard.network;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.pat.aapkatrade.R;
import com.example.pat.aapkatrade.general.AppSharedPreference;
import com.example.pat.aapkatrade.general.progressbar.ProgressBarHandler;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Created by PPC16 on 03-Mar-17.
 */



public class NetworkAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>
{

    final LayoutInflater inflater;
    List<NetworkData> itemList;
    Context context;
    NetworkHolder viewHolder;
    NetworkActivity networkActivity;
    AppSharedPreference app_sharedpreference;
    ProgressBarHandler progress_handler;


    public NetworkAdapter(Context context, List<NetworkData> itemList,NetworkActivity networkActivity)
    {

        this.networkActivity = networkActivity;
        this.itemList = itemList;
        this.context = context;
        inflater = LayoutInflater.from(context);
        app_sharedpreference = new AppSharedPreference(context);
        progress_handler = new ProgressBarHandler(context);

    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view = inflater.inflate(R.layout.row_network_list, parent, false);

        viewHolder = new NetworkHolder(view);

        System.out.println("data-----------"+itemList);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position)
    {

        final NetworkHolder homeHolder = (NetworkHolder) holder;

        homeHolder.tvUserName.setText(itemList.get(position).user_name+" "+itemList.get(position).user_last_name);

        homeHolder.tvEmail.setText(itemList.get(position).email);

        homeHolder.tvMobile.setText(itemList.get(position).mobile);

        SimpleDateFormat form = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        java.util.Date date = null;

        try
        {
            date = form.parse(itemList.get(position).creation_date);

        }
        catch (ParseException e)
        {

            e.printStackTrace();
        }

        SimpleDateFormat postFormater = new SimpleDateFormat("MMMM dd, yyyy");
        String newDateStr = postFormater.format(date);

        homeHolder.tvCreatedDate.setText(newDateStr);



    }

    @Override
    public int getItemCount()
    {

        return itemList.size();
    }


}