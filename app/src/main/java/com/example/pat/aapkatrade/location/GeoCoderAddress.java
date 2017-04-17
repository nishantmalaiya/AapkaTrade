package com.example.pat.aapkatrade.location;

import android.content.Context;
import android.location.Address;
import android.util.Log;

import com.example.pat.aapkatrade.general.entity.KeyValue;
import com.example.pat.aapkatrade.general.progressbar.ProgressBarHandler;
import com.google.android.gms.maps.model.LatLng;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

/**
 * Created by PPC17 on 17-Mar-17.
 */

public class GeoCoderAddress {

    Context c;
    public double latitude, longitude;
    String result ;
    HashMap<Object,String > geocoder_address_list=new HashMap<>();
   // ProgressBarHandler progressBarHandler;
    public GeoCoderAddress(Context c, double latitude, double longitude) {
        this.c = c;
        this.latitude = latitude;
        this.longitude = longitude;
//        progressBarHandler = new ProgressBarHandler(c);
        get_state_name();

    }




    public HashMap<Object,String > get_state_name() {
       // progressBarHandler.show();
        android.location.Geocoder geocoder = new android.location.Geocoder(c, Locale.getDefault());


        List<Address> addressList = null;
        try {
            addressList = geocoder.getFromLocation(
                    latitude, longitude, 1);

            if (addressList != null && addressList.size() > 0) {
                Address address = addressList.get(0);


                result = address.getAdminArea();



                geocoder_address_list.put(AddressEnum.STATE, address.getAdminArea());
                geocoder_address_list.put(AddressEnum.CITY, address.getLocality());
                geocoder_address_list.put(AddressEnum.ADDRESS, address.getCountryName());
                geocoder_address_list.put(AddressEnum.PINCODE, address.getPostalCode());

               // progressBarHandler.hide();

            }
            else {

                Log.e("addressList_is_empty","addressList_is_empty");
            }


        } catch (IOException e) {
            e.printStackTrace();
           // progressBarHandler.hide();
        }
        return geocoder_address_list;
    }






}
