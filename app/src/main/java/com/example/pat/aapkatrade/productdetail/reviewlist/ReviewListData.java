package com.example.pat.aapkatrade.productdetail.reviewlist;

/**
 * Created by PPC16 on 4/8/2017.
 */

public class ReviewListData
{

    String email,title, message_description, username,rating,created_date;

    public ReviewListData(String email,String username, String message_description, String rating, String title,String created_date)
    {
        this.email = email;
        this.username = username;
        this.message_description = message_description;
        this.rating = rating;
        this.title = title;
        this.created_date = created_date;
    }

}
