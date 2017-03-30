package com.example.pat.aapkatrade.map;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.directions.route.AbstractRouting;
import com.directions.route.Route;
import com.directions.route.RouteException;
import com.directions.route.Routing;
import com.directions.route.RoutingListener;
import com.example.pat.aapkatrade.Home.HomeActivity;
import com.example.pat.aapkatrade.R;
import com.example.pat.aapkatrade.general.CheckPermission;
import com.example.pat.aapkatrade.general.LocationManager_check;
import com.example.pat.aapkatrade.general.Utils.AndroidUtils;
import com.example.pat.aapkatrade.general.progressbar.ProgressBarHandler;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class GoogleMapActivity extends AppCompatActivity implements OnMapReadyCallback, RoutingListener {


    private LatLng currentLatLng;
    public GoogleMap mMap;
    public LocationManager locationManager;
    private Context context;

    public Polyline newPolyline;
    private LatLng product_location_lat_lng, latLng;
    private Button search, done;
    private ImageView img_view_travelmode_car, img_view_travelmode_bus, img_view_travelmode_walking, imgview_navigation;

    ProgressBarHandler pg_handler;
    String address = "Address not found";
    public JsonObject doc;
    Marker marker;
    Toolbar toolbar;
    private PlaceAutocompleteFragment autocompleteFragment;
    String TAG = "google_place";
    private float zoomLevel = 10;
    private LocationManager mLocationManager;
    String address_name;
    String drivingmode;
    LinearLayout location_container;
    double source_latitute, source_longitude;
    ArrayList<String> polylineids = new ArrayList<>();
    ArrayList<String> route_distance = new ArrayList<>();
    ArrayList<String> route_timeduration = new ArrayList<>();

    boolean permission_status;
    

    public static TextView tv_travel_duration, travel_time;

    RelativeLayout location_car_selected, location_bus_selected, location_walking_selected;
    private List<Polyline> polylines;
    private static final int[] COLORS = new int[]{R.color.green, R.color.orange, R.color.dark_green};



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        Log.e("time_taken map ",(System.currentTimeMillis()/1000)+"");
        context = GoogleMapActivity.this;
        setContentView(R.layout.activity_map);
        polylines = new ArrayList<>();
        MapFragment googleMap = (MapFragment) getFragmentManager().findFragmentById(R.id.map);
        googleMap.getMapAsync(GoogleMapActivity.this);

        initView();
        setUpToolBar();


        //done = (Button) findViewById(R.id.done_button);


        location_container = (LinearLayout) findViewById(R.id.location_container);
        location_container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    pg_handler.show();
                    Intent intent =
                            new PlaceAutocomplete
                                    .IntentBuilder(PlaceAutocomplete.MODE_FULLSCREEN)
                                    .build(GoogleMapActivity.this);
                    startActivityForResult(intent, 1);


                } catch (GooglePlayServicesRepairableException e) {

                    pg_handler.hide();
                    Log.e("GooglePlayServices", e.toString());
                    // TODO: Handle the error.
                } catch (GooglePlayServicesNotAvailableException e) {
                    pg_handler.hide();
                    Log.e("GooglePlayServices_not", e.toString());
                    // TODO: Handle the error.
                }


            }
        });
    }

    public void initView() {
        pg_handler = new ProgressBarHandler(this);
        Log.e("time_taken map  1",(System.currentTimeMillis()/1000)+"");

        final String product_location = getIntent().getStringExtra("product_location");
        product_location_lat_lng = getLocationFromAddress(GoogleMapActivity.this, product_location);
        Log.e("product_", product_location_lat_lng + "");
        location_walking_selected = (RelativeLayout) findViewById(R.id.location_walking_selected);
        location_car_selected = (RelativeLayout) findViewById(R.id.location_car_selected);
        location_bus_selected = (RelativeLayout) findViewById(R.id.location_bus_selected);
        img_view_travelmode_car = (ImageView) findViewById(R.id.img_view_travelmode_car);
        img_view_travelmode_bus = (ImageView) findViewById(R.id.img_view_travelmode_bus);
        imgview_navigation = (ImageView) findViewById(R.id.img_view_direction);
        img_view_travelmode_walking = (ImageView) findViewById(R.id.img_view_travelmode_walking);
        tv_travel_duration = (TextView) findViewById(R.id.tv_travel_duration);
        travel_time = (TextView) findViewById(R.id.travel_time);
        init_location_elements(Routing.TravelMode.DRIVING);


        img_view_travelmode_car.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                img_view_travelmode_car.setVisibility(View.GONE);
                location_car_selected.setVisibility(View.VISIBLE);
                location_bus_selected.setVisibility(View.GONE);
                location_walking_selected.setVisibility(View.GONE);
                img_view_travelmode_bus.setVisibility(View.VISIBLE);
                img_view_travelmode_walking.setVisibility(View.VISIBLE);
//                img_view_travelmode_car.setImageResource(R.drawable.ic_location_car_white);
                img_view_travelmode_walking.setImageResource(R.drawable.ic_location_walking_white);
                img_view_travelmode_bus.setImageResource(R.drawable.ic_location_bus_white);
                init_location_elements(Routing.TravelMode.DRIVING);
            }
        });


        img_view_travelmode_bus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                img_view_travelmode_bus.setVisibility(View.GONE);
                img_view_travelmode_car.setVisibility(View.VISIBLE);
                img_view_travelmode_walking.setVisibility(View.VISIBLE);

                location_bus_selected.setVisibility(View.VISIBLE);
                location_car_selected.setVisibility(View.GONE);
                //img_view_travelmode_bus.setVisibility();

                location_walking_selected.setVisibility(View.GONE);

                img_view_travelmode_car.setImageResource(R.drawable.ic_location_car_white);
                img_view_travelmode_walking.setImageResource(R.drawable.ic_location_walking_white);
                img_view_travelmode_bus.setImageResource(R.drawable.ic_location_bus_white);
                init_location_elements(Routing.TravelMode.TRANSIT);


            }
        });

        img_view_travelmode_walking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                img_view_travelmode_walking.setVisibility(View.GONE);
                location_car_selected.setVisibility(View.GONE);
                location_bus_selected.setVisibility(View.GONE);
                location_walking_selected.setVisibility(View.VISIBLE);
                img_view_travelmode_car.setVisibility(View.VISIBLE);
                img_view_travelmode_bus.setVisibility(View.VISIBLE);
                // location_walking_selected.setVisibility(View.GONE);


                img_view_travelmode_car.setImageResource(R.drawable.ic_location_car_white);
                img_view_travelmode_walking.setImageResource(R.drawable.ic_location_walking_white);
                img_view_travelmode_bus.setImageResource(R.drawable.ic_location_bus_white);
                init_location_elements(Routing.TravelMode.WALKING);
            }
        });

        imgview_navigation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String format = "geo:0,0?q=" + product_location_lat_lng.latitude + "," + product_location_lat_lng.longitude + "(Product Location)";

                Uri uri = Uri.parse(format);


                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
    }

    private void init_location_elements(AbstractRouting.TravelMode Travelmode) {


        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);

        permission_status = CheckPermission.checkPermissions(GoogleMapActivity.this);
        if (permission_status) {
            LocationManager_check locationManagerCheck = new LocationManager_check(
                    this);
            Location location = null;

            if (locationManagerCheck.isLocationServiceAvailable()) {
                if (locationManagerCheck.getProviderType() == 1)
                    location = locationManager
                            .getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                else if (locationManagerCheck.getProviderType() == 2)
                    location = locationManager
                            .getLastKnownLocation(LocationManager.GPS_PROVIDER);
                GoogleMap.OnMyLocationChangeListener myLocationChangeListener = new GoogleMap.OnMyLocationChangeListener() {
                    @Override
                    public void onMyLocationChange(Location location) {
                        source_latitute = location.getLatitude();
                        source_longitude = location.getLongitude();
                        LatLng loc = new LatLng(location.getLatitude(), location.getLongitude());
                        Log.e("source+source_longitude", source_longitude + "***" + source_latitute);

                        ArrayList<LatLng> currenttoproduct = new ArrayList<>();
                        currenttoproduct.add(loc);
                        currenttoproduct.add(product_location_lat_lng);

                        // handleGetDirectionsResult(currenttoproduct);
                        //mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(loc, 16.0f));
                    }
                };
                // mMap.setOnMyLocationChangeListener(myLocationChangeListener);
//
//
//                mMap.getUiSettings().setRotateGesturesEnabled(true);
                if (location != null) {


                    findDirections(location.getLatitude(), location.getLongitude(), product_location_lat_lng, Travelmode);
                } else {
                    Log.e("location not found", "location not found");

                }

            } else {
                locationManagerCheck.createLocationServiceError(GoogleMapActivity.this);
            }


            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.


                return;
            }


        }


    }


    private void showMessage(String s) {
        Toast.makeText(context, s, Toast.LENGTH_SHORT).show();
    }


    public void onMapReady(GoogleMap map) {

        mMap = map;


        search = (Button) findViewById(R.id.search_button);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mMap.setMyLocationEnabled(true);


    }


    @Override
    protected void onPause() {
        super.onPause();
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        try {
            mLocationManager.removeUpdates(mLocationListener);
            mLocationManager = null;

        } catch (Exception ex) {

        }
    }

    private void setUpToolBar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        ImageView homeIcon = (ImageView) findViewById(R.id.iconHome);
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



    private final LocationListener mLocationListener = new LocationListener() {
        @Override
        public void onLocationChanged(final Location location) {
            //your code here
            try {
                marker.remove();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            latLng = new LatLng(location.getLatitude(), location.getLongitude());
            marker = mMap.addMarker(new MarkerOptions().position(latLng).title(getString(R.string.selected_place)));
            currentLatLng = latLng;
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 18));
            zoomLevel = 18;
            //    setCompleteAddress(latLng);
            mMap.setMyLocationEnabled(true);

            if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            try {
                mLocationManager.removeUpdates(mLocationListener);
                mLocationManager = null;

            } catch (Exception ex) {
                Log.e("Exception_ex", ex.toString());

            }


        }

        @Override
        public void onStatusChanged(String s, int i, Bundle bundle) {

        }

        @Override
        public void onProviderEnabled(String s) {

        }

        @Override
        public void onProviderDisabled(String s) {

        }
    };


    private void setCompleteAddress(LatLng latLng) {

        Geocoder geocoder;
        List<Address> addresses;
        geocoder = new Geocoder(this, Locale.getDefault());
        StringBuilder completeAddress = new StringBuilder();
        try {
            addresses = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
        } catch (Exception ex) {
            return;
        }
        try {
            String address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
            completeAddress.append(address);
        } catch (Exception ex) {
            completeAddress.append("");
        }
        try {
            String city = addresses.get(0).getLocality();
            completeAddress.append(city);

        } catch (Exception ex) {
            completeAddress.append("");

        }
        try {
            String state = addresses.get(0).getAdminArea();
            completeAddress.append(state);
        } catch (Exception ex) {
            completeAddress.append("");
        }
        try {
            String knownName = addresses.get(0).getFeatureName();
            completeAddress.append(knownName);
        } catch (Exception ex) {
            completeAddress.append("");
        }
        try {
            marker.remove();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        marker = mMap.addMarker(new MarkerOptions().position(latLng).title(completeAddress.toString()));
        currentLatLng = latLng;
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoomLevel));
        GoogleMapActivity.this.address = completeAddress.toString();
    }

    public LatLng getLocationFromAddress(Context context, String strAddress) {

        Geocoder coder = new Geocoder(context);
        List<Address> address;
        LatLng p1 = null;

        try {
            address = coder.getFromLocationName(strAddress, 5);
            if (address == null) {
                return null;
            }
            Address location = address.get(0);
            location.getLatitude();
            location.getLongitude();

            p1 = new LatLng(location.getLatitude(), location.getLongitude());

        } catch (Exception ex) {

            ex.printStackTrace();
        }

        return p1;
    }


    private void clearMarker() {
        try {
            mMap.clear();
        } catch (Exception ex) {

        }
    }


    public void findDirections(double fromPositionDoubleLat, double fromPositionDoubleLong, LatLng product_location, AbstractRouting.TravelMode mode) {
        pg_handler.show();

        Routing routing = new Routing.Builder()
                .travelMode(mode)
                .withListener(this)
                .waypoints(new LatLng(fromPositionDoubleLat, fromPositionDoubleLong), product_location)
                .key(getResources().getString(R.string.google_api))
                .alternativeRoutes(true)
                .build();
        routing.execute();


    }


    @Override
    public void onRoutingFailure(RouteException e) {

    }

    @Override
    public void onRoutingStart() {

    }

    @Override
    public void onRoutingSuccess(final ArrayList<Route> route, int shortestRouteIndex) {


        route_distance = new ArrayList<>();
        route_timeduration = new ArrayList<>();
        polylineids = new ArrayList<>();


        pg_handler.hide();


//        CameraUpdate center = CameraUpdateFactory.newLatLng(new LatLng(route.get(0).getPoints().get(route.get(0).getPoints().size()-1).latitude, route.get(0).getPoints().get(route.get(0).getPoints().size()-1).longitude));
//        CameraUpdate zoom = CameraUpdateFactory.zoomTo(16);
//
//        mMap.moveCamera(zoom);

        if (polylines.size() > 0) {
            for (Polyline poly : polylines) {
                poly.remove();
            }
        }

        polylines = new ArrayList<>();

        //add route(s) to the map.
        for (int i = 0; i < route.size(); i++) {
            Log.e("route_distance", route.get(i).getDistanceText());
            Log.e("route_duration", route.get(i).getDurationText());
            route_distance.add(route.get(i).getDistanceText());
            route_timeduration.add(route.get(i).getDurationText());
            //In case of more than 5 alternative routes
            int colorIndex = i % COLORS.length;

            PolylineOptions polyOptions = new PolylineOptions();
            polyOptions.color(getResources().getColor(COLORS[colorIndex]));
            polyOptions.width(10);
            polyOptions.addAll(route.get(i).getPoints());

            Polyline polyline = mMap.addPolyline(polyOptions);
            polylineids.add(polyline.getId());

            polyline.setZIndex(1);
            polyline.setClickable(true);


            polylines.add(polyline);
            travel_time.setText("(" + route_distance.get(0).toString() + ")");

            tv_travel_duration.setText(route_timeduration.get(0).toString());

            route.get(0).getDistanceValue();




        }


        mMap.setOnPolylineClickListener(new GoogleMap.OnPolylineClickListener() {
            @Override
            public void onPolylineClick(Polyline polyline)

            {
                polyline.setColor(R.color.green);
                for (int k = 0; k < polylineids.size(); k++) {


                    if (polylineids.get(k).toString().trim().equals(polyline.getId().toString().trim())) {
                        tv_travel_duration.setText(route_timeduration.get(k).toString());
                        travel_time.setText("(" + route_distance.get(k).toString() + ")");
                        route.get(k).getDistanceValue();

                        Log.e("working", "working");

                        //  polyline.setColor(R.color.green);
                        polyline.setColor(R.color.green);
                        polyline.setZIndex(100);

                    } else {
                        Log.e("polyline_index", polylineids.get(k).toString().trim() + "***********" + polyline.getId().toString().trim());

                        polyline.setZIndex(1);


                    }
//                    if(polylineids.contains(polyline.getId()))
//                    {
//                        polyline.setColor(R.color.green);
//                        polyline.setZIndex(100);
//                        Log.e("polylineids",polylineids.toString());
//                    }
                }


            }
        });

        MarkerOptions marker2 = new MarkerOptions().position(
                new LatLng(route.get(0).getPoints().get(0).latitude, route.get(0).getPoints().get(0).longitude)).icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_my_location_icon)).title(
                "My Location");
        Log.e("size_route", route.get(0).getPoints().get(route.get(0).getPoints().size() - 1).latitude + "***" + (route.get(0).getPoints().size() - 1));


        MarkerOptions marker3 = new MarkerOptions().position(
                product_location_lat_lng).icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_product_location)).title(
                "Product Location");

        mMap.addMarker(marker2);
        mMap.addMarker(marker3);
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(route.get(0).getPoints().get(0).latitude, route.get(0).getPoints().get(0).longitude), 10.0f));

        //mMap.setTrafficEnabled(true);


    }

    @Override
    public void onRoutingCancelled() {

    }




    public interface AddressListner {
        void gotAddress(String address, boolean source);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                pg_handler.hide();
                // retrive the data by using getPlace() method.
                Place place = PlaceAutocomplete.getPlace(this, data);
                Log.e("Tag", "Place: " + place.getAddress() + place.getLatLng());
                Geocoder mGeocoder = new Geocoder(GoogleMapActivity.this, Locale.getDefault());


                findDirections(place.getLatLng().latitude, place.getLatLng().longitude, new LatLng(product_location_lat_lng.latitude, product_location_lat_lng.longitude), AbstractRouting.TravelMode.DRIVING);


            } else if (resultCode == PlaceAutocomplete.RESULT_ERROR) {
                pg_handler.hide();
                Status status = PlaceAutocomplete.getStatus(this, data);
                // TODO: Handle the error.
                Log.e("Tag", status.getStatusMessage());

            } else if (resultCode == RESULT_CANCELED) {
                pg_handler.hide();
                // The user canceled the operation.
            }
        }
    }


}
