package com.example.pat.aapkatrade.categories_tab;

import android.app.Activity;
import android.content.Intent;
import android.location.Location;
import android.os.Looper;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.pat.aapkatrade.R;
import com.example.pat.aapkatrade.general.CheckPermission;
import com.example.pat.aapkatrade.general.LocationManager_check;
import com.example.pat.aapkatrade.general.Tabletsize;
import com.example.pat.aapkatrade.general.progressbar.ProgressBarHandler;
import com.example.pat.aapkatrade.map.GoogleMapActivity;
import com.example.pat.aapkatrade.productdetail.ProductDetail;
import com.koushikdutta.ion.Ion;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by PPC16 on 16-Jan-17.
 */

public class CategoriesListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final LayoutInflater inflater;
    private List<CategoriesListData> itemList;
    public Activity context;
    CategoriesListHolder viewHolder;
    ProgressBarHandler progressBarHandler;


    public CategoriesListAdapter(Activity context, List<CategoriesListData> itemList) {
        this.itemList = itemList;
        this.context = context;
        inflater = LayoutInflater.from(context);
        progressBarHandler=new ProgressBarHandler(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.product_list_item, parent, false);

        viewHolder = new CategoriesListHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {

        CategoriesListHolder homeHolder = (CategoriesListHolder) holder;

        homeHolder.tvProductName.setText(itemList.get(position).product_name);

        homeHolder.tvProductPrice.setText("\u20A8" + "." + " " + itemList.get(position).product_price);

        homeHolder.tvProductCrossPrice.setText("\u20A8" + "." + " " + itemList.get(position).product_cross_price);


        if(Tabletsize.isTablet(context))
        {
            String product_imageurl=itemList.get(position).product_image.replace("small","large");

            Ion.with(homeHolder.productimage)
                    .error(ContextCompat.getDrawable(context, R.drawable.ic_applogo1))
                    .placeholder(ContextCompat.getDrawable(context, R.drawable.ic_applogo1))
                    .load(product_imageurl);
            Log.e("image_large","image_large");

        }
        else if(Tabletsize.isMedium(context))
        {
            String product_imageurl=itemList.get(position).product_image.replace("small","medium");

            Ion.with(homeHolder.productimage)
                    .error(ContextCompat.getDrawable(context, R.drawable.ic_applogo1))
                    .placeholder(ContextCompat.getDrawable(context, R.drawable.ic_applogo1))
                    .load(product_imageurl);
            Log.e("image_medium","image_medium"+product_imageurl);

        }
        else if(Tabletsize.isSmall(context))
        {
            String product_imageurl=itemList.get(position).product_image.replace("small","medium");

            Ion.with(homeHolder.productimage)
                    .error(ContextCompat.getDrawable(context, R.drawable.ic_applogo1))
                    .placeholder(ContextCompat.getDrawable(context, R.drawable.ic_applogo1))
                    .load(product_imageurl);

            Log.e("image_small","image_small");
        }





       // Ion.with(homeHolder.productimage).load(itemList.get(position).product_image);

        Picasso.with(context).load(itemList.get(position).product_image)
                .placeholder(R.drawable.default_noimage)
                .error(R.drawable.default_noimage)
                .into(homeHolder.productimage);

//        Ion.with(homeHolder.productimage).load(itemList.get(position).product_image);


        homeHolder.linearlayout1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {

                Log.e("product_id",itemList.get(position).product_id);
              //  System.out.println("product_id-------------"+itemList.get(position).product_id);

                Intent intent = new Intent(context, ProductDetail.class);
                intent.putExtra("product_id", itemList.get(position).product_id);
                intent.putExtra("product_location", itemList.get(position).product_location);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);


            }
        });


        homeHolder.linearlayoutMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                boolean permission_status = CheckPermission.checkPermissions(context);


                if (permission_status)

                {

                    LocationManager_check locationManagerCheck = new LocationManager_check(
                            context);
                    Location location = null;
                    if (locationManagerCheck.isLocationServiceAvailable())
                    {

                        if (Looper.myLooper() == null) {
                            Looper.prepare();

                        }
                        Log.e("time_taken 1",(System.currentTimeMillis()/1000)+"");
                        progressBarHandler.show();
                        Intent intent = new Intent(context, GoogleMapActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        intent.putExtra("product_location", itemList.get(position).product_location);
                        context.startActivity(intent);

                        progressBarHandler.hide();
                        Log.e("time_taken 2",(System.currentTimeMillis()/1000)+"");

                    }
                    else
                        {
                        locationManagerCheck.createLocationServiceError(context);
                    }

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
        // return itemList.size();
    }

    public String getCurrentTimeStamp() {
        return new SimpleDateFormat("dd MMM yyyy HH:mm").format(new Date());
    }


}
