package com.example.pat.aapkatrade.user_dashboard.order_list;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.pat.aapkatrade.R;

/**
 * Created by PPC16 on 17-Jan-17.
 */

public class OrderListHolder extends RecyclerView.ViewHolder
{


    RelativeLayout relativeOrderlist;
    View view;
    TextView tvProductName,tvOrderId;


    public OrderListHolder(View itemView)
    {
        super(itemView);

       /* relativeOrderlist = (RelativeLayout) itemView.findViewById(R.id.relativeOrderlist);

        tvProductName = (TextView) itemView.findViewById(R.id.tvProductName);

        tvOrderId = (TextView) itemView.findViewById(R.id.tvOrderId);
*/
        view = itemView;
    }
}
