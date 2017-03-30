package com.example.pat.aapkatrade.Home.registration.entity;

/**
 * Created by PPC09 on 19-Jan-17.
 */

public class City {
    public String cityId;
    public String cityName;
    public boolean isChecked;

    public City(String cityId, String cityName) {
        this.cityId = cityId;
        this.cityName = cityName;
    }

    public City(String cityId, String cityName, boolean isChecked) {
        this.cityId = cityId;
        this.cityName = cityName;
        this.isChecked = isChecked;
    }
}
