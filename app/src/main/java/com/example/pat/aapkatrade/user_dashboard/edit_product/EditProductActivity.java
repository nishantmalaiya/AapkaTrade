package com.example.pat.aapkatrade.user_dashboard.edit_product;


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
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

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
import com.example.pat.aapkatrade.user_dashboard.add_product.ProductImages;
import com.example.pat.aapkatrade.user_dashboard.add_product.ProductImagesData;
import com.example.pat.aapkatrade.user_dashboard.addcompany.CompanyData;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.async.http.body.FilePart;
import com.koushikdutta.async.http.body.Part;
import com.koushikdutta.ion.Ion;
import com.koushikdutta.ion.ProgressCallback;
import com.nbsp.materialfilepicker.MaterialFilePicker;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Pattern;

public class EditProductActivity extends AppCompatActivity
{

    
    private Context context;
    private LinearLayout contentAddProduct, add_product_root_container;
    private Spinner spCompanyName, spSubCategory, spCategory, spState, spCity, spdeliverydistance, spUnit;
    private String countryID = "101", stateID, cityID, companyID, categoryID, subCategoryID, deliveryDistanceID, unit;
    private HashMap<String, String> webservice_header_type = new HashMap<>();
    private ArrayList<CategoryHome> categoryList = new ArrayList<>();
    private ArrayList<SubCategory> subCategoryList = new ArrayList<>();
    private ArrayList<State> stateList = new ArrayList<>();
    private ArrayList<City> cityList = new ArrayList<>(), unitList = new ArrayList<>();
    private ArrayList<String> deliveryDistanceList = new ArrayList<>();
    private ArrayList<CompanyData> companyNames = new ArrayList<>();
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
    List<Part> files_image = new ArrayList();
    TextView tvTitle;
    String user_id,product_name,price,cross_price,description,company_id,distance_id,
    country_id,state_id,city_id,category_id,sub_category_id,unit_id;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);

        setuptoolbar();

        Intent i = getIntent();

        user_id = i.getStringExtra("user_id");

        product_name= i.getStringExtra("product_name");

        price = i.getStringExtra("product_price");

        cross_price = i.getStringExtra("product_cross_price");

        description = i.getStringExtra("description");

        company_id = i.getStringExtra("company_id");

        distance_id = i.getStringExtra("distance_id");

        country_id = i.getStringExtra("country_id");

        state_id = i.getStringExtra("state_id");

        city_id = i.getStringExtra("city_id");

        category_id = i.getStringExtra("category_id");

        sub_category_id = i.getStringExtra("sub_category_id");

        unit_id = i.getStringExtra("unit_id");


        System.out.println("product_name-"+product_name+"price-"+price+"cross_price-"+cross_price+"description-"+description+"company_id-"+company_id+"distance_id-"+distance_id+"country_id-"+country_id+"state_id-"+state_id+"city_id-"+city_id+"category_id"+category_id+"sub_category_id-"+sub_category_id+"unit_id-"+unit_id);

        Log.e("unit_id",unit_id);

        initView();


        setupRecyclerView();

        btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                count++;
                callEditProductWebService();
            }
        });

    }

    private File savebitmap(String filePath) {
        File file = new File(filePath);
        String extension = filePath.substring(filePath.lastIndexOf(".") + 1, filePath.length());
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        Bitmap bitmap = BitmapFactory.decodeFile(filePath, bmOptions);
        OutputStream outStream = null;
        try {
            // make a new bitmap from your file
            outStream = new FileOutputStream(file);
            if (extension.equalsIgnoreCase("png")) {
                bitmap.compress(Bitmap.CompressFormat.JPEG, 20, outStream);
            } else if (extension.equalsIgnoreCase("jpg") || extension.equalsIgnoreCase("jpeg")) {
                bitmap.compress(Bitmap.CompressFormat.JPEG, 20, outStream);
            } else {
                return null;
            }
            outStream.flush();
            outStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return file;
    }


    private void callEditProductWebService()
    {
        progressBar.show();

        for (int i = 0; i < productImagesDatas.size(); i++) {
            files_image.add(new FilePart("image[]", savebitmap(productImagesDatas.get(i).image_path)));
        }


        Log.e("files_image", "  ==>   " + productImagesDatas.size());

        Log.e("company result", app_sharedpreference.getsharedpref("userid", "0"));

        Ion.with(context)
                .load("http://aapkatrade.com/slim/add_product")
                .setHeader("authorization", "xvfdbgfdhbfdhtrh54654h54ygdgerwer3")
                .progress(new ProgressCallback() {
                    @Override
                    public void onProgress(long downloaded, long total) {
                        Log.e("status", downloaded + "  * " + total);
                    }
                })
                .addMultipartParts(files_image)

                .setMultipartParameter("authorization", "xvfdbgfdhbfdhtrh54654h54ygdgerwer3")
                .setMultipartParameter("user_id", app_sharedpreference.getsharedpref("userid", "0"))
                .setMultipartParameter("name", AndroidUtils.getEditTextData(etProductName))
                .setMultipartParameter("company_id", companyID)
                .setMultipartParameter("deliverTime", "")
                .setMultipartParameter("distance_id", deliveryDistanceID)
                .setMultipartParameter("deliverday", "")
                .setMultipartParameter("cross_price", "")
                .setMultipartParameter("unit_id", "1")
                .setMultipartParameter("availablestatus_id", "")
                .setMultipartParameter("short_des", "company")
                .setMultipartParameter("deliveryArea", AndroidUtils.getEditTextData(etDeliverLocation))
                .setMultipartParameter("country_id", countryID)
                .setMultipartParameter("state_id", stateID)
                .setMultipartParameter("city_id", cityID)
                .setMultipartParameter("category_id", categoryID)
                .setMultipartParameter("sub_cat_id", subCategoryID)
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {
                        progressBar.hide();
                        Log.e("company result", "Result" + result);

                        if (result != null && result.get("message").getAsString().equals("Product Added Successfully!")) {
                            Intent Homedashboard = new Intent(EditProductActivity.this, HomeActivity.class);
                            Homedashboard.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(Homedashboard);
                        } else {
                            showMessage("Company Not Found");
                        }

                    }
                });


    }


    private void initView() {
        context = EditProductActivity.this;

        uploadButton = (ImageView) findViewById(R.id.uploadButton);

        uploadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                picPhoto();

            }
        });

        contentAddProduct = (LinearLayout) findViewById(R.id.contentAddProduct);
        add_product_root_container = (LinearLayout) findViewById(R.id.add_product_root_container);
        webservice_header_type.put("authorization", "xvfdbgfdhbfdhtrh54654h54ygdgerwer3");
        progressBar = new ProgressBarHandler(context);
        app_sharedpreference = new AppSharedPreference(context);

        spCompanyName = (Spinner) findViewById(R.id.spCompanyName);

        int company_position = companyNames.indexOf(company_id);

        Log.e("company_position",company_id);

        spCompanyName.setSelection(company_position);

        spSubCategory = (Spinner) findViewById(R.id.spSubCategory);


        spCategory = (Spinner) findViewById(R.id.spCategory);
        spState = (Spinner) findViewById(R.id.spState);
        spCity = (Spinner) findViewById(R.id.spCity);
        spdeliverydistance = (Spinner) findViewById(R.id.spDeliverydistance);
        spUnit = (Spinner) findViewById(R.id.spUnit);

        btnUpload = (TextView) findViewById(R.id.btnUpload);
        btnUpload.setText("Add");

        etProductName = (EditText) findViewById(R.id.etProductName);

        etProductName.setText(product_name);

        etDeliverLocation = (EditText) findViewById(R.id.etDeliverLocation);

        etPrice = (EditText) findViewById(R.id.etPrice);
        etPrice.setText(price);

        etCrossedPrice = (EditText) findViewById(R.id.etCrossedPrice);
        etCrossedPrice.setText(cross_price);

        etDescription = (EditText) findViewById(R.id.etDescription);
        etDescription.setText(description);

        initSpinner();
        getCompany();
        getCategory();
        getState();
        getUnit();
        pickDeliveryLocation();
    }


    private void initSpinner() {
        CompanyData companyData = new CompanyData("Please Select Company", "-1");
        companyNames.add(companyData);
        CustomSimpleListAdapter adapter = new CustomSimpleListAdapter(context, companyNames);
        spCompanyName.setAdapter(adapter);


        State stateEntity_init = new State("-1", "Please Select State");
        stateList.add(stateEntity_init);
        SpStateAdapter spStateAdapter = new SpStateAdapter(context, stateList);
        spState.setAdapter(spStateAdapter);

        City cityEntity_init = new City("-1", "Please Select City");
        cityList.add(cityEntity_init);
        SpCityAdapter spCityAdapter = new SpCityAdapter(context, cityList);
        spCity.setAdapter(spCityAdapter);

        City unitEntity_init = new City("-1", "Please Select Unit");
        unitList.add(unitEntity_init);
        SpCityAdapter spUnitAdapter = new SpCityAdapter(context, unitList);
        spUnit.setAdapter(spUnitAdapter);
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

                            int selectedIndex =0;
                            JsonArray jsonResultArray = result.getAsJsonArray("result");
                            if (jsonResultArray != null) {
//                                companyNames = new ArrayList<>();
                                for (int i = 0; i < jsonResultArray.size(); i++) {
                                    JsonObject jsonObject = (JsonObject) jsonResultArray.get(i);
                                    CompanyData companyData = new CompanyData(jsonObject.get("name").getAsString(), jsonObject.get("id").getAsString());
                                    companyNames.add(companyData);
                                    if (company_id.equals(companyData.getCompanyId())) {
                                        selectedIndex = i + 1;

                                    }
                                }
                                CustomSimpleListAdapter adapter = new CustomSimpleListAdapter(context, companyNames);
                                spCompanyName.setAdapter(adapter);
                                spCompanyName.setSelection(selectedIndex);

                                spCompanyName.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    @Override
                                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                        if (position > 0) {
                                            companyID = companyNames.get(position).getCompanyId();
                                        } else {
                                            Log.e("hi***", String.valueOf(count));
                                            if (count >= 0)
                                                showMessage("Invalid Company");
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

                            int selectedIndex=0;
                            JsonArray jsonResultArray = result.getAsJsonArray("result");
                            stateList = new ArrayList<>();
                            State stateEntity_init = new State("-1", "Please Select State");
                            stateList.add(stateEntity_init);

                            for (int i = 0; i < jsonResultArray.size(); i++) {
                                JsonObject jsonObject1 = (JsonObject) jsonResultArray.get(i);
                                State stateEntity = new State(jsonObject1.get("id").getAsString(), jsonObject1.get("name").getAsString());
                                stateList.add(stateEntity);
                                if (state_id.equals(stateEntity.stateId)) {
                                    selectedIndex = i + 1;

                                }


                            }
                            SpStateAdapter spStateAdapter = new SpStateAdapter(context, stateList);
                            spState.setAdapter(spStateAdapter);
                            spState.setSelection(selectedIndex);

                            spStateAdapter.notifyDataSetChanged();
                            spState.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                                @Override
                                public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
                                {
                                    stateID = stateList.get(position).stateId;
                                    cityList = new ArrayList<>();
                                    if (position > 0)
                                    {
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

    private void showMessage(String message) {
        AndroidUtils.showSnackBar(contentAddProduct, message);
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
                            int selectedIndex = 0;
                            JsonArray jsonResultArray = result.getAsJsonArray("result");

                            City cityEntity_init = new City("-1", "Please Select City");
                            cityList.add(cityEntity_init);

                            for (int i = 0; i < jsonResultArray.size(); i++) {
                                JsonObject jsonObject1 = (JsonObject) jsonResultArray.get(i);
                                City cityEntity = new City(jsonObject1.get("id").getAsString(), jsonObject1.get("name").getAsString());
                                cityList.add(cityEntity);

                                if (city_id.equals(cityEntity.cityId)) {
                                    selectedIndex = i + 1;
                                    Log.e("HOooo434oooooo", jsonObject1.get("id").getAsString() + "  State Found  " + jsonObject1.get("name").getAsString());

                                }

                            }

                            SpCityAdapter spCityAdapter = new SpCityAdapter(context, cityList);
                            spCity.setAdapter(spCityAdapter);
                            spCity.setSelection(selectedIndex);
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
        subCategoryList.clear();
        categoryList.clear();
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

                            int selectedIndex=0;
                            JsonObject jsonObject = data.getAsJsonObject();
                            JsonArray jsonResultArray = jsonObject.getAsJsonArray("result");

                            categoryList = new ArrayList<>();
                            subCategoryList.add(new SubCategory("-1", "Please Select SubCategory"));

                            categoryList.add(new CategoryHome("-1", "Please Select Category", "", subCategoryList));
                            for (int i = 0; i < jsonResultArray.size(); i++)
                            {
                                JsonObject jsonObject1 = (JsonObject) jsonResultArray.get(i);
                                JsonArray json_subcategory = jsonObject1.getAsJsonArray("subcategory");

                                if (json_subcategory != null)
                                {
                                    subCategoryList = new ArrayList<>();
                                    for (int k = 0; k < json_subcategory.size(); k++)
                                    {
                                        JsonObject jsonObject_subcategory = (JsonObject) json_subcategory.get(k);
                                        SubCategory subCategory = new SubCategory(jsonObject_subcategory.get("id").getAsString(), jsonObject_subcategory.get("name").getAsString());
                                        subCategoryList.add(subCategory);


                                    }
                                    CategoryHome categoryHome = new CategoryHome(jsonObject1.get("id").getAsString(), jsonObject1.get("name").getAsString(), jsonObject1.get("icon").getAsString(), subCategoryList);

                                    categoryList.add(categoryHome);

                                    if (category_id.equals(categoryHome.getCategoryId()))
                                    {
                                        selectedIndex = i + 1;

                                    }

                                    Log.e("data", categoryHome.toString());

                                }
                            }
                            setCategoryAdapter(selectedIndex);
                        }

                    }
                });
    }

    private void setCategoryAdapter(int selectedIndex) {
        Log.e("data", this.categoryList.toString());
        CustomSimpleListAdapter categoriesAdapter = new CustomSimpleListAdapter(context, this.categoryList);
        spCategory.setAdapter(categoriesAdapter);
        spCategory.setSelection(selectedIndex);
        spCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position > 0)
                {
                    int subselectedIndex= 0;
                    categoryID = categoryList.get(position).getCategoryId();
                    subCategoryList = new ArrayList<>();
                    subCategoryList = categoryList.get(position).getSubCategoryList();
                    if (subCategoryList.size() > 0)
                    {
                        for (int i = 0;i<subCategoryList.size();i++){

                            if (category_id.equals(subCategoryList.get(i).subCategoryId))
                            {
                                subselectedIndex = i + 1;

                            }


                        }
                        CustomSimpleListAdapter adapter = new CustomSimpleListAdapter(context, subCategoryList);
                        spSubCategory.setSelection(subselectedIndex);
                        spSubCategory.setAdapter(adapter);


                        spSubCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                if (position > 0) {
                                    subCategoryID = subCategoryList.get(position).subCategoryId;
                                }
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {

                            }
                        });
                    }
                    else {
                        subCategoryList = new ArrayList<>();
                        subCategoryList.add(new SubCategory("0", "No SubCategory Found"));
                        CustomSimpleListAdapter adapter = new CustomSimpleListAdapter(context, subCategoryList);
                        spSubCategory.setAdapter(adapter);

                    }
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }


    private void getUnit() {
        Ion.with(context)
                .load("http://aapkatrade.com/slim/dropdown")
                .setHeader("authorization", webservice_header_type.get("authorization"))
                .setBodyParameter("authorization", webservice_header_type.get("authorization"))
                .setBodyParameter("type", "unit")
                .asJsonObject()
//                .asString()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {
//                        Log.e("hi******", result);
                        unitList = new ArrayList<>();
                        if (result != null)
                        {
                            int selectedIndex=0;

                            JsonArray jsonArray = result.getAsJsonArray("result");
                            unitList = new ArrayList<>();
                            unitList.add(new City("-1", "Please Select Unit"));
                            if (jsonArray != null) {
                                for (int i = 0; i < jsonArray.size(); i++) {
                                    JsonObject jsonObject = (JsonObject) jsonArray.get(i);
                                    City unit = new City(jsonObject.get("id").getAsString(), jsonObject.get("name").getAsString());
                                    unitList.add(unit);

                                    if (unit_id.equals(unit.cityId)) {
                                        selectedIndex = i + 1;

                                    }

                                }
                            }

                            spUnit.setAdapter(new SpCityAdapter(context, unitList));
                            spUnit.setSelection(selectedIndex);


                        } else {
                            showMessage("Unit Not loaded");
                        }
                    }
                });
    }


    private void setuptoolbar()
    {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(null);

        tvTitle = (TextView) findViewById(R.id.header_name);
        tvTitle.setText("Add Product");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_map, menu);
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


    private void pickDeliveryLocation() {
        loadDistanceList();
        CustomSimpleListAdapter adapter = new CustomSimpleListAdapter(context, deliveryDistanceList);
        spdeliverydistance.setAdapter(adapter);
        spdeliverydistance.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position > 0) {
                    deliveryDistanceID = String.valueOf(position);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void loadDistanceList() {
        deliveryDistanceList.add(0, "Please Select distance");
        deliveryDistanceList.add(1, "0 to 0.5 km");
        deliveryDistanceList.add(2, "0.5 to 1 km");
        deliveryDistanceList.add(3, "1 to 2 km");
        deliveryDistanceList.add(4, "2 to 3 km");
        deliveryDistanceList.add(5, "3 to 4 km");
        deliveryDistanceList.add(6, "4 to 5 km");
        deliveryDistanceList.add(7, "5 to 6 km");
        deliveryDistanceList.add(8, "6 to 7 km");
        deliveryDistanceList.add(9, "7 to 8 km");
        deliveryDistanceList.add(10, "8 to 9 km");
        deliveryDistanceList.add(11, "9 to 10 km");
        deliveryDistanceList.add(12, "10 to 11 km");
        deliveryDistanceList.add(13, "11 to 12 km");
        deliveryDistanceList.add(14, "12 to 13 km");
        deliveryDistanceList.add(15, "13 to 14 km");
        deliveryDistanceList.add(16, "14 to 15 km");
        deliveryDistanceList.add(17, "15 to 16 km");
        deliveryDistanceList.add(18, "16 to 17 km");
        deliveryDistanceList.add(19, "17 to 18 km");
        deliveryDistanceList.add(20, "18 to 19 km");
        deliveryDistanceList.add(21, "19 to 20 km");
        deliveryDistanceList.add(22, "20 to 21 km");
        deliveryDistanceList.add(23, "21 to 22 km");
        deliveryDistanceList.add(24, "22 to 23 km");
        deliveryDistanceList.add(25, "23 to 24 km");
        deliveryDistanceList.add(26, "24 to 25 km");
        deliveryDistanceList.add(27, "25 to 26 km");
        deliveryDistanceList.add(28, "26 to 27 km");
        deliveryDistanceList.add(29, "27 to 28 km");
        deliveryDistanceList.add(30, "28 to 29 km");
        deliveryDistanceList.add(31, "29 to 30 km");
    }


    void picPhoto() {
        String str[] = new String[]{"Camera", "Gallery", "PDF Files"};
        new AlertDialog.Builder(this).setItems(str,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        performImgPicAction(which);
                    }
                }).show();

    }

    void performImgPicAction(int which) {
        Intent in;
        if (which == 1) {
            in = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(Intent.createChooser(in, "Select profile picture"), 11);
        } else if (which == 2) {
            new MaterialFilePicker()
                    .withActivity(this)
                    .withRequestCode(1)
                    .withFilter(Pattern.compile(".*\\.pdf$"))
                    .withFilterDirectories(false)
                    .withHiddenFiles(true)
                    .start();
        } else {
            in = new Intent();
            in.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(Intent.createChooser(in, "Select profile picture"), 11);
        }
    }



    private void setupRecyclerView()
    {

        recyclerView = (RecyclerView) findViewById(R.id.recycler);
        adapter = new ProductImages(EditProductActivity.this, productImagesDatas);
        layoutManager = new LinearLayoutManager(EditProductActivity.this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);


    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Log.e("hi", "requestCode : " + requestCode + "result code : " + resultCode);
        try {
            if (requestCode == 11) {
                Log.e("hi", " if else if 2 ");
                BitmapFactory.Options option = new BitmapFactory.Options();
                option.inDither = false;
                option.inPurgeable = true;
                option.inInputShareable = true;
                option.inTempStorage = new byte[32 * 1024];
                option.inPreferredConfig = Bitmap.Config.RGB_565;
                if (Build.VERSION.SDK_INT < 19) {
                    // Uri selectedImageURI = data.getData();
                    imageForPreview = BitmapFactory.decodeFile(getFilesDir().getPath(), option);

                } else {
                    if (data.getData() != null) {

                        ParcelFileDescriptor pfd;
                        try {
                            pfd = getContentResolver()
                                    .openFileDescriptor(data.getData(), "r");
                            if (pfd != null) {
                                FileDescriptor fileDescriptor = pfd
                                        .getFileDescriptor();

                                imageForPreview = BitmapFactory.decodeFileDescriptor(
                                        fileDescriptor, null, option);
                            }
                            pfd.close();


                        } catch (FileNotFoundException e) {
                            Log.e("FileNotFoundException", e.toString());
                        } catch (IOException e) {
                            Log.e("IOException", e.toString());
                        }
                    } else {
                        imageForPreview = (Bitmap) data.getExtras().get("data");
                        Log.e("data_not_found", "data_not_found");
                    }

                }
                try {

                    Log.e("doc", "***START.****** ");
                    if (ImageUtils.sizeOf(imageForPreview) > 2048) {
                        Log.e("doc", "if doc file path 1");

                        docFile = getFile(ImageUtils.resize(imageForPreview, imageForPreview.getHeight() / 2, imageForPreview.getWidth() / 2));
                        Log.e("doc", "if doc file path" + docFile.getAbsolutePath());
                    } else {

                        Log.e("doc", " else doc file path 1");
                        docFile = getFile(imageForPreview);
                        Log.e("doc", " else doc file path" + docFile.getAbsolutePath());
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
                productImagesDatas.add(new ProductImagesData(docFile.getAbsolutePath()));
                // imageViewDP.setImageURI(Uri.parse(finalFile.getAbsolutePath()));
                adapter.notifyDataSetChanged();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    private File getFile(Bitmap photo)
    {
        Uri tempUri = null;
        if (photo != null) {
            tempUri = getImageUri(EditProductActivity.this, photo);
        }
        File finalFile = new File(getRealPathFromURI(tempUri));
        Log.e("data", getRealPathFromURI(tempUri));

        return finalFile;
    }

    public Uri getImageUri(Context inContext, Bitmap inImage)
    {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }


    public String getRealPathFromURI(Uri uri) {
        Cursor cursor = null;
        int idx = 0;
        if (uri != null) {
            cursor = EditProductActivity.this.getContentResolver().query(uri, null, null, null, null);
            assert cursor != null;
            cursor.moveToFirst();
            idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
        }
        return cursor.getString(idx);
    }

}
