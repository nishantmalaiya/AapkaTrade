package com.example.pat.aapkatrade.user_dashboard.address.add_address;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import com.example.pat.aapkatrade.R;
import com.example.pat.aapkatrade.general.AppSharedPreference;
import com.example.pat.aapkatrade.general.Call_webservice;
import com.example.pat.aapkatrade.general.TaskCompleteReminder;
import com.example.pat.aapkatrade.general.progressbar.ProgressBarHandler;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import java.util.ArrayList;
import java.util.Arrays;


public class AddAddressActivity extends AppCompatActivity
{

    ArrayList<String> stateList = new ArrayList<>();
    TextView tvTitle;
    AppSharedPreference app_sharedpreference;
    String userid,firstName,lastName,address,mobile,state_id;
    EditText etFirstName,etLastName,etMobileNo,etAddress;
    Button buttonSave;
    Spinner spState;
    RelativeLayout activity_add_address;
    ProgressBarHandler progress_handler;



    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_add_address);

        stateList =  new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.state_list)));

        progress_handler = new ProgressBarHandler(this);

        app_sharedpreference = new AppSharedPreference(getApplicationContext());

        userid = app_sharedpreference.getsharedpref("userid", "");

        firstName = app_sharedpreference.getsharedpref("username", "");

        lastName = app_sharedpreference.getsharedpref("lname", "");

        mobile = app_sharedpreference.getsharedpref("mobile", "");

        address = app_sharedpreference.getsharedpref("address", "");

        state_id= app_sharedpreference.getsharedpref("state_id", "");

        setuptoolbar();

        setup_layout();


    }

    private void setup_layout()
    {
        activity_add_address = (RelativeLayout)  findViewById(R.id.activity_add_address);

        spState = (Spinner) findViewById(R.id.spStateCategory);


        spState.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id)
            {
                // your code here
                app_sharedpreference.setsharedpref("state_id",  String.valueOf(position));
                state_id= app_sharedpreference.getsharedpref("state_id", "");
                spState.setSelection(Integer.valueOf(state_id));

            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here

            }

        });

        ArrayAdapter spinnerArrayAdapter = new ArrayAdapter(AddAddressActivity.this,R.layout.white_textcolor_spinner,stateList);
        spinnerArrayAdapter.setDropDownViewResource(R.layout.white_textcolor_spinner);
        spState.setAdapter(spinnerArrayAdapter);

        spState.setSelection(Integer.valueOf(state_id));

        etFirstName = (EditText) findViewById(R.id.etFirstName);

        etLastName = (EditText) findViewById(R.id.etLastName);

        etMobileNo = (EditText) findViewById(R.id.etMobileNo);

        etAddress = (EditText) findViewById(R.id.etAddress);

        buttonSave = (Button) findViewById(R.id.buttonSave);

        etFirstName.setText(firstName);

        etLastName.setText(lastName);

        etMobileNo.setText(mobile);

        etAddress.setText(address);

        buttonSave.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                callAddCompanyWebService(userid, etFirstName.getText().toString(),etLastName.getText().toString(),etAddress.getText().toString());
            }
        });


    }

    private void callAddCompanyWebService(String userId, final String firstName,final String lName , final String address)
    {
        progress_handler.show();
        Ion.with(AddAddressActivity.this)
                .load((getResources().getString(R.string.webservice_base_url))+"/edit_shipping_address")
                .setHeader("authorization", "xvfdbgfdhbfdhtrh54654h54ygdgerwer3")
                .setBodyParameter("authorization", "xvfdbgfdhbfdhtrh54654h54ygdgerwer3")
                .setBodyParameter("name", firstName)
                .setBodyParameter("lastname",lName)
                .setBodyParameter("address", address)
                .setBodyParameter("state_id", String.valueOf(spState.getSelectedItemPosition()))
                .setBodyParameter("user_id", userId)
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>()
                {

                    @Override
                    public void onCompleted(Exception e, JsonObject result)
                    {


                        if (result == null){

                            progress_handler.hide();
                        }
                        else
                        {
                            JsonObject jsonObject = result.getAsJsonObject();
                            String message = jsonObject.get("message").getAsString();

                            System.out.println("message---------"+message);

                            if (message.equals("Updated Successfully!"))
                            {
                                app_sharedpreference.setsharedpref("username", firstName);
                                app_sharedpreference.setsharedpref("lname", lName);
                                app_sharedpreference.setsharedpref("address", address);
                                app_sharedpreference.setsharedpref("state_id", String.valueOf(spState.getSelectedItemPosition()));
                                progress_handler.hide();
                                Toast.makeText(getApplicationContext(),"Updated Successfully!",Toast.LENGTH_SHORT).show();

                               /* Intent companylist = new Intent(AddAddressActivity.this, .class);
                                startActivity(companylist);*/

                                finish();
                            }
                            else
                            {
                                progress_handler.hide();
                                Toast.makeText(getApplicationContext(),message,Toast.LENGTH_SHORT).show();
                            }

                        }
                    }
                });
    }


    private void setuptoolbar()
    {

        tvTitle = (TextView) findViewById(R.id.tvTitle);
        tvTitle.setText("Shipping Address");

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Shipping Address");
        //getSupportActionBar().setIcon(R.drawable.home_logo);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.button_menu, menu);
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


    /*public void getState() {

        HashMap<String, String> webservice_body_parameter = new HashMap<>();
        webservice_body_parameter.put("authorization", "xvfdbgfdhbfdhtrh54654h54ygdgerwer3");
        webservice_body_parameter.put("type", "state");
        webservice_body_parameter.put("id", "101");//country id fixed 101 for India

        //   Call_webservice.getcountrystatedata(AddAddressActivity.this, "state", getResources().getString(R.string.webservice_base_url) + "/dropdown", webservice_body_parameter, webservice_header_type);

        Call_webservice.taskCompleteReminder = new TaskCompleteReminder() {
            @Override
            public void Taskcomplete(JsonObject state_data_webservice)
            {

                System.out.println("Data is actualy--------"+stateList.size());



                if (state_data_webservice != null)
                {
                    Log.e("Taskcomplete", "TaskcompleteError" + state_data_webservice.toString());
                    JsonObject jsonObject = state_data_webservice.getAsJsonObject();
                    JsonArray jsonResultArray = jsonObject.getAsJsonArray("result");
                    stateList.clear();
                    State stateEntity_init = new State("-1", "Please Select State");
                    stateList.add(stateEntity_init);

                    for (int i = 0; i < jsonResultArray.size(); i++) {
                        JsonObject jsonObject1 = (JsonObject) jsonResultArray.get(i);
                        State stateEntity = new State(jsonObject1.get("id").getAsString(), jsonObject1.get("name").getAsString());
                        stateList.add(stateEntity);
                    }
                    SpStateAdapter spStateAdapter = new SpStateAdapter(AddAddressActivity.this, stateList);
                    spState.setAdapter(spStateAdapter);

                    spState.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
                        {
                           *//* stateID = stateList.get(position).stateId;
                            cityList = new ArrayList<>();
                            if (position > 0) {
                                getCity(stateList.get(position).stateId);
                            }*//*
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent)
                        {



                        }

                    });
                }
                else
                {

                  //  AndroidUtils.showSnackBar(registrationLayout, "State Not Found");
                }
            }

        };

        System.out.println("Data is actualy--------"+stateList.size());



    }
*/
}
