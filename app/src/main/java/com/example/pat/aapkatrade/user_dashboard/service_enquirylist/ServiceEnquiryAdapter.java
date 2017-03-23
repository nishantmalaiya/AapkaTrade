package com.example.pat.aapkatrade.user_dashboard.service_enquirylist;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.pat.aapkatrade.R;
import com.example.pat.aapkatrade.general.AppSharedPreference;
import com.example.pat.aapkatrade.general.progressbar.ProgressBarHandler;
import com.example.pat.aapkatrade.user_dashboard.companylist.CompanyList;
import com.example.pat.aapkatrade.user_dashboard.companylist.CompanyListHolder;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by PPC16 on 10-Mar-17.
 */

public class ServiceEnquiryAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>
{

    final LayoutInflater inflater;
    List<ServiceEnquiryData> itemList;
    Context context;
    ServiceEnquiryHolder viewHolder;
    ServiceEnquiryActivity  serviceEnquiryActivity;
    AppSharedPreference app_sharedpreference;
    ProgressBarHandler progress_handler;



    public ServiceEnquiryAdapter(Context context, List<ServiceEnquiryData> itemList,ServiceEnquiryActivity serviceEnquiryActivity)
    {

        this.serviceEnquiryActivity = serviceEnquiryActivity;
        this.itemList = itemList;
        this.context = context;
        inflater = LayoutInflater.from(context);
        app_sharedpreference = new AppSharedPreference(context);
        progress_handler = new ProgressBarHandler(context);

    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view = inflater.inflate(R.layout.row_service_enquiry, parent, false);

        viewHolder = new ServiceEnquiryHolder(view);

        System.out.println("data-----------"+itemList);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position)
    {

        final ServiceEnquiryHolder homeHolder = (ServiceEnquiryHolder) holder;

        homeHolder.tvProductName.setText(itemList.get(position).product_name);

        homeHolder.tvPrice.setText("\u20A8"+" "+itemList.get(position).product_price);

        homeHolder.tvUserName.setText(itemList.get(position).user_name);

        homeHolder.tvEmail.setText(itemList.get(position).user_email);

        homeHolder.tvMobile.setText(itemList.get(position).user_mobile);

        homeHolder.tvDescription.setText(itemList.get(position).description);


        SimpleDateFormat form = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        java.util.Date date = null;

        try
        {
            date = form.parse(itemList.get(position).created_date);

        }
        catch (ParseException e)
        {

            e.printStackTrace();
        }

        SimpleDateFormat postFormater = new SimpleDateFormat("MMMM dd, yyyy");
        String newDateStr = postFormater.format(date);

        homeHolder.tvCreatedDate.setText(newDateStr);

        homeHolder.tvCategoryName.setText(itemList.get(position).category_name);

    }

    @Override
    public int getItemCount()
    {

        return itemList.size();
    }




}