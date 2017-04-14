package com.example.pat.aapkatrade.user_dashboard.add_product;

import com.example.pat.aapkatrade.general.entity.KeyValue;

import java.util.ArrayList;

/**
 * Created by PPC17 on 12-Apr-17.
 */

public class Dynamic_form_data_entity {
    public KeyValue type,title;
    public ArrayList<KeyValue> values_arraylist=new ArrayList<>();

    public Dynamic_form_data_entity() {
        this.type=new KeyValue("", "");
        this.title=new KeyValue("", "");

    }
}
