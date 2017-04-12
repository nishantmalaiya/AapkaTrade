package com.example.pat.aapkatrade.productdetail.reviewlist;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import com.example.pat.aapkatrade.R;
import com.example.pat.aapkatrade.general.AppSharedPreference;
import com.example.pat.aapkatrade.general.Utils.AndroidUtils;
import com.example.pat.aapkatrade.general.progressbar.ProgressBarHandler;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by PPC16 on 4/8/2017.
 */

public class ReviewListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>
{

    final LayoutInflater inflater;
    List<ReviewListData> itemList;
    Context context;
    ReviewListHolder viewHolder;
    AppSharedPreference app_sharedpreference;
    ProgressBarHandler progress_handler;



    public ReviewListAdapter(Context context, List<ReviewListData> itemList)
    {
        this.itemList = itemList;
        this.context = context;
        inflater = LayoutInflater.from(context);

        app_sharedpreference = new AppSharedPreference(context);
        progress_handler = new ProgressBarHandler(context);

    }



    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view = inflater.inflate(R.layout.row_review_list, parent, false);

        viewHolder = new ReviewListHolder(view);

        AndroidUtils.setImageColor(viewHolder.imgStar,context,R.color.white);

        System.out.println("data-----------"+itemList);

        return viewHolder;
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position)
    {
        final ReviewListHolder homeHolder = (ReviewListHolder) holder;

        Log.e("size----------",String.valueOf(itemList.size()));

        homeHolder.title.setText(itemList.get(position).title);

        homeHolder.message_description.setText(itemList.get(position).message_description);

        homeHolder.username.setText(itemList.get(position).username);

        homeHolder.tvRating.setText(itemList.get(position).rating);

        homeHolder.deliver_date.setText(itemList.get(position).created_date);



    }

    private void showMessage(String s)
    {
        Toast.makeText(context, s, Toast.LENGTH_SHORT).show();
    }


    @Override
    public int getItemCount()
    {
        return itemList.size();
        //return itemList.size();
    }

    public String getCurrentTimeStamp()
    {
        return new SimpleDateFormat("dd MMM yyyy HH:mm").format(new Date());
    }


}