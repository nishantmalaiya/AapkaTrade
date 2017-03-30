package com.example.pat.aapkatrade.user_dashboard.product_list;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.chauthai.swipereveallayout.ViewBinderHelper;
import com.example.pat.aapkatrade.R;
import com.example.pat.aapkatrade.general.progressbar.ProgressBarHandler;
import com.example.pat.aapkatrade.user_dashboard.edit_product.EditProductActivity;
import com.example.pat.aapkatrade.user_dashboard.product_list.listproduct_detail.ListProductDetailActivity;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by PPC16 on 11-Jan-17.
 */

public class ProductListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>
{


    final LayoutInflater inflater;
    List<ProductListData> itemList;
    Context context;
    ProductListHolder viewHolder;
    ProgressBarHandler progress_handler;
    int p;
    ProductListHolder homeHolder;
    final ViewBinderHelper binderHelper = new ViewBinderHelper();



    public ProductListAdapter(Context context, List<ProductListData> itemList)
    {
        progress_handler = new ProgressBarHandler(context);
        this.itemList = itemList;
        this.context = context;
        inflater = LayoutInflater.from(context);


    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view = inflater.inflate(R.layout.row_productlist, parent, false);
        viewHolder = new ProductListHolder(view);


        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position)
    {

        homeHolder = (ProductListHolder) holder;

        binderHelper.bind(homeHolder.swipeReavelLayout, itemList.toString());

        homeHolder.tvProductName.setText(itemList.get(position).product_name);

        homeHolder.tvProductPrice.setText("\u20A8"+" "+itemList.get(position).product_price);

        homeHolder.tvCategoriesName.setText(itemList.get(position).category_name);

        homeHolder.tvProductCity.setText(itemList.get(position).state);

        Ion.with(homeHolder.imgProduct).load(itemList.get(position).product_image);


        homeHolder.linearlayout1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

              /*  Intent product_detail = new Intent(context, ListProductDetailActivity.class);
                product_detail.putExtra("user_id", itemList.get(position).user_id);
                product_detail.putExtra("product_name", itemList.get(position).product_name);
                product_detail.putExtra("product_price", itemList.get(position).product_price);
                product_detail.putExtra("product_cross_price", itemList.get(position).product_cross_price);
                product_detail.putExtra("product_image", itemList.get(position).product_image);
                product_detail.putExtra("category_name", itemList.get(position).category_name);
                product_detail.putExtra("description", itemList.get(position).description);
                product_detail.putExtra("delivery_distance", itemList.get(position).delivery_distance);
                product_detail.putExtra("delivery_area_name", itemList.get(position).delivery_area_name);
                product_detail.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(product_detail);
             */

            }
        });


        homeHolder.deleteRelative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                delete_product(itemList.get(position).product_id,position);
            }
        });

        homeHolder.editRelative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {


                Intent product_detail = new Intent(context, EditProductActivity.class);
                product_detail.putExtra("user_id", itemList.get(position).user_id);
                product_detail.putExtra("product_id", itemList.get(position).product_id);
                product_detail.putExtra("product_name", itemList.get(position).product_name);
                product_detail.putExtra("product_price", itemList.get(position).product_price);
                product_detail.putExtra("product_cross_price", itemList.get(position).product_cross_price);
                product_detail.putExtra("description", itemList.get(position).description);

               // product_detail.putExtra("product_image", itemList.get(position).product_image);
               /* product_detail.putExtra("category_name", itemList.get(position).category_name);
                product_detail.putExtra("description", itemList.get(position).description);
                product_detail.putExtra("delivery_distance", itemList.get(position).delivery_distance);
                product_detail.putExtra("delivery_area_name", itemList.get(position).delivery_area_name);
               */

                product_detail.putExtra("company_id",itemList.get(position).company_id);
                product_detail.putExtra("distanec_id",itemList.get(position).distance_id);
                product_detail.putExtra("country_id",itemList.get(position).country_id);
                product_detail.putExtra("state_id",itemList.get(position).state_id);
                product_detail.putExtra("city_id",itemList.get(position).city_id);
                product_detail.putExtra("category_id",itemList.get(position).category_id);
                product_detail.putExtra("sub_category_id",itemList.get(position).sub_category_id);
                product_detail.putExtra("unit_id",itemList.get(position).unit_id);
                product_detail.putStringArrayListExtra("product_images",itemList.get(position).product_images);

                System.out.println("itemlist-------------"+itemList.get(position).product_images);

                product_detail.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(product_detail);




/*
                Ion.with(context)
                        .load("https://aapkatrade.com/slim/add_product\n")
                        .setHeader("authorization", "xvfdbgfdhbfdhtrh54654h54ygdgerwer3")
                        .setBodyParameter("authorization", "xvfdbgfdhbfdhtrh54654h54ygdgerwer3")
                        .setBodyParameter("user_id", )
                        .setBodyParameter("productauthorization", "xvfdbgfdhbfdhtrh54654h54ygdgerwer3")
                        .setBodyParameter("company_id", )
                        .setBodyParameter("distance_id", )
                        .setBodyParameter("country_id", "101")
                        .setBodyParameter("state_id", )
                        .setBodyParameter("city_id", )
                        .setBodyParameter("price", )
                        .setBodyParameter("cross_price", )
                        .setBodyParameter("unit_id", )
                        .setBodyParameter("short_des",)
                        .setBodyParameter("descriptiondeliveryArea", )
                        .setBodyParameter("category_id", )
                        .asJsonObject()
                        .setCallback(new FutureCallback<JsonObject>() {
                            @Override
                            public void onCompleted(Exception e, JsonObject result) {

                            }
                        });*/


            }
        });



       /*   homeHolder.imgMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "Hi ", Toast.LENGTH_SHORT).show();
            }
        });*/




    }


    public void saveStates(Bundle outState) {
        binderHelper.saveStates(outState);
    }

    public void restoreStates(Bundle inState) {
        binderHelper.restoreStates(inState);
    }

    private void showMessage(String s) {
        Toast.makeText(context, s, Toast.LENGTH_SHORT).show();
    }


    @Override
    public int getItemCount() {
        return itemList.size();
        // return itemList.size();
    }



    public String getCurrentTimeStamp() {
        return new SimpleDateFormat("dd MMM yyyy HH:mm").format(new Date());
    }

    private void delete_product(String product_id, int pos)
    {
        progress_handler.show();
        p = pos;
        System.out.println("company--------"+product_id);
        Ion.with(context)
                .load("https://aapkatrade.com/slim/delete_product")
                .setHeader("authorization", "xvfdbgfdhbfdhtrh54654h54ygdgerwer3")
                .setBodyParameter("id",product_id)
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>()
                {
                    @Override
                    public void onCompleted(Exception e, JsonObject result)
                    {
                        System.out.println("result--------"+result);

                        if (result == null)
                        {
                            progress_handler.hide();
                        }
                        else
                        {
                            JsonObject jsonObject = result.getAsJsonObject();
                            String message = jsonObject.get("message").getAsString();
                            if (message.equals("Success"))
                            {
                                itemList.remove(p);
                                notifyItemRemoved(p);
                                notifyItemRangeChanged(p, itemList.size());
                                progress_handler.hide();
                            }
                            else
                            {
                                   Toast.makeText(context,message.toString(),Toast.LENGTH_SHORT).show();
                                   progress_handler.hide();
                            }
                        }
                    }
                });
    }

}
