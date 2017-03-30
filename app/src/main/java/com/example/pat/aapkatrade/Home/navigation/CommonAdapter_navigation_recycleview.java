package com.example.pat.aapkatrade.Home.navigation;

import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.pat.aapkatrade.Home.navigation.entity.CategoryHome;
import com.example.pat.aapkatrade.R;
import com.example.pat.aapkatrade.categories_tab.CategoryListActivity;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import java.util.ArrayList;

/**
 * Created by Netforce on 7/25/2016.
 */
public class CommonAdapter_navigation_recycleview extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    Context context;

    private ArrayList<CategoryHome> _listDataHeader;
    View v;


    public CommonAdapter_navigation_recycleview(Context context, ArrayList<CategoryHome> listDataHeader) {
        this.context = context;
        this._listDataHeader = listDataHeader;

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {


        CommonHolder_navigation_recycleview viewHolder;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        v = inflater.inflate(R.layout.list_group, parent, false);
        viewHolder = new CommonHolder_navigation_recycleview(v);
        return viewHolder;


    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewholder, final int position) {

        final CommonHolder_navigation_recycleview viewHolder = new CommonHolder_navigation_recycleview(v);
        TypedArray images = context.getResources().obtainTypedArray(R.array.category_icon_background);
        int choice = (int) (Math.random() * images.length());
        viewHolder.imageViewIcon.setBackgroundResource(images.getResourceId(choice, R.drawable.circle_sienna));
        images.recycle();
        viewHolder.tv_categoryname.setText(_listDataHeader.get(position).getCategoryName());

        Ion.with(context).load(_listDataHeader.get(position).getCategoryIconPath()).withBitmap().asBitmap()
                .setCallback(new FutureCallback<Bitmap>() {
                    @Override
                    public void onCompleted(Exception e, Bitmap result) {
                        if (result != null) {

                            viewHolder.imageViewIcon.setImageBitmap(result);
                        }
                    }

                });


        viewHolder.rl_category_container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, CategoryListActivity.class);
                i.putExtra("category_id", _listDataHeader.get(position).getCategoryId());
                context.startActivity(i);

            }
        });
    }

    @Override
    public int getItemCount() {
        return _listDataHeader.size();
    }


}
