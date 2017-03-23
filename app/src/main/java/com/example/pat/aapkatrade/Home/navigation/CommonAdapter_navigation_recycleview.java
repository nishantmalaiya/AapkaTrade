package com.example.pat.aapkatrade.Home.navigation;

import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.pat.aapkatrade.Home.CommomData;
import com.example.pat.aapkatrade.Home.CommomHolder;
import com.example.pat.aapkatrade.Home.CommonHolder_grid;
import com.example.pat.aapkatrade.Home.navigation.entity.CategoryHome;
import com.example.pat.aapkatrade.R;
import com.example.pat.aapkatrade.categories_tab.CategoryListActivity;
import com.example.pat.aapkatrade.general.Tabletsize;
import com.example.pat.aapkatrade.productdetail.ProductDetail;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.squareup.picasso.Picasso;

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

        //RecyclerView.ViewHolder viewHolder;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

            v = inflater.inflate(R.layout.list_group, parent, false);
            viewHolder = new CommonHolder_navigation_recycleview(v);
            return viewHolder;





    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewholder, final int position) {

        final CommonHolder_navigation_recycleview viewHolder=new CommonHolder_navigation_recycleview(v);
        TypedArray images = context.getResources().obtainTypedArray(R.array.category_icon_background);
        int choice = (int) (Math.random() * images.length());
        viewHolder.imageViewIcon.setBackgroundResource(images.getResourceId(choice,R.drawable.circle_sienna));
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
        i.putExtra("category_id",_listDataHeader.get(position).getCategoryId());
        //i.putExtra("sub_category_id",_listDataHeader.get(position).get);
        context. startActivity(i);

    }
});


//        if (arrangementtype == "list")
//
//        {
//            CommomHolder holder = new CommomHolder(v);
//
//
//            Picasso.with(context).load(commomDatas.get(position).imageurl)
//                    .error(R.drawable.banner)
//                    .placeholder(R.drawable.default_noimage)
//                    .error(R.drawable.default_noimage)
//                    .into(holder.cimageview);
////            Animation a = AnimationUtils.loadAnimation(context, R.anim.show_progress);
////            a.setDuration(1000);
////            holder.cimageview.startAnimation(a);
//
//
////        Ion.with(holder.cimageview)
////                 .placeholder(R.drawable.ms__drawable)
////
////                .load(commomDatas.get(0).imageurl);
//            Log.e("imageurl", commomDatas.get(0).imageurl);
//
//            holder.cardview.setOnClickListener(new View.OnClickListener()
//            {
//                @Override
//                public void onClick(View view)
//                {
//
//                Intent intent = new Intent(context,ProductDetail.class);
//                    intent.putExtra("product_id",commomDatas.get(position).id.toString());
//                context.startActivity(intent);
//                ((AppCompatActivity) context).overridePendingTransition(R.anim.enter, R.anim.exit);
//
//                }
//            });
//            holder.tvProductName.setText(commomDatas.get(position).name);
//
//        }
//        else {
//
//            final CommonHolder_grid grid_holder = new CommonHolder_grid(v);
//
//            Picasso.with(context).load(commomDatas.get(position).imageurl)
//                    .error(R.drawable.default_noimage)
//                    .placeholder(R.drawable.default_noimage)
//                    .into(grid_holder.product_imageview);
//            grid_holder.tvProductName.setText(commomDatas.get(position).name);
//            grid_holder.rl_grid_row_parent.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Intent intent = new Intent(context, ProductDetail.class);
//                    intent.putExtra("product_id",commomDatas.get(position).id.toString());
//                    context.startActivity(intent);
//                    ((AppCompatActivity) context).overridePendingTransition(R.anim.enter, R.anim.exit);
//                }
//            });
//
//
////            Animation a = AnimationUtils.loadAnimation(context, R.anim.show_progress);
////            a.setDuration(1000);
////            grid_holder.product_imageview.startAnimation(a);
//
//            if (Tabletsize.isTablet(context)) {
//
//                if (position % 3 == 0) {
//
//                    grid_holder.view_grid_left.setVisibility(View.GONE);
//                    grid_holder.view_grid_right.setVisibility(View.GONE);
//
//
//                } else {
//                    grid_holder.view_grid_left.setVisibility(View.VISIBLE);
//                    grid_holder.view_grid_right.setVisibility(View.GONE);
//                }
//
//
//            } else {
//                if (position % 2 == 0) {
//
//                    grid_holder.view_grid_left.setVisibility(View.GONE);
//                    grid_holder.view_grid_right.setVisibility(View.GONE);
//
//
//                } else {
//                    grid_holder.view_grid_left.setVisibility(View.VISIBLE);
//                    grid_holder.view_grid_right.setVisibility(View.GONE);
//                }
//
//
//            }
//        }




    }

    @Override
    public int getItemCount() {
        return _listDataHeader.size();
    }


}
