package com.example.pat.aapkatrade.filter.entity;

import com.example.pat.aapkatrade.general.entity.KeyValue;

/**
 * Created by PPC15 on 04-04-2017.
 */

public class FilterObject {
    public KeyValue id;
    public KeyValue name;
    public KeyValue count;

    public FilterObject(KeyValue id, KeyValue name, KeyValue count) {
        this.id = id;
        this.name = name;
        this.count = count;
    }

    public FilterObject() {
        this.id = new KeyValue("", "");
        this.name = new KeyValue("", "");
        this.count = new KeyValue("", "");
    }

    @Override
    public String toString() {
        return "FilterObject{" +
                "id='" + id.value + '\'' +
                ", name='" + name.value + '\'' +
                ", count='" + count.value + '\'' +
                '}';
    }
}
