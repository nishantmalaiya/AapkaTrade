package com.example.pat.aapkatrade.user_dashboard.network;

/**
 * Created by PPC16 on 03-Mar-17.
 */

public class NetworkData
{

    String  user_name,user_last_name,mobile,email,creation_date;

    NetworkData(String user_name, String user_last_name ,String email,String mobile,String creation_date)
    {
        this.user_name = user_name;
        this.user_last_name = user_last_name;
        this.mobile = mobile;
        this.email = email;
        this.creation_date = creation_date;

    }

}
