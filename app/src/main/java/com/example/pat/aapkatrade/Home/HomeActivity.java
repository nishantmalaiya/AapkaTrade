package com.example.pat.aapkatrade.Home;

import android.annotation.TargetApi;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.speech.RecognizerIntent;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem;
import com.example.pat.aapkatrade.Home.aboutus.AboutUsFragment;
import com.example.pat.aapkatrade.Home.navigation.NavigationFragment;
import com.example.pat.aapkatrade.R;
import com.example.pat.aapkatrade.contact_us.ContactUsFragment;
import com.example.pat.aapkatrade.general.AppSharedPreference;
import com.example.pat.aapkatrade.general.App_config;
import com.example.pat.aapkatrade.general.CheckPermission;
import com.example.pat.aapkatrade.general.ConnetivityCheck;
import com.example.pat.aapkatrade.general.Utils.AndroidUtils;
import com.example.pat.aapkatrade.general.progressbar.ProgressBarHandler;
import com.example.pat.aapkatrade.location.Mylocation;
import com.example.pat.aapkatrade.login.LoginDashboard;
import com.example.pat.aapkatrade.user_dashboard.User_DashboardFragment;
import com.example.pat.aapkatrade.user_dashboard.associateagreement.AssociateAgreementDialog;
import com.example.pat.aapkatrade.user_dashboard.my_profile.ProfilePreviewActivity;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity
{

    private NavigationFragment drawer;
    private Toolbar toolbar;
    private DashboardFragment homeFragment;
    private AboutUsFragment aboutUsFragment;

    Context context;
    public static String shared_pref_name = "aapkatrade";
    App_config aa;
    AHBottomNavigation bottomNavigation;
    CoordinatorLayout coordinatorLayout;
    User_DashboardFragment user_dashboardFragment;
    ContactUsFragment contactUsFragment;
    ProgressBar progressBar;
    Boolean permission_status;
    public static String userid, username;
    NestedScrollView scrollView;
    float initialX, initialY;
    public static  RelativeLayout rl_main_content,rl_searchview_dashboard;
    AppSharedPreference app_sharedpreference;

    Mylocation mylocation;
    boolean doubleBackToExitPressedOnce = false;
    LinearLayout linearlayout_home;
ProgressBarHandler progressBarHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        rl_main_content=(RelativeLayout)findViewById(R.id.rl_main_content);
        progressBarHandler=new ProgressBarHandler(this);

        linearlayout_home=(LinearLayout)findViewById(R.id.ll_toolbar_container_home) ;
        app_sharedpreference = new AppSharedPreference(HomeActivity.this);

        App_config.set_defaultfont(HomeActivity.this);
        aboutUsFragment= new AboutUsFragment();
        contactUsFragment=new ContactUsFragment();
        user_dashboardFragment=new User_DashboardFragment();

        if(!(app_sharedpreference.getsharedpref("usertype", "-1").equals("3") && app_sharedpreference.getsharedpref("term_accepted", "-1").equals("0"))) {
            loadLocale();

            permission_status = CheckPermission.checkPermissions(HomeActivity.this);

            if (permission_status) {

                setContentView(R.layout.activity_homeactivity);

                //prefs = getSharedPreferences(shared_pref_name, Activity.MODE_PRIVATE);
                context = this;
                //  permissions  granted.
                setupToolBar();
                //setupNavigation();
                setupNavigationCustom();
                setupDashFragment();
                Intent iin = getIntent();
                Bundle b = iin.getExtras();
                setup_bottomNavigation();


                App_config.deleteCache(HomeActivity.this);

            }


            else {

                setContentView(R.layout.activity_homeactivity);



                //prefs = getSharedPreferences(shared_pref_name, Activity.MODE_PRIVATE);
                context = this;
                //  permissions  granted.
                setupToolBar();
                //setupNavigation();
                setupNavigationCustom();
                setupDashFragment();
                Intent iin = getIntent();
                Bundle b = iin.getExtras();
                setup_bottomNavigation();
                checked_wifispeed();
                App_config.deleteCache(HomeActivity.this);
            }
        } else {
Log.e("HIIIIIIII","UJUJUJUJUJUJUJUJUJUJ");
            AssociateAgreementDialog dialog = new AssociateAgreementDialog(HomeActivity.this);
            dialog.show();

        }
    }


    private void checked_wifispeed() {

        int a = ConnetivityCheck.get_wifi_speed(this);
        Log.e("a", a + "");




    }


    private void setupNavigationCustom() {
        drawer = (NavigationFragment) getSupportFragmentManager().findFragmentById(R.id.fragment);
        drawer.setup(R.id.fragment, (DrawerLayout) findViewById(R.id.drawer), toolbar);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.home_menu, menu);
        // ActionItemBadge.update(((AppCompatActivity) context), menu.findItem(R.id.login), ContextCompat.getDrawable(context, R.drawable.ic_cart_black)
        // , ActionItemBadge.BadgeStyles.GREY, 3);
        return true;
    }


    private void setupToolBar()
    {
        toolbar = (Toolbar) findViewById(R.id.toolbar_home);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setTitle(null);
        ImageView home_link=(ImageView)toolbar.findViewById(R.id.iconHome);
        home_link.setVisibility(View.GONE);
        // getSupportActionBar().setIcon(R.drawable.logo_word);



    }

    private void replaceFragment(Fragment newFragment, String tag) {

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.drawer_layout, newFragment, tag).addToBackStack(tag);
        transaction.commit();
    }

    private void setupDashFragment() {
        if (homeFragment == null) {
            homeFragment = new DashboardFragment();
        }
        String tagName = homeFragment.getClass().getName();
        replaceFragment(homeFragment, tagName);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.login:

                if (app_sharedpreference.getsharedpref("userid", "notlogin").equals("notlogin"))
                {
                    Intent i = new Intent(HomeActivity.this, LoginDashboard.class);
                    startActivity(i);
                    overridePendingTransition(R.anim.left_to_right, R.anim.right_to_left);
                    return true;
                }

                else

                    {
                    Intent i = new Intent(HomeActivity.this, ProfilePreviewActivity.class);
                    startActivity(i);
                    overridePendingTransition(R.anim.left_to_right, R.anim.right_to_left);
                    return true;
                }
                //finish();


            case R.id.language:
                View menuItemView = findViewById(R.id.language);
                PopupMenu popup = new PopupMenu(HomeActivity.this.context, menuItemView);
                //Inflating the Popup using xml file
                popup.getMenuInflater().inflate(R.menu.menu_language_list, popup.getMenu());


                //registering popup with OnMenuItemClickListener
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.english_language:
                                saveLocale("en");

                                // getIntent().start
                                return true;

                            case R.id.hindi_language:
                                saveLocale("hi");

                                return true;
                        }


                        return true;
                    }
                });

                popup.show();//showing popup menu


