package com.example.pat.aapkatrade.user_dashboard.add_product;

/**
 * Created by PPC16 on 21-Feb-17.
 */

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.pat.aapkatrade.R;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.squareup.picasso.Picasso;

import java.util.List;


public class ProductImages extends RecyclerView.Adapter<RecyclerView.ViewHolder>
{

    final LayoutInflater inflater;
    List<ProductImagesData> itemList;
    Context context;
    ProductImagesHolder viewHolder;
    Bitmap imageForPreview;


    public ProductImages(Context context, List<ProductImagesData> itemList)
    {

        this.itemList = itemList;
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        final View view = inflater.inflate(R.layout.row_product_images, parent, false);
        viewHolder = new ProductImagesHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        final ProductImagesHolder homeHolder = (ProductImagesHolder) holder;

        Log.e("itemimage", itemList.get(position).image_path);
        if (itemList.get(position).image_path.toString().equals(""))
        {
            Ion.with(context)
                    .load(itemList.get(position).image_url)
                    .withBitmap().asBitmap()
                    .setCallback(new FutureCallback<Bitmap>() {
                        @Override
                        public void onCompleted(Exception e, Bitmap result) {
                            if (result != null)
                                homeHolder.previewImage.setImageBitmap(result);
                        }
                    });

        }
        else
        {
            Ion.with(context)
                    .load(itemList.get(position).image_path)
                    .withBitmap().asBitmap()
                    .setCallback(new FutureCallback<Bitmap>() {
                        @Override
                        public void onCompleted(Exception e, Bitmap result) {
                            if (result != null)
                                homeHolder.previewImage.setImageBitmap(result);
                        }
                    });

        }
        //Ion.with(homeHolder.previewImage).load(itemList.get(position).image_path);


        viewHolder.cancelImage.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {

                itemList.remove(position);
                notifyDataSetChanged();
                //notifyItemRemoved(position);

            }
        });
        System.out.println("data-----------" + itemList);
    }

    @Override
    public int getItemCount() {
        return itemList.size();
        //return itemList.size();
    }
}