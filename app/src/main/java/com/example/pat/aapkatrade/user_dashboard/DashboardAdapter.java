package com.example.pat.aapkatrade.user_dashboard;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.pat.aapkatrade.Home.HomeActivity;
import com.example.pat.aapkatrade.Home.registration.RegistrationActivity;
import com.example.pat.aapkatrade.R;
import com.example.pat.aapkatrade.general.AppSharedPreference;
import com.example.pat.aapkatrade.login.LoginDashboard;
import com.example.pat.aapkatrade.user_dashboard.add_product.AddProductActivity;
import com.example.pat.aapkatrade.user_dashboard.addcompany.AddCompany;
import com.example.pat.aapkatrade.user_dashboard.associateagreement.AssociateAgreementDialog;
import com.example.pat.aapkatrade.user_dashboard.changepassword.ChangePassword;
import com.example.pat.aapkatrade.user_dashboard.companylist.CompanyList;
import com.example.pat.aapkatrade.user_dashboard.my_company_profile.MyCompanyProfile;
import com.example.pat.aapkatrade.user_dashboard.my_profile.MyProfileActivity;
import com.example.pat.aapkatrade.user_dashboard.my_profile.MyProfileForBusinessAssociates;
import com.example.pat.aapkatrade.user_dashboard.network.NetworkActivity;
import com.example.pat.aapkatrade.user_dashboard.order_list.OrderManagementActivity;
import com.example.pat.aapkatrade.user_dashboard.payout.PayoutActivity;
import com.example.pat.aapkatrade.user_dashboard.product_list.ProductListActivity;
import com.example.pat.aapkatrade.user_dashboard.service_enquirylist.ServiceEnquiryActivity;
import com.example.pat.aapkatrade.user_dashboard.vender_detail.VendorActivity;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by PPC16 on 10-Jan-17.
 */

