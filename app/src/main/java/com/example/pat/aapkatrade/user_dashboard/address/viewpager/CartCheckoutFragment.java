package com.example.pat.aapkatrade.user_dashboard.address.viewpager;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.pat.aapkatrade.R;
import com.example.pat.aapkatrade.user_dashboard.address.AddressViewPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class CartCheckoutFragment extends Fragment {
    ViewPager vp;
    private ImageView[] dots;
    private int dotsCount;
    int currentPage=0;
    private List<Integer> imageIdList;
    RelativeLayout relativeLayout;
    LinearLayout viewpagerindicator;
    private AddressViewPagerAdapter viewpageradapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.shopping_cart_address_layout, container, false);

        vp=(ViewPager)view.findViewById(R.id.cartCheckOutViewPager);
        relativeLayout = (RelativeLayout) view.findViewById(R.id.cartViewPagerContainer);
        viewpagerindicator = (LinearLayout) view.findViewById(R.id.viewpager_indicator);
        Toast.makeText(getContext(), "new fragment", Toast.LENGTH_SHORT).show();
        setupviewpager();
        view.findViewById(R.id.radioButtonContainer).setVisibility(View.GONE);



        return view;
    }


    private void setupviewpager() {

        imageIdList = new ArrayList<>();
        imageIdList.add(R.drawable.banner_home);
        imageIdList.add(R.drawable.banner_home);
        imageIdList.add(R.drawable.banner_home);
        viewpageradapter  = new AddressViewPagerAdapter(getContext(), null);
        vp.setAdapter(viewpageradapter);
        vp.setCurrentItem(currentPage);
        setUiPageViewController();
        vp.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                try {
                    for (int i = 0; i < dotsCount; i++) {
                        dots[i].setImageDrawable(getResources().getDrawable(R.drawable.nonselected_item));
                    }
                    dots[position].setImageDrawable(getResources().getDrawable(R.drawable.selecteditem_dot));
                }
                catch (Exception e)
                {
                }
            }


            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    private void setUiPageViewController() {

        dotsCount = viewpageradapter.getCount();
        dots = new ImageView[dotsCount];

        for (int i = 0; i < dotsCount; i++) {
            dots[i] = new ImageView(getActivity());
            dots[i].setImageDrawable(getResources().getDrawable(R.drawable.nonselected_item));

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );

            params.setMargins(4, 0, 4, 0);

            viewpagerindicator.addView(dots[i], params);
        }

        dots[0].setImageDrawable(getResources().getDrawable(R.drawable.selecteditem_dot));
    }

}
