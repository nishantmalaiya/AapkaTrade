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
import android.os.ParcelFileDescriptor;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.pat.aapkatrade.Home.HomeActivity;
import com.example.pat.aapkatrade.Home.registration.entity.BusinessAssociateRegistration;
import com.example.pat.aapkatrade.Home.registration.entity.City;
import com.example.pat.aapkatrade.Home.registration.entity.State;
import com.example.pat.aapkatrade.Home.registration.spinner_adapter.SpCityAdapter;
import com.example.pat.aapkatrade.Home.registration.spinner_adapter.SpStateAdapter;
import com.example.pat.aapkatrade.R;
import com.example.pat.aapkatrade.general.App_config;
import com.example.pat.aapkatrade.general.AppSharedPreference;
import com.example.pat.aapkatrade.general.Call_webservice;
import com.example.pat.aapkatrade.general.Utils.AndroidUtils;
import com.example.pat.aapkatrade.general.Utils.ImageUtils;
import com.example.pat.aapkatrade.general.TaskCompleteReminder;
import com.example.pat.aapkatrade.general.Validation;
import com.example.pat.aapkatrade.general.Utils.adapter.CustomSimpleListAdapter;
import com.example.pat.aapkatrade.general.progressbar.ProgressBarHandler;
import com.example.pat.aapkatrade.login.ActivityOTPVerify;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.koushikdutta.ion.ProgressCallback;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

public class RegistrationBusinessAssociateActivity extends AppCompatActivity implements TimePickerDialog.OnTimeSetListener, DatePickerDialog.OnDateSetListener {

