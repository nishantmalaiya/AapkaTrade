package com.example.pat.aapkatrade.user_dashboard.address;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.pat.aapkatrade.R;
import com.example.pat.aapkatrade.user_dashboard.address.add_address.AddAddressActivity;
import com.example.pat.aapkatrade.user_dashboard.address.viewpager.CartCheckoutActivity;

import java.util.ArrayList;

public class AddressActivity extends AppCompatActivity
{

    TextView tvTitle;
    ArrayList<AddressData> addressList = new ArrayList<>();
    RecyclerView addressRecyclerView;
    AddressListAdapter addressListAdapter;
    RelativeLayout relativeDeliverAddress,relativeAddNewAddress;



    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_address);

        setuptoolbar();

        setup_data();

        setup_layout();

    }


    private void setuptoolbar()
    {

        tvTitle = (TextView) findViewById(R.id.tvTitle);
        tvTitle.setText("Address");


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Address");



        //getSupportActionBar().setIcon(R.drawable.home_logo);
    }


    private void setup_data()
    {
        addressList.clear();
        try
        {
            addressList.add(new AddressData("Line 1", "Line 2", "Line 3"));
            addressList.add(new AddressData("Line 1", "Line 2", "Line 3"));
            addressList.add(new AddressData("Line 1", "Line 2", "Line 3"));
            addressList.add(new AddressData("Line 1", "Line 2", "Line 3"));
            addressList.add(new AddressData("Line 1", "Line 2", "Line 3"));
            addressList.add(new AddressData("Line 1", "Line 2", "Line 3"));
        }
        catch (Exception e)
        {

        }
    }

    private void setup_layout()
    {

        relativeDeliverAddress = (RelativeLayout) findViewById(R.id.relativeDeliverAddress);

        relativeAddNewAddress = (RelativeLayout) findViewById(R.id.relativeAddNewAddress);

        addressRecyclerView = (RecyclerView) findViewById(R.id.addressRecyclerView);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());

        addressListAdapter = new AddressListAdapter(this, addressList);

        addressRecyclerView.setAdapter(addressListAdapter);

        addressRecyclerView.setLayoutManager(mLayoutManager);

        relativeDeliverAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                 Intent i = new Intent(AddressActivity.this, CartCheckoutActivity.class);
                 startActivity(i);

            }
        });


        relativeAddNewAddress.setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick(View v)
            {

                Intent i = new Intent(AddressActivity.this, CartCheckoutActivity.class);
                startActivity(i);


            }

        });




    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.menu_map, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case android.R.id.home:
                finish();
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return super.onOptionsItemSelected(item);
    }


}
