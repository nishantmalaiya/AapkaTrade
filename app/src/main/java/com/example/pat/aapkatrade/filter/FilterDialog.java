package com.example.pat.aapkatrade.filter;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.util.ArrayMap;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.pat.aapkatrade.R;
import com.example.pat.aapkatrade.filter.adapter.FilterColumn1RecyclerAdapter;
import com.example.pat.aapkatrade.filter.adapter.FilterColumn2RecyclerAdapter;
import com.example.pat.aapkatrade.filter.entity.FilterObject;
import com.example.pat.aapkatrade.general.AppSharedPreference;
import com.example.pat.aapkatrade.general.CommonInterface;
import com.example.pat.aapkatrade.general.Utils.AndroidUtils;
import com.example.pat.aapkatrade.general.entity.KeyValue;
import com.example.pat.aapkatrade.general.progressbar.ProgressBarHandler;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by PPC09 on 25-Mar-17.
 */


public class FilterDialog extends Dialog {
    private AppSharedPreference app_sharedpreference;
    private ProgressBarHandler progress_handler;
    private Context context;
    private Button imgCLose;
    private RelativeLayout dialogue_toolbar;
    private RecyclerView recyclerViewColumn1, recyclerViewColumn2;
    private String categoryId;
    private TextView applyFilter, clearAll;
    private ArrayList<String> filterNameArrayList = new ArrayList<>();
    private ArrayList<FilterObject> filterValueArrayList = new ArrayList<>();
    private ArrayList<FilterObject> selectedValueArrayList = new ArrayList<>();
    private ArrayMap<String, ArrayList<FilterObject>> filterHashMap = null, selectedHashMap = new ArrayMap<>();
    private String key = "";
    private int count = 0;


