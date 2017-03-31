package com.example.pat.aapkatrade.Home.registration;

import android.app.Activity;

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
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.pat.aapkatrade.Home.HomeActivity;
import com.example.pat.aapkatrade.Home.registration.entity.BuyerRegistration;
import com.example.pat.aapkatrade.Home.registration.entity.City;
import com.example.pat.aapkatrade.Home.registration.entity.Country;
import com.example.pat.aapkatrade.Home.registration.entity.SellerRegistration;
import com.example.pat.aapkatrade.Home.registration.entity.State;
import com.example.pat.aapkatrade.Home.registration.spinner_adapter.SpBussinessAdapter;
import com.example.pat.aapkatrade.Home.registration.spinner_adapter.SpCityAdapter;
import com.example.pat.aapkatrade.Home.registration.spinner_adapter.SpCountrysAdapter;
import com.example.pat.aapkatrade.Home.registration.spinner_adapter.SpStateAdapter;
import com.example.pat.aapkatrade.R;
import com.example.pat.aapkatrade.general.AppSharedPreference;
import com.example.pat.aapkatrade.general.App_config;
import com.example.pat.aapkatrade.general.Call_webservice;
import com.example.pat.aapkatrade.general.TaskCompleteReminder;
import com.example.pat.aapkatrade.general.Utils.AndroidUtils;
import com.example.pat.aapkatrade.general.Utils.ImageUtils;
import com.example.pat.aapkatrade.general.Validation;
import com.example.pat.aapkatrade.general.progressbar.ProgressBarHandler;
import com.example.pat.aapkatrade.login.ActivityOTPVerify;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.koushikdutta.ion.ProgressCallback;
import com.nbsp.materialfilepicker.MaterialFilePicker;
import com.nbsp.materialfilepicker.ui.FilePickerActivity;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.regex.Pattern;

import de.hdodenhof.circleimageview.CircleImageView;


public class RegistrationActivity extends AppCompatActivity implements TimePickerDialog.OnTimeSetListener, DatePickerDialog.OnDateSetListener
{

    private static SellerRegistration formSellerData = new SellerRegistration();
    private static BuyerRegistration formBuyerData = new BuyerRegistration();
    private int isAllFieldSet = 0;
    private LinearLayout uploadCard;
    private Spinner spBussinessCategory, spCountry, spState, spCity;
    private String[] spBussinessName = {"Please Select Business Type", "Licence", "Personal"};
    private EditText etProductName, etFirstName, etLastName, etDOB, etEmail, etMobileNo, etAddress, etPassword, etReenterPassword, et_tin_number, et_tan_number;
    private TextView tvSave, uploadMsg;
    private LinearLayout registrationLayout;
    private ArrayList<Country> countryList = new ArrayList<>();
    private ArrayList<State> stateList = new ArrayList<>();
    private ArrayList<City> cityList = new ArrayList<>();
    private LinearLayout businessDetails, uploadView, uploadPDFView;
    private static final int reqCode = 33;
    private File compIncorpFile = new File(""), docFile = new File("");
    private boolean isReqCode = false, isCompIncorp = false;
    private ImageView uploadImage, uploadPDFButton, openCalander, cancelImage, cancelFile;
    AppSharedPreference app_sharedpreference;
    private CircleImageView circleImageView, previewPDF;
    private Bitmap imageForPreview;
    HashMap<String, String> webservice_header_type = new HashMap<>();
    private String busiType = "", countryID = "101", stateID, cityID, isAddVendorCall = "false", business_id;
    private RelativeLayout spBussinessCategoryLayout, previewImageLayout, previewPDFLayout, dobLayout;
    private DatePickerDialog datePickerDialog;
    ProgressBarHandler progressBarHandler;
    private CardView businessDetailsCard;
    private RelativeLayout relativeCompanyListheader;
    private Context context;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        context = RegistrationActivity.this;
        app_sharedpreference = new AppSharedPreference(RegistrationActivity.this);
        isAddVendorCall = app_sharedpreference.getsharedpref("isAddVendorCall");
        setUpToolBar();
        initView();
        saveUserTypeInSharedPreferences();
        setUpBusinessCategory();
        getState();

        uploadPDFButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isCompIncorp = true;
                performImgPicAction(2);
            }
        });
        uploadImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                picPhoto();
            }
        });
        tvSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.e("reach", "reach");
                if (app_sharedpreference.shared_pref != null) {

                    Log.e("reach", "reach1");
                    /*
                    Seller Registration
                     */
                    if (app_sharedpreference.getsharedpref("usertype", "0").equals("1") || isAddVendorCall.equals("true")) {

                        Log.e("reach", "reach2");
                        setSellerFormData();
                        validateFields(String.valueOf(1));
                        if (isAllFieldSet == 0) {
                            callWebServiceForSellerRegistration();
                        }
                    }

                    /*
                    Buyer Registration
                     */
                    else if (app_sharedpreference.getsharedpref("usertype", "0").equals("2")) {

                        Log.e("reach", "reach3");
                        getBuyerFormData();
                        validateFields(String.valueOf(2));
                        if (isAllFieldSet == 0) {
                            callWebServiceForBuyerRegistration();
                        }
                    }
                }
            }


        });

        openCalander.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Calendar now = Calendar.getInstance();
                DatePickerDialog dpd = DatePickerDialog.newInstance(
                        RegistrationActivity.this,
                        now.get(Calendar.YEAR),
                        now.get(Calendar.MONTH),
                        now.get(Calendar.DAY_OF_MONTH)
                );
                dpd.setMaxDate(now);
                dpd.show(getFragmentManager(), "DatePickerDialog");

            }
        });

        cancelImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                previewImageLayout.setVisibility(View.GONE);
            }
        });

        cancelFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                previewPDFLayout.setVisibility(View.GONE);
            }
        });




    }

    private void showDate(int year, int month, int day) {
        etDOB.setTextColor(getResources().getColor(R.color.black));
        etDOB.setText(new StringBuilder().append(year).append("-").append(month).append("-").append(day));
    }

    private void saveUserTypeInSharedPreferences() {
        if (app_sharedpreference != null) {
            if (app_sharedpreference.getsharedpref("usertype", "0").equals("1") || isAddVendorCall.equals("true")) {
                etAddress.setVisibility(View.GONE);
//                findViewById(R.id.height1).setVisibility(View.GONE);
                Log.e("user", "user");
            }
            if (app_sharedpreference.getsharedpref("usertype", "0").equals("2")) {
                Log.e("user2", "user2");
                uploadView.setVisibility(View.GONE);
                spBussinessCategoryLayout.setVisibility(View.GONE);
                etProductName.setVisibility(View.GONE);
                dobLayout.setVisibility(View.GONE);
                uploadCard.setVisibility(View.GONE);
                relativeCompanyListheader.setVisibility(View.GONE);
                businessDetailsCard.setVisibility(View.GONE);

            }
        } else {
            Log.e("user3", "user3");
        }
    }


    private void callWebServiceForSellerRegistration() {
        String URL = isAddVendorCall.equals("true")?getResources().getString(R.string.webservice_base_url)+"/vendorregister":getResources().getString(R.string.webservice_base_url)+"/sellerregister";

        if (docFile.getAbsolutePath().equals("/")) {
            Log.e("reach", "NUL_______DOCCCCCCCLICENCE");
            showmessage("Please Upload Company Document");

        } else {
            if (formSellerData.getBusinessType().contains("1"))

            {
                Log.e("work1", "work1");

                if (compIncorpFile.getAbsolutePath().equals("/")) {
                    Log.e("reach", "NUL_______compDOCCCCCCCLiCENSE");
                    showmessage("Please Upload Company Incorporation ( PDF Only )");

                } else {
                    progressBarHandler.show();

                    Ion.with(RegistrationActivity.this)
                            .load(URL)
                            .setHeader("authorization", "xvfdbgfdhbfdhtrh54654h54ygdgerwer3").progress(new ProgressCallback() {
                        @Override
                        public void onProgress(long downloaded, long total) {
                            Log.e("status", downloaded + "  * " + total);
                        }
                    })
                            .setMultipartFile("company_doc", "image*//*", docFile)
                            //.setMultipartFile("personal_doc", "image*//*", docFile)
                            .setMultipartFile("comp_incorporation", "image*//*", compIncorpFile)
                            .setMultipartParameter("authorization", "xvfdbgfdhbfdhtrh54654h54ygdgerwer3")
                            .setMultipartParameter("business_type", formSellerData.getBusinessType())
                            .setMultipartParameter("business_id", business_id)
                            .setMultipartParameter("companyname", formSellerData.getCompanyName())
                            .setMultipartParameter("name", formSellerData.getFirstName())
                            .setMultipartParameter("lastname", formSellerData.getLastName())
                            .setMultipartParameter("dob", formSellerData.getDOB())
                            .setMultipartParameter("mobile", formSellerData.getMobile())
                            .setMultipartParameter("email", formSellerData.getEmail())
                            .setMultipartParameter("password", formSellerData.getPassword())
                            .setMultipartParameter("country_id", formSellerData.getCountryId())
                            .setMultipartParameter("state_id", formSellerData.getStateId())
                            .setMultipartParameter("city_id", formSellerData.getCityId())
                            .setMultipartParameter("client_id", formSellerData.getClientId())
                            .setMultipartParameter("shopname", formSellerData.getShopName())
                            .setMultipartParameter("tin_number", "521651")
                            .setMultipartParameter("tan_number", "13546848")
                            .setMultipartParameter("tc", "fdssd")
                            .asJsonObject()
                            .setCallback(new FutureCallback<JsonObject>() {
                                @Override
                                public void onCompleted(Exception e, JsonObject result) {
                                    progressBarHandler.hide();

                                    // Log.e("result_seller",result);
                                    if (result != null) {
                                        Log.e("registration_seller", result.toString());
                                        if (result.get("error").getAsString().equals("false")) {

                                            Log.e("registration_seller", "done");
                                            AndroidUtils.showSnackBar(registrationLayout, result.get("message").getAsString());

                                            Intent call_to_startactivity=new Intent(RegistrationActivity.this, ActivityOTPVerify.class);
                                            call_to_startactivity.putExtra("class_name",context.getClass().getName());



                                        } else {
                                            AndroidUtils.showSnackBar(registrationLayout, result.get("message").getAsString());
                                        }
                                    } else {
                                        Log.e("result_seller_error", e.toString());
                                        showmessage(e.toString());
                                    }

                                }

                            });


                }
            } else {
                Log.e("work2", "work2");
                progressBarHandler.show();

                Ion.with(RegistrationActivity.this)
                        .load(URL)
                        .setHeader("authorization", "xvfdbgfdhbfdhtrh54654h54ygdgerwer3").progress(new ProgressCallback() {
                    @Override
                    public void onProgress(long downloaded, long total) {
                        Log.e("status", downloaded + "  * " + total);
                    }
                })
                        //.setMultipartFile("company_doc", "image*//*", docFile)
                        .setMultipartFile("personal_doc", "image*//*", docFile)

                        .setMultipartParameter("authorization", "xvfdbgfdhbfdhtrh54654h54ygdgerwer3")
                        .setMultipartParameter("business_type", formSellerData.getBusinessType())
                        .setMultipartParameter("business_id", business_id)
                        .setMultipartParameter("companyname", formSellerData.getCompanyName())
                        .setMultipartParameter("name", formSellerData.getFirstName())
                        .setMultipartParameter("lastname", formSellerData.getLastName())
                        .setMultipartParameter("dob", formSellerData.getDOB())
                        .setMultipartParameter("mobile", formSellerData.getMobile())
                        .setMultipartParameter("email", formSellerData.getEmail())
                        .setMultipartParameter("password", formSellerData.getPassword())
                        .setMultipartParameter("country_id", formSellerData.getCountryId())
                        .setMultipartParameter("state_id", formSellerData.getStateId())
                        .setMultipartParameter("city_id", formSellerData.getCityId())
                        .setMultipartParameter("client_id", formSellerData.getClientId())
                        .setMultipartParameter("shopname", formSellerData.getShopName())
                        .setMultipartParameter("tin_number", "521651")
                        .setMultipartParameter("tan_number", "13546848")
                        .setMultipartParameter("tc", "fdssd")
                        .asJsonObject()
                        .setCallback(new FutureCallback<JsonObject>() {
                            @Override
                            public void onCompleted(Exception e, JsonObject result) {
                                progressBarHandler.hide();

//                        Log.e("result_seller",result);

                                if (result != null) {
                                    Log.e("registration_seller", result.toString());
                                    if (result.get("error").getAsString().equals("false")) {
                                        AndroidUtils.showSnackBar(registrationLayout, result.get("message").getAsString());
                                        Log.e("registration_seller", "done");
                                        startActivity(new Intent(RegistrationActivity.this, ActivityOTPVerify.class));
                                    } else {

                                        AndroidUtils.showSnackBar(registrationLayout, result.get("message").getAsString());
                                    }

                                } else {
                                    Log.e("result_seller_error", e.toString());
                                    showmessage(e.toString());
                                }

                            }

                        });


            }
        }
    }


    private File getFile(Bitmap photo) {
        Uri tempUri = null;
        if (photo != null) {
            tempUri = getImageUri(RegistrationActivity.this, photo);
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
            cursor = RegistrationActivity.this.getContentResolver().query(uri, null, null, null, null);
            assert cursor != null;
            cursor.moveToFirst();
            idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
        }
        return cursor.getString(idx);
    }


    private void callWebServiceForBuyerRegistration() {
        Log.e("reach", " Buyer Data--------->\n" + formBuyerData.toString());
        progressBarHandler.show();
        Ion.with(RegistrationActivity.this)
                .load(getResources().getString(R.string.webservice_base_url)+"/buyerregister")
                .setHeader("authorization", "xvfdbgfdhbfdhtrh54654h54ygdgerwer3")
                .setBodyParameter("authorization", "xvfdbgfdhbfdhtrh54654h54ygdgerwer3")
                .setBodyParameter("type", "1")
                .setBodyParameter("country_id", formBuyerData.getCountryId())
                .setBodyParameter("state_id", formBuyerData.getStateId())
                .setBodyParameter("city_id", formBuyerData.getCityId())
                .setBodyParameter("address", formBuyerData.getAddress())
                .setBodyParameter("name", formBuyerData.getFirstName())
                .setBodyParameter("lastname", formBuyerData.getLastName())
                .setBodyParameter("email", formBuyerData.getEmail())
                .setBodyParameter("mobile", formBuyerData.getMobile())
                .setBodyParameter("password", formBuyerData.getPassword())
                .setBodyParameter("client_id", formBuyerData.getClientId())
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {
                        if (result != null) {

                            if (result.get("error").getAsString().equals("false")) {
                                Log.e("registration_buyer", result.toString());
                                AndroidUtils.showSnackBar(registrationLayout, result.get("message").getAsString());

                                progressBarHandler.hide();
                                Intent call_to_startactivity=new Intent(RegistrationActivity.this, ActivityOTPVerify.class);
                                call_to_startactivity.putExtra("class_name",context.getClass().getName());
                                startActivity(new Intent(RegistrationActivity.this, ActivityOTPVerify.class));
                            } else {

                                progressBarHandler.hide();
                                AndroidUtils.showSnackBar(registrationLayout, result.get("message").getAsString());


                            }
                        } else {

                            Log.e("result_seller_error", e.toString());
                            showmessage(e.toString());
                        }
                    }

                });
    }


    private void setUpBusinessCategory() {

        SpBussinessAdapter spadapter = new SpBussinessAdapter(getApplicationContext(), spBussinessName);

        spBussinessCategory.setDropDownHorizontalOffset(Gravity.CENTER);

        spBussinessCategory.setAdapter(spadapter);
        spBussinessCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                busiType = String.valueOf(position);
                if (position == 0) {
                    uploadCard.setVisibility(View.GONE);
                    findViewById(R.id.input_layout_tin_number).setVisibility(View.GONE);
                    findViewById(R.id.input_layout_tan_number).setVisibility(View.GONE);
                }
                if (position == 2) {
                    uploadCard.setVisibility(View.VISIBLE);
                    uploadView.setVisibility(View.VISIBLE);
                    uploadPDFView.setVisibility(View.GONE);
                    ((TextInputLayout) findViewById(R.id.input_layout_shop_name)).setHint(getString(R.string.shop_name));
                    uploadMsg.setText(getString(R.string.personal_doc));
                    findViewById(R.id.input_layout_tin_number).setVisibility(View.GONE);
                    findViewById(R.id.input_layout_tan_number).setVisibility(View.GONE);

                } else if (position == 1) {
                    uploadCard.setVisibility(View.VISIBLE);
                    uploadView.setVisibility(View.VISIBLE);
                    uploadPDFView.setVisibility(View.VISIBLE);
                    ((TextInputLayout) findViewById(R.id.input_layout_shop_name)).setHint(getString(R.string.company_name_heading));
                    findViewById(R.id.input_layout_tin_number).setVisibility(View.VISIBLE);
                    findViewById(R.id.input_layout_tan_number).setVisibility(View.VISIBLE);
                    uploadMsg.setText(getString(R.string.comp_doc));
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    }

    private void getCountry() {
        HashMap<String, String> webservice_body_parameter = new HashMap<>();
        webservice_body_parameter.put("authorization", "xvfdbgfdhbfdhtrh54654h54ygdgerwer3");
        webservice_body_parameter.put("type", "country");

        HashMap<String, String> webservice_header_type = new HashMap<>();
        webservice_header_type.put("authorization", "xvfdbgfdhbfdhtrh54654h54ygdgerwer3");


        Call_webservice.getcountrystatedata(RegistrationActivity.this, "country", getResources().getString(R.string.webservice_base_url) + "/dropdown", webservice_body_parameter, webservice_header_type);

        Call_webservice.taskCompleteReminder = new TaskCompleteReminder() {
            @Override
            public void Taskcomplete(JsonObject webservice_returndata) {


                if (webservice_returndata != null) {
                    Log.e("webservice_returndata", webservice_returndata.toString());
                    JsonObject jsonObject = webservice_returndata.getAsJsonObject();
                    JsonArray jsonResultArray = jsonObject.getAsJsonArray("result");
                    countryList.clear();
                    Country countryEntity_init = new Country("-1", "-Please Select Country-");
                    countryList.add(countryEntity_init);
                    for (int i = 0; i < jsonResultArray.size(); i++) {

                        JsonObject jsonObject1 = (JsonObject) jsonResultArray.get(i);

                        Country countryEntity = new Country(jsonObject1.get("id").getAsString(), jsonObject1.get("name").getAsString());
                        countryList.add(countryEntity);
                    }
                    SpCountrysAdapter spCountrysAdapter = new SpCountrysAdapter(RegistrationActivity.this, countryList);
                    spCountry.setAdapter(spCountrysAdapter);
                    spCountry.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            Log.d("datacountry", countryList.get(position).countryId);
                            countryID = countryList.get(position).countryId;
//                            stateList = new ArrayList<>();
                            if (position > 0) {
//                                getState(countryID);
                            }
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }


                    });
                } else {
                    AndroidUtils.showSnackBar(registrationLayout, "Country Not Found");
                }

            }
        };

    }

    public void getState() {

        HashMap<String, String> webservice_body_parameter = new HashMap<>();
        webservice_body_parameter.put("authorization", "xvfdbgfdhbfdhtrh54654h54ygdgerwer3");
        webservice_body_parameter.put("type", "state");
        webservice_body_parameter.put("id", "101");//country id fixed 101 for India

        Call_webservice.getcountrystatedata(RegistrationActivity.this, "state", getResources().getString(R.string.webservice_base_url) + "/dropdown", webservice_body_parameter, webservice_header_type);

        Call_webservice.taskCompleteReminder = new TaskCompleteReminder() {
            @Override
            public void Taskcomplete(JsonObject state_data_webservice) {

                if (state_data_webservice != null) {
                    Log.e("Taskcomplete", "TaskcompleteError" + state_data_webservice.toString());
                    JsonObject jsonObject = state_data_webservice.getAsJsonObject();
                    JsonArray jsonResultArray = jsonObject.getAsJsonArray("result");
                    stateList.clear();
                    State stateEntity_init = new State("-1", "Please Select State");
                    stateList.add(stateEntity_init);

                    for (int i = 0; i < jsonResultArray.size(); i++) {
                        JsonObject jsonObject1 = (JsonObject) jsonResultArray.get(i);
                        State stateEntity = new State(jsonObject1.get("id").getAsString(), jsonObject1.get("name").getAsString());
                        stateList.add(stateEntity);
                    }
                    SpStateAdapter spStateAdapter = new SpStateAdapter(RegistrationActivity.this, stateList);
                    spState.setAdapter(spStateAdapter);

                    spState.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view,
                                                   int position, long id) {
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
                    AndroidUtils.showSnackBar(registrationLayout, "State Not Found");
                }
            }

        };
    }


    public void getCity(String stateId) {

        HashMap<String, String> webservice_body_parameter = new HashMap<>();
        webservice_body_parameter.put("authorization", "xvfdbgfdhbfdhtrh54654h54ygdgerwer3");
        webservice_body_parameter.put("type", "city");
        webservice_body_parameter.put("id", stateId);

        Call_webservice.getcountrystatedata(RegistrationActivity.this, "city", getResources().getString(R.string.webservice_base_url) + "/dropdown", webservice_body_parameter, webservice_header_type);

        Call_webservice.taskCompleteReminder = new TaskCompleteReminder() {
            @Override
            public void Taskcomplete(JsonObject city_data_webservice) {
                if (city_data_webservice != null) {
                    JsonObject jsonObject = city_data_webservice.getAsJsonObject();
                    JsonArray jsonResultArray = jsonObject.getAsJsonArray("result");

                    City cityEntity_init = new City("-1", "Please Select City");
                    cityList.add(cityEntity_init);

                    for (int i = 0; i < jsonResultArray.size(); i++) {
                        JsonObject jsonObject1 = (JsonObject) jsonResultArray.get(i);
                        City cityEntity = new City(jsonObject1.get("id").getAsString(), jsonObject1.get("name").getAsString());
                        cityList.add(cityEntity);
                    }

                    SpCityAdapter spCityAdapter = new SpCityAdapter(RegistrationActivity.this, cityList);
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
                    AndroidUtils.showSnackBar(registrationLayout, "No City Found");
                }
            }
        };
    }


    private void setUpToolBar() {
        ImageView homeIcon = (ImageView) findViewById(R.id.iconHome) ;
        AppCompatImageView back_imagview = (AppCompatImageView) findViewById(R.id.back_imagview);
        back_imagview.setVisibility(View.VISIBLE);
        back_imagview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               finish();
            }
        });
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        AndroidUtils.setImageColor(homeIcon, context, R.color.white);
        homeIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, HomeActivity.class);
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

    private void initView() {

        businessDetailsCard = (CardView) findViewById(R.id.businessDetailsCard);
        et_tin_number = (EditText) findViewById(R.id.et_tin_number);
        et_tan_number = (EditText) findViewById(R.id.et_tan_number);
        relativeCompanyListheader = (RelativeLayout) findViewById(R.id.relativeCompanyListheader);
        uploadCard = (LinearLayout) findViewById(R.id.uploadCard);
        progressBarHandler = new ProgressBarHandler(this);
        registrationLayout = (LinearLayout) findViewById(R.id.registrationLayout);
        spBussinessCategory = (Spinner) findViewById(R.id.spBussinessCategory);
        spCountry = (Spinner) findViewById(R.id.spCountryCategory);
        spState = (Spinner) findViewById(R.id.spStateCategory);
        spCity = (Spinner) findViewById(R.id.spCityCategory);
        tvSave = (TextView) findViewById(R.id.tvSave);
        tvSave.setText(getString(R.string.save));
        uploadMsg = (TextView) findViewById(R.id.uploadMsg);
        etProductName = (EditText) findViewById(R.id.etshopname);
        etFirstName = (EditText) findViewById(R.id.etFirstName);
        etLastName = (EditText) findViewById(R.id.etLastName);
        etDOB = (EditText) findViewById(R.id.etDOB);
        etEmail = (EditText) findViewById(R.id.etEmail);
        etMobileNo = (EditText) findViewById(R.id.etMobileNo);
        etPassword = (EditText) findViewById(R.id.etPassword);
        etAddress = (EditText) findViewById(R.id.etAddress);
        businessDetails = (LinearLayout) findViewById(R.id.businessDetails);
        spBussinessCategoryLayout = (RelativeLayout) findViewById(R.id.spBussinessCategoryLayout);
        etReenterPassword = (EditText) findViewById(R.id.etReenterPassword);
        uploadView = (LinearLayout) findViewById(R.id.uploadView);
        uploadPDFView = (LinearLayout) findViewById(R.id.uploadPDFView);
        circleImageView = (CircleImageView) findViewById(R.id.previewImage);
        previewPDF = (CircleImageView) findViewById(R.id.previewPDF);
        uploadImage = (ImageView) findViewById(R.id.uploadButton);
        uploadPDFButton = (ImageView) findViewById(R.id.uploadPDFButton);
        openCalander = (ImageView) findViewById(R.id.openCalander);
        previewImageLayout = (RelativeLayout) findViewById(R.id.previewImageLayout);
        previewPDFLayout = (RelativeLayout) findViewById(R.id.previewPDFLayout);
        cancelImage = (ImageView) findViewById(R.id.cancelImage);
        cancelFile = (ImageView) findViewById(R.id.cancelFile);
        dobLayout = (RelativeLayout) findViewById(R.id.dobLayout);
        webservice_header_type.put("authorization", "xvfdbgfdhbfdhtrh54654h54ygdgerwer3");
        business_id = app_sharedpreference.getsharedpref("business_id")== null? "": app_sharedpreference.getsharedpref("business_id");


//        Country countryEntity_init = new Country("-1", "Please Select Country");
//        countryList.add(countryEntity_init);
//        SpCountrysAdapter spCountrysAdapter = new SpCountrysAdapter(RegistrationActivity.this, countryList);
//        spCountry.setAdapter(spCountrysAdapter);

        State stateEntity_init = new State("-1", "Please Select State");
        stateList.add(stateEntity_init);
        SpStateAdapter spStateAdapter = new SpStateAdapter(RegistrationActivity.this, stateList);
        spState.setAdapter(spStateAdapter);

        City cityEntity_init = new City("-1", "Please Select City");
        cityList.add(cityEntity_init);
        SpCityAdapter spCityAdapter = new SpCityAdapter(RegistrationActivity.this, cityList);
        spCity.setAdapter(spCityAdapter);


    }

    private void validateFields(String userType) {
        isAllFieldSet = 0;
        Log.e("reach", "validateFiledsCalled");
        if (userType.equals("1") || isAddVendorCall.equals("true")) {
            if (formSellerData != null) {

                Log.e("reach", formSellerData.toString() + "            DATAAAAAAAAA");
                if (Validation.isEmptyStr(formSellerData.getBusinessType())
                        || formSellerData.getBusinessType().equals("0")) {
                    showmessage("Please Select Business Category");
                    isAllFieldSet++;
                } else if (Validation.isEmptyStr(etProductName.getText().toString())) {
                    putError(12);
                    isAllFieldSet++;
                } else if (formSellerData.getBusinessType().equals("1") && Validation.isEmptyStr(et_tin_number.getText().toString())) {
                    putError(13);
                    isAllFieldSet++;
                } else if (formSellerData.getBusinessType().equals("1") && Validation.isEmptyStr(et_tan_number.getText().toString())) {
                    putError(14);
                    isAllFieldSet++;
                } else if (!(Validation.isNonEmptyStr(formSellerData.getStateId()) &&
                        Integer.parseInt(formSellerData.getStateId()) > 0)) {
                    showmessage("Please Select State");
                    isAllFieldSet++;
                } else if (!(Validation.isNonEmptyStr(formSellerData.getCityId()) &&
                        Integer.parseInt(formSellerData.getCityId()) > 0)) {
                    showmessage("Please Select City");
                    isAllFieldSet++;
                } else if (Validation.isEmptyStr(formSellerData.getFirstName())) {
                    putError(0);
                    isAllFieldSet++;
                } else if (Validation.isEmptyStr(formSellerData.getLastName())) {
                    putError(1);
                    isAllFieldSet++;
                } else if (!Validation.isValidDOB(formSellerData.getDOB())) {
                    putError(6);
                    isAllFieldSet++;
                } else if (!Validation.isValidEmail(formSellerData.getEmail())) {
                    putError(2);
                    isAllFieldSet++;
                } else if (!Validation.isValidNumber(formSellerData.getMobile(), Validation.getNumberPrefix(formSellerData.getMobile()))) {
                    putError(3);
                    isAllFieldSet++;
                } else if (!Validation.isValidPassword(formSellerData.getPassword())) {
                    putError(4);
                    isAllFieldSet++;
                } else if (!Validation.isValidPassword(formSellerData.getConfirmPassword())) {
                    putError(4);
                    isAllFieldSet++;
                } else if (!Validation.isPasswordMatching(formSellerData.getPassword(), formSellerData.getConfirmPassword())) {
                    putError(5);
                    isAllFieldSet++;
                }


            }
            Log.d("error", "error Null");
        }
        if (userType.equals("2")) {
            Log.e("reach", "BuyerValidate Called");
            if (formBuyerData != null) {
                if (!(Validation.isNonEmptyStr(formBuyerData.getCountryId()) &&
                        Integer.parseInt(formBuyerData.getCountryId()) > 0)) {
                    showmessage("Please Select Country");
                    isAllFieldSet++;
                } else if (!(Validation.isNonEmptyStr(formBuyerData.getStateId()) &&
                        Integer.parseInt(formBuyerData.getStateId()) > 0)) {
                    showmessage("Please Select State");
                    isAllFieldSet++;
                } else if (!(Validation.isEmptyStr(formBuyerData.getCityId()) ||
                        Integer.parseInt(formBuyerData.getCityId()) > 0)) {
                    showmessage("Please Select City");
                    isAllFieldSet++;
                } else if (Validation.isEmptyStr(formBuyerData.getAddress())) {
                    putError(9);
                    isAllFieldSet++;
                } else if (Validation.isEmptyStr(formBuyerData.getFirstName())) {
                    putError(0);
                    isAllFieldSet++;
                } else if (Validation.isEmptyStr(formBuyerData.getLastName())) {
                    putError(1);
                    isAllFieldSet++;
                } else if (!Validation.isValidEmail(formBuyerData.getEmail())) {
                    putError(2);
                    isAllFieldSet++;
                } else if (!Validation.isValidNumber(formBuyerData.getMobile(), Validation.getNumberPrefix(formBuyerData.getMobile()))) {
                    putError(3);
                    isAllFieldSet++;
                } else if (!Validation.isEmptyStr(formBuyerData.getUserName())) {
                    putError(10);
                    isAllFieldSet++;
                } else if (!Validation.isValidPassword(formBuyerData.getPassword())) {
                    putError(4);
                    isAllFieldSet++;
                } else if (!Validation.isValidPassword(formBuyerData.getConfirmPassword())) {
                    putError(4);
                    isAllFieldSet++;
                } else if (!Validation.isPasswordMatching(formBuyerData.getPassword(), formBuyerData.getConfirmPassword())) {
                    putError(5);
                    isAllFieldSet++;
                }

            }
        }


    }

    private void putError(int id) {
        Log.e("reach", "       )))))))))" + id);
        switch (id) {
            case 0:
                etFirstName.setError("First Name Can't be empty");
                showmessage("First Name Can't be empty");
                break;
            case 1:
                etLastName.setError("Last Name Can't be empty");
                showmessage("Last Name Can't be empty");
                break;
            case 2:
                etEmail.setError("Please Enter Valid Email");
                showmessage("Please Enter Valid Email");
                break;
            case 3:
                etMobileNo.setError("Please Enter Valid Mobile Number");
                showmessage("Please Enter Valid Mobile Number");
                break;
            case 4:
                if (etPassword.getText().toString().length() < 6) {
                    etPassword.setError("Password must be greater than 6 digits");
                } else if (etReenterPassword.getText().toString().length() < 6) {
                    etReenterPassword.setError("Password must be greater than 6 digits");
                }
                showmessage("Password must be greater than 6 digits");
                break;
            case 5:
                etReenterPassword.setError("Password did not matched");
                showmessage("Password did not matched");
                break;
            case 6:
//                etDOB.setError("Please Select Date");
                showmessage("Please Select Date");
                break;
            case 9:
                etAddress.setError("Address Can't be empty");
                showmessage("Address Can't be empty");
                break;
            case 10:
                showmessage("Please Enter Valid UserName");
                break;
            case 12:
                if (formSellerData.getBusinessType().equals("1")) {
                    etProductName.setError("Please Enter Shop Name");
                    showmessage("Please Enter Shop Name");
                } else if (formSellerData.getBusinessType().equals("2")) {
                    etProductName.setError("Please Enter Company Name");
                    showmessage("Please Enter Company Name");
                }
                break;
            case 13:
                et_tin_number.setError("Tin Number Can't be empty");
                showmessage("Tin Number Can't be empty");
                break;
            case 14:
                et_tan_number.setError("Tan Number Can't be empty");
                showmessage("Tan Number Can't be empty");
                break;

            default:
                break;
        }
    }

    public void showmessage(String message) {
        AndroidUtils.showSnackBar(registrationLayout, message);
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


            MaterialFilePicker filePicker=        new MaterialFilePicker();


            filePicker.withActivity(this)
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






    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Log.e("hi", "requestCode : " + requestCode + "result code : " + resultCode);

        try {
            /*if (requestCode == 11) {
                Log.e("hi", " if 1 " );

                if (resultCode == Activity.RESULT_OK) {
                    isReqCode = true;
                }
            } else*/
            if (requestCode == 1) {

                Log.e("hi", " if else if 1 ");
                String filePath = data.getStringExtra(FilePickerActivity.RESULT_FILE_PATH);
                File file = new File(filePath);
                if (!filePath.equals("result_file_path")) {
                    if (isCompIncorp) {
                        previewPDFLayout.setVisibility(View.VISIBLE);
                        previewPDF.setImageDrawable(ContextCompat.getDrawable(RegistrationActivity.this, R.drawable.pdf));
                        compIncorpFile = file;
                        isCompIncorp = false;
                    } else {
                        previewImageLayout.setVisibility(View.VISIBLE);
                        circleImageView.setImageDrawable(ContextCompat.getDrawable(RegistrationActivity.this, R.drawable.pdf));
                        docFile = file;
                    }
                }
                Log.e("hi", "pdf file path : " + file.getAbsolutePath() + "\n" + filePath);


            } else if (requestCode == 11) {
                Log.e("hi", " if else if 2 ");
                BitmapFactory.Options option = new BitmapFactory.Options();
                option.inDither = false;
                option.inPurgeable = true;
                option.inInputShareable = true;
                option.inTempStorage = new byte[32 * 1024];
                option.inPreferredConfig = Bitmap.Config.RGB_565;
                if (Build.VERSION.SDK_INT < 19) {
//                    Uri selectedImageURI = data.getData();

                    imageForPreview = BitmapFactory.decodeFile(getFilesDir().getPath(), option);
                } else {
                    if (data.getData() != null) {

                        ParcelFileDescriptor pfd;
                        try {
                            pfd = getContentResolver()
                                    .openFileDescriptor(data.getData(), "r");
                            if (pfd != null) {
                                FileDescriptor fileDescriptor = pfd.getFileDescriptor();

                                imageForPreview = BitmapFactory.decodeFileDescriptor(fileDescriptor, null, option);
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
                    previewImageLayout.setVisibility(View.VISIBLE);
                    Log.e("doc", "***START.****** ");
                    if (ImageUtils.sizeOf(imageForPreview) > 2048) {
                        Log.e("doc", "if doc file path 1");
                        circleImageView.setImageBitmap(ImageUtils.resize(imageForPreview, imageForPreview.getHeight() / 2, imageForPreview.getWidth() / 2));
                        docFile = getFile(ImageUtils.resize(imageForPreview, imageForPreview.getHeight() / 2, imageForPreview.getWidth() / 2));
                        Log.e("doc", "if doc file path" + docFile.getAbsolutePath());
                    } else {
                        circleImageView.setImageBitmap(imageForPreview);
                        Log.e("doc", " else doc file path 1");
                        docFile = getFile(imageForPreview);
                        Log.e("doc", " else doc file path" + docFile.getAbsolutePath());
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    public void setSellerFormData() {

        formSellerData.setBusinessType(busiType);
        formSellerData.setCompanyName(etProductName.getText().toString());
        formSellerData.setShopName(etProductName.getText().toString());
        formSellerData.setFirstName(etFirstName.getText().toString());
        formSellerData.setLastName(etLastName.getText().toString());
        formSellerData.setEmail(etEmail.getText().toString());
        formSellerData.setDOB(etDOB.getText() == null ? "1992-10-10" : etDOB.getText().toString());
        formSellerData.setMobile(etMobileNo.getText().toString());
        formSellerData.setPassword(etPassword.getText().toString());
        formSellerData.setConfirmPassword(etReenterPassword.getText().toString());
        formSellerData.setClientId(App_config.getCurrentDeviceId(RegistrationActivity.this));
        formSellerData.setBusinessType(busiType == null ? "" : busiType);
        formSellerData.setCountryId(countryID == null ? "" : countryID);
        formSellerData.setStateId(stateID == null ? "" : stateID);
        formSellerData.setCityId(cityID == null ? "" : cityID);
    }


    public void getBuyerFormData() {
        formBuyerData.setCountryId(countryID == null ? "" : countryID);
        formBuyerData.setStateId(stateID == null ? "" : stateID);
        formBuyerData.setCityId(cityID == null ? "" : cityID);
        formBuyerData.setAddress(etAddress.getText().toString());
        formBuyerData.setFirstName(etFirstName.getText().toString());
        formBuyerData.setLastName(etLastName.getText().toString());
        formBuyerData.setEmail(etEmail.getText().toString());
        formBuyerData.setMobile(etMobileNo.getText().toString());
        formBuyerData.setPassword(etPassword.getText().toString());
        formBuyerData.setConfirmPassword(etReenterPassword.getText().toString());
        formBuyerData.setClientId(App_config.getCurrentDeviceId(RegistrationActivity.this));
    }

    private String getBusiType(String busyType) {
        if (Validation.isNonEmptyStr(busyType)) {
            if (busyType.equalsIgnoreCase(spBussinessName[1]))
                return "1";
            else if (busyType.equalsIgnoreCase(spBussinessName[2]))
                return "2";
        }
        return "";
    }


    @Override
    public void onTimeSet(TimePickerDialog view, int hourOfDay, int minute, int second) {

    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        showDate(year, monthOfYear + 1, dayOfMonth);
    }
}