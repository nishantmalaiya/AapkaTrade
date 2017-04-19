package com.example.pat.aapkatrade.user_dashboard.add_product;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by PPC21 on 20-Jan-17.
 */

public class Add_Product_Datas {


    String productname, category, subcategory, unit, deliver_duration, deliver_time, product_description;
    ArrayList<File> imagesfile = new ArrayList<File>();

    public Add_Product_Datas(String productname, String category, String subcategory, String unit, String deliver_duration, String deliver_time, String product_description, ArrayList<File> imagesfile) {
        this.productname = productname;
        this.category = category;
        this.subcategory = subcategory;
        this.unit = unit;
        this.deliver_duration = deliver_duration;
        this.deliver_time = deliver_time;
        this.product_description = product_description;
        this.imagesfile = imagesfile;

    }

    public String getProductname() {
        return productname;
    }

    public void setProductname(String productname) {
        this.productname = productname;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getSubcategory() {
        return subcategory;
    }

    public void setSubcategory(String subcategory) {
        this.subcategory = subcategory;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getDeliver_duration() {
        return deliver_duration;
    }

    public void setDeliver_duration(String deliver_duration) {
        this.deliver_duration = deliver_duration;
    }

    public String getDeliver_time() {
        return deliver_time;
    }

    public void setDeliver_time(String deliver_time) {
        this.deliver_time = deliver_time;
    }

    public String getProduct_description() {
        return product_description;
    }

    public void setProduct_description(String product_description) {
        this.product_description = product_description;
    }

    public ArrayList<File> getImagesfile() {
        return imagesfile;
    }

    public void setImagesfile(ArrayList<File> imagesfile) {
        this.imagesfile = imagesfile;
    }
}

