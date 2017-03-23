package com.example.pat.aapkatrade.user_dashboard.vender_detail;

/**
 * Created by PPC16 on 09-Feb-17.
 */

public class VendorData {

    String vender_id, vender_name,vender_last_name,vender_creation_date,mobile,email,bussiness_type;

    VendorData(String vender_id, String vender_name, String vender_last_name ,String bussiness_type,String mobile ,String email,String vender_creation_date)
    {

        this.vender_id = vender_id;
        this.vender_name = vender_name;
        this.vender_last_name = vender_last_name;
        this.bussiness_type= bussiness_type;
        this.mobile = mobile;
        this.email = email;
        this.vender_creation_date = vender_creation_date;

    }
}
