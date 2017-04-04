package com.example.pat.aapkatrade.payment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.pat.aapkatrade.Home.HomeActivity;
import com.example.pat.aapkatrade.R;
import com.example.pat.aapkatrade.categories_tab.BlankFragment;
import com.example.pat.aapkatrade.general.Utils.AndroidUtils;
import com.example.pat.aapkatrade.payment.payment_method.CreditDebitFragment;
import com.example.pat.aapkatrade.payment.payment_method.NetBankingFragment;
import com.example.pat.aapkatrade.user_dashboard.order_list.cancel_order_fragment.CancelOrderFragment;
import com.example.pat.aapkatrade.user_dashboard.order_list.complete_order.CompleteOrderFragment;
import com.example.pat.aapkatrade.user_dashboard.order_list.shipped_fragment.ShippedFragment;

import java.util.ArrayList;
import java.util.List;


public class PaymentActivity extends AppCompatActivity implements TabLayout.OnTabSelectedListener {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private Context context;

    private int[] tabIcons = {

            R.drawable.new_order_wht,

            R.drawable.cancel_order_wht,

            R.drawable.shipped_order_wht,

            R.drawable.complete_order_wht,

    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_order_management);
        context = PaymentActivity.this;
        setUpToolBar();
        viewPager = (ViewPager) findViewById(R.id.htab_viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.addOnTabSelectedListener(this);


        tabLayout.setTabTextColors(Color.parseColor("#066C57"), Color.parseColor("#FF6600"));
        //setupTabIcons();


    }

    private void setUpToolBar() {
        ImageView homeIcon = (ImageView) findViewById(R.id.iconHome);
        AppCompatImageView back_imagview = (AppCompatImageView) findViewById(R.id.back_imagview);
        back_imagview.setVisibility(View.VISIBLE);
        back_imagview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        findViewById(R.id.logoWord).setVisibility(View.GONE);
        ;
        TextView header_name = (TextView) findViewById(R.id.header_name);
        header_name.setVisibility(View.VISIBLE);
        header_name.setText(getResources().getString(R.string.order_mgt_heading));
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
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
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
           // tab.setIcon(AndroidUtils.setImageColor(context,tabIcons[0],R.color.orange));
            tabLayout.setTabTextColors(Color.parseColor("#066C57"), Color.parseColor("#FF6600"));
        } else if (tab.getPosition() == 1) {
            //tab.setIcon(AndroidUtils.setImageColor(context,tabIcons[1],R.color.orange));
            tabLayout.setTabTextColors(Color.parseColor("#066C57"), Color.parseColor("#FF6600"));
        } else if (tab.getPosition() == 2) {
           // tab.setIcon(AndroidUtils.setImageColor(context,tabIcons[2],R.color.orange));
            tabLayout.setTabTextColors(Color.parseColor("#066C57"), Color.parseColor("#FF6600"));
        } else if (tab.getPosition() == 3) {
            //tab.setIcon(AndroidUtils.setImageColor(context,tabIcons[3],R.color.orange));
            tabLayout.setTabTextColors(Color.parseColor("#066C57"), Color.parseColor("#FF6600"));
        }
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {
        if (tab.getPosition() == 0) {
           // tab.setIcon(AndroidUtils.setImageColor(context,tabIcons[0],R.color.text_order_tab));
            tabLayout.setTabTextColors(Color.parseColor("#066C57"), Color.parseColor("#FF6600"));
        } else if (tab.getPosition() == 1) {
            //tab.setIcon(AndroidUtils.setImageColor(context,tabIcons[1],R.color.text_order_tab));
            tabLayout.setTabTextColors(Color.parseColor("#066C57"), Color.parseColor("#FF6600"));
        } else if (tab.getPosition() == 2) {
           // tab.setIcon(AndroidUtils.setImageColor(context,tabIcons[2],R.color.text_order_tab));
            tabLayout.setTabTextColors(Color.parseColor("#066C57"), Color.parseColor("#FF6600"));
        } else if (tab.getPosition() == 3) {
            //tab.setIcon(AndroidUtils.setImageColor(context,tabIcons[3],R.color.text_order_tab));
            tabLayout.setTabTextColors(Color.parseColor("#066C57"), Color.parseColor("#FF6600"));
        }
    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {
        if (tab.getPosition() == 0) {
            //tab.setIcon(AndroidUtils.setImageColor(context,tabIcons[0],R.color.orange));
            tabLayout.setTabTextColors(Color.parseColor("#066C57"), Color.parseColor("#FF6600"));
        } else if (tab.getPosition() == 1) {
           // tab.setIcon(AndroidUtils.setImageColor(context,tabIcons[1],R.color.orange));
            tabLayout.setTabTextColors(Color.parseColor("#066C57"), Color.parseColor("#FF6600"));
        } else if (tab.getPosition() == 2) {
           // tab.setIcon(AndroidUtils.setImageColor(context,tabIcons[2],R.color.orange));
            tabLayout.setTabTextColors(Color.parseColor("#066C57"), Color.parseColor("#FF6600"));
        } else if (tab.getPosition() == 3) {
            //tab.setIcon(AndroidUtils.setImageColor(context,tabIcons[3],R.color.orange));
            tabLayout.setTabTextColors(Color.parseColor("#066C57"), Color.parseColor("#FF6600"));
        }
    }


    private void setupTabIcons()
    {
        tabLayout.getTabAt(0).setIcon(AndroidUtils.setImageColor(context,tabIcons[0],R.color.orange));
        tabLayout.getTabAt(1).setIcon(AndroidUtils.setImageColor(context,tabIcons[1],R.color.text_order_tab));
        tabLayout.getTabAt(2).setIcon(AndroidUtils.setImageColor(context,tabIcons[2],R.color.text_order_tab));
        tabLayout.getTabAt(3).setIcon(AndroidUtils.setImageColor(context,tabIcons[3],R.color.text_order_tab));
    }


    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFrag(new NetBankingFragment(), "NET BANKING");
        adapter.addFrag(new CreditDebitFragment(), "DEBIT/CREDIT CARD");

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