    public FilterDialog(Context context, String category_id, ArrayMap<String, ArrayList<FilterObject>> filterHashMap) {
        super(context);
        this.context = context;
        this.categoryId = categoryId;
        this.filterHashMap = filterHashMap;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        setContentView(R.layout.dialog_filter);
        initView();
        getColumn1CallBack();
        getColumn2CallBack();

        applyFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                printHashMap();
            }
        });

        imgCLose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        clearAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (String key : filterHashMap.keySet()) {
                    filterValueArrayList = filterHashMap.get(key);
                    for (int i = 0; i < filterValueArrayList.size(); i++) {
                        if(filterValueArrayList.get(i).isChecked)   {
                            filterHashMap.get(key).get(i).isChecked = false;
                        }
                    }
                }

                setUpColumn1Adapter();
                setUpColumn2Adapter(filterHashMap.keyAt(0), true);
            }
        });
    }

    private void printHashMap() {
        syncHashMap();
        AndroidUtils.showErrorLog(context,"Size printHashMap"+ selectedHashMap.size());
        for (String key : selectedHashMap.keySet()) {
            AndroidUtils.showErrorLog(context,"Main printHashMap     "+ key);
            selectedValueArrayList = selectedHashMap.get(key);
            for (int i = 0; i < selectedValueArrayList.size(); i++) {
                AndroidUtils.showErrorLog(context, " printHashMap Key : " + selectedValueArrayList.get(i).name.key + " printHashMap Value : " + selectedValueArrayList.get(i).name.value);
            }
        }

    }


    private void syncHashMap() {
        AndroidUtils.showErrorLog(context,"syncHashMap printHashMap"+ selectedHashMap.size());
        for (String key : filterHashMap.keySet()) {
            syncEachArrayList(key);
        }
    }

    private void syncEachArrayList(String key) {
        AndroidUtils.showErrorLog(context,"syncHashMap printHashMap"+ selectedHashMap.size());
        ArrayList<FilterObject> tempList = new ArrayList<>();
        selectedHashMap.put(key, tempList);
        tempList = filterHashMap.get(key);
        int count = 0;
        for (int i = 0; i < tempList.size(); i++) {
            if(tempList.get(i).isChecked){
                selectedHashMap.get(key).add(filterHashMap.get(key).get(i));
            }else {
                count++;
            }
        }
        if(count == tempList.size()){
            selectedHashMap.put(key, tempList);
        }
    }
    private void initView() {
        app_sharedpreference = new AppSharedPreference(context);
        progress_handler = new ProgressBarHandler(context);
        dialogue_toolbar = (RelativeLayout) findViewById(R.id.dialogue_toolbar);
        AndroidUtils.setBackgroundSolid(dialogue_toolbar, context, R.color.green, 8);
        imgCLose = (Button) findViewById(R.id.imgCLose);
        applyFilter = (TextView) findViewById(R.id.applyFilter);
        clearAll = (TextView) findViewById(R.id.clearAll);
        recyclerViewColumn1 = (RecyclerView) findViewById(R.id.recyclerView1);
        recyclerViewColumn2 = (RecyclerView) findViewById(R.id.recyclerView2);
        recyclerViewColumn1.setNestedScrollingEnabled(false);
        recyclerViewColumn2.setNestedScrollingEnabled(false);
        decodeData(filterHashMap);

        setUpColumn1Adapter();
        setUpColumn2Adapter(key, true);
    }

    private void showMessage(String msg) {
        AndroidUtils.showSnackBar((LinearLayout) findViewById(R.id.dialog_filter_root), msg);
    }


    private void decodeData(ArrayMap<String, ArrayList<FilterObject>> filterHashMap) {
        for (String key : filterHashMap.keySet()) {
            filterNameArrayList.add(key);
            AndroidUtils.showErrorLog(context,"Main decoded     "+ key);
            filterValueArrayList = filterHashMap.get(key);
            for (int i = 0; i < filterValueArrayList.size(); i++) {
                AndroidUtils.showErrorLog(context, " decoded Key : " + filterValueArrayList.get(i).name.key + " decoded Value : " + filterValueArrayList.get(i).name.value);
            }
        }
        AndroidUtils.showErrorLog(context,"Size decoded"+ filterHashMap.size());
    }

    private void setUpColumn1Adapter() {
        AndroidUtils.showErrorLog(context, "entered" + filterNameArrayList.size());
        if (filterNameArrayList.size() > 0) {
            AndroidUtils.showErrorLog(context, "entered");
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(context);
            FilterColumn1RecyclerAdapter filterNameAdapter = new FilterColumn1RecyclerAdapter(context, filterNameArrayList);
            recyclerViewColumn1.setAdapter(filterNameAdapter);
            recyclerViewColumn1.setLayoutManager(mLayoutManager);
        }
    }

    private void setUpColumn2Adapter(String key1, boolean isInitialCall) {
        if (key1.equals("") && isInitialCall) {
            key1 = filterNameArrayList.get(0);
        }
        AndroidUtils.showErrorLog(context, "setUpColumn2Adapter key is " + key1);
        if (filterValueArrayList.size() > 0) {
            AndroidUtils.showErrorLog(context, "entered2");
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(context);
            FilterColumn2RecyclerAdapter filterValueAdapter = new FilterColumn2RecyclerAdapter(context, key1, filterHashMap.get(key1));
            recyclerViewColumn2.setAdapter(filterValueAdapter);
            recyclerViewColumn2.setLayoutManager(mLayoutManager);
        }
    }


    private void getColumn1CallBack() {
        AndroidUtils.showErrorLog(context, "getColumn1CallBack1");
        FilterColumn1RecyclerAdapter.commonInterface = new CommonInterface() {
            @Override
            public Object getData(Object object) {
                key = (String) object;
                setUpColumn2Adapter(key, false);
                return null;
            }
        };
        count = 0;
    }

    private void getColumn2CallBack() {
        AndroidUtils.showErrorLog(context, "getColumn2CallBack2");
        FilterColumn2RecyclerAdapter.commonInterface = new CommonInterface() {
            @Override
            public Object getData(Object object) {
                KeyValue keyValue = (KeyValue) object;
                String key = (String) keyValue.key;
                selectedValueArrayList = (ArrayList<FilterObject>) keyValue.value;
//                selectedHashMap.put(key, selectedValueArrayList);

                filterValueArrayList = filterHashMap.get(key);
                for (int i = 0; i < filterValueArrayList.size(); i++) {
                    if(filterValueArrayList.get(i).isChecked && !filterHashMap.get(key).get(i).isChecked){
                        filterHashMap.get(key).get(i).isChecked = true;
                    }
                }

//                AndroidUtils.showErrorLog(context, "HashMap Key : " + key + " HashMap Key Size : " + selectedHashMap.get(key).size());
                return null;
            }
        };
    }

}
