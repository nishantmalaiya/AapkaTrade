package com.example.pat.aapkatrade.general.Utils;

import android.support.v4.util.ArrayMap;
import android.util.Log;

import com.example.pat.aapkatrade.filter.entity.FilterObject;
import com.example.pat.aapkatrade.general.entity.KeyValue;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by PPC15 on 10-04-2017.
 */

public class ParseUtils {
    public static HashMap<String, String> mapToJsonString(ArrayMap arrayMap){
        HashMap<String, String> map = new HashMap<>();
        for (Object obj :  arrayMap.keySet()){
            String key = (String) obj;
            ArrayList<String> arrayList = new ArrayList<>();
            ArrayList<FilterObject> tempArrayList = (ArrayList<FilterObject>) arrayMap.get(key);
            for (int i = 0; i < tempArrayList.size(); i++){
                FilterObject filterObject = tempArrayList.get(i);
                arrayList.add(filterObject.name.value.toString().replaceAll("\\\"", "\""));
            }
            map.put(key, new Gson().toJson(arrayList));

            Log.e("parsing", "OOOOOOOOOOOO@@@!!!        "+map.get(key));
        }
        return map;
    }

    public static String mapToJson(HashMap<String, String> hashMap){
        return new Gson().toJson(hashMap);
    }
}