//               User_DashboardFragment dashboardFragment = new User_DashboardFragment();
//               FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
//               transaction.replace(R.id.drawer_layout, dashboardFragment, null).addToBackStack(null);
//               transaction.commit();

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {

        FragmentManager fm = getSupportFragmentManager();
        DashboardFragment dashboardFragment = (DashboardFragment) fm.findFragmentByTag(homeFragment.getClass().getName());
        ContactUsFragment showcontactUsFragment = (ContactUsFragment) fm.findFragmentByTag(contactUsFragment.getClass().getName());
        AboutUsFragment showaboutUsFragment = (AboutUsFragment) fm.findFragmentByTag(aboutUsFragment.getClass().getName());

        User_DashboardFragment showuserdashboardfragment = (User_DashboardFragment) fm.findFragmentByTag(user_dashboardFragment.getClass().getName());


        if(dashboardFragment.isVisible())
        {

            double_back_pressed("finish");
            //finish();

            Log.e("myfragment_visible","myfragment visible");
        }
        else if(showcontactUsFragment!=null && showcontactUsFragment.isVisible())
        {
            double_back_pressed("finish");
           // finish();
            Log.e("contact us visible","contact us visible");

        }
        else if (showaboutUsFragment!=null && showaboutUsFragment.isVisible())
        {
            double_back_pressed("finish");
            //finish();
            Log.e("showabout visible","showaboutUsFragment visible");
        }
        else if(showuserdashboardfragment!=null&&showuserdashboardfragment.isVisible())
        {
            double_back_pressed("finish");
            //finish();
            Log.e("userdashboard visible","userdashboard visible");
        }
        else {

            double_back_pressed("onbackpressed");
           // super.onBackPressed();
        }





//        if (getSupportFragmentManager().getBackStackEntryCount() == 1) {
//
//
//            Log.e("condition_one",getSupportFragmentManager().getBackStackEntryCount()+"");
//            finish();
//        } else {
//            Log.e("condition_else",getSupportFragmentManager().getBackStackEntryCount()+"");
//            super.onBackPressed();
//        }


    }


    public void loadLocale() {
        String langPref = "Language";

        String language = app_sharedpreference.getsharedpref(langPref, "");
        App_config.setLocaleFa(HomeActivity.this, language);
        Log.e("language", language);
        // changeLang(language);
    }

    public void saveLocale(String lang) {
        String langPref = "Language";
        app_sharedpreference.setsharedpref(langPref, lang);
        Log.e("language_pref", langPref + "****" + lang);
        Intent intent = getIntent();
        finish();
        startActivity(intent);
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case CheckPermission.MULTIPLE_PERMISSIONS: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Log.e("permission_granted", "permission_granted");
                    // permissions granted.
                } else {
                    Log.e("permission_requried", "permission_requried");
                    // no permissions granted.
                }
                return;
            }
        }
    }


    private void setup_bottomNavigation() {
        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordination_home_activity);

        bottomNavigation = (AHBottomNavigation) findViewById(R.id.bottom_navigation);

        scrollView = (NestedScrollView) findViewById(R.id.scroll_main);

        //setup_scrollview(scrollView);

