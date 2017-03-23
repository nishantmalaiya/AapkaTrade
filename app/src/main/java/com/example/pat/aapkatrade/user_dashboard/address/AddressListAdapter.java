package com.example.pat.aapkatrade.user_dashboard.address;

/**
 * Created by PPC09 on 24-Jan-17.
 */

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.pat.aapkatrade.R;
import com.example.pat.aapkatrade.user_dashboard.address.add_address.AddAddressActivity;
import com.example.pat.aapkatrade.user_dashboard.address.viewpager.CartCheckoutActivity;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class AddressListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>
{

    private final LayoutInflater inflater;
    private List<AddressData> itemList;
    private Context context;
    AddressListViewHolder viewHolder;
    private RadioButton lastCheckedRB = null;



    public AddressListAdapter(Context context, List<AddressData> itemList)
    {
        this.itemList = itemList;
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view = inflater.inflate(R.layout.content_address, parent, false);
        viewHolder = new AddressListViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position)
    {
        AddressListViewHolder addressListViewHolder = (AddressListViewHolder) holder;


        addressListViewHolder.linearLayoutDelete.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

                Intent editAddress = new Intent(context, AddAddressActivity.class);
                context.startActivity(editAddress);


            }
        });

        addressListViewHolder.linearLayoutEdit.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

                Intent editAddress = new Intent(context, AddAddressActivity.class);
                context.startActivity(editAddress);


            }
        });


        addressListViewHolder.addressRadioButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
            {

                RadioButton checked_rb = (RadioButton) buttonView.findViewById(R.id.addressRadioButton);
                if (lastCheckedRB != null)
                {
                    lastCheckedRB.setChecked(false);
                }
                //store the clicked radiobutton
                lastCheckedRB = checked_rb;

            }
        });

        addressListViewHolder.linearAddress.setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick(View v)
            {

               /* Intent i = new Intent(context, CartCheckoutActivity.class);
                context.startActivity(i);*/
            }

        });

    }

    private void showMessage(String s) {
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

