package com.example.pat.aapkatrade.user_dashboard.product_list;

/**
 * Created by PPC16 on 11-Jan-17.
 */

public class ProductListData {

    public String user_id, product_id, product_name, product_price, product_cross_price, product_image, category_name, state, delivery_distance, description, delivery_area_name,
            company_id,distance_id,country_id,state_id,city_id,category_id, sub_category_id,unit_id;

    public ProductListData(String user_id, String product_id, String product_name, String product_price, String product_cross_price, String product_image, String category_name, String state, String description, String delivery_distance, String delivery_area_name,String company_id,String distance_id,String country_id,String state_id,String city_id,String category_id, String  sub_category_id, String unit_id)
    {

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
        this.company_id = company_id;
        this.distance_id = distance_id;
        this.country_id = country_id;
        this.state_id = state_id;
        this.city_id = city_id;
        this.category_id = category_id;
        this.sub_category_id = sub_category_id;
        this.unit_id =unit_id;



    }

}
