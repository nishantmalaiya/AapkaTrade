package com.example.pat.aapkatrade.user_dashboard;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.pat.aapkatrade.Home.navigation.NavigationFragment;
import com.example.pat.aapkatrade.Home.registration.RegistrationActivity;
import com.example.pat.aapkatrade.Home.registration.RegistrationBusinessAssociateActivity;
import com.example.pat.aapkatrade.R;
import com.example.pat.aapkatrade.general.AppSharedPreference;

import com.example.pat.aapkatrade.general.progressbar.ProgressBarHandler;
import com.example.pat.aapkatrade.user_dashboard.my_profile.MyProfileActivity;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class User_DashboardFragment extends Fragment {


    RecyclerView dashboardlist;
    DashboardAdapter dashboardAdapter;
    ArrayList<DashboardData> dashboardDatas = new ArrayList<>();
    AppSharedPreference app_sharedpreference;
    TextView tvMobile, tvEmail, textViewName, tvUserType;
    ImageView btnEdit;
    ProgressBarHandler progressBarHandler;
    RecyclerView.LayoutManager layoutManager;
    CircleImageView imageviewpp;
    String user_image;
    public User_DashboardFragment() {

    }


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_dashboard, container, false);
        app_sharedpreference = new AppSharedPreference(getActivity());
        progressBarHandler = new ProgressBarHandler(getActivity());
        tvUserType = (TextView) v.findViewById(R.id.tvUserType);
        setup_layout(v);
        dashboardlist = (RecyclerView) v.findViewById(R.id.dashboardlist);
        layoutManager = new LinearLayoutManager(getActivity());
        setup_data();
        dashboardlist.setNestedScrollingEnabled(false);
        return v;
    }

    private void setup_layout(View v) {
        imageviewpp=(CircleImageView)v.findViewById(R.id.imageviewpp) ;

        user_image = app_sharedpreference.getsharedpref("profile_pic","demo");
        Log.e("user_image",user_image);
//        Picasso.with(getActivity()).load(user_image)
//                .error(R.drawable.ic_profile_user)
//                .into(imageviewpp);
        textViewName = (TextView) v.findViewById(R.id.textViewName);
        tvMobile = (TextView) v.findViewById(R.id.tvMobile);
        tvEmail = (TextView) v.findViewById(R.id.tvEmail);
        btnEdit = (ImageView) v.findViewById(R.id.btnEdit);
        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), MyProfileActivity.class);
                startActivity(i);
            }
        });

        if (app_sharedpreference.getsharedpref("username", "notlogin") != null) {
            String Username = app_sharedpreference.getsharedpref("name", "not");
            String Emailid = app_sharedpreference.getsharedpref("emailid", "not");
            if (Username.equals("notlogin")) {
                textViewName.setText("");
                tvEmail.setText("");
            } else {
                textViewName.setText(Username);
                tvEmail.setText(Emailid);
            }
        }




    }

    private void setup_data() {
        dashboardDatas.clear();
        try {

            if (app_sharedpreference.shared_pref != null) {

                String userid = app_sharedpreference.getsharedpref("userid", "0");

                String user_detail_webserviceurl = (getResources().getString(R.string.webservice_base_url)) + "/userdata";

                if (app_sharedpreference.getsharedpref("usertype", "0").equals("3")) {

                    userdata_webservice(user_detail_webserviceurl, "3", userid);


                } else if ((app_sharedpreference.getsharedpref("usertype", "0").equals("2"))) {

                    userdata_webservice(user_detail_webserviceurl, "1", userid);


                } else if (app_sharedpreference.getsharedpref("usertype", "0").equals("1")) {

                    userdata_webservice(user_detail_webserviceurl, "2", userid);


                }
            } else {
                Log.e("null_sharedPreferences", "sharedPreferences");
            }

        } catch (Exception e) {
        }


    }

    public void userdata_webservice(String url, final String user_type, String user_id) {
        Log.e("url", url);
        Log.e("user_type", user_type);
        Log.e("user_id", user_id);
        progressBarHandler.show();
        Ion.with(getActivity())
                .load(url)
                .setHeader("authorization", "xvfdbgfdhbfdhtrh54654h54ygdgerwer3")
                .setBodyParameter("type",user_type)
                .setBodyParameter("id", user_id)
                .setBodyParameter("authorization", "xvfdbgfdhbfdhtrh54654h54ygdgerwer3")
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {
                        if (result == null) {
                            Log.e("result_myProfile", "result_myProfile is null");
                            progressBarHandler.hide();
                        } else {
                            Log.e("result_userdata", result.toString());
                            String error = result.get("error").getAsString();
                            if (error.contains("true")) {
                                progressBarHandler.hide();
                            }
                            else
                                {

                                progressBarHandler.hide();
                                if (user_type.contains("1")) {

                                    String order_quantity = result.get("order").getAsString();
                                    app_sharedpreference.setsharedpref("order_quantity", order_quantity);
                                    dashboardDatas.add(new DashboardData("", "My Profile", R.drawable.ic_myprofile, R.drawable.circle_teal, false, ""));
                                    dashboardDatas.add(new DashboardData("", "Change Password", R.drawable.ic_chngpswd, R.drawable.circle_purple, false, ""));
                                    dashboardDatas.add(new DashboardData("", "Order", R.drawable.ic_lstprdct, R.drawable.circle_sienna, true, order_quantity));
                                    dashboardDatas.add(new DashboardData("", "Cancel Order", R.drawable.ic_lstprdct, R.drawable.circle_cherry_red, true, ""));

                                    tvUserType.setText("Welcome Buyer");
                                    dashboardlist.setLayoutManager(layoutManager);
                                    dashboardAdapter = new DashboardAdapter(getContext(), dashboardDatas);
                                    dashboardlist.setAdapter(dashboardAdapter);


                                } else if (user_type.contains("2")) {
                                    String order_quantity = result.get("order").getAsString();
                                    String product_quantity = result.get("product").getAsString();
                                    String company_quantity = result.get("company").getAsString();

                                    app_sharedpreference.setsharedpref("order_quantity", order_quantity);
                                    app_sharedpreference.setsharedpref("product_quantity", product_quantity);
                                    app_sharedpreference.setsharedpref("company_quantity", company_quantity);

                                    String order_quantity_shared_pref = app_sharedpreference.getsharedpref("order_quantity", "0");
                                    String product_quantity_shared_pref = app_sharedpreference.getsharedpref("product_quantity", "0");
                                    String company_quantity_shared_pref = app_sharedpreference.getsharedpref("company_quantity", "0");
                                    Log.e("order_quantity", order_quantity);
                                    Log.e("product_quantity", product_quantity);
                                    Log.e("company_quantity", company_quantity);

                                    dashboardDatas.add(new DashboardData("", "My Profile", R.drawable.ic_myprofile, R.drawable.circle_teal, false, ""));
                                    dashboardDatas.add(new DashboardData("", "Change Password", R.drawable.ic_chngpswd, R.drawable.circle_purple, false, ""));
                                    dashboardDatas.add(new DashboardData("", "Add Company", R.drawable.ic_add_company, R.drawable.circle_cherry_red, false, ""));
                                    dashboardDatas.add(new DashboardData("", "Company List", R.drawable.ic_lstcmpny, R.drawable.circle_deep_pink, true, company_quantity_shared_pref));
                                    dashboardDatas.add(new DashboardData("", "Add Product", R.drawable.ic_adprdct, R.drawable.circle_turquoise, false, ""));
                                    dashboardDatas.add(new DashboardData("", "List Product", R.drawable.ic_lstprdct, R.drawable.circle_slate_gray, true, product_quantity_shared_pref));
                                    dashboardDatas.add(new DashboardData("", "Order", R.drawable.ic_lstprdct, R.drawable.circle_sienna, true, order_quantity_shared_pref));
                                    dashboardDatas.add(new DashboardData("", "Enquiry Services List", R.drawable.ic_svr_enquiry, R.drawable.circle_purple, true, ""));

                                    String Username = app_sharedpreference.getsharedpref("username", "not");

                                    if (Username.equals("notlogin")) {
                                        tvUserType.setText("Welcome Guest");
                                        NavigationFragment.usertype.setText("Welcome Guest");
                                    } else {
                                        tvUserType.setText("Welcome Seller");
                                    }


                                    dashboardlist.setLayoutManager(layoutManager);
                                    dashboardAdapter = new DashboardAdapter(getContext(), dashboardDatas);
                                    dashboardlist.setAdapter(dashboardAdapter);
                                }
                                else if (user_type.contains("3"))
                                {

                                    String vendor_quantity = result.get("vendor").getAsString();
                                    String network_quantity = result.get("network").getAsString();
                                    app_sharedpreference.setsharedpref("vendor_quantity", vendor_quantity);
                                    app_sharedpreference.setsharedpref("network_quantity", network_quantity);
                                    String vendor_quantity_sharedpref = app_sharedpreference.getsharedpref("vendor_quantity", "0");
                                    Log.e("vendor_quantity", vendor_quantity);
                                    dashboardDatas.add(new DashboardData("", "My Profile", R.drawable.ic_myprofile, R.drawable.circle_teal, false));
                                    dashboardDatas.add(new DashboardData("", "Change Password", R.drawable.ic_chngpswd, R.drawable.circle_purple, false));
                                    dashboardDatas.add(new DashboardData("", "Add Vendor", R.drawable.ic_companyprofile, R.drawable.circle_voilet, false));
                                    dashboardDatas.add(new DashboardData("", "Vendor List", R.drawable.ic_vendor_list, R.drawable.circle_sienna, true, vendor_quantity_sharedpref));
                                    dashboardDatas.add(new DashboardData("", "Associate Agreement", R.drawable.ic_companyprofile, R.drawable.circle_voilet, false, ""));
                                    dashboardDatas.add(new DashboardData("", "Payout Reports", R.drawable.ic_payout, R.drawable.circle_deep_pink, false));
//                                    dashboardDatas.add(new DashboardData("", "My Network", R.drawable.ic_lstprdct, R.drawable.circle_teal, true, ""));

                                    tvUserType.setText("Welcome Bussiness Associate");
                                    dashboardlist.setLayoutManager(layoutManager);
                                    dashboardAdapter = new DashboardAdapter(getActivity(), dashboardDatas);
                                    dashboardlist.setAdapter(dashboardAdapter);


                                }

                            }
                        }

                    }

                });
    }


    @Override
    public void onResume()
    {
        super.onResume();
        if (app_sharedpreference.getsharedpref("username", "notlogin") != null)
        {
            String Username = app_sharedpreference.getsharedpref("name", "not");
            String Emailid = app_sharedpreference.getsharedpref("emailid", "not");
            if (Username.equals("notlogin"))
            {
                textViewName.setText("");
                tvEmail.setText("");
            }
            else
            {
                textViewName.setText(Username);
                tvEmail.setText(Emailid);
            }
        }


    }
}