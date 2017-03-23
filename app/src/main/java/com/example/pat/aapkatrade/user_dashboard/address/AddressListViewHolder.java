package com.example.pat.aapkatrade.user_dashboard.address;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import com.example.pat.aapkatrade.R;

/**
 * Created by PPC09 on 24-Jan-17.
 */

public class AddressListViewHolder extends RecyclerView.ViewHolder {

    private TextView addressLine1;
    private TextView addressLine2;
    private TextView addressLine3;
    private RadioButton isSelected;
    LinearLayout linearAddress,linearLayoutEdit,linearLayoutDelete;
    RadioButton addressRadioButton;




    public AddressListViewHolder(View view)
    {
        super(view);

        linearLayoutEdit = (LinearLayout) view.findViewById(R.id.linearLayoutEdit);

        linearLayoutDelete = (LinearLayout) view.findViewById(R.id.linearLayoutDelete);

        linearAddress = (LinearLayout) view.findViewById(R.id.linearAddress);

        addressRadioButton = (RadioButton) view.findViewById(R.id.addressRadioButton);

        addressLine1 = (TextView) view.findViewById(R.id.addressLine1);
        addressLine2 = (TextView) view.findViewById(R.id.addressLine2);
        addressLine3 = (TextView) view.findViewById(R.id.addressLine3);
        isSelected = (RadioButton) view.findViewById(R.id.addressRadioButton);
    }
}
