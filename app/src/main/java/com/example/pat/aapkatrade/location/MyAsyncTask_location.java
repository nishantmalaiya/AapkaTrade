package com.example.pat.aapkatrade.location;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Looper;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

import com.example.pat.aapkatrade.general.progressbar.ProgressBarHandler;
import com.example.pat.aapkatrade.search.Search;

import java.util.List;

import static android.content.Context.LOCATION_SERVICE;

/**
 * Created by PPC17 on 24-Mar-17.
 */

public class MyAsyncTask_location extends AsyncTask<Void, Void, Void> implements LocationListener {
    Dialog progress;
    private String providerAsync;
    int clicked_item=0;
    private LocationManager locationManagerAsync;

    String thikanaAsync = "Scan sms for location";


    Geocoder GeocoderAsync;


String AddressAsync;
    private  Context ContextAsync;

    // Flag for GPS status
    boolean isGPSEnabled = false;

    // Flag for network status
    boolean isNetworkEnabled = false;

    // Flag for GPS status
    boolean canGetLocation = false;

    Location location; // Location
   public static double latitude; // Latitude
    public static double longitude; // Longitude

    // The minimum distance to change Updates in meters
    private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 10; // 10 meters

    // The minimum time between updates in milliseconds
    private static final long MIN_TIME_BW_UPDATES = 1000 * 60 * 1; // 1 minute

    // Declaring a Location Manager
    protected LocationManager locationManager;

    ProgressBarHandler progressBarHandler;

    String class_name;


