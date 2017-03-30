package com.example.pat.aapkatrade.filter;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.pat.aapkatrade.Home.HomeActivity;
import com.example.pat.aapkatrade.R;
import com.example.pat.aapkatrade.categories_tab.CategoriesListData;
import com.example.pat.aapkatrade.categories_tab.CategoryListActivity;
import com.example.pat.aapkatrade.general.AppSharedPreference;
import com.example.pat.aapkatrade.general.TaskCompleteReminder;
import com.example.pat.aapkatrade.general.Utils.AndroidUtils;
import com.example.pat.aapkatrade.general.progressbar.ProgressBarHandler;
import com.example.pat.aapkatrade.search.Search;
import com.example.pat.aapkatrade.search.common_category_search;
import com.example.pat.aapkatrade.search.common_state_search;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import java.util.ArrayList;

/**
 * Created by PPC09 on 25-Mar-17.
 */


public class FilterDialog extends Dialog {
    private AppSharedPreference app_sharedpreference;
    private ProgressBarHandler progress_handler;
    private Context context;
    private ArrayList<CategoriesListData> productListData = new ArrayList<>();
    private Button imgCLose;
    private RelativeLayout dialogue_toolbar;
    private LinearLayout categoryFilter;
    private String categoryId, stateId, cityId;
    private TextView applyFilter, clearAll;
    private Intent intent;
    String search_name;
    public ArrayList<common_category_search> common_category_searchlist;
    public static TaskCompleteReminder taskCompleteReminder = null;
    String classname ;
    Spinner category_list,state_list;
    ArrayList<String> categorynames=new ArrayList<>();
    ArrayList<String> categoryids=new ArrayList<>();
    ArrayList<String> statenames=new ArrayList<>();
    ArrayList<String> stateids=new ArrayList<>();


    common_state_search common_state_search;


    public FilterDialog(Context context, String categoryId) {
        super(context);
        this.context = context;
        this.categoryId = categoryId;

    }

    public FilterDialog(Context context, String search_name, ArrayList<common_category_search> common_category_searchlist, String classname) {
        super(context);
        this.context = context;
        this.search_name = search_name;
        this.common_category_searchlist = common_category_searchlist;
        this.classname=classname;

        Log.e("classname", classname);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        setContentView(R.layout.dialog_filter);
        initView();
        setUpData();

        applyFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDataByCategory();
//
            }
        });

        imgCLose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FilterDialog.this.hide();
            }
        });
    }

    private void setUpData() {

    }

    private void showMessage(String msg) {
        AndroidUtils.showSnackBar((LinearLayout) findViewById(R.id.dialog_filter_root), msg);
    }

    private void initView() {
        app_sharedpreference = new AppSharedPreference(context);
        progress_handler = new ProgressBarHandler(context);
        dialogue_toolbar = (RelativeLayout) findViewById(R.id.dialogue_toolbar);
        AndroidUtils.setBackgroundSolid(dialogue_toolbar, context, R.color.green, 8);
        imgCLose = (Button) findViewById(R.id.imgCLose);
        categoryFilter = (LinearLayout) findViewById(R.id.categoryFilter);

        category_list=(Spinner)findViewById(R.id.spCategory);
        state_list=(Spinner)findViewById(R.id.spStateCategory);
        if (classname.contains("search")) {
            if (categoryFilter.getVisibility() !=View.VISIBLE) {
                categoryFilter.setVisibility(View.VISIBLE);

            }
        } else {
            categoryFilter.setVisibility(View.GONE);
        }
        applyFilter = (TextView) findViewById(R.id.applyFilter);



        if(classname.contains("search"))
        {
            setup_search_functionality();





        }

    }

    private void setup_search_functionality() {

setup_category_spinner();
        Log.e("search_name_filter",search_name);
        Log.e("common_category_search",""+common_category_searchlist);




    }

    private void setup_category_spinner() {

        for(int i=0;i<common_category_searchlist.size();i++)
        {String cat_id=common_category_searchlist.get(i).cat_id;
            categoryids.add(cat_id);
            String cat_name=common_category_searchlist.get(i).catname;
            categorynames.add(cat_name);
        }

        ArrayAdapter spinnerArrayAdapter = new ArrayAdapter(context,android.R.layout.simple_spinner_item, categorynames);
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);

        category_list.setAdapter(spinnerArrayAdapter);

        category_list.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                call_category_to_state_webservice(context,categoryids.get(position),search_name);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    }

    private void call_category_to_state_webservice(Context c,String category_id,String search_name) {

        statenames.clear();
        stateids.clear();
        Log.e("message_data---", "calledd with category id "+category_id);
        Log.e("message_data---URL", context.getResources().getString(R.string.webservice_base_url) + "/search");
        progress_handler.show();
        Ion.with(c)
                .load(context.getResources().getString(R.string.webservice_base_url) + "/search")
                .setHeader("authorization", "xvfdbgfdhbfdhtrh54654h54ygdgerwer3")
//                .setBodyParameter("authorization", "xvfdbgfdhbfdhtrh54654h54ygdgerwer3")
                .setBodyParameter("category_id", category_id)
                .setBodyParameter("name", search_name)
                .setBodyParameter("apply", "1")
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {
//                        Log.e("message_data---", result==null?e.toString():result);

                        progress_handler.hide();

                        if (result == null) {
                            Log.e("message_data---", "null found");

                        } else {







                            JsonObject jsonObject = result.getAsJsonObject();


                            JsonArray jsonarray_states = jsonObject.getAsJsonArray("states");


                            for (int l = 0; l < jsonarray_states.size(); l++) {

                                JsonObject jsonObject_result = (JsonObject) jsonarray_states.get(l);
                                String state_id = jsonObject_result.get("state_id").getAsString();
                                String statename = jsonObject_result.get("statename").getAsString();
                                String countprod = jsonObject_result.get("countprod").getAsString();
                                statenames.add(statename);
                                stateids.add(state_id);

                               // common_state_search=  new common_state_search(state_id, statename, countprod);





                            }

                            ArrayAdapter spinnerArrayAdapter = new ArrayAdapter(context,android.R.layout.simple_spinner_item, statenames);
                            spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);

                            state_list.setAdapter(spinnerArrayAdapter);



                            state_list.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {



                                    call_state_to_city_webservice();

                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> parent) {

                                }
                            });







                           Log.e("result_state_list",result.toString());

                           // dismiss();






                        }





                    }

                });






    }

    private void call_state_to_city_webservice() {







    }

    private void getDataByCategory() {
        Log.e("message_data---", "calledd with category id "+categoryId);
        Log.e("message_data---URL", context.getResources().getString(R.string.webservice_base_url) + "/productlist");
        progress_handler.show();
        Ion.with(context)
                .load(context.getResources().getString(R.string.webservice_base_url) + "/productlist")
                .setHeader("authorization", "xvfdbgfdhbfdhtrh54654h54ygdgerwer3")
//                .setBodyParameter("authorization", "xvfdbgfdhbfdhtrh54654h54ygdgerwer3")
                .setBodyParameter("category_id", categoryId)
                .setBodyParameter("apply", "1")
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {
//                        Log.e("message_data---", result==null?e.toString():result);

                        progress_handler.hide();

                        if (result == null) {
                            Log.e("message_data---", "null found");

                        } else {

                            taskCompleteReminder.Taskcomplete(result);
                            JsonObject jsonObject = result.getAsJsonObject();

                            String message = jsonObject.get("message").toString().substring(0, jsonObject.get("message").toString().length());

                            String message_data = message.replace("\"", "");

dismiss();






                        }





                    }

                });
        Log.e("message_data---", "calledd closed");

    }

}
