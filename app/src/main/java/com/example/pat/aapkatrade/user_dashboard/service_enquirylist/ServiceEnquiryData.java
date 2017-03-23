package com.example.pat.aapkatrade.user_dashboard.service_enquirylist;

/**
 * Created by PPC16 on 10-Mar-17.
 */

public class ServiceEnquiryData
{

    String service_enquiry_id, product_name, product_price,  user_name,user_email,user_mobile,description,created_date,category_name;

    public ServiceEnquiryData(String service_enquiry_id, String product_name, String product_price, String user_name,String user_email,String user_mobile,String description,String created_date,String category_name)
    {

        this.service_enquiry_id = service_enquiry_id;
        this.product_name = product_name;
        this.product_price = product_price;
        this.user_name = user_name;
        this.user_email = user_email;
        this.user_mobile = user_mobile;
        this.description = description;
        this.created_date = created_date;
        this.category_name = category_name;
    }


}
