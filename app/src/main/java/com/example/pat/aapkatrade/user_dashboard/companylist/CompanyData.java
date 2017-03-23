package com.example.pat.aapkatrade.user_dashboard.companylist;

/**
 * Created by PPC16 on 10-Jan-17.
 */

public class CompanyData {

    String company_id, company_name, company_creation_date,address,description,sEmail;
    boolean clicked;

    CompanyData(String company_id, String company_name, String company_creation_date,boolean clicked,String address,String description,String sEmail)
    {

        this.company_id = company_id;
        this.company_name = company_name;
        this.company_creation_date = company_creation_date;
        this.clicked=clicked;
        this.address = address;
        this.description = description;
        this.sEmail = sEmail;

    }
}
