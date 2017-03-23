package com.example.pat.aapkatrade.user_dashboard;

/**
 * Created by PPC16 on 10-Jan-17.
 */

public class DashboardData {

    public String dashboard_id, dashboard_name,quantities;
    public int image, color;
    public boolean isList;


    DashboardData(String dashboard_id, String dashboard_name, int image, int color, boolean isList,String quantities) {
        this.dashboard_id = dashboard_id;
        this.dashboard_name = dashboard_name;
        this.image = image;
        this.color = color;
        this.isList = isList;
        this.quantities=quantities;
    }

    public DashboardData(String dashboard_id, String dashboard_name, int image, int color, boolean isList) {
        this.dashboard_id = dashboard_id;
        this.dashboard_name = dashboard_name;
        this.image = image;
        this.color = color;
        this.isList = isList;
    }
}
