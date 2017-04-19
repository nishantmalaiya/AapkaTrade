package com.example.pat.aapkatrade.user_dashboard.add_product;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.pat.aapkatrade.Home.navigation.entity.CategoryHome;
import com.example.pat.aapkatrade.Home.navigation.entity.SubCategory;
import com.example.pat.aapkatrade.Home.registration.entity.City;
import com.example.pat.aapkatrade.Home.registration.spinner_adapter.SpCityAdapter;
import com.example.pat.aapkatrade.R;
import com.example.pat.aapkatrade.general.AppSharedPreference;
import com.example.pat.aapkatrade.general.Utils.AndroidUtils;
import com.example.pat.aapkatrade.general.Utils.ImageUtils;
import com.example.pat.aapkatrade.general.Utils.adapter.CustomSimpleListAdapter;
import com.example.pat.aapkatrade.general.progressbar.ProgressBarHandler;
import com.example.pat.aapkatrade.location.AddressEnum;
import com.example.pat.aapkatrade.location.GeoCoderAddress;
import com.example.pat.aapkatrade.user_dashboard.addcompany.CompanyData;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;

public class AddProductFragment extends Fragment {

    private LinearLayout fragment_add_product_container, uploadViewRootForCompanyName, uploadViewForCompanyName;
    private Context context;
    private TextView uploadMsgForCompanyName;
    private Spinner spCompanyName, spSubCategory, spCategory, spState, spCity, spServiceType;
    private EditText etAreaLocation, etPinCode;
    private ArrayList<CompanyData> companyNameArrayList = new ArrayList<>();
    private ArrayList<String> serviceTypes = new ArrayList<>();
    private ArrayList<CategoryHome> listDataHeader = new ArrayList<>();
    private ArrayList<SubCategory> listDataChild = new ArrayList<>();
    private ArrayList<String> stateList = new ArrayList<>();
    private ArrayList<City> cityList = new ArrayList<>();
    private AppSharedPreference appSharedPreference;
    private ProgressBarHandler progressBar;
    private String companyID = "", categoryID = "", subCategoryID = "", cityID = "";
    private GeoCoderAddress geoCoderAddress;
    private RecyclerView companyPhotoRecyclerView;
    private ProductImages productImagesAdapter;
    LinearLayoutManager layoutManager;
    File docFile = new File("");
    ArrayList<Bitmap> multipleImagesArrayList;
    public ArrayList<ProductImagesData> productImageArrayList = new ArrayList<>();
    private int pageNumber;
    private boolean isLast = false;


    public static AddProductFragment newInstance(int page, boolean isLast) {
        Bundle args = new Bundle();
        args.putInt("page", page);
        if (isLast)
            args.putBoolean("isLast", true);
        AddProductFragment fragment = new AddProductFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_product, container, false);
        initView(view);
        Bundle bundle = getArguments();
        if (bundle != null) {
            pageNumber = bundle.getInt("page");
        }

