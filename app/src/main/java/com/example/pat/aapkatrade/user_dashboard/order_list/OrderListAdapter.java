package com.example.pat.aapkatrade.user_dashboard.order_list;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.pat.aapkatrade.R;
import com.example.pat.aapkatrade.user_dashboard.order_list.order_details.OrderDetailsActivity;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by PPC16 on 17-Jan-17.
 */

public class OrderListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>
{

    private final LayoutInflater inflater;
    private List<OrderListData> itemList;
    private Context context;
    OrderListHolder viewHolder;


    public OrderListAdapter(Context context, List<OrderListData> itemList)
    {
        this.itemList = itemList;
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {

        View view = inflater.inflate(R.layout.row_order_list, parent, false);
        viewHolder = new OrderListHolder(view);


        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position)
    {

        OrderListHolder homeHolder = (OrderListHolder) holder;

        homeHolder.tvOrderId.setText(itemList.get(position).order_id);

        homeHolder.tvProductName.setText(itemList.get(position).product_name);

        homeHolder.relativeOrderlist.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

                Intent i = new Intent(context, OrderDetailsActivity.class);
                i.putExtra("order_id",itemList.get(position).order_id);
                i.putExtra("product_name",itemList.get(position).product_name);
                i.putExtra("product_qty",itemList.get(position).product_qty);
                i.putExtra("address",itemList.get(position).address);
                i.putExtra("email",itemList.get(position).email);
                i.putExtra("buyersmobile",itemList.get(position).buyersmobile);
                i.putExtra("buyersname",itemList.get(position).buyersname);
                i.putExtra("buyersmobile",itemList.get(position).buyersmobile);
                i.putExtra("company_name",itemList.get(position).company_name);
                i.putExtra("created_at",itemList.get(position).created_at);
                context.startActivity(i);



            }
        });

    }

    private void showMessage(String s)
    {

        Toast.makeText(context, s, Toast.LENGTH_SHORT).show();
    }


    @Override
    public int getItemCount()
    {
        return itemList.size();
        // return itemList.size();
    }

    public String getCurrentTimeStamp()
    {
        return new SimpleDateFormat("dd MMM yyyy HH:mm").format(new Date());
    }



}

