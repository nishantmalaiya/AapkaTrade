package com.example.pat.aapkatrade.user_dashboard.add_product;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.provider.MediaStore;
import android.support.design.widget.TextInputLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.TypedValue;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CheckedTextView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.directions.route.AbstractRouting;
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
import com.example.pat.aapkatrade.general.Call_webservice;
import com.example.pat.aapkatrade.general.TaskCompleteReminder;
import com.example.pat.aapkatrade.general.Utils.AndroidUtils;
import com.example.pat.aapkatrade.general.Utils.ImageUtils;
import com.example.pat.aapkatrade.general.Utils.adapter.CustomMultipleCheckBoxAdapter;
import com.example.pat.aapkatrade.general.Utils.adapter.CustomSimpleListAdapter;
import com.example.pat.aapkatrade.general.Validation;
import com.example.pat.aapkatrade.general.entity.KeyValue;
import com.example.pat.aapkatrade.general.progressbar.ProgressBarHandler;
import com.example.pat.aapkatrade.location.AddressEnum;
import com.example.pat.aapkatrade.location.GeoCoderAddress;
import com.example.pat.aapkatrade.map.GoogleMapActivity;
import com.example.pat.aapkatrade.service_enquiry.ServiceEnquiry;
import com.example.pat.aapkatrade.user_dashboard.add_product.Dialog.Timing_dialog;
import com.example.pat.aapkatrade.user_dashboard.addcompany.CompanyData;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;
import com.google.android.gms.maps.model.LatLng;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.async.http.body.FilePart;
import com.koushikdutta.async.http.body.Part;
import com.koushikdutta.async.http.body.StringBody;
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
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TreeMap;
import java.util.UUID;


public class AddProductActivity extends AppCompatActivity {


    private Context context;
    private LinearLayout contentAddProduct, add_product_root_container, ll_dynamic_fields_step2;
    private Spinner spCompanyName, spSubCategory, spCategory, spState, spCity, spdeliverydistance, spService_type;
    private String countryID = "101", stateID, cityID, companyID, categoryID, subCategoryID, deliveryDistanceID, unit;
    private HashMap<String, String> webservice_header_type = new HashMap<>();
    private ArrayList<CategoryHome> listDataHeader = new ArrayList<>();
    private ArrayList<SubCategory> listDataChild = new ArrayList<>();
    private ArrayList<String> stateList = new ArrayList<>();
    private ArrayList<City> cityList = new ArrayList<>(), unitList = new ArrayList<>();
    private ArrayList<String> deliveryDistanceList = new ArrayList<>();
    private ArrayList<CompanyData> companyNames = new ArrayList<>();
    private ArrayList<String> serviceTypes = new ArrayList<>();
    private ArrayList<String> opening_time_list = new ArrayList<>();
    private ArrayList<String> closing_time_list = new ArrayList<>();
    private ProgressBarHandler progressBar;
    private AppSharedPreference app_sharedpreference;
    HashMap<String, Dynamic_form_data_entity> dynamic_form_map_parent = new HashMap<>();

    HashMap<String, Dynamic_form_data_entity> dynamic_form_map_spinner = new HashMap<>();
    HashMap<String, Dynamic_form_data_entity> dynamic_form_map_single_text = new HashMap<>();
    HashMap<String, Dynamic_form_data_entity> dynamic_form_map_radiogroup = new HashMap<>();
    HashMap<String, Dynamic_form_data_entity> dynamic_form_map_checkbox = new HashMap<>();


    private TextView btnUpload;
    private int count = -1;
    private EditText etProductName, etDeliverLocation, etPrice, etCrossedPrice, etDescription, etDiscount, etarea_location, etpincode, etaddress;
    ImageView uploadButton;
    File docFile = new File("");
    public ArrayList<ProductImagesData> productImagesDatas = new ArrayList<>();
    LinearLayoutManager layoutManager;
    RecyclerView recyclerView;
    ProductImages adapter;
    Bitmap imageForPreview;
    int values_count = 0;
    ArrayList<Bitmap> multiple_images;

    RelativeLayout rl_layout1_saveandcontinue_container;

