package com.example.pat.aapkatrade.general.Utils.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.TextView;

import com.example.pat.aapkatrade.Home.CommonHolder_grid;
import com.example.pat.aapkatrade.R;
import com.example.pat.aapkatrade.filter.entity.FilterObject;
import com.example.pat.aapkatrade.filter.viewholder.FilterColumn2ViewHolder;
import com.example.pat.aapkatrade.general.CommonInterface;
import com.example.pat.aapkatrade.general.Utils.AndroidUtils;
import com.example.pat.aapkatrade.general.entity.KeyValue;

import java.util.ArrayList;

/**
 * Created by PPC17 on 13-Apr-17.
 */

public class CustomMultipleCheckBoxAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private boolean isRadioButton;
    private ArrayList<KeyValue> arrayList;
    private ArrayList<KeyValue> selectedValueList = new ArrayList<>();
    private LayoutInflater inflter;
    public static CommonInterface commonInterface_dynamic_checkbox;
    View v;
    private RadioButton lastRadioButton = null;


    private static int lastCheckedPos = 0;


    public CustomMultipleCheckBoxAdapter(Context context, ArrayList arrayList) {
        this.context = context;
        this.arrayList = arrayList;
        this.inflter = LayoutInflater.from(context);
    }

    public CustomMultipleCheckBoxAdapter(Context context, ArrayList arrayList, boolean isRadioButton) {
        this.context = context;
        this.arrayList = arrayList;
        this.inflter = LayoutInflater.from(context);
        this.isRadioButton = isRadioButton;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        FilterColumn2ViewHolder viewHolder2;
        v = inflater.inflate(R.layout.row_filter_column2, parent, false);
        if (isRadioButton) {
            viewHolder2 = new FilterColumn2ViewHolder(v, isRadioButton);

        } else {
            viewHolder2 = new FilterColumn2ViewHolder(v);
        }

        return viewHolder2;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {

        final FilterColumn2ViewHolder viewHolder2 = new FilterColumn2ViewHolder(v, isRadioButton);
        AndroidUtils.showErrorLog(context, arrayList.get(position).value.toString());
        final CheckBox dynamic_form_checked = viewHolder2.checkFilterValue;

        final RadioButton dynamic_radio_button = viewHolder2.radioButton;
       // final TextView dynamic_form_textview = viewHolder2.filterValue;

        //dynamic_form_textview.setText(arrayList.get(position).value.toString());

        if (isRadioButton) {
            viewHolder2.radioButton.setText(arrayList.get(position).value.toString());
            dynamic_radio_button.setChecked(false);



            dynamic_radio_button.setTag(new Integer(position));

            if (position == 0 && dynamic_radio_button.isChecked()) {
                lastRadioButton = dynamic_radio_button;
                lastCheckedPos = 0;
            }


            dynamic_radio_button.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    onRadioClickListener(dynamic_radio_button);
                }
            });


        } else {
            dynamic_form_checked.setChecked(false);
            dynamic_form_checked.setText(arrayList.get(position).value.toString());
//
            dynamic_form_checked.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        addItemsToList(position);
                    } else {
                        if(selectedValueList.contains(arrayList.get(position)))
                        selectedValueList.remove(arrayList.get(position));
                    }
                //    commonInterface_dynamic_checkbox.getData(selectedValueList.size() <= 0 ? new KeyValue("", "") : selectedValueList);
                }
            });
        }


    }

    private void onRadioClickListener(RadioButton dynamic_radio_button) {

        RadioButton radioButton = dynamic_radio_button;
        int clickedPos = ((Integer) radioButton.getTag()).intValue();

        if (radioButton.isChecked()) {
            if (lastRadioButton != null) {
                lastRadioButton.setChecked(false);

            }

            lastRadioButton = radioButton;
            lastCheckedPos = clickedPos;
        } else {
            lastRadioButton = null;
        }

//                    commonInterface_dynamic_checkbox.getData(selectedValueList.size()<=0?new KeyValue("", ""): selectedValueList);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    private void addItemsToList(int position) {
        AndroidUtils.showErrorLog(context, "Item No Selected ;  " + position);
        if (!selectedValueList.contains(arrayList.get(position))) {
            selectedValueList.add(arrayList.get(position));
            AndroidUtils.showErrorLog(context, "Item No Added ;  " + position + "  new size " + selectedValueList.size());
        }
    }
}