//        tabColors = getActivity().getResources().getIntArray(R.array.tab_colors);
//        bottom_menuAdapter = new AHBottomNavigationAdapter(getActivity(), R.menu.button_menu);
//        bottom_menuAdapter.setupWithBottomNavigation(bottomNavigation, tabColors);

// Create items
        AHBottomNavigationItem item1 = new AHBottomNavigationItem(R.string.tab_1, R.drawable.ic_navigation_home, R.color.dark_green);
        AHBottomNavigationItem item2 = new AHBottomNavigationItem(R.string.tab_2, R.drawable.ic_home_dashboard_aboutus, R.color.orange);
        AHBottomNavigationItem item3 = new AHBottomNavigationItem(R.string.tab_3, R.drawable.ic_home_dashboard_rate_us, R.color.dark_green);
        AHBottomNavigationItem item4 = new AHBottomNavigationItem(R.string.tab_4, R.drawable.ic_home_bottom_account, R.color.dark_green);
        AHBottomNavigationItem item5 = new AHBottomNavigationItem(R.string.tab_5, R.drawable.ic_about_us, R.color.dark_green);

// Add items


        bottomNavigation.setOnNavigationPositionListener(new AHBottomNavigation.OnNavigationPositionListener() {
            @Override
            public void onPositionChange(int y) {
                Log.d("DemoActivity", "BottomNavigation Position: " + y);
            }
        });
        bottomNavigation.addItem(item1);
        bottomNavigation.addItem(item2);
        bottomNavigation.addItem(item3);
        bottomNavigation.addItem(item4);
        bottomNavigation.addItem(item5);

// Set background color
        bottomNavigation.setDefaultBackgroundColor(getResources().getColor(R.color.dark_green));

// Disable the translation inside the CoordinatorLayout
        bottomNavigation.setBehaviorTranslationEnabled(true);
        bottomNavigation.setSelectedBackgroundVisible(true);


// Enable the translation of the FloatingActionButton
        //  bottomNavigation.manageFloatingActionButtonBehavior(floatingActionButton);

// Change colors
        bottomNavigation.setAccentColor(getResources().getColor(R.color.white));
        bottomNavigation.setInactiveColor(Color.parseColor("#000000"));

// Force to tint the drawable (useful for font with icon for example)
        bottomNavigation.setForceTint(true);

// Display color under navigation bar (API 21+)
// Don't forget these lines in your style-v21
// <item name="android:windowTranslucentNavigation">true</item>
// <item name="android:fitsSystemWindows">true</item>
        bottomNavigation.setTranslucentNavigationEnabled(true);

// Manage titles
        bottomNavigation.setTitleState(AHBottomNavigation.TitleState.ALWAYS_SHOW);
        bottomNavigation.setTitleState(AHBottomNavigation.TitleState.ALWAYS_SHOW);
        bottomNavigation.setTitleState(AHBottomNavigation.TitleState.ALWAYS_SHOW);

// Use colored navigation with circle reveal effect
        bottomNavigation.setColored(false);

// Set current item programmatically
        bottomNavigation.setCurrentItem(0);

// Customize notification (title, background, typeface)
//       bottomNavigation.setNotificationBackgroundColor(Color.parseColor("#F63D2B"));
//
//// Add or remove notification for each item
//        bottomNavigation.setNotification("", 3);
// OR
//        AHNotification notification = new AHNotification.Builder()
//                .setText("1")
//                .setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.dark_green))
//                .setTextColor(ContextCompat.getColor(getActivity(), R.color.grey))
//                .build();
//        bottomNavigation.setNotification(notification, 1);