    List<Part> files_image = new ArrayList();
    TextView tvTitle;
    private Spinner dynamicSpinner;
    private GeoCoderAddress GeocoderAsync;
    private int current_state_index;
    private int step1FieldsSet=-1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);
        AndroidUtils.showErrorLog(AddProductActivity.this, "onCreate");
        setUpToolBar();
        setupRecyclerView();
        initview();
        initspinner();
        getCompany();
        getCategory();


    }


    private void setupRecyclerView() {
        recyclerView = (RecyclerView) findViewById(R.id.recycler);
        adapter = new ProductImages(AddProductActivity.this, productImagesDatas);
        layoutManager = new LinearLayoutManager(AddProductActivity.this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);


    }

    private void initview() {

        context = AddProductActivity.this;
        progressBar = new ProgressBarHandler(context);
        app_sharedpreference = new AppSharedPreference(context);
        spService_type = (Spinner) findViewById(R.id.sp_service_type);
        spCompanyName = (Spinner) findViewById(R.id.spCompanyName);
        spCategory = (Spinner) findViewById(R.id.spCategory);
        spSubCategory = (Spinner) findViewById(R.id.spSubCategory);
        spState = (Spinner) findViewById(R.id.spState);
        spCity = (Spinner) findViewById(R.id.spCity);

        contentAddProduct = (LinearLayout) findViewById(R.id.contentAddProduct);
        etProductName = (EditText) findViewById(R.id.etProductName);
        etPrice = (EditText) findViewById(R.id.etPrice);
        etDiscount = (EditText) findViewById(R.id.etDiscount);

        etarea_location = (EditText) findViewById(R.id.etAreaLocation);
        etpincode = (EditText) findViewById(R.id.et_pincode);
        etaddress = (EditText) findViewById(R.id.et_Address);


        etarea_location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    progressBar.show();
                    Intent intent =
                            new PlaceAutocomplete
                                    .IntentBuilder(PlaceAutocomplete.MODE_FULLSCREEN)
                                    .build(AddProductActivity.this);
                    startActivityForResult(intent, 1);


                } catch (GooglePlayServicesRepairableException e) {

                    progressBar.hide();
                    Log.e("GooglePlayServices", e.toString());
                    // TODO: Handle the error.
                } catch (GooglePlayServicesNotAvailableException e) {
                    progressBar.hide();
                    Log.e("GooglePlayServices_not", e.toString());
                    // TODO: Handle the error.
                }


            }


        });

        rl_layout1_saveandcontinue_container = (RelativeLayout) findViewById(R.id.rl_layout1_saveandcontinue_container);

        //container 1 save& continue click event


        rl_layout1_saveandcontinue_container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //validateFields(1);

                if (findViewById(R.id.content_add_product_company_detail).getVisibility() == View.VISIBLE) {


                    findViewById(R.id.content_add_product_company_detail).setVisibility(View.GONE);
                    findViewById(R.id.content_add_product_product_location).setVisibility(View.GONE);
                    if (findViewById(R.id.content_add_product_dynamic_form).getVisibility() == View.GONE) {

                        findViewById(R.id.content_add_product_dynamic_form).setVisibility(View.VISIBLE);
                        dynamic_view_builders();


                    }


                }


            }
        });


        uploadButton = (ImageView) findViewById(R.id.uploadButton);


        uploadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                picPhoto();

            }
        });


        //container 2


        ll_dynamic_fields_step2 = (LinearLayout) findViewById(R.id.content_add_product_dynamic_form).findViewById(R.id.ll_dynamic_fields);

//        ll_dynamic_fields_step2.setBackgroundColor(context.getResources().getColor(R.color.green));
    }

