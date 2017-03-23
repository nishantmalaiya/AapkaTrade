package com.example.pat.aapkatrade.user_dashboard.vender_detail;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import com.example.pat.aapkatrade.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by PPC16 on 09-Feb-17.
 */

public class VendorAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>
{

    final LayoutInflater inflater;
    List<VendorData> itemList;
    Context context;
    VendorHolder viewHolder;
    VendorActivity vendorActivity;


    public VendorAdapter(Context context, List<VendorData> itemList,VendorActivity vendorActivity)
    {
        this.vendorActivity = vendorActivity;
        this.itemList = itemList;
        this.context = context;
        inflater = LayoutInflater.from(context);
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {

        View view = inflater.inflate(R.layout.row_vendor_list, parent, false);

        viewHolder = new VendorHolder(view);

        System.out.println("data-----------"+itemList);

        return viewHolder;

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position)
    {

        VendorHolder homeHolder = (VendorHolder) holder;

        homeHolder.tvUserName.setText(itemList.get(position).vender_name +" "+ itemList.get(position).vender_last_name);

        homeHolder.tvUserEmail.setText(itemList.get(position).email);

        homeHolder.tvUserMobile.setText(itemList.get(position).mobile);

       SimpleDateFormat form = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        java.util.Date date = null;
        try
        {
            date = form.parse(itemList.get(position).vender_creation_date);
        }
        catch (ParseException e)
        {
            e.printStackTrace();
        }
        SimpleDateFormat postFormater = new SimpleDateFormat("MMMM dd, yyyy");
        String newDateStr = postFormater.format(date);

        homeHolder.tvCreationDate.setText(newDateStr);


        if(itemList.get(position).bussiness_type.equals("1"))
        {
            homeHolder.tvUserType.setText("Personal");
        }
        else
        {
            homeHolder.tvUserType.setText("License");
        }


    }

    private void showMessage(String s)
    {

        Toast.makeText(context, s, Toast.LENGTH_SHORT).show();
    }


    @Override
    public int getItemCount()
    {
        return itemList.size();
    }

    public String getCurrentTimeStamp()
    {
        return new SimpleDateFormat("dd MMM yyyy HH:mm").format(new Date());
    }





}