package com.example.pat.aapkatrade.productdetail;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.example.pat.aapkatrade.Home.HomeActivity;
import com.example.pat.aapkatrade.R;
import com.example.pat.aapkatrade.general.AppSharedPreference;
import com.example.pat.aapkatrade.general.CheckPermission;
import com.example.pat.aapkatrade.general.LocationManager_check;
import com.example.pat.aapkatrade.general.Utils.AndroidUtils;
import com.example.pat.aapkatrade.general.progressbar.ProgressBarHandler;
import com.example.pat.aapkatrade.login.LoginDashboard;
import com.example.pat.aapkatrade.map.GoogleMapActivity;
import com.example.pat.aapkatrade.payment.PaymentActivity;
import com.example.pat.aapkatrade.productdetail.open_shop.OpenShopAdapter;
import com.example.pat.aapkatrade.productdetail.open_shop.OpenShopData;
import com.example.pat.aapkatrade.productdetail.reviewlist.ReviewListAdapter;

import com.example.pat.aapkatrade.productdetail.reviewlist.ReviewListData;
import com.example.pat.aapkatrade.rateus.RateusActivity;
import com.example.pat.aapkatrade.service_enquiry.ServiceEnquiry;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.shehabic.droppy.DroppyClickCallbackInterface;
import com.shehabic.droppy.DroppyMenuItem;
import com.shehabic.droppy.DroppyMenuPopup;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import github.nisrulz.stackedhorizontalprogressbar.StackedHorizontalProgressBar;


public class ProductDetail extends AppCompatActivity implements DatePickerDialog.OnDateSetListener
{


    LinearLayout viewpagerindicator, linearlayoutShare, linearlayoutLocation;
    Spinner spinner;
    int max = 10;
    private ArrayList<String> imageList;
    int currentPage = 0;
    private int isStartDate = -1;
    ServiceEnquiry serviceEnquiry;
    private String date;
    StackedHorizontalProgressBar progressbarFive, progressbarFour, progressbarThree, progressbarTwo, progressbarOne;
    ViewPager vp;
    ProductViewPagerAdapter viewpageradapter;
    private int dotsCount;
    private ImageView[] dots;
    Timer banner_timer = new Timer();
    RelativeLayout relativeBuyNow, RelativeProductDetail, relativeRateReview;
    LinearLayout linearProductDetail;
    TextView tvProductName, tvProPrice, tvCrossPrice, tvDiscription, tvSpecification, tvQuatity;
    ProgressBarHandler progress_handler;
    String product_id, product_location;
    ImageView imgViewPlus, imgViewMinus;
    int quantity_value = 1;
    ProgressBarHandler progressBarHandler;
    String productlocation, categoryName;
    LinearLayout linearLayoutQuantity;
    EditText firstName, quantity, price, mobile, email, etEndDate, etStatDate, description, editText;
    TextView tvServiceBuy, textViewQuantity,tvRatingAverage,tvToatal_rating_review;
    // TextView tvDurationHeading,tvDuration;
    Dialog dialog;
    private Context context;
    private String product_name;
    DroppyMenuPopup droppyMenu;
    AppSharedPreference app_sharedpreference;
    RecyclerView reviewList,openShopList;
    LinearLayoutManager mLayoutManager,mLayoutManagerShoplist;
    ReviewListAdapter reviewListAdapter;
    OpenShopAdapter openShopAdapter;
    ArrayList<OpenShopData> openShopDatas = new ArrayList<>();
    ArrayList<ReviewListData> reviewListDatas = new ArrayList<>();
    ArrayList<Integer> color_openshop = new ArrayList<>();





    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        Log.e("time  Product Detail", String.valueOf(System.currentTimeMillis()));

        setContentView(R.layout.activity_product_detail);