//    private void validateFields(int stepNo) {
//
//        if (stepNo == 1) {
//            step1FieldsSet = 0;
//            if (productImagesDatas.size()!=0) {
//                putError(2);
//                step1FieldsSet++;
//            } else if (!Validation.isEmptyStr(etProductName.getText().toString()))) {
//                putError(4);
//                step1FieldsSet++;
//            } else if (!Validation.isValidPassword(formBusinessData.getConfirmPassword())) {
//                putError(19);
//                step1FieldsSet++;
//            } else if (!Validation.isPasswordMatching(formBusinessData.getPassword(), formBusinessData.getConfirmPassword())) {
//                putError(5);
//                step1FieldsSet++;
//            } else if (Validation.isEmptyStr(formBusinessData.getFirstName())) {
//                putError(0);
//                step1FieldsSet++;
//            } else if (Validation.isEmptyStr(formBusinessData.getLastName())) {
//                putError(1);
//                step1FieldsSet++;
//            } else if (!Validation.isValidDOB(formBusinessData.getDob())) {
//                putError(6);
//                step1FieldsSet++;
//            } else if (Validation.isEmptyStr(formBusinessData.getFatherName())) {
//                putError(10);
//                step1FieldsSet++;
//            } else if (!Validation.isValidNumber(formBusinessData.getMobile_no(), Validation.getNumberPrefix(formBusinessData.getMobile_no()))) {
//                putError(3);
//                step1FieldsSet++;
//            } else if (!(Validation.isNonEmptyStr(formBusinessData.getStateID()) &&
//                    Integer.parseInt(formBusinessData.getStateID()) > 0)) {
//                AndroidUtils.showSnackBar(registrationLayout, "Please Select State");
//                step1FieldsSet++;
//            } else if (!(Validation.isNonEmptyStr(formBusinessData.getCityID()) &&
//                    Integer.parseInt(formBusinessData.getCityID()) > 0)) {
//                AndroidUtils.showSnackBar(registrationLayout, "Please Select City");
//                step1FieldsSet++;
//            } else if (Validation.isEmptyStr(formBusinessData.getAddress())) {
//                putError(9);
//                step1FieldsSet++;
//            } else if (!Validation.isPincode(formBusinessData.getPinCode())) {
//                putError(11);
//                step1FieldsSet++;
//            } else if (!formBusinessData.isAgreementAccepted()) {
//                putError(20);
//                step1FieldsSet++;
//            } else if (step1PhotoFile.getAbsolutePath().equals("/")) {
//                showmessage("Please Upload File");
//                step1FieldsSet++;
//            } else if (!agreement_check.isChecked()) {
//                putError(20);
//            }
//
//            Log.e("hi", "step1FieldsSet=" + step1FieldsSet);
//
//            if (step1FieldsSet == 0) {
//                stepNumber = 2;
//            }
//        } else if (stepNo == 2) {
//            step2FieldsSet = 0;
//            if (Validation.isEmptyStr(formBusinessData.getQualification()) ||
//                    formBusinessData.getQualification().equals(qualificationList.get(0))) {
//                AndroidUtils.showSnackBar(registrationLayout, "Please Select Qualification");
//                step2FieldsSet++;
//            } else if (Validation.isEmptyStr(formBusinessData.getTotalExperience()) ||
//                    formBusinessData.getTotalExperience().equals(totalExpList.get(0))) {
//                AndroidUtils.showSnackBar(registrationLayout, "Please Select Total Experience");
//                step2FieldsSet++;
//            } else if (Validation.isEmptyStr(formBusinessData.getRelaventExperience()) ||
//                    formBusinessData.getRelaventExperience().equals(relaventExpList.get(0))) {
//                AndroidUtils.showSnackBar(registrationLayout, "Please Select Relavent Experience");
//                step2FieldsSet++;
//            } else if ((step2PhotoFile == null) || step2PhotoFile.getAbsolutePath().equals("/")) {
//                showmessage("Please Upload File");
//                step2FieldsSet++;
//            }
//            if (step2FieldsSet == 0) {
//                stepNumber = 3;
//            }
//
//        } else if (stepNo == 3) {
//            step3FieldsSet = 0;
//            if (Validation.isEmptyStr(formBusinessData.getBankName())) {
//                AndroidUtils.showSnackBar(registrationLayout, "Please Select Bank Name");
//                step3FieldsSet++;
//            } else if (!(Validation.isNonEmptyStr(formBusinessData.getAccountNumber()) &&
//                    Validation.isNumber(formBusinessData.getAccountNumber()))) {
//                putError(12);
//                step3FieldsSet++;
//            } else if (Validation.isEmptyStr(formBusinessData.getBranchCode())) {
//                putError(13);
//                step3FieldsSet++;
//            } else if (Validation.isEmptyStr(formBusinessData.getBranchName())) {
//                putError(14);
//                step3FieldsSet++;
//            } else if (Validation.isEmptyStr(formBusinessData.getIfscCode())) {
//                putError(15);
//                step3FieldsSet++;
//            } else if (Validation.isEmptyStr(formBusinessData.getMicrCode())) {
//                putError(16);
//                step3FieldsSet++;
//            } else if (Validation.isEmptyStr(formBusinessData.getAccountHolderName())) {
//                putError(17);
//                step3FieldsSet++;
//            } else if (!Validation.isValidNumber(formBusinessData.getRegisteredMobileWithBank(), Validation.getNumberPrefix(formBusinessData.getRegisteredMobileWithBank()))) {
//                putError(18);
//                step3FieldsSet++;
//            }
//
//        }
//
//    }
//
//    private void putError(int id) {
//        Log.e("reach", "       )))))))))" + id);
//        switch (id) {
//            case 0:
//                et_first_name.setError("First Name Can't be empty");
//                showmessage("First Name Can't be empty");
//                break;
//            case 1:
//                et_last_name.setError("Last Name Can't be empty");
//                showmessage("Last Name Can't be empty");
//                break;
//            case 2:
//                et_email.setError("Please Enter Valid Email");
//                showmessage("Please Enter Valid Email");
//                break;
//            case 3:
//                et_mobile.setError("Please Enter Valid Mobile Number");
//                showmessage("Please Enter Valid Mobile Number");
//                break;
//            case 4:
//                et_password.setError("Password must be greater than or equals to 6 digits");
//                showmessage("Password must be greater than or equals to 6 digits");
//                break;
//            case 5:
//                et_confirm_password.setError("Password did not matched");
//                showmessage("Password did not matched");
//                break;
//            case 6:
//                etDOB.setError("Please Select Date");
//                showmessage("Please Select Date");
//                break;
//            case 7:
//                ((TextView) findViewById(R.id.tv_agreement)).setError("Please Accept Terms & Conditions");
//                showmessage("Please Accept Terms & Conditions");
//                break;
//            case 9:
//                et_address.setError("Address Can't be empty");
//                showmessage("Address Can't be empty");
//                break;
//            case 10:
//                et_father_name.setError("Father's Name Can't be empty");
//                showmessage("Father's Name Can't be empty");
//                break;
//            case 11:
//                et_pincode.setError("Please Enter Valid PINCODE");
//                showmessage("Please Enter Valid PINCODE");
//                break;
//            case 12:
//                et_account_no.setError("Please Enter Valid Account Number");
//                showmessage("Please Enter Valid Account Number");
//                break;
//            case 13:
//                et_branch_code.setError("Please Enter Branch Code");
//                showmessage("Please Enter Branch Code");
//                break;
//            case 14:
//                et_branch_name.setError("Please Enter Branch Name");
//                showmessage("Please Enter Branch Name");
//                break;
//            case 15:
//                et_ifsc_code.setError("Please Enter IFSC Code");
//                showmessage("Please Enter IFSC Code");
//                break;
//            case 16:
//                et_micr_code.setError("Please Enter MICR Code");
//                showmessage("Please Enter MICR Code");
//                break;
//            case 17:
//                et_account_holder_name.setError("Please Enter Account Holder Name");
//                showmessage("Please Enter Account Holder Name");
//                break;
//            case 18:
//                et_registered_mobile_with_bank.setError("Please Enter Your Registered mobile number");
//                showmessage("Please Enter Your Registered mobile number");
//                break;
//            case 19:
//                et_confirm_password.setError("Password must be greater than or equals to 6 digits");
//                showmessage("Password must be greater than or equals to 6 digits");
//                break;
//
//            case 20:
//                showmessage("Please Check the Agreement");
//                break;
//            default:
//                break;
//        }
//    }

    private void initspinner() {

//step 1 data spinner

        CompanyData companyData = new CompanyData("Please Select Company", "-1");
        companyNames.add(companyData);
        CustomSimpleListAdapter adapter = new CustomSimpleListAdapter(context, companyNames);
        spCompanyName.setAdapter(adapter);


        serviceTypes.add("Select Service Type");
        serviceTypes.add("Service Enquiry");
        serviceTypes.add("Sell");

        CustomSimpleListAdapter adapter_spinner_service_type = new CustomSimpleListAdapter(context, serviceTypes);
        spService_type.setAdapter(adapter_spinner_service_type);


        getState();


        spService_type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                final String sellorservice;

                if (position > 0) {
                    progressBar.show();
                    opening_time_list.clear();
                    closing_time_list.clear();
                    if (position == 1) {
                        sellorservice = "0";
                    } else {
                        sellorservice = "1";
                    }

                    String getdailytime = getResources().getString(R.string.webservice_base_url) + "/getoctime";

                    Ion.with(context)
                            .load(getdailytime)
                            .setHeader("authorization", "xvfdbgfdhbfdhtrh54654h54ygdgerwer3")
                            .setBodyParameter("authorization", "xvfdbgfdhbfdhtrh54654h54ygdgerwer3")
                            .setBodyParameter("id", sellorservice)
                            .asJsonObject()
                            .setCallback(new FutureCallback<JsonObject>() {
                                @Override
                                public void onCompleted(Exception e, JsonObject result) {

                                    Log.e(AndroidUtils.getTag(context) + "add_product" + sellorservice, result.toString());

                                    String error = result.get("error").getAsString();
                                    String message = result.get("message").getAsString();
                                    String status = result.get("status").getAsString();


                                    if (status.contains("1")) {
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
                                        Intent goto_dialog_timing = new Intent(AddProductActivity.this, Timing_dialog.class);
                                        goto_dialog_timing.putStringArrayListExtra("opening_time_list", opening_time_list);
                                        goto_dialog_timing.putStringArrayListExtra("closing_time_list", closing_time_list);
                                        startActivity(goto_dialog_timing);


                                    }
                                    progressBar.hide();
                                }
                            });


                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                AndroidUtils.showErrorLog(context, "called");
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
                                            if (count >= 0) {
                                            }
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
                        findViewById(R.id.text_input_subcategory).setVisibility(View.VISIBLE);
                        spSubCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                if (position >= 0) {
                                    subCategoryID = listDataChild.get(position).subCategoryId;


                                    call_dynamic_formdata_webservice(categoryID, subCategoryID);

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

    private void call_dynamic_formdata_webservice(String categoryID, String subCategoryID) {


        Log.e(AndroidUtils.getTag(AddProductActivity.this) + "category", categoryID + "******" + subCategoryID);
        String dynamic_formdata_addproduct = getString(R.string.webservice_base_url) + "/get_formdata";

        progressBar.show();
        Ion.with(context)
                .load(dynamic_formdata_addproduct)
                .setHeader("authorization", "xvfdbgfdhbfdhtrh54654h54ygdgerwer3")
                .setBodyParameter("authorization", "xvfdbgfdhbfdhtrh54654h54ygdgerwer3")
                .setBodyParameter("category_id", categoryID)
                .setBodyParameter("subcat_id", subCategoryID)
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {
                        if (result != null) {
                            if (result.get("error").getAsString().contains("false")) {


                                JsonObject jsonObject = result.getAsJsonObject();
                                JsonArray json_form_data = jsonObject.getAsJsonArray("form_data");


                                for (int i = 0; i < json_form_data.size(); i++) {
                                    JsonObject jsonObject1 = (JsonObject) json_form_data.get(i);
                                    Dynamic_form_data_entity dynamic_form_data_entity = new Dynamic_form_data_entity();
                                    String title = jsonObject1.get("title").getAsString();
                                    String type = jsonObject1.get("type").getAsString();
                                    dynamic_form_data_entity.title.key = "title";
                                    dynamic_form_data_entity.title.value = title;

                                    dynamic_form_data_entity.type.key = "type";
                                    dynamic_form_data_entity.type.value = type;

                                    if (type.equals(context.getString(R.string.dynamic_edittext))) {


                                        ++values_count;
                                        dynamic_form_data_entity.values_arraylist.add(new KeyValue("values", ""));

                                        AndroidUtils.showErrorLog(context, "1*****" + values_count);
                                        dynamic_form_map_single_text.put(title, dynamic_form_data_entity);

                                    } else if (type.equals(context.getString(R.string.dynamic_spinner))) {


                                        JsonArray json_values = jsonObject1.getAsJsonArray("value");

                                        AndroidUtils.showErrorLog(context, "jsonvalues**" + json_values.toString() + "***" + json_values.size());
                                        dynamic_form_data_entity.values_arraylist.add(new KeyValue("values", "Please Select " + title));
                                        for (int k = 0; k < json_values.size(); k++) {
                                            JsonObject jsonObject_value = (JsonObject) json_values.get(k);
                                            String values = jsonObject_value.get("value").getAsString();

                                            dynamic_form_data_entity.values_arraylist.add(new KeyValue("values", values));


                                        }
                                        dynamic_form_map_spinner.put(title, dynamic_form_data_entity);
                                    } else if (type.equals(context.getString(R.string.dynamic_multiplechoice_checkbox))) {
                                        JsonArray json_values = jsonObject1.getAsJsonArray("value");

                                        AndroidUtils.showErrorLog(context, "jsonvalues**" + json_values.toString() + "***" + json_values.size());
                                        for (int k = 0; k < json_values.size(); k++) {
                                            JsonObject jsonObject_value = (JsonObject) json_values.get(k);
                                            String values = jsonObject_value.get("value").getAsString();

                                            dynamic_form_data_entity.values_arraylist.add(new KeyValue("values", values));


                                        }
                                        dynamic_form_map_checkbox.put(title, dynamic_form_data_entity);


                                    } else if (type.equals(context.getString(R.string.dynamic_radio_group))) {
                                        JsonArray json_values = jsonObject1.getAsJsonArray("value");

                                        AndroidUtils.showErrorLog(context, "jsonvalues**" + json_values.toString() + "***" + json_values.size());
                                        for (int k = 0; k < json_values.size(); k++) {
                                            JsonObject jsonObject_value = (JsonObject) json_values.get(k);
                                            String values = jsonObject_value.get("value").getAsString();

                                            dynamic_form_data_entity.values_arraylist.add(new KeyValue("values", values));


                                        }
                                        dynamic_form_map_radiogroup.put(title, dynamic_form_data_entity);


                                    }


                                }

                                dynamic_form_map_parent.putAll(dynamic_form_map_single_text);
                                dynamic_form_map_parent.putAll(dynamic_form_map_checkbox);
                                dynamic_form_map_parent.putAll(dynamic_form_map_radiogroup);
                                dynamic_form_map_parent.putAll(dynamic_form_map_spinner);
                                progressBar.hide();
                            } else {
                                AndroidUtils.showErrorLog(context, result.get("message").getAsString());
                                progressBar.hide();
                            }
                        } else {
                            AndroidUtils.showErrorLog(context, "Null Data Found in " + AndroidUtils.getTag(context));
                            progressBar.hide();
                        }

                    }
                });


    }


    private void getState() {
        findViewById(R.id.input_layout_city).setVisibility(View.GONE);
        stateList = new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.state_list)));

        CustomSimpleListAdapter stateAdapter = new CustomSimpleListAdapter(context, stateList);
