package com.example.pat.aapkatrade.user_dashboard.payout;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import com.example.pat.aapkatrade.R;
import com.koushikdutta.ion.Ion;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by PPC16 on 06-Mar-17.
 */

public class PayoutAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    final LayoutInflater inflater;
    List<PayoutData> itemList;
    Context context;
    PayoutHolder viewHolder;



    public PayoutAdapter(Context context, List<PayoutData> itemList) {

        this.itemList = itemList;
        this.context = context;
        inflater = LayoutInflater.from(context);
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.row_payout, parent, false);

        viewHolder = new PayoutHolder(view);


        System.out.println("data-----------" + itemList);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {

        final PayoutHolder homeHolder = (PayoutHolder) holder;

        homeHolder.tvFromDate.setText(itemList.get(position).from_date);
        homeHolder.tvToDate.setText(itemList.get(position).to_date);
        homeHolder.tvRupees.setText(itemList.get(position).total_payout);

    }


    @Override
    public int getItemCount() {
        return itemList.size();
        //return itemList.size();
    }

    public String getCurrentTimeStamp() {
        return new SimpleDateFormat("dd MMM yyyy HH:mm").format(new Date());
    }


}
