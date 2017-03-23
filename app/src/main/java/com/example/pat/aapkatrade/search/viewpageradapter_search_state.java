package com.example.pat.aapkatrade.search;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.pat.aapkatrade.R;
import com.koushikdutta.ion.Ion;

import java.util.ArrayList;

/**
 * Created by PPC21 on 13-Jan-17.
 */

public class viewpageradapter_search_state extends PagerAdapter
{

    private Context mContext;
    ArrayList<String> statename;


    public viewpageradapter_search_state(Context mContext, ArrayList<String> statename) {
        this. statename=statename;
        this.mContext=mContext;

    }


    public int getCount() {
        return statename!=null?statename.size():-1;

}

    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }


    public Object instantiateItem(ViewGroup container, int position)
    {
        View itemView = LayoutInflater.from(mContext).inflate(R.layout.viewpager_search_state, container, false);

        TextView imageView = (TextView) itemView.findViewById(R.id.tv_pager_search_state);
        imageView.setText(statename.get(position));


        container.addView(itemView);

        return itemView;
    }


    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((LinearLayout) object);
    }
}