//        spSubCategory.setAdapter(adapter);
//        ArrayAdapter spinnerArrayAdapter = new ArrayAdapter(context, R.layout.white_textcolor_spinner, stateList);
//        spinnerArrayAdapter.setDropDownViewResource(R.layout.white_textcolor_spinner);

        spState.setAdapter(stateAdapter);


        spState.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {

                cityList = new ArrayList<>();
                AndroidUtils.showErrorLog(context, "State Id is ::::::::  " + position);
                if (position > 0)
                    getCity(String.valueOf(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void getCity(String stateId) {
        findViewById(R.id.input_layout_city).setVisibility(View.VISIBLE);
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


    private void dynamic_view_builders() {

        LinearLayoutManager layoutManager_checkbox = new LinearLayoutManager(context);
        LinearLayoutManager layoutManager_radiobutton = new LinearLayoutManager(context);

        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        layoutParams.setMargins(30, 20, 30, 0);
        for (String titleKey : dynamic_form_map_single_text.keySet()) {
            Dynamic_form_data_entity dynamicFormDataEntity = dynamic_form_map_single_text.get(titleKey);


            View view_edittext = LayoutInflater.from(this).inflate(R.layout.custom_edit_text_layout, null);
//

            TextInputLayout dynamic_single_textinput_layout = (TextInputLayout) view_edittext.findViewById(R.id.dynamic_single_texinputlayout);
            EditText dynamic_single_edittext = (EditText) view_edittext.findViewById(R.id.dynamic_single_edittext);

            dynamic_single_edittext.setTag(titleKey);
            dynamic_single_textinput_layout.setHint(titleKey);


            ll_dynamic_fields_step2.addView(view_edittext);
        }


        for (String titleKey : dynamic_form_map_spinner.keySet()) {
            Dynamic_form_data_entity dynamicFormDataEntity = dynamic_form_map_spinner.get(titleKey);

            ArrayList<KeyValue> keyValueArrayList = dynamicFormDataEntity.values_arraylist;


            View view_spinner = LayoutInflater.from(this).inflate(R.layout.custom_spinner_layout, null);


            dynamicSpinner = (Spinner) view_spinner.findViewById(R.id.customSpinner);
            dynamicSpinner.setTag(titleKey);
            CustomSpinnerAdapter customSpinnerAdapter = new CustomSpinnerAdapter(context, keyValueArrayList);

            dynamicSpinner.setAdapter(customSpinnerAdapter);
            dynamicSpinner.setSelection(0);


            ll_dynamic_fields_step2.addView(view_spinner, layoutParams);
        }


        for (String titleKey : dynamic_form_map_radiogroup.keySet()) {
            Dynamic_form_data_entity dynamicFormDataEntity = dynamic_form_map_radiogroup.get(titleKey);

            ArrayList<KeyValue> keyValueArrayList = dynamicFormDataEntity.values_arraylist;


//
            View view_Textview = LayoutInflater.from(this).inflate(R.layout.custom_text_view_layout, null, false);

            TextView tv_checkbox_header = (TextView) view_Textview.findViewById(R.id.customTextView);
            tv_checkbox_header.setTextColor(ContextCompat.getColor(context, R.color.black));
            tv_checkbox_header.setTypeface(Typeface.DEFAULT_BOLD);

            tv_checkbox_header.setTag(titleKey);
            tv_checkbox_header.setText(titleKey);


            ll_dynamic_fields_step2.addView(tv_checkbox_header);
            View view_checked_container = LayoutInflater.from(this).inflate(R.layout.custom_check_box_layout, null, false);

            RecyclerView recyclerView_checkboxes = (RecyclerView) view_checked_container.findViewById(R.id.custom_recycleview_formbuilder);


            CustomMultipleCheckBoxAdapter customMultipleCheckBoxAdapter = new CustomMultipleCheckBoxAdapter(context, keyValueArrayList, true);
            recyclerView_checkboxes.setLayoutManager(layoutManager_checkbox);
            recyclerView_checkboxes.setAdapter(customMultipleCheckBoxAdapter);


            ll_dynamic_fields_step2.addView(view_checked_container);


        }


        for (String titleKey : dynamic_form_map_checkbox.keySet()) {
            Dynamic_form_data_entity dynamicFormDataEntity = dynamic_form_map_checkbox.get(titleKey);

            ArrayList<KeyValue> keyValueArrayList = dynamicFormDataEntity.values_arraylist;


            View view_Textview = LayoutInflater.from(this).inflate(R.layout.custom_text_view_layout, null, false);

            TextView tv_checkbox_header = (TextView) view_Textview.findViewById(R.id.customTextView);
            tv_checkbox_header.setTextColor(ContextCompat.getColor(context, R.color.black));
            tv_checkbox_header.setTypeface(Typeface.DEFAULT_BOLD);

            tv_checkbox_header.setTag(titleKey);
            tv_checkbox_header.setText(titleKey);


            ll_dynamic_fields_step2.addView(tv_checkbox_header);
            View view_checked_container = LayoutInflater.from(this).inflate(R.layout.custom_check_box_layout, null, false);

            RecyclerView recyclerView_checkboxes = (RecyclerView) view_checked_container.findViewById(R.id.custom_recycleview_formbuilder);


            CustomMultipleCheckBoxAdapter customMultipleCheckBoxAdapter = new CustomMultipleCheckBoxAdapter(context, keyValueArrayList, false);
            recyclerView_checkboxes.setLayoutManager(layoutManager_radiobutton);
            recyclerView_checkboxes.setAdapter(customMultipleCheckBoxAdapter);


            ll_dynamic_fields_step2.addView(view_checked_container);


        }


    }


    private void showMessage(String message) {
        AndroidUtils.showSnackBar(contentAddProduct, message);
    }


    void picPhoto() {
        String str[] = new String[]{"Camera", "Gallery"};
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
            in.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
            in.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(Intent.createChooser(in, "Select Multiple Picture From Gallery"), 11);
        } else {

            in = new Intent();
            in.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(Intent.createChooser(in, "Capture Image from Camera"), 10);
        }


    }


    @Override
    protected void onResume() {
        super.onResume();


        spService_type.setSelection(0);
        AndroidUtils.showErrorLog(context, "onResume");
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        multiple_images = new ArrayList<>();
        Log.e("hi", "requestCode : " + requestCode + "result code : " + resultCode);
        try {
            if (requestCode == 11) {
                if (data.getClipData() != null) {

                    data.getClipData().getItemCount();

                    for (int k = 0; k < 4; k++) {

                        Uri selectedImage = data.getClipData().getItemAt(k).getUri();

                        Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImage);
                        multiple_images.add(bitmap);


                        Log.e("doc", "***START.****** ");
                        if (ImageUtils.sizeOf(bitmap) > 2048) {
                            Log.e("doc", "if doc file path 1");

                            docFile = getFile(ImageUtils.resize(bitmap, bitmap.getHeight() / 2, bitmap.getWidth() / 2));
                            Log.e("doc", "if doc file path" + docFile.getAbsolutePath());
                        } else {

                            Log.e("doc", " else doc file path 1");
                            docFile = getFile(bitmap);
                            Log.e("doc", " else doc file path" + docFile.getAbsolutePath());
                        }

                        productImagesDatas.add(new ProductImagesData(docFile.getAbsolutePath(), ""));
                        Log.e("docfile", docFile.getAbsolutePath());


                        adapter.notifyDataSetChanged();
                        if (productImagesDatas.size() > 0) {
                            recyclerView.setVisibility(View.VISIBLE);

                        }


                    }

                } else {


                    try {
                        InputStream inputStream = getContentResolver().openInputStream(data.getData());
                        Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                        Uri tempUri = getImageUri(AddProductActivity.this, bitmap);

                        // CALL THIS METHOD TO GET THE ACTUAL PATH
                        File finalFile = new File(getRealPathFromURI(tempUri));

                        productImagesDatas.add(new ProductImagesData(finalFile.getAbsolutePath(), ""));
                        Log.e("docfile", finalFile.getAbsolutePath());

                        adapter.notifyDataSetChanged();
                        if (productImagesDatas.size() > 0) {
                            recyclerView.setVisibility(View.VISIBLE);

                        }

                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }


                }
            }
            if (requestCode == 10) {

                Log.e("docfile10", "Sachin sdnsdfjsd fsdjfsd fnmsdabf");

                Bitmap photo = (Bitmap) data.getExtras().get("data");

                Uri tempUri = getImageUri(AddProductActivity.this, photo);

                // CALL THIS METHOD TO GET THE ACTUAL PATH
                File finalFile = new File(getRealPathFromURI(tempUri));

                productImagesDatas.add(new ProductImagesData(finalFile.getAbsolutePath(), ""));
                Log.e("docfile", finalFile.getAbsolutePath());

                adapter.notifyDataSetChanged();
                recyclerView.setVisibility(View.VISIBLE);

            }


            if (requestCode == 1) {
                if (resultCode == RESULT_OK) {
                    progressBar.hide();
                    // retrive the data by using getPlace() method.
                    Place place = PlaceAutocomplete.getPlace(this, data);

                    GeocoderAsync = new GeoCoderAddress(context, place.getLatLng().latitude, place.getLatLng().longitude);

                    try {


                        String AddressAsync = GeocoderAsync.get_state_name().get(AddressEnum.STATE);
                        String AddressCity = GeocoderAsync.get_state_name().get(AddressEnum.CITY);
                        String Pincode = GeocoderAsync.get_state_name().get(AddressEnum.PINCODE);
                        String Address = GeocoderAsync.get_state_name().get(AddressEnum.ADDRESS);


                        etpincode.setText(Pincode);
                        etaddress.setText(Address);

                        set_state_selection(AddressAsync);
                        Log.e("AddressAsync", AddressAsync + "**" + AddressCity + "**" + Pincode + "**" + Address);
                    } catch (Exception e) {
                        Log.e("Exception_geocoder", e.toString());

                    }

                    etarea_location.setText(place.getAddress());


                    Log.e("Tag", place.toString());


                } else if (resultCode == PlaceAutocomplete.RESULT_ERROR) {
                    progressBar.hide();
                    Status status = PlaceAutocomplete.getStatus(this, data);
                    // TODO: Handle the error.
                    Log.e("Tag", status.getStatusMessage());

                } else if (resultCode == RESULT_CANCELED) {
                    progressBar.hide();
                    // The user canceled the operation.
                }
            }


        } catch (Exception e) {
            Log.e("Exception", e.toString());
        }

    }


    private File getFile(Bitmap photo) {
        Uri tempUri = null;
        if (photo != null) {
            tempUri = getImageUri(AddProductActivity.this, photo);
        }
        File finalFile = new File(getRealPathFromURI(tempUri));
        Log.e("data", getRealPathFromURI(tempUri));

        return finalFile;
    }

    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }


    public String getRealPathFromURI(Uri uri) {
        Cursor cursor = null;
        int idx = 0;
        if (uri != null) {
            cursor = AddProductActivity.this.getContentResolver().query(uri, null, null, null, null);
            assert cursor != null;
            cursor.moveToFirst();
            idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
        }
        return cursor.getString(idx);
    }

    private void set_state_selection(String a) {

        progressBar.show();
        Log.e("statelist_state", stateList.toString() + "" + a);


        for (int i = 0; i < stateList.size(); i++) {

            if (a.equals(stateList.get(i))) {

                current_state_index = i;
                Log.e("current_state_index", current_state_index + "");
            }
        }
        Log.e("current_state_index2", current_state_index + "");
        spState.setSelection(current_state_index);
        progressBar.hide();


    }


}














