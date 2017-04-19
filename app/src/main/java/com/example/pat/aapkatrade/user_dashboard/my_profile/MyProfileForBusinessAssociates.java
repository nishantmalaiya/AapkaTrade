package com.example.pat.aapkatrade.user_dashboard.my_profile;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.pat.aapkatrade.Home.HomeActivity;
import com.example.pat.aapkatrade.R;
import com.example.pat.aapkatrade.general.Utils.AndroidUtils;
import com.example.pat.aapkatrade.general.Utils.adapter.CustomPagerAdapter;
import com.example.pat.aapkatrade.general.Validation;

public class MyProfileForBusinessAssociates extends AppCompatActivity implements TabLayout.OnTabSelectedListener {
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private Context context;
    private int currentTab;

    private int[] tabIcons = {

            R.drawable.ic_personal_details_green,
            R.drawable.ic_personal_details_white,

            R.drawable.ic_professional_details_green,
            R.drawable.ic_professional_details_white,

            R.drawable.ic_bank_details_green,
            R.drawable.ic_bank_details_white,

    };


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile_for_business_associates);
        context = MyProfileForBusinessAssociates.this;
        setUpToolBar();
        initView();
        setupViewPager(viewPager);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.addOnTabSelectedListener(this);
        tabLayout.setTabTextColors(Color.parseColor("#066C57"), Color.parseColor("#ffffff"));
        setupTabIcons();
    }

    private void initView() {
        context = MyProfileForBusinessAssociates.this;
        viewPager = (ViewPager) findViewById(R.id.htab_viewpager);
        tabLayout = (TabLayout) findViewById(R.id.tabs);
    }

    private void setupTabIcons() {
        tabLayout.getTabAt(0).setIcon(tabIcons[1]);
        tabLayout.getTabAt(1).setIcon(tabIcons[2]);
        tabLayout.getTabAt(2).setIcon(tabIcons[4]);
    }

    private void setupViewPager(ViewPager viewPager) {
        CustomPagerAdapter adapter = new CustomPagerAdapter(getSupportFragmentManager());
        adapter.addFrag(MyProfileFragment.newInstance(1, false), getResources().getString(R.string.personalInfo));
        adapter.addFrag(MyProfileFragment.newInstance(2, false), getResources().getString(R.string.professionalDetails));
        adapter.addFrag(MyProfileFragment.newInstance(3, true), getResources().getString(R.string.bank_details));
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(0);
    }


    private void setUpToolBar() {
        ImageView homeIcon = (ImageView) findViewById(R.id.iconHome) ;
        findViewById(R.id.logoWord).setVisibility(View.GONE); ;
        TextView header_name = (TextView) findViewById(R.id.header_name);
        header_name.setVisibility(View.VISIBLE);
        header_name.setText(getResources().getString(R.string.my_profile_heading));
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        AndroidUtils.setImageColor(homeIcon, context, R.color.white);
        homeIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, HomeActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle(null);
            getSupportActionBar().setElevation(0);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_map, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return super.onOptionsItemSelected(item);
    }



    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        Log.e("hi---", "IIIIIIIII" + tab.getPosition());
        if (tab.getPosition() == 0) {
            tab.setIcon(tabIcons[1]);
            tabLayout.setTabTextColors(Color.parseColor("#066C57"), Color.parseColor("#ffffff"));
        } else if (tab.getPosition() == 1) {
            tab.setIcon(tabIcons[3]);
            tabLayout.setTabTextColors(Color.parseColor("#066C57"), Color.parseColor("#ffffff"));
        } else if (tab.getPosition() == 2) {
            tab.setIcon(tabIcons[5]);
            tabLayout.setTabTextColors(Color.parseColor("#066C57"), Color.parseColor("#ffffff"));
        }
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {
        if (tab.getPosition() == 0) {
            tab.setIcon(tabIcons[0]);
            tabLayout.setTabTextColors(Color.parseColor("#066C57"), Color.parseColor("#ffffff"));
        } else if (tab.getPosition() == 1) {
            tab.setIcon(tabIcons[2]);
            tabLayout.setTabTextColors(Color.parseColor("#066C57"), Color.parseColor("#ffffff"));
        } else if (tab.getPosition() == 2) {
            tab.setIcon(tabIcons[4]);
            tabLayout.setTabTextColors(Color.parseColor("#066C57"), Color.parseColor("#ffffff"));
        }
    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }


}