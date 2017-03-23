package com.example.pat.aapkatrade.categories_tab;

/**
 * Created by PPC16 on 16-Jan-17.
 */

public class CategoriesListData {

    String product_id, product_name, product_price, product_cross_price, product_image,product_location;

    public CategoriesListData(String product_id, String product_name, String product_price, String product_cross_price, String product_image,String product_location){

        this.product_id = product_id;
        this.product_name = product_name;
        this.product_price = product_price;
        this.product_cross_price = product_cross_price;
        this.product_image = product_image;
        this.product_location=product_location;
    }
}