    public MyAsyncTask_location(Context context,String class_name) {
        this.ContextAsync = context;
        this.class_name=class_name;
        progressBarHandler=new ProgressBarHandler(ContextAsync);
    }




    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progressBarHandler.show();
       // progress = ProgressDialog.show(ContextAsync, "Loading data", "Please wait...");

    }


    @Override
    protected Void doInBackground(Void... arg0) {
        if (Looper.myLooper() == null) {
            Looper.prepare();

        }




        locationManager = (LocationManager) ContextAsync
                .getSystemService(LOCATION_SERVICE);

        // getting GPS status
        isGPSEnabled = locationManager
                .isProviderEnabled(LocationManager.GPS_PROVIDER);

        // getting network status
        isNetworkEnabled = locationManager
                .isProviderEnabled(LocationManager.NETWORK_PROVIDER);

        if (!isGPSEnabled && !isNetworkEnabled) {
            // no network provider is enabled
        } else {
            this.canGetLocation = true;
            // First get location from Network Provider
            if (isNetworkEnabled) {
                if (ActivityCompat.checkSelfPermission(ContextAsync, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(ContextAsync, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return null;
                }
                locationManager.requestLocationUpdates(
                        LocationManager.NETWORK_PROVIDER,
                        MIN_TIME_BW_UPDATES,
                        MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
                Log.e("Network", "Network");
                if (locationManager != null) {
                    location = locationManager
                            .getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

                    latitude = location.getLatitude();
                    longitude = location.getLongitude();

                    Log.e("latlng_asynctask",latitude+"**"+longitude+"");
                }
            }
            // if GPS Enabled get lat/long using GPS Services
            if (isGPSEnabled) {
                if (location == null) {
                    locationManager.requestLocationUpdates(
                            LocationManager.GPS_PROVIDER,
                            MIN_TIME_BW_UPDATES,
                            MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
                    Log.e("GPS Enabled", "GPS Enabled");
                    if (locationManager != null) {
                        location = locationManager
                                .getLastKnownLocation(LocationManager.GPS_PROVIDER);
                        if (location != null) {
                            latitude = location.getLatitude();
                            longitude = location.getLongitude();
                            Log.e("latlng_asynctask",latitude+"**"+longitude+"");
                        }
                    }
                }
            }

            Log.e("latlng_asynctask",latitude+"**"+longitude+"");
        List<Address> addresses = null;
        GeocoderAsync = new Geocoder(ContextAsync, latitude,longitude);

        try {
           AddressAsync=GeocoderAsync.get_state_name();
            Log.e("AddressAsync",AddressAsync);
        } catch (Exception e) {
            e.printStackTrace();

        }
        }









//
//        // TODO Auto-generated method stub
//        locationManagerAsync = (LocationManager) ContextAsync.getSystemService(ContextAsync.LOCATION_SERVICE);
//
//
//        Criteria criteria = new Criteria();
//        criteria.setAccuracy(Criteria.ACCURACY_COARSE);
//        criteria.setCostAllowed(false);
//        criteria.setPowerRequirement(Criteria.NO_REQUIREMENT);
//        providerAsync = locationManagerAsync.getBestProvider(criteria, false);
//
//
//        if (locationManagerAsync.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
//            providerAsync = LocationManager.GPS_PROVIDER;
//
//            Log.e("providerAsync_gps",providerAsync.toString());
//        } else if (locationManagerAsync.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
//            providerAsync = LocationManager.NETWORK_PROVIDER;
//            AlertDialog.Builder alert = new AlertDialog.Builder(ContextAsync);
//            alert.setTitle("GPS is disabled in the settings!");
//            alert.setMessage("It is recomended that you turn on your device's GPS and restart the app so the app can determine your location more accurately!");
//            alert.setPositiveButton("OK", null);
//            alert.show();
//            Log.e("providerAsync_network",providerAsync.toString());
//        } else if (locationManagerAsync.isProviderEnabled(LocationManager.PASSIVE_PROVIDER)) {
//            providerAsync = LocationManager.PASSIVE_PROVIDER;
//            Log.e("providerAsync_passive",providerAsync.toString());
//
//            location = locationManagerAsync.getLastKnownLocation(providerAsync);
//            // Initialize the location fields
//            if (location != null) {
//                //  System.out.println("Provider " + provider + " has been selected.");
//                latAsync = location.getLatitude();
//                lonAsync = location.getLongitude();
//
//
//            } else {
//                //Toast.makeText(ContextAsync, " Locationnot available", Toast.LENGTH_SHORT).show();
//            }
//
//            //Toast.makeText(ContextAsync, "Switch On Data Connection!!!!", Toast.LENGTH_LONG).show();
//        }
//
//        if (ActivityCompat.checkSelfPermission(ContextAsync, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(ContextAsync, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//            // TODO: Consider calling
//            //    ActivityCompat#requestPermissions
//            // here to request the missing permissions, and then overriding
//            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
//            //                                          int[] grantResults)
//            // to handle the case where the user grants the permission. See the documentation
//            // for ActivityCompat#requestPermissions for more details.
//            return null;
//        }
//        location = locationManagerAsync.getLastKnownLocation(providerAsync);
//        // Initialize the location fields
//        if (location != null) {
//            //  System.out.println("Provider " + provider + " has been selected.");
//            latAsync = location.getLatitude();
//            lonAsync = location.getLongitude();
//
//
//        } else {
//            //Toast.makeText(ContextAsync, " Locationnot available", Toast.LENGTH_SHORT).show();
//        }
//
//        Log.e("latlng_asynctask",latAsync+"**"+lonAsync+"");
//        List<Address> addresses = null;
//        GeocoderAsync = new Geocoder(ContextAsync, latAsync,lonAsync);
//
//        try {
//            String AddressAsync=GeocoderAsync.get_state_name();
//        } catch (Exception e) {
//            e.printStackTrace();
//            AddressAsync = "Refresh for the address";
//        }


        return null;
    }

    @Override
    protected void onPostExecute(Void result) {

        super.onPostExecute(result);
        progressBarHandler.hide();

        Goto_search(class_name);



       // onLocationChanged(location);
        Log.e("latAsync_lonAsync_post", latitude + "_" + longitude);
//        Intent intentAsync = new Intent(ContextAsync,Search.class);
//        intentAsync.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//        intentAsync.putExtra("calculated_Lat", latAsync);
//        intentAsync.putExtra("calculated_Lon", lonAsync);
//        intentAsync.putExtra("calculated_address", AddressAsync);
//
//        ContextAsync.startActivity(intentAsync);

    }

    private void Goto_search(String class_name) {

if(class_name.contains("homeactivity"))
{

    Intent intentAsync = new Intent(ContextAsync,Search.class);
    intentAsync.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
    intentAsync.putExtra("classname","homeactivity");
    intentAsync.putExtra("state_name",AddressAsync);

    ContextAsync.startActivity(intentAsync);

    ((Activity)ContextAsync).finish();


}
else
{
    Log.e("class not found","class not found");
}

    }


    @Override
    public void onLocationChanged(Location location) {
        // TODO Auto-generated method stub
        if (ActivityCompat.checkSelfPermission(ContextAsync, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(ContextAsync, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        locationManagerAsync.requestLocationUpdates(location.getProvider(), 0, 0, this);
    }

    @Override
    public void onProviderDisabled(String provider) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onProviderEnabled(String provider) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        // TODO Auto-generated method stub

    }
}