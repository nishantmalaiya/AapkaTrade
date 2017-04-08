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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof FilterObject)) return false;

        FilterObject that = (FilterObject) o;

        if (!id.equals(that.id)) return false;
        if (!name.equals(that.name)) return false;
        if (!count.equals(that.count)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + name.hashCode();
        result = 31 * result + count.hashCode();
        return result;
    }
}
