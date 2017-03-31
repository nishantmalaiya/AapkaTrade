package com.example.pat.aapkatrade.login;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.pat.aapkatrade.Home.HomeActivity;
import com.example.pat.aapkatrade.R;
import com.example.pat.aapkatrade.general.AppSharedPreference;
import com.example.pat.aapkatrade.general.App_config;
import com.example.pat.aapkatrade.general.Call_webservice;
import com.example.pat.aapkatrade.general.Change_Font;
import com.example.pat.aapkatrade.general.TaskCompleteReminder;
import com.example.pat.aapkatrade.general.Utils.AndroidUtils;
import com.example.pat.aapkatrade.general.Validation;
import com.example.pat.aapkatrade.general.progressbar.ProgressBarHandler;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import java.util.HashMap;


public class Reset_password_fragment extends Fragment implements View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER


    AppSharedPreference app_sharedpreference;
    ProgressBarHandler progressBarHandler;
    TextView tv_forgot_password,tv_forgot_password_description;
    EditText et_password,et_confirm_password;
    Button btn_reset_password;
    private CoordinatorLayout activity_forgot__password;
    private String usertype;
    String user_id;

    String classname;
    Forgot_password_fragment forgot_password_fragment;
    Reset_password_fragment reset_password_fragment;

    String class_index;



    public Reset_password_fragment() {
        // Required empty public constructor
    }






    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_reset_password, container, false);

        initView(v);


        return v;

    }

    private void initView(View v) {
        app_sharedpreference = new AppSharedPreference(getActivity());
        progressBarHandler = new ProgressBarHandler(getActivity());

        tv_forgot_password = (TextView)v. findViewById(R.id.tv_forgot_password);
        tv_forgot_password_description = (TextView) v.findViewById(R.id.tv_forgot_password_description);


        et_password = (EditText)v. findViewById(R.id.et_password_forgot);
        et_confirm_password = (EditText)v. findViewById(R.id.et_confirm_password);

        btn_reset_password = (Button) v.findViewById(R.id.btn_change_password);
        btn_reset_password.setOnClickListener(this);

        // activity_forgot__password = (CoordinatorLayout) findViewById(R.id.activity_forgot__password);

        Change_Font.Change_Font_textview(getActivity(), tv_forgot_password);
        Change_Font.Change_Font_textview(getActivity(), tv_forgot_password_description);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.btn_change_password:


                Validatefields();


                break;
        }

    }

    private void Validatefields() {

        if (Validation.isValidPassword(et_password.getText().toString().trim())) {
            if(Validation.isPasswordMatching(et_password.getText().toString().trim(),et_confirm_password.getText().toString().trim()))
            {

                call_reset_webservice();
            }


        } else if (Validation.isValidPassword(et_confirm_password.getText().toString().trim())) {

            if(Validation.isPasswordMatching(et_password.getText().toString().trim(),et_confirm_password.getText().toString().trim()))
            {
                call_reset_webservice();
            }


        } else {
            showmessage("!Blank password not allowed");

        }


    }

    private void showmessage(String message) {
        AndroidUtils.showSnackBar(activity_forgot__password, message);
    }


    private void call_reset_webservice() {
        progressBarHandler.show();

          user_id=app_sharedpreference.getsharedpref("userid","notlogin");
        String webservice_reset_password = getResources().getString(R.string.webservice_base_url) + "/resetPwd";


        if (app_sharedpreference.shared_pref != null) {
            if (app_sharedpreference.getsharedpref("usertype", "0").equals("3")) {

                usertype = "business";
            } else if ((app_sharedpreference.getsharedpref("usertype", "0").equals("1"))) {
                usertype = "seller";

            } else if (app_sharedpreference.getsharedpref("usertype", "0").equals("2")) {
                usertype = "business";

            }
        } else {
            Log.e("null_sharedPreferences", "sharedPreferences");
        }



        Ion.with(getActivity())
                .load(webservice_reset_password)
                .setHeader("authorization", "xvfdbgfdhbfdhtrh54654h54ygdgerwer3")
                .setBodyParameter("authorization","xvfdbgfdhbfdhtrh54654h54ygdgerwer3")
                .setBodyParameter("user_id ", user_id)
                .setBodyParameter("password", et_confirm_password.getText().toString())
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {
                        progressBarHandler.hide();

                        if (result != null) {
                            String error = result.get("error").getAsString();
                            if (error.contains("false")) {
                                Intent go_to_activity_otp_verify = new Intent(getActivity(), HomeActivity.class);
                                go_to_activity_otp_verify.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                                startActivity(go_to_activity_otp_verify);
                            }
                            String message = result.get("message").getAsString();
                            showmessage(message);
                            progressBarHandler.hide();
                        } else {
                            Log.e("error_json",e.toString());
                            progressBarHandler.hide();
                        }
                        Log.e("reset_password", result.toString());

//                        taskCompleteReminder.Taskcomplete(result);

                        //taskCompleteReminder.Taskcomplete("complete");
//
                    }

                });







    }











    // TODO: Rename method, update argument and hook method into UI event





}
