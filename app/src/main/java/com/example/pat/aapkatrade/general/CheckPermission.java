package com.example.pat.aapkatrade.general;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import com.example.pat.aapkatrade.general.progressbar.ProgressBarHandler;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by PPC21 on 20-Jan-17.
 */

public class CheckPermission {


    public static final int MULTIPLE_PERMISSIONS = 10; // code you want.

  public static   String[] permissions= new String[]{
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA,
          Manifest.permission.ACCESS_WIFI_STATE,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.READ_SMS
          ,Manifest.permission.RECEIVE_SMS
            ,Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.ACCESS_NETWORK_STATE};


    public static   boolean checkPermissions(Activity c) {


        int result;
        List<String> listPermissionsNeeded = new ArrayList<>();
        for (String p:permissions) {
            result = ContextCompat.checkSelfPermission(c,p);
            if (result != PackageManager.PERMISSION_GRANTED) {
                listPermissionsNeeded.add(p);
            }
        }
        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(c, listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]),MULTIPLE_PERMISSIONS );

            return false;
        }
        else{

        }

        return true;
    }


}
