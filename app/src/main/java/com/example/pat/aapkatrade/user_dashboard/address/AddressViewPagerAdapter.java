package com.example.pat.aapkatrade.user_dashboard.address;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.pat.aapkatrade.R;
import com.koushikdutta.ion.Ion;

import java.util.ArrayList;

/**
 * Created by PPC21 on 13-Jan-17.
 */

public class AddressViewPagerAdapter extends PagerAdapter {

    private Context mContext;
    ArrayList<String> imageurl;


//this is adapterview
    public AddressViewPagerAdapter(Context mContext, ArrayList<String>productImage_url) {
        this. imageurl=productImage_url;
        this.mContext=mContext;
    }


    public int getCount() {
        return 3;

}

    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }


    public Object instantiateItem(ViewGroup container, int position) {
        View itemView = LayoutInflater.from(mContext).inflate(R.layout.viewpager_item, container, false);
        ImageView imageView = (ImageView) itemView.findViewById(R.id.img_pager_item);
        Ion.with(imageView)
//               .placeholder(R.drawable.placeholder_image)

                .load("http://aapkatrade.com/laraveldemo/public/image/demo/slider/3.jpg");
        imageView.setImageResource(R.drawable.banner_home);

        container.addView(itemView);

        return itemView;
    }


    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((LinearLayout) object);
    }
}