package com.example.pat.aapkatrade.Home;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SearchView;
import android.widget.TextView;

import com.example.pat.aapkatrade.Home.banner_home.viewpageradapter_home;
import com.example.pat.aapkatrade.R;
import com.example.pat.aapkatrade.categories_tab.PurticularDataActivity.PurticularActivity;
import com.example.pat.aapkatrade.general.Tabletsize;
import com.example.pat.aapkatrade.general.progressbar.ProgressBarHandler;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import it.carlom.stikkyheader.core.StikkyHeaderBuilder;


/**
 * A simple {@link Fragment} subclass.
 */
public class DashboardFragment extends Fragment implements View.OnClickListener {

    Context context;
    int currentPage = 0;
    LinearLayout viewpagerindicator;
    private RecyclerView recyclerlatestpost, recyclerlatestupdate;
    private LinearLayoutManager llManagerEclipseCollection;
    ArrayList<CommomData> commomDatas = new ArrayList<>();
    ArrayList<CommomData> commomDatas_latestpost = new ArrayList<>();
    ArrayList<CommomData> commomDatas_latestupdate = new ArrayList<>();
    private CommomAdapter commomAdapter_latestpost,commomAdapter_latestproduct;
    //  public latestproductadapter latestproductadapter;
    ProgressBarHandler progress_handler;
    private int dotsCount;
    private ArrayList<String> imageIdList;
    private ImageView[] dots;
    public static SearchView searchView;
    ImageView home_ads;
    private StikkyHeaderBuilder.ScrollViewBuilder stikkyHeader;
    RelativeLayout view_all_latest_post, view_all_latest_update;
    ViewPager vp;
    NestedScrollView scrollView;
    Timer banner_timer = new Timer();
    CoordinatorLayout coordinatorLayout;
    GridLayoutManager gridLayoutManager;
    viewpageradapter_home viewpageradapter;
    JsonObject home_result;
    RelativeLayout rl_searchview_dashboard;

    View view;

    public DashboardFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_dashboard_new, container, false);
            initializeview(view, container);
        }
        return view;
    }


    private void setupviewpager(ArrayList<String> imageIdList) {


        viewpageradapter = new viewpageradapter_home(getActivity(), imageIdList);
        vp.setAdapter(viewpageradapter);
        vp.setCurrentItem(currentPage);
        setUiPageViewController();

        final Handler handler = new Handler();

        final Runnable update = new Runnable() {
            public void run() {
                if (currentPage == viewpageradapter.getCount() - 1) {
                    currentPage = 0;
                }
                vp.setCurrentItem(currentPage++, true);
            }
        };


        banner_timer.schedule(new TimerTask() {

            @Override
            public void run() {
                handler.post(update);
            }
        }, 0, 3000);


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
                } catch (Exception e) {
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    private void initializeview(View v, ViewGroup v2) {


        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);



        progress_handler = new ProgressBarHandler(getActivity());

        coordinatorLayout = (CoordinatorLayout) v.findViewById(R.id.coordination_home);
        coordinatorLayout.setVisibility(View.INVISIBLE);

        home_ads = (ImageView) v.findViewById(R.id.home_ads);
        home_ads.setImageResource(R.drawable.ic_home_ads_banner);


        vp = (ViewPager) view.findViewById(R.id.viewpager_custom);
        context = getActivity();

        llManagerEclipseCollection = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);

        viewpagerindicator = (LinearLayout) view.findViewById(R.id.viewpagerindicator);
       // latestproductadapter = new latestproductadapter(context, commomDatas);
        recyclerlatestpost = (RecyclerView) view.findViewById(R.id.recyclerlatestpost);
        recyclerlatestpost.setLayoutManager(llManagerEclipseCollection);


        scrollView = (NestedScrollView) view.findViewById(R.id.scrollView);

        recyclerlatestupdate = (RecyclerView) view.findViewById(R.id.recyclerlatestupdate);
        if(Tabletsize.isTablet(getActivity()))
        {
            gridLayoutManager = new GridLayoutManager(context, 3);
        }
        else
        {

            gridLayoutManager = new GridLayoutManager(context, 2);
        }


        recyclerlatestupdate.setLayoutManager(gridLayoutManager);
        recyclerlatestupdate.setHasFixedSize(true);

        view_all_latest_post = (RelativeLayout) view.findViewById(R.id.rl_viewall_latest_post);
        view_all_latest_post.setOnClickListener(this);


        view_all_latest_update = (RelativeLayout) view.findViewById(R.id.rl_viewall_latest_update);
        view_all_latest_update.setOnClickListener(this);



        get_home_data();
