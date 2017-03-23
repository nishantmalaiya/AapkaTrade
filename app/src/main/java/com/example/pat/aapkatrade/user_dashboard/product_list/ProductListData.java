package com.example.pat.aapkatrade.user_dashboard.product_list;

/**
 * Created by PPC16 on 11-Jan-17.
 */

public class ProductListData {

    public String user_id, product_id, product_name, product_price, product_cross_price, product_image, category_name, state, delivery_distance, description, delivery_area_name;

    public ProductListData(String user_id, String product_id, String product_name, String product_price, String product_cross_price, String product_image, String category_name, String state, String description, String delivery_distance, String delivery_area_name) {
        this.user_id = user_id;
        this.product_id = product_id;
        this.product_name = product_name;
        this.product_price = product_price;
        this.product_cross_price = product_cross_price;
        this.product_image = product_image;
        this.category_name = category_name;
        this.state = state;
        this.description = description;
        this.delivery_distance = delivery_distance;
        this.delivery_area_name = delivery_area_name;
    }
}