        setUpTab(pageNumber, view);
        return view;
    }

    private void setUpTab(int pageNumber, View view) {
        if (pageNumber == 1) {
            AndroidUtils.showErrorLog(context, "Fragment1", pageNumber);
            view.findViewById(R.id.content_add_product_company_detail).setVisibility(View.VISIBLE);
            setUpCompanyNameSpinner();
            setUpServiceTypeSpinner();
            getCategory(view);
            getState(view);
            getAreaLocation();
            setupRecyclerView();
            uploadViewForCompanyName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    picPhoto();
                }
            });
        } else if (pageNumber == 3) {
            AndroidUtils.showErrorLog(context, "Fragment3", pageNumber);
            view.findViewById(R.id.content_add_product_company_detail).setVisibility(View.GONE);
        } else if(isLast){
            AndroidUtils.showErrorLog(context, "Fragment isLast", pageNumber);
        }
    }

    private void initView(View view) {
        context = getContext();

        fragment_add_product_container = (LinearLayout) view.findViewById(R.id.fragment_add_product_container);
        progressBar = new ProgressBarHandler(context);
        appSharedPreference = new AppSharedPreference(context);


        companyPhotoRecyclerView = (RecyclerView) view.findViewById(R.id.companyPhotoRecyclerView);
        uploadViewRootForCompanyName = (LinearLayout) view.findViewById(R.id.upload_image_layout).findViewById(R.id.uploadViewRoot);
        uploadViewForCompanyName = (LinearLayout) view.findViewById(R.id.upload_image_layout).findViewById(R.id.uploadView);
        uploadMsgForCompanyName = (TextView) view.findViewById(R.id.upload_image_layout).findViewById(R.id.uploadMsg);
        uploadMsgForCompanyName.setText("Upload Company/Shop Photos");
        spCompanyName = (Spinner) view.findViewById(R.id.spCompanyName);
        spServiceType = (Spinner) view.findViewById(R.id.sp_service_type);
        spCategory = (Spinner) view.findViewById(R.id.spCategory);
        spSubCategory = (Spinner) view.findViewById(R.id.spSubCategory);
        spState = (Spinner) view.findViewById(R.id.spState);
        spCity = (Spinner) view.findViewById(R.id.spCity);

        etAreaLocation = (EditText) view.findViewById(R.id.etAreaLocation);
        etPinCode = (EditText) view.findViewById(R.id.et_pincode);


    }


    void picPhoto() {
        String str[] = new String[]{"Camera", "Gallery"};
        new AlertDialog.Builder(context).setItems(str,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        performImgPicAction(which);
                    }
                }).show();

    }

    void performImgPicAction(int which) {
        Intent in;
        if (which == 1) {
            in = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            in.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
            in.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(Intent.createChooser(in, "Select Multiple Picture From Gallery"), 11);
        } else {

            in = new Intent();
            in.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(Intent.createChooser(in, "Capture Image from Camera"), 10);
        }


    }

    private void setUpCompanyNameSpinner() {
        progressBar.show();
        Log.e("company result", appSharedPreference.getsharedpref("userid", "0"));
        Ion.with(context)
                .load("http://aapkatrade.com/slim/dropdown")
                .setHeader("authorization", "xvfdbgfdhbfdhtrh54654h54ygdgerwer3")
                .setBodyParameter("authorization", "xvfdbgfdhbfdhtrh54654h54ygdgerwer3")
                .setBodyParameter("type", "company")
                .setBodyParameter("id", appSharedPreference.getsharedpref("userid", "0"))
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {
                        progressBar.hide();
                        Log.e("company result", result == null ? "state data found" : result.toString());

                        if (result != null) {

                            JsonArray jsonResultArray = result.getAsJsonArray("result");
                            if (jsonResultArray != null) {
                                companyNameArrayList = new ArrayList<>();
                                companyNameArrayList.add(new CompanyData("Please Select Company Name", "-1"))
                                ;
                                for (int i = 0; i < jsonResultArray.size(); i++) {
                                    JsonObject jsonObject = (JsonObject) jsonResultArray.get(i);
                                    CompanyData companyData = new CompanyData(jsonObject.get("name").getAsString(), jsonObject.get("id").getAsString());
                                    companyNameArrayList.add(companyData);
                                }
                                CustomSimpleListAdapter adapter = new CustomSimpleListAdapter(context, companyNameArrayList);
                                spCompanyName.setAdapter(adapter);
                                spCompanyName.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    @Override
                                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                                        if (position > 0) {
                                            companyID = companyNameArrayList.get(position).getCompanyId();
                                        }
                                    }

                                    @Override
                                    public void onNothingSelected(AdapterView<?> parent) {


                                    }
                                });
                            } else {
                                showMessage("Company Data Not Found");
                            }

                        } else {
                            showMessage("Company Not Found");
                        }
                    }
                });


    }

    private void setUpServiceTypeSpinner() {
        serviceTypes = new ArrayList<>();

        serviceTypes.add("Select Service Type");
        serviceTypes.add("Service Enquiry");
        serviceTypes.add("Sell");

        CustomSimpleListAdapter adapter_spinner_service_type = new CustomSimpleListAdapter(context, serviceTypes);
        spServiceType.setAdapter(adapter_spinner_service_type);
    }

    private void getCategory(final View v) {
        listDataChild.clear();
        listDataHeader.clear();
        progressBar.show();
        Log.e("data", "getCategory Entered");
        Ion.with(context)
                .load("http://aapkatrade.com/slim/dropdown")
                .setHeader("authorization", "xvfdbgfdhbfdhtrh54654h54ygdgerwer3")
                .setBodyParameter("authorization", "xvfdbgfdhbfdhtrh54654h54ygdgerwer3")
                .setBodyParameter("type", "category")

                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject data) {
                        progressBar.hide();
//                        Log.e("data", "getCategory result" + data.toString());
                        if (data != null) {
                            JsonObject jsonObject = data.getAsJsonObject();
                            JsonArray jsonResultArray = jsonObject.getAsJsonArray("result");

                            listDataHeader = new ArrayList<>();
                            listDataChild.add(new SubCategory("-1", "Please Select SubCategory"));

                            listDataHeader.add(new CategoryHome("-1", "Please Select Category", "", listDataChild));
                            for (int i = 0; i < jsonResultArray.size(); i++) {
                                JsonObject jsonObject1 = (JsonObject) jsonResultArray.get(i);
                                JsonArray json_subcategory = jsonObject1.getAsJsonArray("subcategory");

                                if (json_subcategory != null) {
                                    listDataChild = new ArrayList<>();
                                    for (int k = 0; k < json_subcategory.size(); k++) {
                                        JsonObject jsonObject_subcategory = (JsonObject) json_subcategory.get(k);
                                        SubCategory subCategory = new SubCategory(jsonObject_subcategory.get("id").getAsString(), jsonObject_subcategory.get("name").getAsString());
                                        listDataChild.add(subCategory);
                                    }
                                    CategoryHome categoryHome = new CategoryHome(jsonObject1.get("id").getAsString(), jsonObject1.get("name").getAsString(), jsonObject1.get("icon").getAsString(), listDataChild);
                                    listDataHeader.add(categoryHome);
                                    Log.e("data", categoryHome.toString());

                                }
                            }
                            setCategoryAdapter(v);
                        }

                    }
                });
    }

    private void setCategoryAdapter(final View v) {
        Log.e("data", this.listDataHeader.toString());
        CustomSimpleListAdapter categoriesAdapter = new CustomSimpleListAdapter(context, this.listDataHeader);
        spCategory.setAdapter(categoriesAdapter);

        spCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position > 0) {
                    categoryID = listDataHeader.get(position).getCategoryId();
                    listDataChild = new ArrayList<>();
                    listDataChild = listDataHeader.get(position).getSubCategoryList();
                    if (listDataChild.size() > 0) {
                        CustomSimpleListAdapter adapter = new CustomSimpleListAdapter(context, listDataChild);
                        spSubCategory.setAdapter(adapter);
                        v.findViewById(R.id.text_input_subcategory).setVisibility(View.VISIBLE);
                        spSubCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                if (position >= 0) {
                                    subCategoryID = listDataChild.get(position).subCategoryId;


//                                    call_dynamic_formdata_webservice(categoryID, subCategoryID);

                                }
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {

                            }
                        });
                    } else {
                        listDataChild = new ArrayList<>();
                        listDataChild.add(new SubCategory("0", "No SubCategory Found"));
                        CustomSimpleListAdapter adapter = new CustomSimpleListAdapter(context, listDataChild);
                        spSubCategory.setAdapter(adapter);
                    }
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    private void getState(final View v) {
        v.findViewById(R.id.input_layout_city).setVisibility(View.GONE);
        stateList = new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.state_list)));

        CustomSimpleListAdapter stateAdapter = new CustomSimpleListAdapter(context, stateList);
        spState.setAdapter(stateAdapter);


        spState.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {

                cityList = new ArrayList<>();
                AndroidUtils.showErrorLog(context, "State Id is ::::::::  " + position);
                if (position > 0)
                    getCity(v, String.valueOf(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void getCity(View v, String stateId) {
        v.findViewById(R.id.input_layout_city).setVisibility(View.VISIBLE);
        progressBar.show();
        Ion.with(context)
                .load("http://aapkatrade.com/slim/dropdown")
                .setHeader("authorization", "xvfdbgfdhbfdhtrh54654h54ygdgerwer3")
                .setBodyParameter("authorization", "xvfdbgfdhbfdhtrh54654h54ygdgerwer3")
                .setBodyParameter("type", "city")
                .setBodyParameter("id", stateId)
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {
                        progressBar.hide();
                        Log.e("city result ", result == null ? "null" : result.toString());

                        if (result != null) {
                            JsonArray jsonResultArray = result.getAsJsonArray("result");

                            City cityEntity_init = new City("-1", "Please Select City");
                            cityList.add(cityEntity_init);

                            for (int i = 0; i < jsonResultArray.size(); i++) {
                                JsonObject jsonObject1 = (JsonObject) jsonResultArray.get(i);
                                City cityEntity = new City(jsonObject1.get("id").getAsString(), jsonObject1.get("name").getAsString());
                                cityList.add(cityEntity);
                            }

                            SpCityAdapter spCityAdapter = new SpCityAdapter(context, cityList);
                            spCity.setAdapter(spCityAdapter);

                            spCity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                    cityID = cityList.get(position).cityId;
//                        if (!(Integer.parseInt(cityID) > 0)) {
//                            showmessage("Please Select City");
//                        }
                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> parent) {

                                }
                            });
                        } else {
                            showMessage("No City Found");
                        }
                    }

                });

    }

    private void getAreaLocation() {
        etAreaLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    progressBar.show();
                    Intent intent =
                            new PlaceAutocomplete
                                    .IntentBuilder(PlaceAutocomplete.MODE_FULLSCREEN)
                                    .build(getActivity());
                    startActivityForResult(intent, 1);


                } catch (GooglePlayServicesRepairableException e) {

                    progressBar.hide();
                    AndroidUtils.showErrorLog(context, "GooglePlayServices" + e.toString());
                } catch (GooglePlayServicesNotAvailableException e) {
                    progressBar.hide();
                    AndroidUtils.showErrorLog(context, "GooglePlayServices_not" + e.toString());
                }


            }


        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        AndroidUtils.showErrorLog(context, "hi", "requestCode : " + requestCode + "result code : " + resultCode);

        if (data != null) {

            multipleImagesArrayList = new ArrayList<>();

            if (requestCode == 11) {
                if (data.getClipData() != null) {

                    data.getClipData().getItemCount();

                    for (int k = 0; k < 4; k++) {

                        Uri selectedImage = data.getClipData().getItemAt(k).getUri();

                        Bitmap bitmap = null;
                        try {
                            bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), selectedImage);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        multipleImagesArrayList.add(bitmap);


                        Log.e("doc", "***START.****** ");
                        if (ImageUtils.sizeOf(bitmap) > 2048) {
                            Log.e("doc", "if doc file path 1");

                            docFile = getFile(ImageUtils.resize(bitmap, bitmap.getHeight() / 2, bitmap.getWidth() / 2));
                            Log.e("doc", "if doc file path" + docFile.getAbsolutePath());
                        } else {

                            Log.e("doc", " else doc file path 1");
                            docFile = getFile(bitmap);
                            Log.e("doc", " else doc file path" + docFile.getAbsolutePath());
                        }

                        productImageArrayList.add(new ProductImagesData(docFile.getAbsolutePath(), ""));
                        Log.e("docfile", docFile.getAbsolutePath());


                        productImagesAdapter.notifyDataSetChanged();
                        if (productImageArrayList.size() > 0) {
                            companyPhotoRecyclerView.setVisibility(View.VISIBLE);

                        }


                    }

                } else {


                    try {
                        InputStream inputStream = getActivity().getContentResolver().openInputStream(data.getData());
                        Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                        Uri tempUri = getImageUri(context, bitmap);

                        // CALL THIS METHOD TO GET THE ACTUAL PATH
                        File finalFile = new File(getRealPathFromURI(tempUri));

                        productImageArrayList.add(new ProductImagesData(finalFile.getAbsolutePath(), ""));
                        Log.e("docfile", finalFile.getAbsolutePath());

                        productImagesAdapter.notifyDataSetChanged();
                        if (productImageArrayList.size() > 0) {
                            companyPhotoRecyclerView.setVisibility(View.VISIBLE);

                        }

                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }

                }
            }
            if (requestCode == 10) {

                Log.e("docfile10", "Sachin sdnsdfjsd fsdjfsd fnmsdabf");

                Bitmap photo = (Bitmap) data.getExtras().get("data");

                Uri tempUri = getImageUri(context, photo);

                // CALL THIS METHOD TO GET THE ACTUAL PATH
                File finalFile = new File(getRealPathFromURI(tempUri));

                productImageArrayList.add(new ProductImagesData(finalFile.getAbsolutePath(), ""));
                Log.e("docfile", finalFile.getAbsolutePath());

                productImagesAdapter.notifyDataSetChanged();
                companyPhotoRecyclerView.setVisibility(View.VISIBLE);

            }


            if (requestCode == 1) {
                if (resultCode == RESULT_OK) {
                    Place place = PlaceAutocomplete.getPlace(context, data);
                    geoCoderAddress = new GeoCoderAddress(context, place.getLatLng().latitude, place.getLatLng().longitude);

                    try {


                        String stateName = geoCoderAddress.get_state_name().get(AddressEnum.STATE);
                        String cityName = geoCoderAddress.get_state_name().get(AddressEnum.CITY);
                        String pinCode = geoCoderAddress.get_state_name().get(AddressEnum.PINCODE);
                        String address = geoCoderAddress.get_state_name().get(AddressEnum.ADDRESS);


                        etPinCode.setText(pinCode);
                        setStateSelection(stateName);
                        setCitySelection(cityName);
                        Log.e("stateName", stateName + "**" + cityName + "**" + pinCode + "**" + address);
                    } catch (Exception e) {
                        Log.e("Exception_geocoder", e.toString());

                    }

                    etAreaLocation.setText(place.getAddress());


                    AndroidUtils.showErrorLog(context, "Tag", place.toString());


                } else if (resultCode == PlaceAutocomplete.RESULT_ERROR) {
                    progressBar.hide();
                    Status status = PlaceAutocomplete.getStatus(context, data);
                    // TODO: Handle the error.
                    Log.e("Tag", status.getStatusMessage());

                } else if (resultCode == RESULT_CANCELED) {
                    progressBar.hide();
                }
            }
        }

    }

    private void setCitySelection(String cityName) {

        for (int i = 0; i < cityList.size(); i++) {
            if (cityList.get(i).cityName.equalsIgnoreCase(cityName)) {
                spCity.setSelection(i);
            }
        }
    }

    private void setStateSelection(String a) {

        progressBar.show();
        Log.e("statelist_state", stateList.toString() + "" + a);

        int currentStateIndex = 0;
        for (int i = 0; i < stateList.size(); i++) {

            if (a.equals(stateList.get(i))) {

                currentStateIndex = i;
                Log.e("current_state_index", currentStateIndex + "");
            }
        }
        Log.e("current_state_index2", currentStateIndex + "");
        spState.setSelection(currentStateIndex);
        progressBar.hide();


    }

    private File getFile(Bitmap photo) {
        Uri tempUri = null;
        if (photo != null) {
            tempUri = getImageUri(context, photo);
        }
        File finalFile = new File(getRealPathFromURI(tempUri));
        Log.e("data", getRealPathFromURI(tempUri));

        return finalFile;
    }

    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }

    public String getRealPathFromURI(Uri uri) {
        Cursor cursor = null;
        int idx = 0;
        if (uri != null) {
            cursor = context.getContentResolver().query(uri, null, null, null, null);
            assert cursor != null;
            cursor.moveToFirst();
            idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
        }
        return cursor.getString(idx);
    }


    private void setupRecyclerView() {
        productImagesAdapter = new ProductImages(context, productImageArrayList);
        layoutManager = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
        companyPhotoRecyclerView.setLayoutManager(layoutManager);
        companyPhotoRecyclerView.setAdapter(productImagesAdapter);


    }


    private void showMessage(String message) {
        AndroidUtils.showSnackBar(fragment_add_product_container, message);
    }

}