//        StikkyHeaderBuilder.stickTo(scrollView)
//                .setHeader(R.id.coordination_home, v2)
//                .minHeightHeaderDim(R.dimen.min_header_height)
//                .build();

    }

    public void get_home_data()
    {


        progress_handler.show();
        coordinatorLayout.setVisibility(View.INVISIBLE);


        Ion.with(getActivity())
                .load("http://aapkatrade.com/slim/home")
                .setHeader("authorization", "xvfdbgfdhbfdhtrh54654h54ygdgerwer3")
                .setBodyParameter("authorization", "xvfdbgfdhbfdhtrh54654h54ygdgerwer3")
                .setBodyParameter("city_id", "")
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {



                        if (result != null)
                        {

                            home_result=result;
                            Log.e("data===============", result.toString());

                            JsonObject jsonResult = result.getAsJsonObject("result");


                            JsonArray jsonarray_top_banner = jsonResult.getAsJsonArray("top_banner");
                            imageIdList = new ArrayList<>();


                            for (int l = 0; l < jsonarray_top_banner.size(); l++) {

                                JsonObject jsonObject_top_banner = (JsonObject) jsonarray_top_banner.get(l);
                                String banner_imageurl=jsonObject_top_banner.get("image_url").getAsString();

                                imageIdList.add(banner_imageurl);

                            }


                            setupviewpager(imageIdList);

                            JsonArray latest_post = jsonResult.getAsJsonArray("latest_post");

                            JsonArray latest_update = jsonResult.getAsJsonArray("latest_update");

                            System.out.println("jsonArray---------" + latest_post.toString());

                            for (int i = 0; i < latest_post.size(); i++) {

                                JsonObject jsonObject_latest_post = (JsonObject) latest_post.get(i);

                                System.out.println("jsonArray jsonObject2" + jsonObject_latest_post.toString());

                                String product_id = jsonObject_latest_post.get("id").getAsString();

                                String product_name = jsonObject_latest_post.get("prodname").getAsString();
                                String imageurl = jsonObject_latest_post.get("image_url").getAsString();
                                String productlocation=jsonObject_latest_post.get("city_name").getAsString()+","+
                                        jsonObject_latest_post.get("state_name").getAsString()+","+
                                        jsonObject_latest_post.get("country_name").getAsString();
                                String categoryName = jsonObject_latest_post.get("category_name").getAsString();
                                commomDatas_latestpost.add(new CommomData(product_id, product_name, "", imageurl,productlocation, categoryName));

                            }

                            commomAdapter_latestpost = new CommomAdapter(context, commomDatas_latestpost, "list", "latestdeals");
                            recyclerlatestpost.setAdapter(commomAdapter_latestpost);

                            commomAdapter_latestpost.notifyDataSetChanged();

                            for (int i = 0; i < latest_update.size(); i++) {

                                JsonObject jsonObject_latest_update = (JsonObject) latest_post.get(i);

                                System.out.println("jsonArray jsonObject2" + jsonObject_latest_update.toString());

                                String update_product_id = jsonObject_latest_update.get("id").getAsString();

                                String update_product_name = jsonObject_latest_update.get("prodname").getAsString();
                                String imageurl = jsonObject_latest_update.get("image_url").getAsString();


                                String productlocation=jsonObject_latest_update.get("city_name").getAsString()+","+
                                        jsonObject_latest_update.get("state_name").getAsString()+","+
                                        jsonObject_latest_update.get("country_name").getAsString();


                                String categoryName = jsonObject_latest_update.get("category_name").getAsString();
                                commomDatas_latestupdate.add(new CommomData(update_product_id, update_product_name, "",imageurl,productlocation, categoryName));

                            }

                            commomAdapter_latestproduct = new CommomAdapter(context, commomDatas_latestupdate, "gridtype", "latestupdate");
                            recyclerlatestupdate.setAdapter(commomAdapter_latestproduct);
                            commomAdapter_latestproduct.notifyDataSetChanged();
                            if (scrollView.getVisibility() == View.INVISIBLE) {
                                scrollView.setVisibility(View.VISIBLE);
                            }

                            progress_handler.hide();
                            coordinatorLayout.setVisibility(View.VISIBLE);

                        }

                        else
                        {


                            progress_handler.hide();
                            coordinatorLayout.setVisibility(View.VISIBLE);
                            connection_problem_message();

                        }
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


    @Override
    public void onDestroy() {
        super.onDestroy();
        banner_timer.cancel();
    }


    private void replaceFragment(Fragment newFragment, String tag) {

        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.drawer_layout, newFragment, tag).addToBackStack(null);
        transaction.commit();
    }


    @Override
    public void onClick(View v)
    {

        switch (v.getId())
        {
            case R.id.rl_viewall_latest_post:
                go_to_product_list_activity();
                break;

            case R.id.rl_viewall_latest_update:
                go_to_latest_update_list_activity();
                break;
        }



    }

    private void go_to_product_list_activity() {
        if(home_result!=null)

        {
            Intent go_to_product_listactivity = new Intent(getActivity(), PurticularActivity.class);
            go_to_product_listactivity.putExtra("url","https://aapkatrade.com/slim/latestpost");

            startActivity(go_to_product_listactivity);
            ((AppCompatActivity) context).overridePendingTransition(R.anim.enter, R.anim.exit);
        }
        else{

        }
    }



    private void go_to_latest_update_list_activity() {
        if(home_result!=null)

        {
            Intent go_to_product_listactivity = new Intent(getActivity(), PurticularActivity.class);
            go_to_product_listactivity.putExtra("url","https://aapkatrade.com/slim/latestupdate");

            startActivity(go_to_product_listactivity);
            ((AppCompatActivity) context).overridePendingTransition(R.anim.enter, R.anim.exit);
        }
        else{

        }
    }



    public void connection_problem_message()
    {

        Snackbar snackbar = Snackbar
                .make(getActivity().findViewById(R.id.rl_main_content), "No internet connection!", Snackbar.LENGTH_LONG)
                .setAction("RETRY", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        Intent intent = getActivity().getIntent();
                        getActivity().finish();
                        startActivity(intent);
                    }
                });

// Changing message text color
        snackbar.setActionTextColor(Color.RED);

// Changing action button text color
        View sbView = snackbar.getView();
        TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
        textView.setTextColor(Color.YELLOW);
        snackbar.show();


















    }



}