    private Context context;
    private TextView uploadMsg, uploadMsg2, tvSave;
    private static BusinessAssociateRegistration formBusinessData = new BusinessAssociateRegistration();
    private TextView step1HeaderCircle, step2HeaderCircle, step3HeaderCircle;
    private TextView step1HeaderText, step2HeaderText, step3HeaderText;
    private ImageView uploadImage, uploadImage2, openCalander, cancelImage, cancelImage2;
    private HashMap<String, String> webservice_header_type = new HashMap<>();
    private EditText et_email, et_password, et_confirm_password, et_ref_number, et_first_name, et_last_name, et_father_name, et_mobile, et_account_no, et_branch_code, et_branch_name, et_ifsc_code, et_micr_code, et_account_holder_name, et_registered_mobile_with_bank, etDOB, et_address, et_pincode;
    private Spinner spState, spCity, spQualification, spTotalExp, spRelExp, spSelectBank;
    private CheckBox agreement_check;
    private Bitmap imageForPreview, imageForPreview2;
    private CircleImageView circleImageView, circleImageView2;
    private static final int reqCode = 33;
    private File step1PhotoFile = new File(""), step2PhotoFile = new File("");
    private boolean isReqCode = false;
    private ArrayList<State> stateList = new ArrayList<>();
    private ArrayList<City> cityList = new ArrayList<>();
    private String stateID, cityID, qualification, totalExperience, relaventExperience, bankName;
    private RelativeLayout previewImageLayout, previewImageLayout2;
    private CardView step1aLayout, step1bLayout, step1cLayout, step2Layout, step3Layout;
    private LinearLayout registrationLayout, step1Photo, step2Photo;
    private int step1FieldsSet = -1, step2FieldsSet = -1, step3FieldsSet = -1;
    private int stepNumber = 1;
    private ArrayList<String> qualificationList = new ArrayList<>();
    private ArrayList<String> totalExpList = new ArrayList<>(), relaventExpList = new ArrayList<>(), bankList = new ArrayList<>();
    ProgressBarHandler progressBarHandler;
    AppSharedPreference app_sharedpreference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_business_associate);
        context = RegistrationBusinessAssociateActivity.this;
        initView();
        setStepLayout(1);
        uploadImageCall();
        saveAndContinue();
        openCalender();
    }

    private void callWebServiceForRegistration() {
        progressBarHandler.show();

        Log.e("hi", "webservice invoked" + formBusinessData.toString());


        if (step2PhotoFile != null) {
            Log.e("hi", "file2" + step2PhotoFile.getAbsolutePath());
        } else {

            Log.e("hi", "file2 null");
        }
        Ion.with(context)
                .load(getResources().getString(R.string.webservice_base_url)+"/businessregister")
                .setHeader("authorization", webservice_header_type.get("authorization"))
                .progress(new ProgressCallback() {
                    @Override
                    public void onProgress(long downloaded, long total) {
                        Log.e("status", downloaded + "  * " + total);
                    }
                })
                .setMultipartFile("photo", "image/jpg", step1PhotoFile)
                .setMultipartFile("id_proof", "image/jpeg", step2PhotoFile)
                .setMultipartParameter("authorization", webservice_header_type.get("authorization"))
                .setMultipartParameter("business_type", "3")
                .setMultipartParameter("email", formBusinessData.getEmail())
                .setMultipartParameter("password", formBusinessData.getPassword())
                .setMultipartParameter("confirm_password", formBusinessData.getConfirmPassword())
                .setMultipartParameter("name", formBusinessData.getFirstName())
                .setMultipartParameter("last_name", formBusinessData.getLastName())
                .setMultipartParameter("father_name", formBusinessData.getFatherName())
                .setMultipartParameter("mobile", formBusinessData.getMobile_no())
                .setMultipartParameter("dob", formBusinessData.getDob())
                .setMultipartParameter("address", formBusinessData.getAddress())
                .setMultipartParameter("country_id", "101")
                .setMultipartParameter("state_id", formBusinessData.getStateID())
                .setMultipartParameter("city_id", formBusinessData.getCityID())
                .setMultipartParameter("pincode", formBusinessData.getPinCode())
                .setMultipartParameter("term", String.valueOf(formBusinessData.isAgreementAccepted()))
                .setMultipartParameter("id_proof", "")
                .setMultipartParameter("qualification", formBusinessData.getQualification())
                .setMultipartParameter("total_exp", formBusinessData.getTotalExperience())
                .setMultipartParameter("relevant_exp", formBusinessData.getRelaventExperience())
                .setMultipartParameter("bank_name", formBusinessData.getBankName())
                .setMultipartParameter("account_no", formBusinessData.getAccountNumber())
                .setMultipartParameter("branch_code", formBusinessData.getBranchCode())
                .setMultipartParameter("branch_name", formBusinessData.getBranchName())
                .setMultipartParameter("ifsc_code", formBusinessData.getIfscCode())
                .setMultipartParameter("micr_code", formBusinessData.getMicrCode())
                .setMultipartParameter("account_holder", formBusinessData.getAccountHolderName())
                .setMultipartParameter("register_mobile", formBusinessData.getRegisteredMobileWithBank())
                .setMultipartParameter("client_id", App_config.getCurrentDeviceId(RegistrationBusinessAssociateActivity.this))
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {
                        progressBarHandler.hide();
                        if (result != null) {
                            Log.e("registration_seller", result.toString());
                            if (result.get("error").getAsString().equals("false")) {
                                Log.e("registration_seller", "done");
                                AndroidUtils.showSnackBar(registrationLayout, result.get("message").getAsString());
                                Intent call_to_startactivity=new Intent(RegistrationBusinessAssociateActivity.this, ActivityOTPVerify.class);
                                call_to_startactivity.putExtra("class_name",context.getClass().getName());



                            } else {
                                showmessage(result.get("message").getAsString());
                            }
                        } else {
                            Log.e("result_seller_error", e.toString());
                            showmessage(e.toString());
                        }
                    }

                });
    }

    private void uploadImageCall() {
        uploadImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                picPhoto();
            }
        });

        cancelImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                step1PhotoFile = null;
                previewImageLayout.setVisibility(View.GONE);
            }
        });
        uploadImage2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                picPhoto();
            }
        });

        cancelImage2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                step2PhotoFile = null;
                previewImageLayout2.setVisibility(View.GONE);
            }
        });
    }

    private void openCalender() {
        openCalander.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar now = Calendar.getInstance();
                DatePickerDialog dpd = DatePickerDialog.newInstance(
                        RegistrationBusinessAssociateActivity.this,
                        now.get(Calendar.YEAR),
                        now.get(Calendar.MONTH),
                        now.get(Calendar.DAY_OF_MONTH)
                );
                dpd.setMaxDate(now);
                dpd.show(getFragmentManager(), "DatePickerDialog");
            }
        });

    }

    private void saveAndContinue() {
        tvSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("hi", "-->stepnumber" + stepNumber);
                Log.e("hi", "-->step1FieldsSet" + step1FieldsSet);
                Log.e("hi", "-->step2FieldsSet" + step2FieldsSet);
                Log.e("hi", "-->step3FieldsSet" + step3FieldsSet);
                setBusinessFormData(stepNumber);
                validateFields(stepNumber);

                Log.e("hi", "-->stepnumber" + stepNumber);
                Log.e("hi", "-->step1FieldsSet" + step1FieldsSet);
                Log.e("hi", "-->step2FieldsSet" + step2FieldsSet);
                Log.e("hi", "-->step3FieldsSet" + step3FieldsSet);
                if (stepNumber == 3 && step1FieldsSet == 0 && step2FieldsSet == 0 && step3FieldsSet == 0) {
                    callWebServiceForRegistration();
                } else {
                    if (stepNumber == 2 || stepNumber == 3) {
                        if (step1FieldsSet == 0 && stepNumber == 2) {
                            setStepLayout(2);
                        } else if (step1FieldsSet == 0 && step2FieldsSet == 0) {
                            setStepLayout(3);
                        }
                    }
                }
            }
        });
    }

    private void initView() {
        webservice_header_type.put("authorization", "xvfdbgfdhbfdhtrh54654h54ygdgerwer3");
        progressBarHandler = new ProgressBarHandler(this);
        app_sharedpreference = new AppSharedPreference(context);
        setUpToolBar();

        step1Photo = (LinearLayout) findViewById(R.id.step1Photo);
        step2Photo = (LinearLayout) findViewById(R.id.step2Photo);
        registrationLayout = (LinearLayout) findViewById(R.id.register_busi_assoc_layout);
        uploadMsg = (TextView) step1Photo.findViewById(R.id.uploadMsg);
        uploadMsg2 = (TextView) step2Photo.findViewById(R.id.uploadMsg);
        step1HeaderCircle = (TextView) findViewById(R.id.step1Circle);
        step2HeaderCircle = (TextView) findViewById(R.id.step2Circle);
        step3HeaderCircle = (TextView) findViewById(R.id.step3Circle);
        step1HeaderText = (TextView) findViewById(R.id.step1HeaderText);
        step2HeaderText = (TextView) findViewById(R.id.step2HeaderText);
        step3HeaderText = (TextView) findViewById(R.id.step3HeaderText);
        uploadImage = (ImageView) step1Photo.findViewById(R.id.uploadButton);
        openCalander = (ImageView) findViewById(R.id.openCalander);
        circleImageView = (CircleImageView) step1Photo.findViewById(R.id.previewImage);
        previewImageLayout = (RelativeLayout) step1Photo.findViewById(R.id.previewImageLayout);
        cancelImage = (ImageView) step1Photo.findViewById(R.id.cancelImage);

        uploadImage2 = (ImageView) step2Photo.findViewById(R.id.uploadButton);
        circleImageView2 = (CircleImageView) step2Photo.findViewById(R.id.previewImage);
        previewImageLayout2 = (RelativeLayout) step2Photo.findViewById(R.id.previewImageLayout);
        cancelImage2 = (ImageView) step2Photo.findViewById(R.id.cancelImage);
        etDOB = (EditText) findViewById(R.id.et_dob);
        tvSave = (TextView) findViewById(R.id.tvSave);
        et_email = (EditText) findViewById(R.id.et_email);
        et_password = (EditText) findViewById(R.id.et_password);
        et_confirm_password = (EditText) findViewById(R.id.et_confirm_password);
        et_ref_number = (EditText) findViewById(R.id.et_ref_number);
        et_first_name = (EditText) findViewById(R.id.et_first_name);
        et_last_name = (EditText) findViewById(R.id.et_last_name);
        et_father_name = (EditText) findViewById(R.id.et_father_name);
        et_mobile = (EditText) findViewById(R.id.et_mobile);
        et_account_no = (EditText) findViewById(R.id.et_account_no);
        et_branch_code = (EditText) findViewById(R.id.et_branch_code);
        et_branch_name = (EditText) findViewById(R.id.et_branch_name);
        et_ifsc_code = (EditText) findViewById(R.id.et_ifsc_code);
        et_micr_code = (EditText) findViewById(R.id.et_micr_code);
        et_account_holder_name = (EditText) findViewById(R.id.et_account_holder_name);
        et_registered_mobile_with_bank = (EditText) findViewById(R.id.et_registered_mobile_with_bank);
        et_address = (EditText) findViewById(R.id.et_address);
        et_pincode = (EditText) findViewById(R.id.et_pincode);
        spState = (Spinner) findViewById(R.id.spState);
        spCity = (Spinner) findViewById(R.id.spcity);
        spQualification = (Spinner) findViewById(R.id.spQualification);
        spTotalExp = (Spinner) findViewById(R.id.spTotalExp);
        spRelExp = (Spinner) findViewById(R.id.spRelExp);
        spSelectBank = (Spinner) findViewById(R.id.spSelectBank);
        agreement_check = (CheckBox) findViewById(R.id.agreement_check);
        step1aLayout = (CardView) findViewById(R.id.step1aLayout);
        step1bLayout = (CardView) findViewById(R.id.step1bLayout);
        step1cLayout = (CardView) findViewById(R.id.step1cLayout);
        step2Layout = (CardView) findViewById(R.id.step2Layout);
        step3Layout = (CardView) findViewById(R.id.step3Layout);


        State stateEntity_init = new State("-1", "Please Select State");
        stateList.add(stateEntity_init);
        SpStateAdapter spStateAdapter = new SpStateAdapter(context, stateList);
        spState.setAdapter(spStateAdapter);

        City cityEntity_init = new City("-1", "Please Select City");
        cityList.add(cityEntity_init);
        SpCityAdapter spCityAdapter = new SpCityAdapter(context, cityList);
        spCity.setAdapter(spCityAdapter);

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


    private void setStepLayout(int stepNo) {
        if (stepNo == 1) {
            getState();
            step1HeaderCircle.setBackground(ContextCompat.getDrawable(context, R.drawable.green_circle_border));
            step1HeaderCircle.setTextColor(ContextCompat.getColor(context, R.color.orange));
            step1HeaderText.setTextColor(ContextCompat.getColor(context, R.color.orange));
            step2HeaderCircle.setBackground(ContextCompat.getDrawable(context, R.drawable.green_circle_border_solid));
            step2HeaderCircle.setTextColor(ContextCompat.getColor(context, R.color.white));
            step2HeaderText.setTextColor(ContextCompat.getColor(context, R.color.green));
            step3HeaderCircle.setBackground(ContextCompat.getDrawable(context, R.drawable.green_circle_border_solid));
            step3HeaderCircle.setTextColor(ContextCompat.getColor(context, R.color.white));
            step3HeaderText.setTextColor(ContextCompat.getColor(context, R.color.green));
            step1aLayout.setVisibility(View.VISIBLE);
            step1bLayout.setVisibility(View.VISIBLE);
            step1cLayout.setVisibility(View.VISIBLE);
            step2Layout.setVisibility(View.GONE);
            step3Layout.setVisibility(View.GONE);
            uploadMsg.setText("Upload Your Photo");
        } else if (stepNo == 2) {
            if (formBusinessData.getQualification() == null || formBusinessData.getQualification().equals(qualificationList.get(0))) {
                setQualificationAdapter();
            }
            if (formBusinessData.getTotalExperience() == null || formBusinessData.getTotalExperience().equals(totalExpList.get(0))) {
                setTotalExperienceAdapter();
            }
            if (formBusinessData.getRelaventExperience() == null || formBusinessData.getRelaventExperience().equals(relaventExpList.get(0))) {
                setRelaventExperienceAdapter();
            }
            step2HeaderCircle.setBackground(ContextCompat.getDrawable(context, R.drawable.green_circle_border));
            step2HeaderCircle.setTextColor(ContextCompat.getColor(context, R.color.orange));
            step2HeaderText.setTextColor(ContextCompat.getColor(context, R.color.orange));
            step1HeaderCircle.setBackground(ContextCompat.getDrawable(context, R.drawable.green_circle_border_solid));
            step1HeaderCircle.setTextColor(ContextCompat.getColor(context, R.color.white));
            step1HeaderText.setTextColor(ContextCompat.getColor(context, R.color.green));
            step3HeaderCircle.setBackground(ContextCompat.getDrawable(context, R.drawable.green_circle_border_solid));
            step3HeaderCircle.setTextColor(ContextCompat.getColor(context, R.color.white));
            step3HeaderText.setTextColor(ContextCompat.getColor(context, R.color.green));
            step1aLayout.setVisibility(View.GONE);
            step1bLayout.setVisibility(View.GONE);
            step1cLayout.setVisibility(View.GONE);
            step2Layout.setVisibility(View.VISIBLE);
            step3Layout.setVisibility(View.GONE);
            previewImageLayout2.setVisibility(View.GONE);
            uploadMsg2.setText("Upload Your Pan card, Aadhar Card, Passport");

        } else if (stepNo == 3) {
            setBankListAdapter();
            tvSave.setText(getString(R.string.save));
            step3HeaderCircle.setBackground(ContextCompat.getDrawable(context, R.drawable.green_circle_border));
            step3HeaderCircle.setTextColor(ContextCompat.getColor(context, R.color.orange));
            step3HeaderText.setTextColor(ContextCompat.getColor(context, R.color.orange));
            step2HeaderCircle.setBackground(ContextCompat.getDrawable(context, R.drawable.green_circle_border_solid));
            step2HeaderCircle.setTextColor(ContextCompat.getColor(context, R.color.white));
            step2HeaderText.setTextColor(ContextCompat.getColor(context, R.color.green));
            step1HeaderCircle.setBackground(ContextCompat.getDrawable(context, R.drawable.green_circle_border_solid));
            step1HeaderCircle.setTextColor(ContextCompat.getColor(context, R.color.white));
            step1HeaderText.setTextColor(ContextCompat.getColor(context, R.color.green));
            step1aLayout.setVisibility(View.GONE);
            step1bLayout.setVisibility(View.GONE);
            step1cLayout.setVisibility(View.GONE);
            step2Layout.setVisibility(View.GONE);
            step3Layout.setVisibility(View.VISIBLE);

        }
    }

    private void showDate(int year, int month, int day) {
        etDOB.setTextColor(ContextCompat.getColor(context, R.color.black));
        etDOB.setText(new StringBuilder().append(year).append("-").append(month).append("-").append(day));
    }


    public void getState() {
        HashMap<String, String> webservice_body_parameter = new HashMap<>();
        webservice_body_parameter.put("authorization", "xvfdbgfdhbfdhtrh54654h54ygdgerwer3");
        webservice_body_parameter.put("type", "state");
        webservice_body_parameter.put("id", "101");//country id fixed 101 for India

        Call_webservice.getcountrystatedata(context, "state", getResources().getString(R.string.webservice_base_url) + "/dropdown", webservice_body_parameter, webservice_header_type);

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
                    SpStateAdapter spStateAdapter = new SpStateAdapter(context, stateList);
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

        Call_webservice.getcountrystatedata(context, "city", getResources().getString(R.string.webservice_base_url) + "/dropdown", webservice_body_parameter, webservice_header_type);

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

                    SpCityAdapter spCityAdapter = new SpCityAdapter(context, cityList);
                    spCity.setAdapter(spCityAdapter);

                    spCity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            cityID = cityList.get(position).cityId;
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
        } else {
            in = new Intent();
            in.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
        }
        startActivityForResult(Intent.createChooser(in, "Select profile picture"), which);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        try {
            if (reqCode == requestCode) {
                if (resultCode == Activity.RESULT_OK) {
                    isReqCode = true;
                }
            } else if (resultCode == Activity.RESULT_OK) {
                BitmapFactory.Options option = new BitmapFactory.Options();
                option.inDither = false;
                option.inPurgeable = true;
                option.inInputShareable = true;
                option.inTempStorage = new byte[32 * 1024];
                option.inPreferredConfig = Bitmap.Config.RGB_565;
                if (Build.VERSION.SDK_INT < 19) {
                    if (stepNumber == 1) {
                        imageForPreview = BitmapFactory.decodeFile(getFilesDir().getPath(), option);
                    } else if (stepNumber == 2) {
                        imageForPreview2 = BitmapFactory.decodeFile(getFilesDir().getPath(), option);
                    }
                } else {
                    if (data.getData() != null) {

                        ParcelFileDescriptor pfd;
                        try {
                            pfd = getContentResolver()
                                    .openFileDescriptor(data.getData(), "r");
                            if (pfd != null) {
                                FileDescriptor fileDescriptor = pfd
                                        .getFileDescriptor();
                                if (stepNumber == 1) {
                                    imageForPreview = BitmapFactory.decodeFileDescriptor(fileDescriptor, null, option);
                                } else if (stepNumber == 2) {
                                    imageForPreview2 = BitmapFactory.decodeFileDescriptor(fileDescriptor, null, option);
                                }
                            }
                            pfd.close();


                        } catch (FileNotFoundException e) {
                            Log.e("FileNotFoundException", e.toString());
                        } catch (IOException e) {
                            Log.e("IOException", e.toString());
                        }
                    } else {
                        if (stepNumber == 1) {
                            imageForPreview = (Bitmap) data.getExtras().get("data");
                        } else if (stepNumber == 2) {
                            imageForPreview2 = (Bitmap) data.getExtras().get("data");
                        }
                        Log.e("data_not_found", "data_not_found");
                    }

                }
                try {
                    previewImageLayout.setVisibility(View.VISIBLE);
                    previewImageLayout2.setVisibility(View.VISIBLE);
                    if (stepNumber == 1) {
                        if (ImageUtils.sizeOf(imageForPreview) > 2048) {
                            circleImageView.setImageBitmap(ImageUtils.resize(imageForPreview, imageForPreview.getHeight() / 2, imageForPreview.getWidth() / 2));
                            step1PhotoFile = getFile(ImageUtils.resize(imageForPreview, imageForPreview.getHeight() / 2, imageForPreview.getWidth() / 2));
                        } else {
                            circleImageView.setImageBitmap(imageForPreview);
                            step1PhotoFile = getFile(imageForPreview);
                        }
                    } else if (stepNumber == 2) {
                        if (ImageUtils.sizeOf(imageForPreview2) > 2048) {
                            circleImageView2.setImageBitmap(ImageUtils.resize(imageForPreview2, imageForPreview2.getHeight() / 2, imageForPreview2.getWidth() / 2));
                            step2PhotoFile = getFile(ImageUtils.resize(imageForPreview2, imageForPreview2.getHeight() / 2, imageForPreview2.getWidth() / 2));
                        } else {
                            circleImageView2.setImageBitmap(imageForPreview2);
                            step2PhotoFile = getFile(imageForPreview2);
                        }
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    public void setBusinessFormData(int stepNo) {

        if (stepNo == 1) {
            formBusinessData.setEmail(et_email.getText() == null ? "" : et_email.getText().toString());
            formBusinessData.setPassword(et_password.getText() == null ? "" : et_password.getText().toString());
            formBusinessData.setConfirmPassword(et_confirm_password.getText() == null ? "" : et_confirm_password.getText().toString());
            formBusinessData.setFirstName(et_first_name.getText() == null ? "" : et_first_name.getText().toString());
            formBusinessData.setLastName(et_last_name.getText() == null ? "" : et_last_name.getText().toString());
            formBusinessData.setFatherName(et_father_name.getText() == null ? "" : et_father_name.getText().toString());
            formBusinessData.setMobile_no(et_mobile.getText() == null ? "" : et_mobile.getText().toString());
            formBusinessData.setDob(etDOB.getText() == null ? "" : etDOB.getText().toString());
            formBusinessData.setAddress(et_address.getText() == null ? "" : et_address.getText().toString());
            formBusinessData.setStateID(stateID);
            formBusinessData.setCityID(cityID);
            formBusinessData.setPinCode(et_pincode.getText() == null ? "" : et_pincode.getText().toString());
            formBusinessData.setAgreementAccepted(agreement_check.isChecked());
        } else if (stepNo == 2) {
            formBusinessData.setQualification(qualification == null ? "" : qualification);
            formBusinessData.setTotalExperience(totalExperience == null ? "" : totalExperience);
            formBusinessData.setRelaventExperience(relaventExperience == null ? "" : relaventExperience);
        } else if (stepNo == 3) {
            tvSave.setText("Save");
            formBusinessData.setBankName(bankName == null ? "" : bankName);
            formBusinessData.setAccountNumber(et_account_no == null ? "" : et_account_no.getText().toString());
            formBusinessData.setBranchCode(et_branch_code == null ? "" : et_branch_code.getText().toString());
            formBusinessData.setBranchName(et_branch_name == null ? "" : et_branch_name.getText().toString());
            formBusinessData.setIfscCode(et_ifsc_code == null ? "" : et_ifsc_code.getText().toString());
            formBusinessData.setMicrCode(et_micr_code == null ? "" : et_micr_code.getText().toString());
            formBusinessData.setAccountHolderName(et_account_holder_name == null ? "" : et_account_holder_name.getText().toString());
            formBusinessData.setRegisteredMobileWithBank(et_registered_mobile_with_bank == null ? "" : et_registered_mobile_with_bank.getText().toString());
        }
    }


    private void setQualificationAdapter() {
        if (qualificationList != null && qualificationList.size() > 0) {
            qualificationList.clear();
        }
        progressBarHandler.show();
        qualificationList.add("Please Select Qualification");
        CustomSimpleListAdapter qualificationAdapter = new CustomSimpleListAdapter(context, qualificationList);
        spQualification.setAdapter(qualificationAdapter);
        Ion.with(context)
                .load((getResources().getString(R.string.webservice_base_url))+"/qualification")
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {
                        progressBarHandler.hide();
                        if(result == null){
                            showmessage("Qualification Data is Null.");
                            Log.e("qualification", "Qualification Data is Null.");
                        }else {
                            if(result.get("error").getAsString().equals(String.valueOf(false))){
                                JsonArray jsonResultArray = result.getAsJsonArray("result");
                                for(int i = 0; i < jsonResultArray.size(); i++){
                                    JsonObject jsonObject = (JsonObject) jsonResultArray.get(i);
                                    qualificationList.add(jsonObject.get("name").getAsString());
                                }
                                CustomSimpleListAdapter qualificationAdapter = new CustomSimpleListAdapter(context, qualificationList);
                                spQualification.setAdapter(qualificationAdapter);
                                spQualification.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    @Override
                                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                        Log.e("hi", "Selected Qualification is " + qualificationList.get(position) + System.currentTimeMillis());
                                        if (position > 1) {
                                            qualification = qualificationList.get(position);
                                            formBusinessData.setQualification(qualification);
                                        }
                                        Log.e("hi", formBusinessData.getQualification() + System.currentTimeMillis());
                                    }

                                    @Override
                                    public void onNothingSelected(AdapterView<?> parent) {

                                    }
                                });

                            }else {
                                String msg = result.get("message").getAsString();
                                showmessage(msg);
                                Log.e("qualification", msg);
                            }
                        }
                    }
                });


    }

    private void setTotalExperienceAdapter() {
        if (totalExpList != null && totalExpList.size() > 0) {
            totalExpList.clear();
        }
        progressBarHandler.show();
        totalExpList.add("Please Select Total Experience");
        CustomSimpleListAdapter totalExpAdapter = new CustomSimpleListAdapter(context, totalExpList);
        spTotalExp.setAdapter(totalExpAdapter);


        Ion.with(context)
                .load((getResources().getString(R.string.webservice_base_url))+"/total_experience")
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {
                        progressBarHandler.hide();
                        if(result == null){
                            showmessage("Total Exp Data is Null.");
                            Log.e("totalExp", "Total Exp Data is Null.");
                        }else {
                            if(result.get("error").getAsString().equals(String.valueOf(false))){
                                JsonArray jsonResultArray = result.getAsJsonArray("result");
                                for(int i = 0; i < jsonResultArray.size(); i++){
                                    JsonObject jsonObject = (JsonObject) jsonResultArray.get(i);
                                    totalExpList.add(jsonObject.get("name").getAsString());
                                }
                                CustomSimpleListAdapter totalExpAdapter = new CustomSimpleListAdapter(context, totalExpList);
                                spTotalExp.setAdapter(totalExpAdapter);
                                spTotalExp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    @Override
                                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                                        Log.e("totalExp", "Selected Total Experience is " + qualificationList.get(position)+"     " + System.currentTimeMillis());
                                        if (position > 1) {
                                            totalExperience = totalExpList.get(position);
                                            formBusinessData.setTotalExperience(totalExperience);
                                        }
                                        Log.e("totalExp", formBusinessData.getTotalExperience() + System.currentTimeMillis());
                                    }

                                    @Override
                                    public void onNothingSelected(AdapterView<?> parent) {

                                    }
                                });

                            }else {
                                String msg = result.get("message").getAsString();
                                showmessage(msg);
                                Log.e("totalExp", msg);
                            }
                        }
                    }
                });

    }

    private void setRelaventExperienceAdapter() {
        if (relaventExpList != null && relaventExpList.size() > 0) {
            relaventExpList.clear();
        }
        progressBarHandler.show();
        relaventExpList.add("Please Select Relavent Experience");
        CustomSimpleListAdapter relaventExpAdapter = new CustomSimpleListAdapter(context, relaventExpList);
        spRelExp.setAdapter(relaventExpAdapter);


        Ion.with(context)
                .load((getResources().getString(R.string.webservice_base_url))+"/relevant_experience")
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {
                        progressBarHandler.hide();
                        if(result == null){
                            showmessage("Relavent Exp Data is Null.");
                            Log.e("relaventExpList", "Relavent Exp Data is Null.");
                        }else {
                            if(result.get("error").getAsString().equals(String.valueOf(false))){
                                JsonArray jsonResultArray = result.getAsJsonArray("result");
                                for(int i = 0; i < jsonResultArray.size(); i++){
                                    JsonObject jsonObject = (JsonObject) jsonResultArray.get(i);
                                    relaventExpList.add(jsonObject.get("experience").getAsString());
                                }
                                CustomSimpleListAdapter relaventExpAdapter = new CustomSimpleListAdapter(context, relaventExpList);
                                spRelExp.setAdapter(relaventExpAdapter);
                                spRelExp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    @Override
                                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                        Log.e("relaventExpList", "Selected Relavent Experience is " + relaventExpList.get(position)+"     " + System.currentTimeMillis());
                                        if (position > 1) {
                                            relaventExperience = relaventExpList.get(position);
                                            formBusinessData.setTotalExperience(relaventExperience);
                                        }
                                        Log.e("relaventExpList", formBusinessData.getRelaventExperience() + System.currentTimeMillis());
                                    }

                                    @Override
                                    public void onNothingSelected(AdapterView<?> parent) {

                                    }
                                });

                            }else {
                                String msg = result.get("message").getAsString();
                                showmessage(msg);
                                Log.e("relaventExpList", msg);
                            }
                        }
                    }
                });

    }

    private void setBankListAdapter() {
        if (bankList != null && bankList.size() > 0) {
            bankList.clear();
        }
        progressBarHandler.show();
        bankList.add("Please Select Bank Name");
        CustomSimpleListAdapter bankListAdapter = new CustomSimpleListAdapter(context, bankList);
        spSelectBank.setAdapter(bankListAdapter);


        Ion.with(context)
                .load((getResources().getString(R.string.webservice_base_url))+"/bank_list")
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {
                        progressBarHandler.hide();
                        if(result == null){
                            showmessage("Bank Name Data is Null.");
                            Log.e("bankList", "Bank Name Data is Null.");
                        }else {
                            if(result.get("error").getAsString().equals(String.valueOf(false))){
                                JsonArray jsonResultArray = result.getAsJsonArray("result");
                                for(int i = 0; i < jsonResultArray.size(); i++){
                                    JsonObject jsonObject = (JsonObject) jsonResultArray.get(i);
                                    bankList.add(jsonObject.get("name").getAsString());
                                }
                                CustomSimpleListAdapter bankListAdapter = new CustomSimpleListAdapter(context, bankList);
                                spSelectBank.setAdapter(bankListAdapter);
                                spSelectBank.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    @Override
                                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                       if (position > 1) {
                                            bankName = bankList.get(position);
                                            formBusinessData.setBankName(bankName);
                                        }
                                        Log.e("bankList", "Selected Bank is " + bankList.get(position)+"     " + System.currentTimeMillis());
                                        Log.e("bankList", formBusinessData.getBankName() + System.currentTimeMillis());
                                    }

                                    @Override
                                    public void onNothingSelected(AdapterView<?> parent) {

                                    }
                                });

                            }else {
                                String msg = result.get("message").getAsString();
                                showmessage(msg);
                                Log.e("bankList", msg);
                            }
                        }
                    }
                });
    }

    private void validateFields(int stepNo) {
        if (formBusinessData != null) {
            Log.e("hi", formBusinessData.toString());
            if (stepNo == 1) {
                step1FieldsSet = 0;
                if (!Validation.isValidEmail(formBusinessData.getEmail())) {
                    putError(2);
                    step1FieldsSet++;
                } else if (!Validation.isValidPassword(formBusinessData.getPassword())) {
                    putError(4);
                    step1FieldsSet++;
                } else if (!Validation.isValidPassword(formBusinessData.getConfirmPassword())) {
                    putError(19);
                    step1FieldsSet++;
                } else if (!Validation.isPasswordMatching(formBusinessData.getPassword(), formBusinessData.getConfirmPassword())) {
                    putError(5);
                    step1FieldsSet++;
                } else if (Validation.isEmptyStr(formBusinessData.getFirstName())) {
                    putError(0);
                    step1FieldsSet++;
                } else if (Validation.isEmptyStr(formBusinessData.getLastName())) {
                    putError(1);
                    step1FieldsSet++;
                } else if (Validation.isEmptyStr(formBusinessData.getFatherName())) {
                    putError(10);
                    step1FieldsSet++;
                } else if (!Validation.isValidNumber(formBusinessData.getMobile_no(), Validation.getNumberPrefix(formBusinessData.getMobile_no()))) {
                    putError(3);
                    step1FieldsSet++;
                } else if (!Validation.isValidDOB(formBusinessData.getDob())) {
                    putError(6);
                    step1FieldsSet++;
                } else if (Validation.isEmptyStr(formBusinessData.getAddress())) {
                    putError(9);
                    step1FieldsSet++;
                }else if (!(Validation.isNonEmptyStr(formBusinessData.getStateID()) &&
                        Integer.parseInt(formBusinessData.getStateID()) > 0)) {
                    AndroidUtils.showSnackBar(registrationLayout, "Please Select State");
                    step1FieldsSet++;
                } else if (!(Validation.isNonEmptyStr(formBusinessData.getCityID()) &&
                        Integer.parseInt(formBusinessData.getCityID()) > 0)) {
                    AndroidUtils.showSnackBar(registrationLayout, "Please Select City");
                    step1FieldsSet++;
                } else if (!Validation.isPincode(formBusinessData.getPinCode())) {
                    putError(11);
                    step1FieldsSet++;
                } else if (!formBusinessData.isAgreementAccepted()) {
                    putError(7);
                    step1FieldsSet++;
                } else if (step1PhotoFile.getAbsolutePath().equals("/")) {
                    showmessage("Please Upload File");
                    step1FieldsSet++;
                }
                Log.e("hi", "step1FieldsSet=" + step1FieldsSet);

                if (step1FieldsSet == 0) {
                    stepNumber = 2;
                }
            } else if (stepNo == 2) {
                step2FieldsSet = 0;
                if (Validation.isEmptyStr(formBusinessData.getQualification()) ||
                        formBusinessData.getQualification().equals(qualificationList.get(0))) {
                    AndroidUtils.showSnackBar(registrationLayout, "Please Select Qualification");
                    step2FieldsSet++;
                } else if (Validation.isEmptyStr(formBusinessData.getTotalExperience()) ||
                        formBusinessData.getTotalExperience().equals(totalExpList.get(0))) {
                    AndroidUtils.showSnackBar(registrationLayout, "Please Select Total Experience");
                    step2FieldsSet++;
                } else if (Validation.isEmptyStr(formBusinessData.getRelaventExperience()) ||
                        formBusinessData.getRelaventExperience().equals(relaventExpList.get(0))) {
                    AndroidUtils.showSnackBar(registrationLayout, "Please Select Relavent Experience");
                    step2FieldsSet++;
                } else if ((step2PhotoFile == null) || step2PhotoFile.getAbsolutePath().equals("/")) {
                    showmessage("Please Upload File");
                    step2FieldsSet++;
                }
                if (step2FieldsSet == 0) {
                    stepNumber = 3;
                }

            } else if (stepNo == 3) {
                step3FieldsSet = 0;
                if (Validation.isEmptyStr(formBusinessData.getBankName())) {
                    AndroidUtils.showSnackBar(registrationLayout, "Please Select Bank Name");
                    step3FieldsSet++;
                } else if (!(Validation.isNonEmptyStr(formBusinessData.getAccountNumber()) &&
                        Validation.isNumber(formBusinessData.getAccountNumber()))) {
                    putError(12);
                    step3FieldsSet++;
                } else if (Validation.isEmptyStr(formBusinessData.getBranchCode())) {
                    putError(13);
                    step3FieldsSet++;
                } else if (Validation.isEmptyStr(formBusinessData.getBranchName())) {
                    putError(14);
                    step3FieldsSet++;
                } else if (Validation.isEmptyStr(formBusinessData.getIfscCode())) {
                    putError(15);
                    step3FieldsSet++;
                } else if (Validation.isEmptyStr(formBusinessData.getMicrCode())) {
                    putError(16);
                    step3FieldsSet++;
                } else if (Validation.isEmptyStr(formBusinessData.getAccountHolderName())) {
                    putError(17);
                    step3FieldsSet++;
                } else if (!Validation.isValidNumber(formBusinessData.getRegisteredMobileWithBank(), Validation.getNumberPrefix(formBusinessData.getRegisteredMobileWithBank()))) {
                    putError(18);
                    step3FieldsSet++;
                }

            }
        } else {
            AndroidUtils.showSnackBar(registrationLayout, "Please fill the registration form");
        }
    }

    private void putError(int id) {
        Log.e("reach", "       )))))))))" + id);
        switch (id) {
            case 0:
                et_first_name.setError("First Name Can't be empty");
                showmessage("First Name Can't be empty");
                break;
            case 1:
                et_last_name.setError("Last Name Can't be empty");
                showmessage("Last Name Can't be empty");
                break;
            case 2:
                et_email.setError("Please Enter Valid Email");
                showmessage("Please Enter Valid Email");
                break;
            case 3:
                et_mobile.setError("Please Enter Valid Mobile Number");
                showmessage("Please Enter Valid Mobile Number");
                break;
            case 4:
                et_password.setError("Password must be greater than or equals to 6 digits");
                showmessage("Password must be greater than or equals to 6 digits");
                break;
            case 5:
                et_confirm_password.setError("Password did not matched");
                showmessage("Password did not matched");
                break;
            case 6:
                etDOB.setError("Please Select Date");
                showmessage("Please Select Date");
                break;
            case 7:
                ((TextView) findViewById(R.id.tv_agreement)).setError("Please Accept Terms & Conditions");
                showmessage("Please Accept Terms & Conditions");
                break;
            case 9:
                et_address.setError("Address Can't be empty");
                showmessage("Address Can't be empty");
                break;
            case 10:
                et_father_name.setError("Father's Name Can't be empty");
                showmessage("Father's Name Can't be empty");
                break;
            case 11:
                et_pincode.setError("Please Enter Valid PINCODE");
                showmessage("Please Enter Valid PINCODE");
                break;
            case 12:
                et_account_no.setError("Please Enter Valid Account Number");
                showmessage("Please Enter Valid Account Number");
                break;
            case 13:
                et_branch_code.setError("Please Enter Branch Code");
                showmessage("Please Enter Branch Code");
                break;
            case 14:
                et_branch_name.setError("Please Enter Branch Name");
                showmessage("Please Enter Branch Name");
                break;
            case 15:
                et_ifsc_code.setError("Please Enter IFSC Code");
                showmessage("Please Enter IFSC Code");
                break;
            case 16:
                et_micr_code.setError("Please Enter MICR Code");
                showmessage("Please Enter MICR Code");
                break;
            case 17:
                et_account_holder_name.setError("Please Enter Account Holder Name");
                showmessage("Please Enter Account Holder Name");
                break;
            case 18:
                et_registered_mobile_with_bank.setError("Please Enter Your Registered mobile number");
                showmessage("Please Enter Your Registered mobile number");
                break;
            case 19:
                et_confirm_password.setError("Password must be greater than or equals to 6 digits");
                showmessage("Password must be greater than or equals to 6 digits");
                break;

            default:
                break;
        }
    }

    private File getFile(Bitmap photo) {
        Uri tempUri = null;
        if (photo != null) {
            tempUri = getImageUri(RegistrationBusinessAssociateActivity.this, photo);
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
            cursor = RegistrationBusinessAssociateActivity.this.getContentResolver().query(uri, null, null, null, null);
            assert cursor != null;
            cursor.moveToFirst();
            idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
        }
        return cursor.getString(idx);
    }


    public void showmessage(String message) {
        AndroidUtils.showSnackBar(registrationLayout, message);
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        showDate(year, monthOfYear + 1, dayOfMonth);
    }

    @Override
    public void onTimeSet(TimePickerDialog view, int hourOfDay, int minute, int second) {

    }
}
