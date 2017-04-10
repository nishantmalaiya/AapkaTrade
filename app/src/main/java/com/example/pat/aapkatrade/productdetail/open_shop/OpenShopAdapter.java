package com.example.pat.aapkatrade.productdetail.open_shop;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import com.example.pat.aapkatrade.R;
import com.example.pat.aapkatrade.general.AppSharedPreference;
import com.example.pat.aapkatrade.general.Utils.AndroidUtils;
import com.example.pat.aapkatrade.general.progressbar.ProgressBarHandler;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by PPC16 on 4/10/2017.
 */

public class OpenShopAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>
{

    final LayoutInflater inflater;
    List<OpenShopData> itemList;
    Context context;
    OpenShopHolder viewHolder;
    AppSharedPreference app_sharedpreference;
    ProgressBarHandler progress_handler;
    ArrayList<Integer> days_color = new ArrayList<>();


    public OpenShopAdapter(Context context, List<OpenShopData> itemList,ArrayList<Integer> days_color)
    {
        this.itemList = itemList;
        this.context = context;
        this.days_color = days_color;
        inflater = LayoutInflater.from(context);
        app_sharedpreference = new AppSharedPreference(context);
        progress_handler = new ProgressBarHandler(context);

    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view = inflater.inflate(R.layout.row_open_shop, parent, false);

        viewHolder = new OpenShopHolder(view);

        System.out.println("data-----------"+itemList);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position)
    {
        final OpenShopHolder homeHolder = (OpenShopHolder) holder;

         System.out.println("home holder------------"+days_color.size());

         AndroidUtils.setBackgroundSolid(viewHolder.relativeOpenShop,context,days_color.get(position),2);


    }

    private void showMessage(String s)
    {
        Toast.makeText(context, s, Toast.LENGTH_SHORT).show();
    }

    @Override
    public int getItemCount()
    {
        return 7;
        //return itemList.size();
    }

    public String getCurrentTimeStamp()
    {
        return new SimpleDateFormat("dd MMM yyyy HH:mm").format(new Date());
    }


}
