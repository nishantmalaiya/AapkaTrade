package com.example.pat.aapkatrade.payment.payment_method;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.RelativeLayout;
import android.widget.Spinner;

import com.example.pat.aapkatrade.Home.registration.entity.State;
import com.example.pat.aapkatrade.Home.registration.spinner_adapter.SpStateAdapter;
import com.example.pat.aapkatrade.R;
import com.example.pat.aapkatrade.general.progressbar.ProgressBarHandler;
import com.example.pat.aapkatrade.user_dashboard.companylist.CompanyData;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import java.util.ArrayList;


public class NetBankingFragment extends Fragment
{

    Spinner spState;
    private ArrayList<State> stateList = new ArrayList<>();
    public  ArrayList<BankData> bankdatas = new ArrayList<>();
    private ProgressBarHandler progressBar;
    RecyclerView recyclerBankList;
    BankAdapter companyListAdapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {

        View view =  inflater.inflate(R.layout.fragment_net_banking, container, false);

        progressBar = new ProgressBarHandler(getActivity());

        setup_layout(view);

        return view;
    }

    private void setup_layout(View v)
    {

        spState = (Spinner) v.findViewById(R.id.spBussinessCategory);

        getState();

        recyclerBankList = (RecyclerView) v.findViewById(R.id.recyclerBankList);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);

        companyListAdapter = new BankAdapter(getActivity(), bankdatas);

        recyclerBankList.setAdapter(companyListAdapter);

        recyclerBankList.setLayoutManager(layoutManager);

    }



    public void getState()
    {
        Log.e("state result ", "getState started");
        progressBar.show();

        Ion.with(getActivity())
                .load(getResources().getString(R.string.webservice_base_url)+"/dropdown")
                .setHeader("authorization", "xvfdbgfdhbfdhtrh54654h54ygdgerwer3")
                .setBodyParameter("authorization", "xvfdbgfdhbfdhtrh54654h54ygdgerwer3")
                .setBodyParameter("type", "state")
                .setBodyParameter("id", "101")
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>()
                {
                    @Override
                    public void onCompleted(Exception e, JsonObject result)
                    {
                        progressBar.hide();
                        Log.e("state result ", result == null ? "state data found" : result.toString());

                        if (result != null)
                        {

                            JsonArray jsonResultArray = result.getAsJsonArray("result");
                            stateList = new ArrayList<>();
                            State stateEntity_init = new State("-1", "Please Select State");
                            stateList.add(stateEntity_init);

                            for (int i = 0; i < jsonResultArray.size(); i++)
                            {
                                JsonObject jsonObject1 = (JsonObject) jsonResultArray.get(i);
                                State stateEntity = new State(jsonObject1.get("id").getAsString(), jsonObject1.get("name").getAsString());
                                stateList.add(stateEntity);
                            }
                            SpStateAdapter spStateAdapter = new SpStateAdapter(getActivity(), stateList);
                            spState.setAdapter(spStateAdapter);
                            spStateAdapter.notifyDataSetChanged();
                            spState.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                                @Override
                                public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
                                {


                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> parent)
                                {

                                }
                            });
                        }
                        else
                        {
                           // showMessage("State Not Found");
                        }
                    }
                });
    }


}
