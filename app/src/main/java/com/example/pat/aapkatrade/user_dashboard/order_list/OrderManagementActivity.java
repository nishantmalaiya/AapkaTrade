package com.example.pat.aapkatrade.user_dashboard.order_list;

import android.graphics.Color;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.example.pat.aapkatrade.R;
import com.example.pat.aapkatrade.categories_tab.BlankFragment;
import com.example.pat.aapkatrade.user_dashboard.order_list.cancel_order_fragment.CancelOrderFragment;
import com.example.pat.aapkatrade.user_dashboard.order_list.complete_order.CompleteOrderFragment;
import com.example.pat.aapkatrade.user_dashboard.order_list.shipped_fragment.ShippedFragment;

import java.util.ArrayList;
import java.util.List;


public class OrderManagementActivity extends AppCompatActivity implements TabLayout.OnTabSelectedListener {

    private TabLayout tabLayout;
    private ViewPager viewPager;

    private int[] tabIcons = {

            R.drawable.new_order_grn,
            R.drawable.new_order_wht,

            R.drawable.cancel_order_grn,
            R.drawable.cancel_order_wht,

            R.drawable.shipped_order_grn,
            R.drawable.shipped_order_wht,

            R.drawable.complete_order_grn,
            R.drawable.complete_order_wht,

    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_order_management);

        setuptoolbar();
        viewPager = (ViewPager) findViewById(R.id.htab_viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.addOnTabSelectedListener(this);


        tabLayout.setTabTextColors(Color.parseColor("#066C57"), Color.parseColor("#ffffff"));

        setupTabIcons();


    }


    private void setuptoolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if(getSupportActionBar()!=null) {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle(null);
            getSupportActionBar().setElevation(0);
        }
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
        } else if (tab.getPosition() == 3) {
            tab.setIcon(tabIcons[7]);
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
        } else if (tab.getPosition() == 3) {
            tab.setIcon(tabIcons[6]);
            tabLayout.setTabTextColors(Color.parseColor("#066C57"), Color.parseColor("#ffffff"));
        }
    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.empty_menu, menu);
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

    private void setupTabIcons() {
        tabLayout.getTabAt(0).setIcon(tabIcons[1]);
        tabLayout.getTabAt(1).setIcon(tabIcons[2]);
        tabLayout.getTabAt(2).setIcon(tabIcons[4]);
        tabLayout.getTabAt(3).setIcon(tabIcons[6]);
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFrag(new BlankFragment(), "New Order");
        adapter.addFrag(new CancelOrderFragment(), "Cancelled");
        adapter.addFrag(new ShippedFragment(), "Shipped");
        adapter.addFrag(new CompleteOrderFragment(), "Completed");
        viewPager.setAdapter(adapter);
    }


    private class ViewPagerAdapter extends FragmentPagerAdapter {

        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFrag(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

}