public class DashboardAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private final LayoutInflater inflater;
    private List<DashboardData> itemList;
    private Context context;
    DashboardHolder viewHolder;
    AppSharedPreference app_sharedpreference;


    public DashboardAdapter(Context context, List<DashboardData> itemList) {
        this.itemList = itemList;
        this.context = context;
        inflater = LayoutInflater.from(context);
        app_sharedpreference = new AppSharedPreference(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.row_dashboard2, parent, false);
        viewHolder = new DashboardHolder(view);

        return viewHolder;
    }




    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        DashboardHolder homeHolder = (DashboardHolder) holder;

        homeHolder.tvDashboard.setText(itemList.get(position).dashboard_name.toString());

        if (itemList.get(position).isList) {
            homeHolder.tvquantity.setText(itemList.get(position).quantities.toString());
        } else {
            homeHolder.tvquantity.setVisibility(View.INVISIBLE);
        }

        Picasso.with(context).load(itemList.get(position).image).into(homeHolder.imageView);

        ((DashboardHolder) holder).imageView.setBackground(context.getResources().getDrawable(itemList.get(position).color));


        homeHolder.relativeDashboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (itemList.get(position).dashboard_name.equals("My Company")) {
                    if (app_sharedpreference.getsharedpref("userid", "notlogin").equals("notlogin")) {

                        Intent i = new Intent(context, LoginDashboard.class);
                        context.startActivity(i);

                    } else {

                        Log.e("login_status", app_sharedpreference.getsharedpref("userid", "notlogin"));
                        Intent my_company = new Intent(context, MyCompanyProfile.class);
                        context.startActivity(my_company);

                    }

                } else if (itemList.get(position).dashboard_name.equals("My Profile") && app_sharedpreference.getsharedpref("usertype", "0").equals("3")) {
                    if (app_sharedpreference.getsharedpref("userid", "notlogin").equals("notlogin")) {

                        Intent i = new Intent(context, LoginDashboard.class);
                        context.startActivity(i);

                    } else {

                        Intent list_product = new Intent(context, MyProfileForBusinessAssociates.class);
                        context.startActivity(list_product);

                    }
                } else if (itemList.get(position).dashboard_name.equals("My Profile")) {
                    if (app_sharedpreference.getsharedpref("userid", "notlogin").equals("notlogin")) {
                        Intent i = new Intent(context, LoginDashboard.class);
                        context.startActivity(i);


                    } else {
                        Intent my_profile = new Intent(context, MyProfileActivity.class);
                        context.startActivity(my_profile);

                    }

                } else if (itemList.get(position).dashboard_name.equals("Change Password")) {
                    if (app_sharedpreference.getsharedpref("userid", "notlogin").equals("notlogin")) {
                        Intent i = new Intent(context, LoginDashboard.class);
                        context.startActivity(i);


                    } else {

                        Intent change_password = new Intent(context, ChangePassword.class);
                        context.startActivity(change_password);

                    }

                } else if (itemList.get(position).dashboard_name.equals("Add Company")) {
                    if (app_sharedpreference.getsharedpref("userid", "notlogin").equals("notlogin")) {
                        Intent i = new Intent(context, LoginDashboard.class);
                        context.startActivity(i);


                    } else {

                        Intent add_company = new Intent(context, AddCompany.class);
                        context.startActivity(add_company);

                    }

                }
                else if (itemList.get(position).dashboard_name.equals("Enquiry Services List"))
                {
                    if (app_sharedpreference.getsharedpref("userid", "notlogin").equals("notlogin"))
                    {
                        Intent i = new Intent(context, LoginDashboard.class);
                        context.startActivity(i);

                    }
                    else
                    {
                        Intent service_enquiry_list = new Intent(context, ServiceEnquiryActivity.class);
                        context.startActivity(service_enquiry_list);
                    }

                }

                else if (itemList.get(position).dashboard_name.equals("My Network"))
                {
                    if (app_sharedpreference.getsharedpref("userid", "notlogin").equals("notlogin"))
                    {
                        Intent i = new Intent(context, LoginDashboard.class);
                        context.startActivity(i);

                    }
                    else
                    {
                        Intent service_enquiry_list = new Intent(context, NetworkActivity.class);
                        context.startActivity(service_enquiry_list);
                    }

                }
                else if (itemList.get(position).dashboard_name.equals("Company List")) {


                    if (app_sharedpreference.getsharedpref("userid", "notlogin").equals("notlogin")) {
                        Intent i = new Intent(context, LoginDashboard.class);
                        context.startActivity(i);


                    } else {

                        Intent list_company = new Intent(context, CompanyList.class);
                        context.startActivity(list_company);

                    }


                } else if (itemList.get(position).dashboard_name.equals("Add Vendor")) {

                    if (app_sharedpreference.getsharedpref("userid", "notlogin").equals("notlogin")) {
                        Intent i = new Intent(context, LoginDashboard.class);
                        context.startActivity(i);


                    } else {
                        app_sharedpreference.setsharedpref("isAddVendorCall", "true");
                        Intent list_company = new Intent(context, RegistrationActivity.class);
                        context.startActivity(list_company);

                    }


                } else if (itemList.get(position).dashboard_name.equals("Vendor List")) {

                    if (app_sharedpreference.getsharedpref("userid", "notlogin").equals("notlogin")) {
                        Intent i = new Intent(context, LoginDashboard.class);
                        context.startActivity(i);


                    } else {

                        Intent list_company = new Intent(context, VendorActivity.class);
                        context.startActivity(list_company);

                    }


                } else if (itemList.get(position).dashboard_name.equals("Add Product")) {
                    if (app_sharedpreference.getsharedpref("userid", "notlogin").equals("notlogin")) {
                        Intent i = new Intent(context, LoginDashboard.class);
                        context.startActivity(i);


                    } else {

                        Intent add_product = new Intent(context, AddProductActivity.class);
                        context.startActivity(add_product);

                    }


                } else if (itemList.get(position).dashboard_name.equals("List Product")) {

                    if (app_sharedpreference.getsharedpref("userid", "notlogin").equals("notlogin")) {
                        Intent i = new Intent(context, LoginDashboard.class);
                        context.startActivity(i);


                    } else {

                        Intent list_product = new Intent(context, ProductListActivity.class);
                        context.startActivity(list_product);

                    }


                } else if (itemList.get(position).dashboard_name.equals("Order")) {
                    if (app_sharedpreference.getsharedpref("userid", "notlogin").equals("notlogin")) {
                        Intent i = new Intent(context, LoginDashboard.class);
                        context.startActivity(i);


                    } else {

                        Intent list_product = new Intent(context, OrderManagementActivity.class);
                        context.startActivity(list_product);

                    }

                } else if (itemList.get(position).dashboard_name.equals("Cancel Order")) {
                    if (app_sharedpreference.getsharedpref("userid", "notlogin").equals("notlogin")) {

                        Intent i = new Intent(context, LoginDashboard.class);
                        context.startActivity(i);

                    } else {

//                        Intent list_product = new Intent(context, OrderActivity.class);
//                        context.startActivity(list_product);

                    }
                    //    Associate Agreement
                } else if (itemList.get(position).dashboard_name.equals("Payout Reports")) {
                    if (app_sharedpreference.getsharedpref("userid", "notlogin").equals("notlogin")) {

                        Intent i = new Intent(context, LoginDashboard.class);
                        context.startActivity(i);

                    } else {

                        Intent list_product = new Intent(context, PayoutActivity.class);
                        context.startActivity(list_product);

                    }


                } else if (itemList.get(position).dashboard_name.equals("Associate Agreement")) {
                    if (app_sharedpreference.getsharedpref("userid", "notlogin").equals("notlogin")) {

                        Intent i = new Intent(context, LoginDashboard.class);
                        context.startActivity(i);



                    }
                     else {
                        AssociateAgreementDialog dialog = new AssociateAgreementDialog(context);
                        dialog.show();


                    }
                    //

                }


            }
        });

    }

    private void showMessage(String s) {

        Toast.makeText(context, s, Toast.LENGTH_SHORT).show();
    }


    @Override
    public int getItemCount() {
        return itemList.size();

    }

    public String getCurrentTimeStamp() {
        return new SimpleDateFormat("dd MMM yyyy HH:mm").format(new Date());
    }


}
