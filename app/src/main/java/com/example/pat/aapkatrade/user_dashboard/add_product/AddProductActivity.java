package com.example.pat.aapkatrade.user_dashboard.add_product;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pat.aapkatrade.Home.CommomData;
import com.example.pat.aapkatrade.Home.HomeActivity;
import com.example.pat.aapkatrade.Home.navigation.entity.CategoryHome;
import com.example.pat.aapkatrade.Home.navigation.entity.SubCategory;
import com.example.pat.aapkatrade.Home.registration.entity.City;
import com.example.pat.aapkatrade.Home.registration.entity.State;
import com.example.pat.aapkatrade.Home.registration.spinner_adapter.SpCityAdapter;
import com.example.pat.aapkatrade.Home.registration.spinner_adapter.SpStateAdapter;
import com.example.pat.aapkatrade.R;
import com.example.pat.aapkatrade.general.AppSharedPreference;
import com.example.pat.aapkatrade.general.Utils.AndroidUtils;
import com.example.pat.aapkatrade.general.Utils.ImageUtils;
import com.example.pat.aapkatrade.general.Utils.adapter.CustomSimpleListAdapter;
import com.example.pat.aapkatrade.general.progressbar.ProgressBarHandler;
import com.example.pat.aapkatrade.service_enquiry.ServiceEnquiry;
import com.example.pat.aapkatrade.user_dashboard.add_product.Dialog.Timing_dialog;
import com.example.pat.aapkatrade.user_dashboard.addcompany.CompanyData;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.async.http.body.FilePart;
import com.koushikdutta.async.http.body.Part;
import com.koushikdutta.ion.Ion;
import com.koushikdutta.ion.ProgressCallback;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class AddProductActivity extends AppCompatActivity {


    private Context context;
    private LinearLayout contentAddProduct, add_product_root_container;
    private Spinner spCompanyName, spSubCategory, spCategory, spState, spCity, spdeliverydistance, spService_type;
    private String countryID = "101", stateID, cityID, companyID, categoryID, subCategoryID, deliveryDistanceID, unit;
    private HashMap<String, String> webservice_header_type = new HashMap<>();
    private ArrayList<CategoryHome> listDataHeader = new ArrayList<>();
    private ArrayList<SubCategory> listDataChild = new ArrayList<>();
    private ArrayList<State> stateList = new ArrayList<>();
    private ArrayList<City> cityList = new ArrayList<>(), unitList = new ArrayList<>();
    private ArrayList<String> deliveryDistanceList = new ArrayList<>();
    private ArrayList<CompanyData> companyNames = new ArrayList<>();
    private ArrayList<String> serviceTypes = new ArrayList<>();
    private ArrayList<String> opening_time_list = new ArrayList<>();
    private ArrayList<String> closing_time_list = new ArrayList<>();
    private ProgressBarHandler progressBar;
    private AppSharedPreference app_sharedpreference;
    private TextView btnUpload;
    private int count = -1;
    private EditText etProductName, etDeliverLocation, etPrice, etCrossedPrice, etDescription;
    ImageView uploadButton;
    File docFile = new File("");
    public ArrayList<ProductImagesData> productImagesDatas = new ArrayList<>();
    LinearLayoutManager layoutManager;
    RecyclerView recyclerView;
    ProductImages adapter;
    Bitmap imageForPreview;
    ArrayList<Bitmap> multiple_images;

    List<Part> files_image = new ArrayList();
    TextView tvTitle;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);
        setUpToolBar();
        initview();
        initspinner();
        getCompany();
        getCategory();


       //getState();


    }

    private void initview() {

        context=AddProductActivity.this;
        progressBar = new ProgressBarHandler(context);
        app_sharedpreference = new AppSharedPreference(context);
        spService_type=(Spinner)findViewById(R.id.sp_service_type);
        spCompanyName=(Spinner)findViewById(R.id.spCompanyName);

        contentAddProduct = (LinearLayout) findViewById(R.id.contentAddProduct);
        etProductName=(EditText)findViewById(R.id.etProductName);
        spCategory=(Spinner)findViewById(R.id.spCategory);
        spSubCategory=(Spinner)findViewById(R.id.spSubCategory);

    }

    private void initspinner() {

//step 1 data spinner

        CompanyData companyData = new CompanyData("Please Select Company", "-1");
        companyNames.add(companyData);
        CustomSimpleListAdapter adapter = new CustomSimpleListAdapter(context, companyNames);
        spCompanyName.setAdapter(adapter);


//        State stateEntity_init = new State("-1", "Please Select State");
//        stateList.add(stateEntity_init);
//        SpStateAdapter spStateAdapter = new SpStateAdapter(context, stateList);
//        spState.setAdapter(spStateAdapter);

//        City cityEntity_init = new City("-1", "Please Select City");
//        cityList.add(cityEntity_init);
//        SpCityAdapter spCityAdapter = new SpCityAdapter(context, cityList);
//        spCity.setAdapter(spCityAdapter);


        serviceTypes.add("Select Service Type");
        serviceTypes.add("Service Enquiry");
        serviceTypes.add("Sell");
        ArrayAdapter<String> service_type_spinner_adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, serviceTypes);
        service_type_spinner_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spService_type.setAdapter(service_type_spinner_adapter);


