package com.example.pat.aapkatrade.payment.payment_method;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import com.example.pat.aapkatrade.R;
import com.example.pat.aapkatrade.user_dashboard.companylist.CompanyListHolder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;



public class BankAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>
{

    final LayoutInflater inflater;
    List<BankData> itemList;
    Context context;
    BankHolder viewHolder;

    public BankAdapter(Context context, List<BankData> itemList)
    {

        this.itemList = itemList;
        this.context = context;
        inflater = LayoutInflater.from(context);

    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view = inflater.inflate(R.layout.row_company_list, parent, false);

        viewHolder = new BankHolder(view);

        System.out.println("data-----------" + itemList);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position)
    {

        final CompanyListHolder homeHolder = (CompanyListHolder) holder;

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