// Set listeners
        bottomNavigation.setOnTabSelectedListener(new AHBottomNavigation.OnTabSelectedListener() {
            @Override
            public boolean onTabSelected(int position, boolean wasSelected) {


                switch (position) {
                    case 0:

                        if (homeFragment == null) {
                            homeFragment = new DashboardFragment();
                        }
                        String tagName = homeFragment.getClass().getName();
                        replaceFragment(homeFragment, tagName);
                        // showOrHideBottomNavigation(true);

                        break;
                    case 1:
                        if (aboutUsFragment == null) {
                            aboutUsFragment = new AboutUsFragment();
                        }
                        String aboutUsFragment_tagName = aboutUsFragment.getClass().getName();
                        replaceFragment(aboutUsFragment, aboutUsFragment_tagName);
                        // showOrHideBottomNavigation(true);
                        break;






                    case 2:
                        goToMyApp(true);


break;
                    case 3:
                        if (user_dashboardFragment == null) {
                            user_dashboardFragment = new User_DashboardFragment();
                        }

                        if (app_sharedpreference.getsharedpref("username", "not").contains("not")) {
                            startActivity(new Intent(HomeActivity.this, LoginDashboard.class));
                        } else {
                            Log.e("hiiii", app_sharedpreference.getsharedpref("username", "not"));
                            String tagName_dashboardFragment = user_dashboardFragment.getClass().getName();
                            replaceFragment(user_dashboardFragment, tagName_dashboardFragment);
                            //showOrHideBottomNavigation(true);
                        }

                        break;

                    case 4:
                        if (contactUsFragment == null) {
                            contactUsFragment = new ContactUsFragment();
                        }
                        String contact_us_fragment = contactUsFragment.getClass().getName();
                        replaceFragment(contactUsFragment, contact_us_fragment);
                        //showOrHideBottomNavigation(true);
                        break;


                }
                // Do something cool here...
                return true;
            }
        });
        bottomNavigation.setOnNavigationPositionListener(new AHBottomNavigation.OnNavigationPositionListener() {
            @Override
            public void onPositionChange(int y) {
                // Manage the new y position
            }
        });

    }


    @TargetApi(Build.VERSION_CODES.M)
    private void setup_scrollview(final NestedScrollView scrollView) {
        if (Build.VERSION.SDK_INT >= 23) {
            // Marshmallow+

            scrollView.setOnScrollChangeListener(new View.OnScrollChangeListener() {
                @Override
                public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                    int pos = scrollView.getChildCount() - 1;
                    if (oldScrollY < scrollY) {

                        showOrHideBottomNavigation(true);
//                    setForceTitleHide(true);


//                    setForceTitleHide(true);


                    } else {
                        showOrHideBottomNavigation(false);
                    }

                    if (oldScrollY == scrollY) {

                        showOrHideBottomNavigation(true);

                    }

                }
            });
        } else {


            scrollView.setOnTouchListener(new View.OnTouchListener() {
                float height;

                @Override
                public boolean onTouch(View v, MotionEvent event) {

                    int action = event.getAction();
                    float height2 = event.getY();
                    if (action == MotionEvent.ACTION_DOWN) {
                        height = height2;
                    } else if (action == MotionEvent.ACTION_UP) {
                        if (this.height > height2) {
                            Log.e("up", "Scrolled up");
                            showOrHideBottomNavigation(false);
                        } else if (this.height < height2) {
                            Log.e("down", "Scrolled down");
                            showOrHideBottomNavigation(true);
                        }
                    }
                    return false;
                }

            });


            // Pre-Marshmallow
        }


    }

    private void setForceTitleHide(boolean forceTitleHide)
    {

        AHBottomNavigation.TitleState state = forceTitleHide ? AHBottomNavigation.TitleState.ALWAYS_HIDE : AHBottomNavigation.TitleState.ALWAYS_SHOW;
        bottomNavigation.setTitleState(state);
    }


    public void showOrHideBottomNavigation(boolean show) {
        if (show) {
            bottomNavigation.restoreBottomNavigation(true);
        } else {
            bottomNavigation.hideBottomNavigation(true);
        }


    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            ArrayList<String> matches = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            if (matches != null && matches.size() > 0) {
                String searchWrd = matches.get(0);
                if (!TextUtils.isEmpty(searchWrd)) {
                    DashboardFragment.searchView.setQuery(searchWrd, false);

                    //DashboardFragment.searchView.setSuggestionsAdapter(getResources().getStringArray(R.array.search_suggestion));


                }
            }

            return;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

public  void double_back_pressed(String type)
{
    if(type.contains("finish")) {
        if (doubleBackToExitPressedOnce) {

            finish();

            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);
    }
    else
    {
        if (doubleBackToExitPressedOnce) {


            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
     //   AndroidUtils.showSnackBar(linearlayout_home,"Please click BACK again to exit"));
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);





    }


}
    public void goToMyApp(boolean googlePlay) {//true if Google Play, false if Amazone Store
        try {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse((googlePlay ? "market://details?id=" : "amzn://apps/android?p=") +getPackageName())));
        } catch (ActivityNotFoundException e1) {
            try {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse((googlePlay ? "http://play.google.com/store/apps/details?id=" : "http://www.amazon.com/gp/mas/dl/android?p=") +getPackageName())));
            } catch (ActivityNotFoundException e2) {
                Toast.makeText(HomeActivity.this, "You don't have any app that can open this link", Toast.LENGTH_SHORT).show();
            }
        }
    }



}