spService_type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        final String sellorservice;

        if(position>0)
        {
            opening_time_list.clear();
            closing_time_list.clear();
            if(position==1)
            {
                sellorservice="0";
            }
            else {
                sellorservice="1";
            }

            String getdailytime=getResources().getString(R.string.webservice_base_url)+"/getoctime";

            Ion.with(context)
                    .load(getdailytime)
                    .setHeader("authorization", "xvfdbgfdhbfdhtrh54654h54ygdgerwer3")
                    .setBodyParameter("authorization", "xvfdbgfdhbfdhtrh54654h54ygdgerwer3")
                    .setBodyParameter("id", sellorservice)
                    .asJsonObject()
                    .setCallback(new FutureCallback<JsonObject>() {
                        @Override
                        public void onCompleted(Exception e, JsonObject result) {

                            Log.e(AndroidUtils.getTag(context)+"add_product"+sellorservice,result.toString());

                            String error = result.get("error").getAsString();
                            String message = result.get("message").getAsString();
                            String status = result.get("status").getAsString();


                            if(status.contains("1"))
{
    JsonObject formdata = result.get("form_data").getAsJsonObject();
    JsonArray jsonarray_opening = formdata.getAsJsonArray("opening_time");



    for (int l = 0; l < jsonarray_opening.size(); l++) {

        JsonObject jsonObject_result = (JsonObject) jsonarray_opening.get(l);
        String opentiming = jsonObject_result.get("timing").getAsString();

        opening_time_list.add(opentiming);
    }
    Log.e("data_opening", opening_time_list.toString());

    JsonArray jsonarray_closing = formdata.getAsJsonArray("closing_time");
    for (int l = 0; l < jsonarray_closing.size(); l++) {

        JsonObject jsonObject_result = (JsonObject) jsonarray_closing.get(l);
        String closetiming = jsonObject_result.get("timing").getAsString();

        closing_time_list.add(closetiming);
    }
    Log.e("data_closing", closing_time_list.toString());

    Timing_dialog serviceEnquiry = new Timing_dialog(context, opening_time_list, closing_time_list);
    serviceEnquiry.show();



}

                        }
                    });


        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
});

        // step 2 spinner_dynamic_data

    }





    private void setUpToolBar() {

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        AppCompatImageView back_imagview = (AppCompatImageView) findViewById(R.id.back_imagview);
        back_imagview.setVisibility(View.VISIBLE);
        back_imagview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        ImageView homeIcon = (ImageView) findViewById(R.id.iconHome);
        ImageView logoWord = (ImageView) findViewById(R.id.logoWord);
        TextView header_name = (TextView) findViewById(R.id.header_name);
        header_name.setVisibility(View.VISIBLE);
        header_name.setText(getResources().getString(R.string.add_product_heading));
        logoWord.setVisibility(View.GONE);
        AndroidUtils.setImageColor(homeIcon, this, R.color.white);
        homeIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddProductActivity.this, HomeActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
            getSupportActionBar().setTitle(null);
            getSupportActionBar().setElevation(0);
        }


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.bottom_home_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return super.onOptionsItemSelected(item);
    }

    private void getCompany() {

        Log.e("company result", app_sharedpreference.getsharedpref("userid", "0"));
        Ion.with(context)
                .load("http://aapkatrade.com/slim/dropdown")
                .setHeader("authorization", "xvfdbgfdhbfdhtrh54654h54ygdgerwer3")
                .setBodyParameter("authorization", "xvfdbgfdhbfdhtrh54654h54ygdgerwer3")
                .setBodyParameter("type", "company")
                .setBodyParameter("id", app_sharedpreference.getsharedpref("userid", "0"))
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {
                        progressBar.hide();
                        Log.e("company result", result == null ? "state data found" : result.toString());

                        if (result != null) {

                            JsonArray jsonResultArray = result.getAsJsonArray("result");
                            if (jsonResultArray != null) {
//                                companyNames = new ArrayList<>();
                                for (int i = 0; i < jsonResultArray.size(); i++) {
                                    JsonObject jsonObject = (JsonObject) jsonResultArray.get(i);
                                    CompanyData companyData = new CompanyData(jsonObject.get("name").getAsString(), jsonObject.get("id").getAsString());
                                    companyNames.add(companyData);
                                }
                                CustomSimpleListAdapter adapter = new CustomSimpleListAdapter(context, companyNames);
                                spCompanyName.setAdapter(adapter);


                                spCompanyName.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    @Override
                                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                        if (position > 0) {
                                            companyID = companyNames.get(position).getCompanyId();
                                        } else {
                                            Log.e("hi***", String.valueOf(count));
                                            if (count >= 0)
                                            {}
                                               // showMessage("Invalid Company");
                                        }
                                    }

                                    @Override
                                    public void onNothingSelected(AdapterView<?> parent) {


                                    }
                                });
                            } else {
                                showMessage("Company Data Not Found");
                            }

                        } else {
                            showMessage("Company Not Found");
                        }
                    }
                });


    }

    public void getState() {
        Log.e("state result ", "getState started");
        progressBar.show();

        Ion.with(context)
                .load("http://aapkatrade.com/slim/dropdown")
                .setHeader("authorization", "xvfdbgfdhbfdhtrh54654h54ygdgerwer3")
                .setBodyParameter("authorization", "xvfdbgfdhbfdhtrh54654h54ygdgerwer3")
                .setBodyParameter("type", "state")
                .setBodyParameter("id", countryID)
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {
                        progressBar.hide();
                        Log.e("state result ", result == null ? "state data found" : result.toString());

                        if (result != null) {

                            JsonArray jsonResultArray = result.getAsJsonArray("result");
                            stateList = new ArrayList<>();
                            State stateEntity_init = new State("-1", "Please Select State");
                            stateList.add(stateEntity_init);

                            for (int i = 0; i < jsonResultArray.size(); i++) {
                                JsonObject jsonObject1 = (JsonObject) jsonResultArray.get(i);
                                State stateEntity = new State(jsonObject1.get("id").getAsString(), jsonObject1.get("name").getAsString());
                                stateList.add(stateEntity);
                            }
                            SpStateAdapter spStateAdapter = new SpStateAdapter(context, stateList);
                            spState.setAdapter(spStateAdapter);
                            spStateAdapter.notifyDataSetChanged();
                            spState.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                                @Override
                                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                    stateID = stateList.get(position).stateId;
                                    cityList = new ArrayList<>();
                                    if (position > 0) {
                                        getCity(stateList.get(position).stateId);
                                    }
                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> parent) {

                                }
                            });
                        } else {
                            showMessage("State Not Found");
                        }
                    }
                });
    }

    public void getCity(String stateId) {
        progressBar.show();
        Ion.with(context)
                .load("http://aapkatrade.com/slim/dropdown")
                .setHeader("authorization", "xvfdbgfdhbfdhtrh54654h54ygdgerwer3")
                .setBodyParameter("authorization", "xvfdbgfdhbfdhtrh54654h54ygdgerwer3")
                .setBodyParameter("type", "city")
                .setBodyParameter("id", stateId)
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {
                        progressBar.hide();
                        Log.e("city result ", result == null ? "null" : result.toString());

                        if (result != null) {
                            JsonArray jsonResultArray = result.getAsJsonArray("result");

                            City cityEntity_init = new City("-1", "Please Select City");
                            cityList.add(cityEntity_init);

                            for (int i = 0; i < jsonResultArray.size(); i++) {
                                JsonObject jsonObject1 = (JsonObject) jsonResultArray.get(i);
                                City cityEntity = new City(jsonObject1.get("id").getAsString(), jsonObject1.get("name").getAsString());
                                cityList.add(cityEntity);
                            }

                            SpCityAdapter spCityAdapter = new SpCityAdapter(context, cityList);
                            spCity.setAdapter(spCityAdapter);

                            spCity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                    cityID = cityList.get(position).cityId;
//                        if (!(Integer.parseInt(cityID) > 0)) {
//                            showmessage("Please Select City");
//                        }
                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> parent) {

                                }
                            });
                        } else {
                            showMessage("No City Found");
                        }
                    }

                });

    }

    private void getCategory() {
        listDataChild.clear();
        listDataHeader.clear();
        progressBar.show();
        Log.e("data", "getCategory Entered");
        Ion.with(context)
                .load("http://aapkatrade.com/slim/dropdown")
                .setHeader("authorization", "xvfdbgfdhbfdhtrh54654h54ygdgerwer3")
                .setBodyParameter("authorization", "xvfdbgfdhbfdhtrh54654h54ygdgerwer3")
                .setBodyParameter("type", "category")

                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject data) {
                        progressBar.hide();
//                        Log.e("data", "getCategory result" + data.toString());
                        if (data != null) {
                            JsonObject jsonObject = data.getAsJsonObject();
                            JsonArray jsonResultArray = jsonObject.getAsJsonArray("result");

                            listDataHeader = new ArrayList<>();
                            listDataChild.add(new SubCategory("-1", "Please Select SubCategory"));

                            listDataHeader.add(new CategoryHome("-1", "Please Select Category", "", listDataChild));
                            for (int i = 0; i < jsonResultArray.size(); i++) {
                                JsonObject jsonObject1 = (JsonObject) jsonResultArray.get(i);
                                JsonArray json_subcategory = jsonObject1.getAsJsonArray("subcategory");

                                if (json_subcategory != null) {
                                    listDataChild = new ArrayList<>();
                                    for (int k = 0; k < json_subcategory.size(); k++) {
                                        JsonObject jsonObject_subcategory = (JsonObject) json_subcategory.get(k);
                                        SubCategory subCategory = new SubCategory(jsonObject_subcategory.get("id").getAsString(), jsonObject_subcategory.get("name").getAsString());
                                        listDataChild.add(subCategory);
                                    }
                                    CategoryHome categoryHome = new CategoryHome(jsonObject1.get("id").getAsString(), jsonObject1.get("name").getAsString(), jsonObject1.get("icon").getAsString(), listDataChild);
                                    listDataHeader.add(categoryHome);
                                    Log.e("data", categoryHome.toString());

                                }
                            }
                            setCategoryAdapter();
                        }

                    }
                });
    }

    private void setCategoryAdapter() {
        Log.e("data", this.listDataHeader.toString());
        CustomSimpleListAdapter categoriesAdapter = new CustomSimpleListAdapter(context, this.listDataHeader);
        spCategory.setAdapter(categoriesAdapter);

        spCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position > 0) {
                    categoryID = listDataHeader.get(position).getCategoryId();
                    listDataChild = new ArrayList<>();
                    listDataChild = listDataHeader.get(position).getSubCategoryList();
                    if (listDataChild.size() > 0) {
                        CustomSimpleListAdapter adapter = new CustomSimpleListAdapter(context, listDataChild);
                        spSubCategory.setAdapter(adapter);
                        spSubCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                if (position > 0) {
                                    subCategoryID = listDataChild.get(position).subCategoryId;
                                }
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {

                            }
                        });
                    } else {
                        listDataChild = new ArrayList<>();
                        listDataChild.add(new SubCategory("0", "No SubCategory Found"));
                        CustomSimpleListAdapter adapter = new CustomSimpleListAdapter(context, listDataChild);
                        spSubCategory.setAdapter(adapter);
                    }
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }
    private void showMessage(String message) {
        AndroidUtils.showSnackBar(contentAddProduct, message);
    }




}