        app_sharedpreference = new AppSharedPreference(ProductDetail.this);

        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);

        context = ProductDetail.this;

        Intent intent = getIntent();

        Bundle b = intent.getExtras();

        product_id = b.getString("product_id");

        Log.e("product_id", product_id);

        product_location = b.getString("product_location");

        progressBarHandler = new ProgressBarHandler(context);

        setUpToolBar();

        color_openshop.add(R.color.open_shop_day_color1);
        color_openshop.add(R.color.open_shop_day_color2);
        color_openshop.add(R.color.open_shop_day_color3);
        color_openshop.add(R.color.open_shop_day_color4);
        color_openshop.add(R.color.open_shop_day_color5);
        color_openshop.add(R.color.open_shop_day_color6);
        color_openshop.add(R.color.open_shop_day_color7);


        initView();

        get_data();


    }

    private void Init_droppy() {

        DroppyMenuPopup.Builder droppyBuilder = new DroppyMenuPopup.Builder(this, linearLayoutQuantity);
        droppyMenu = droppyBuilder.build();
        droppyBuilder.addMenuItem(new DroppyMenuItem("1"))
                .addMenuItem(new DroppyMenuItem("2"))
                .addMenuItem(new DroppyMenuItem("3"))
                .addMenuItem(new DroppyMenuItem("4"))
                .addMenuItem(new DroppyMenuItem("5"))
                .addSeparator()
                .addMenuItem(new DroppyMenuItem("More"));
        droppyBuilder.setOnClick(new DroppyClickCallbackInterface() {
            @Override
            public void call(View v, int id) {
                switch (id) {
                    case 0:
                        textViewQuantity.setText("1");
                        break;
                    case 1:
                        textViewQuantity.setText("2");
                        break;
                    case 2:
                        textViewQuantity.setText("3");
                        break;
                    case 3:
                        textViewQuantity.setText("4");
                        break;
                    case 4:
                        textViewQuantity.setText("5");
                        break;
                    case 5:
                        showPopup("Quantity");
                        break;


                }
            }
        });
        linearLayoutQuantity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        droppyMenu = droppyBuilder.build();


    }

    private void showPopup(String description) {
        boolean wrapInScrollView = true;
        MaterialDialog dialog = new MaterialDialog.Builder(this)
                .title("Quantity")
                .customView(R.layout.more, wrapInScrollView)
                .positiveText(R.string.ok)
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        if (editText.getText().length() <= 0) {
                            showMessage("Nothing done");
                        } else {
                            showMessage(editText.getText().toString());
                            textViewQuantity.setText(editText.getText().toString());
                        }
                        dialog.dismiss();
                    }
                })
                .show();
        editText = (EditText) dialog.findViewById(R.id.editText);

    }


    private void get_data()
    {
        relativeBuyNow.setVisibility(View.INVISIBLE);
        linearProductDetail.setVisibility(View.INVISIBLE);
        progress_handler.show();
        Log.e("data_productdeatil", getResources().getString(R.string.webservice_base_url) + "     "+product_id);

        Ion.with(getApplicationContext())
                .load(getResources().getString(R.string.webservice_base_url) + "/product_detail/" + product_id)
                .setHeader("authorization", "xvfdbgfdhbfdhtrh54654h54ygdgerwer3")
                .setBodyParameter("authorization", "xvfdbgfdhbfdhtrh54654h54ygdgerwer3")
                .setBodyParameter("id","0")
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {

                        if (result != null)
                        {

                            Log.e("result---------", result.toString());

                            JsonObject jsonObject = result.getAsJsonObject();

                            JsonObject json_result = jsonObject.getAsJsonObject("result");

                            JsonObject json_total_rating = jsonObject.getAsJsonObject("total_rating");

                            String avg_rating = json_total_rating.get("avg_rating").getAsString();

                            tvRatingAverage.setText(avg_rating);

                            String total_review = json_total_rating.get("countreviews").getAsString();

                            tvToatal_rating_review.setText(total_review +" rating and "+"review "+total_review);

                            JsonArray jsonArray_image = json_result.getAsJsonArray("product_images");

                           /* JsonArray jsonArray_openshop = result.getAsJsonArray("");

                            for (int k= 0; k<jsonArray_openshop.size(); k++)
                            {
                                JsonObject jsonopenshop_data = (JsonObject) jsonArray_openshop.get(k);
                            }*/


                            JsonArray jsonArray_review = result.getAsJsonArray("reviews");

                            if (jsonArray_review.size()==0){


                            }
                            else
                                {
                                for (int j = 0; j < jsonArray_review.size(); j++) {

                                    JsonObject jsonreview_data = (JsonObject) jsonArray_review.get(j);

                                    String email = jsonreview_data.get("email").getAsString();

                                    String name = jsonreview_data.get("name").getAsString();

                                    String message = jsonreview_data.get("message").getAsString();

                                    String rating = jsonreview_data.get("rating").getAsString();

                                    String title = jsonreview_data.get("title").getAsString();

                                    String created_date = jsonreview_data.get("created_at").getAsString();

                                    Log.e("jsonreview---", title.toString());

                                    reviewListDatas.add(new ReviewListData(email, name, message, rating, title, created_date));


                                }

                                reviewListAdapter = new ReviewListAdapter(ProductDetail.this, reviewListDatas);
                                reviewList.setAdapter(reviewListAdapter);
                            }

                            System.out.println("jsonArray_image------" + jsonArray_review.toString());

                            for (int i = 0; i < jsonArray_image.size(); i++)
                            {
                                JsonObject jsonimage = (JsonObject) jsonArray_image.get(i);

                                String image_url = jsonimage.get("image_url").getAsString();

                                System.out.println("image_url---------" + image_url);
                                imageList.add(image_url);

                            }
                            product_name = json_result.get("name").getAsString();

                            categoryName = json_result.get("catname").getAsString();

                            String product_price = json_result.get("price").getAsString();

                            String product_cross_price = json_result.get("cross_price").getAsString();

                            String description = json_result.get("short_des").getAsString();

                            String duration = json_result.get("deliverday").getAsString();

                            String enquiry = json_result.get("enquiry").getAsString();

                            productlocation = json_result.get("city_name").getAsString() + "," + json_result.get("state_name").getAsString() + "," + json_result.get("country_name").getAsString();

                            if (enquiry.equals("1"))
                            {
                                tvServiceBuy.setText("Buy Now");
                            }
                            else
                            {
                                tvServiceBuy.setText("Service Enquiry");
                            }

                            tvProductName.setText(product_name);
                            tvProPrice.setText("\u20A8" + "." + " " + product_price);
                            tvCrossPrice.setText("\u20A8" + "." + " " + product_cross_price);
                            tvDiscription.setText(description);
                            setupviewpager();

                            progress_handler.hide();

                            linearProductDetail.setVisibility(View.VISIBLE);
                            relativeBuyNow.setVisibility(View.VISIBLE);


                        }
                        else
                        {
                            progress_handler.hide();
                            linearProductDetail.setVisibility(View.INVISIBLE);
                            relativeBuyNow.setVisibility(View.INVISIBLE);
                        }


                    }


                });

    }


    private void setUiPageViewController() {

        dotsCount = viewpageradapter.getCount();
        dots = new ImageView[dotsCount];

        for (int i = 0; i < dotsCount; i++) {
            dots[i] = new ImageView(getApplicationContext());
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


    private void setupviewpager() {

        viewpageradapter = new ProductViewPagerAdapter(ProductDetail.this, imageList);
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
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels)
            {

            }

            @Override
            public void onPageSelected(int position)
            {

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


    private void initView()
    {
        context = ProductDetail.this;


        linearLayoutQuantity = (LinearLayout) findViewById(R.id.linearlayoutQuantity);

        textViewQuantity = (TextView) findViewById(R.id.textViewQuantity);

        progress_handler = new ProgressBarHandler(this);

        imageList = new ArrayList<>();

        relativeRateReview = (RelativeLayout) findViewById(R.id.relativeRateReview);

        linearlayoutShare = (LinearLayout) findViewById(R.id.linearlayoutShare);

        linearlayoutLocation = (LinearLayout) findViewById(R.id.linearlayoutLocation);

        tvRatingAverage = (TextView) findViewById(R.id.tvRatingAverage);

        tvToatal_rating_review = (TextView) findViewById(R.id.tvToatal_rating_review);

        tvServiceBuy = (TextView) findViewById(R.id.tvServiceBuy);

        reviewList = (RecyclerView) findViewById(R.id.reviewList);

        mLayoutManager = new LinearLayoutManager(getApplicationContext());

        reviewList.setLayoutManager(mLayoutManager);

        openShopList = (RecyclerView) findViewById(R.id.openShopList);

        mLayoutManagerShoplist = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);

        openShopList.setLayoutManager(mLayoutManagerShoplist);
        openShopAdapter = new OpenShopAdapter(ProductDetail.this, openShopDatas,color_openshop);
        openShopList.setAdapter(openShopAdapter);


        relativeRateReview.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if (app_sharedpreference.getsharedpref("username", "not").contains("not"))
                {
                    startActivity(new Intent(ProductDetail.this, LoginDashboard.class));
                }
                else
                {
                    Intent rate_us = new Intent(ProductDetail.this,RateusActivity.class);
                    rate_us.putExtra("product_id",product_id);
                    rate_us.putExtra("product_name",tvProductName.getText().toString());
                    rate_us.putExtra("product_price",tvProPrice.getText().toString());
                    rate_us.putExtra("product_image",imageList.get(0).toString());
                    startActivity(rate_us);
                }
            }
        });



        linearlayoutLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean permission_status = CheckPermission.checkPermissions(ProductDetail.this);
                if (permission_status) {
                    progressBarHandler.show();
                    LocationManager_check locationManagerCheck = new LocationManager_check(ProductDetail.this);
                    Location location = null;
                    if (locationManagerCheck.isLocationServiceAvailable()) {
                        Intent intent = new Intent(ProductDetail.this, GoogleMapActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        intent.putExtra("product_location", productlocation);
                        ProductDetail.this.startActivity(intent);
                        progressBarHandler.hide();
                    } else {

                        locationManagerCheck.createLocationServiceError(ProductDetail.this);
                        progress_handler.hide();
                    }
                }
            }
        });
        linearlayoutShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String message = "Text I want to share.";
                Intent share = new Intent(Intent.ACTION_SEND);
                share.setType("text/plain");
                share.putExtra(Intent.EXTRA_TEXT, message);
                startActivity(Intent.createChooser(share, "Title of the dialog the system will open"));

            }
        });

        RelativeProductDetail = (RelativeLayout) findViewById(R.id.RelativeProductDetail);
        linearProductDetail = (LinearLayout) findViewById(R.id.linearProductDetail);
        tvProductName = (TextView) findViewById(R.id.tvProductName);
        tvProPrice = (TextView) findViewById(R.id.tvProPrice);
        tvCrossPrice = (TextView) findViewById(R.id.tvCrossPrice);
        tvCrossPrice.setPaintFlags(tvCrossPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        tvDiscription = (TextView) findViewById(R.id.tvDiscription);
        tvSpecification = (TextView) findViewById(R.id.tvSpecification);
        tvQuatity = (TextView) findViewById(R.id.tvQuatity);
        imgViewPlus = (ImageView) findViewById(R.id.imgViewPlus);
        imgViewPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                quantity_value = quantity_value + 1;
                tvQuatity.setText(String.valueOf(quantity_value));

            }
        });
        imgViewMinus = (ImageView) findViewById(R.id.imgViewMinus);
        imgViewMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (quantity_value == 1) {
                    tvQuatity.setText(String.valueOf(quantity_value));
                } else {
                    quantity_value = quantity_value - 1;
                    tvQuatity.setText(String.valueOf(quantity_value));
                }
            }
        });
        relativeBuyNow = (RelativeLayout) findViewById(R.id.relativeBuyNow);
        vp = (ViewPager) findViewById(R.id.viewpager_custom);
        viewpagerindicator = (LinearLayout) findViewById(R.id.viewpagerindicator);

        progressbarFive = (StackedHorizontalProgressBar) findViewById(R.id.progressbarFive);
        progressbarFive.setMax(max);
        progressbarFive.setProgress(10);
        progressbarFive.setSecondaryProgress(0);

        progressbarFour = (StackedHorizontalProgressBar) findViewById(R.id.progressbarFour);
        progressbarFour.setMax(max);
        progressbarFour.setProgress(6);
        progressbarFour.setSecondaryProgress(4);

        progressbarThree = (StackedHorizontalProgressBar) findViewById(R.id.progressbarThree);
        progressbarThree.setMax(max);
        progressbarThree.setProgress(3);
        progressbarThree.setSecondaryProgress(7);

        progressbarTwo = (StackedHorizontalProgressBar) findViewById(R.id.progressbarTwo);
        progressbarTwo.setMax(max);
        progressbarTwo.setProgress(8);
        progressbarTwo.setSecondaryProgress(2);

        progressbarOne = (StackedHorizontalProgressBar) findViewById(R.id.progressbarOne);
        progressbarOne.setMax(max);
        progressbarOne.setProgress(7);
        progressbarOne.setSecondaryProgress(3);

        relativeBuyNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tvServiceBuy.getText().toString().equals("Buy Now")) {

                    Intent i = new Intent(ProductDetail.this, PaymentActivity.class);
                    startActivity(i);

                } else {
                    ServiceEnquiry serviceEnquiry = new ServiceEnquiry(context, product_name, categoryName);
                    serviceEnquiry.show();
                }

            }
        });



    }

    private void setUpToolBar()
    {
        ImageView homeIcon = (ImageView) findViewById(R.id.iconHome);
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
    public boolean onCreateOptionsMenu(Menu menu)
    {
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
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth)
    {
        showDate(year, monthOfYear + 1, dayOfMonth);
    }


    private void showDate(int year, int month, int day)
    {
        date = (new StringBuilder()).append(year).append("-").append(month).append("-").append(day).toString();
        if (isStartDate == 0) {
            etStatDate.setText(date);
        } else if (isStartDate == 1) {
            etEndDate.setText(date);
        }
    }


    private void showMessage(String s) {
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
    }




}
