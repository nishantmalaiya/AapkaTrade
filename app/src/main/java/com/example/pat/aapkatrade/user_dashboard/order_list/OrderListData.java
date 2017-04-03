package com.example.pat.aapkatrade.user_dashboard.order_list;

/**
 * Created by PPC16 on 17-Jan-17.
 */

public class OrderListData
{
    String order_id, product_name, product_price,product_qty,address,email,buyersmobile,buyersname,company_name,status,created_at,product_image;

    public OrderListData(String order_id, String product_name, String product_price, String product_qty, String address, String email, String buyersmobile, String buyersname, String company_name,String status,String created_at,String product_image)
    {
        this.order_id = order_id;
        this.product_name = product_name;
        this.product_price = product_price;
        this.product_qty = product_qty;
        this.address = address;
        this.email = email;
        this.buyersmobile = buyersmobile;
        this.buyersname = buyersname;
        this.company_name = company_name;
        this.status = status;
        this.created_at = created_at;
        this.product_image = product_image;
    }
}


