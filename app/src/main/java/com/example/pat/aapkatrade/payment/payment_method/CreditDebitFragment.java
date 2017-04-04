package com.example.pat.aapkatrade.payment.payment_method;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import com.example.pat.aapkatrade.R;


public class CreditDebitFragment extends Fragment
{

    EditText edtfirst,edtsecond,edtthree, edtfour,edtfive,edtsix,edtseven,edteight,edtnine,edtten,edteleven,edttwelve,edtthirteen,edtfourteen,edtfifteen,edtsixteen;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {

        View view = inflater.inflate(R.layout.fragment_credit_debit, container, false);

        setup_layout(view);


        return view;
    }

    private void setup_layout(View v)
    {

        edtfirst = (EditText) v.findViewById(R.id.edtFirst);

        edtsecond = (EditText) v.findViewById(R.id.edtSecond);

        edtthree = (EditText) v.findViewById(R.id.edtThird);

        edtfour = (EditText) v.findViewById(R.id.edtFour);

        edtfive =(EditText) v.findViewById(R.id.edtFive);

        edtsix = (EditText) v.findViewById(R.id.edtSix);

        edtseven = (EditText) v.findViewById(R.id.edtSeven);

        edteight = (EditText) v.findViewById(R.id.edtEight);

        edtnine = (EditText) v.findViewById(R.id.edtNine);

        edtten =  (EditText) v.findViewById(R.id.edtTen);

        edteleven = (EditText) v.findViewById(R.id.edtEleven);

        edttwelve = (EditText) v.findViewById(R.id.edtTwelve);

        edtthirteen = (EditText) v.findViewById(R.id.edtThirteen);

        edtfourteen = (EditText) v.findViewById(R.id.edtFourteen);

        edtfifteen = (EditText) v.findViewById(R.id.edtFifteen);

        edtsixteen = (EditText) v.findViewById(R.id.edtSixteen);


        edtfirst.addTextChangedListener(new TextWatcher()
        {
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {
                // TODO Auto-generated method stub
                if (edtfirst.getText().toString().length() == 1)     //size as per your requirement
                {
                    edtsecond.requestFocus();
                }
            }
            public void beforeTextChanged(CharSequence s, int start, int count, int after)
            {
                // TODO Auto-generated method stub
            }

            public void afterTextChanged(Editable s)
            {
                // TODO Auto-generated method stub
            }

        });



        edtsecond.addTextChangedListener(new TextWatcher() {

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // TODO Auto-generated method stub
                if (edtsecond.getText().toString().length() == 1)     //size as per your requirement
                {
                    edtthree.requestFocus();
                }
            }

            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
                // TODO Auto-generated method stub

                edtfirst.requestFocus();
            }

            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub
            }

        });

        edtthree.addTextChangedListener(new TextWatcher() {

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // TODO Auto-generated method stub
                if (edtthree.getText().toString().length() == 1)     //size as per your requirement
                {
                    edtfour.requestFocus();
                }
            }

            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
                // TODO Auto-generated method stub
                edtsecond.requestFocus();


            }

            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub
            }

        });


        edtfour.addTextChangedListener(new TextWatcher() {

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // TODO Auto-generated method stub
                if (edtfour.getText().toString().length() == 1)     //size as per your requirement
                {
                    edtfive.requestFocus();
                }
            }

            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
                // TODO Auto-generated method stub
                edtthree.requestFocus();
            }

            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub
            }

        });



        edtfive.addTextChangedListener(new TextWatcher() {

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // TODO Auto-generated method stub
                if (edtfive.getText().toString().length() == 1)     //size as per your requirement
                {
                    edtsix.requestFocus();
                }
            }

            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
                // TODO Auto-generated method stub
                edtfour.requestFocus();
            }

            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub
            }

        });

        edtsix.addTextChangedListener(new TextWatcher() {

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // TODO Auto-generated method stub
                if (edtsix.getText().toString().length() == 1)     //size as per your requirement
                {
                    edtseven.requestFocus();
                }
            }

            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
                // TODO Auto-generated method stub
                edtfive.requestFocus();
            }

            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub
            }

        });



        edtseven.addTextChangedListener(new TextWatcher() {

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // TODO Auto-generated method stub
                if (edtseven.getText().toString().length() == 1)     //size as per your requirement
                {
                    edteight.requestFocus();
                }
            }

            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
                // TODO Auto-generated method stub
                edtsix.requestFocus();
            }

            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub
            }

        });


        edteight.addTextChangedListener(new TextWatcher() {

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // TODO Auto-generated method stub
                if (edteight.getText().toString().length() == 1)     //size as per your requirement
                {
                    edtnine.requestFocus();
                }
            }

            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
                // TODO Auto-generated method stub
                edtseven.requestFocus();
            }

            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub
            }

        });


        edtnine.addTextChangedListener(new TextWatcher() {

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // TODO Auto-generated method stub
                if (edtnine.getText().toString().length() == 1)     //size as per your requirement
                {
                    edtten.requestFocus();
                }
            }

            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
                // TODO Auto-generated method stub
                edteight.requestFocus();
            }

            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub
            }

        });


        edtten.addTextChangedListener(new TextWatcher() {

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // TODO Auto-generated method stub
                if (edtten.getText().toString().length() == 1)     //size as per your requirement
                {
                    edteleven.requestFocus();
                }
            }

            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
                // TODO Auto-generated method stub
                edtnine.requestFocus();
            }

            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub
            }

        });


        edteleven.addTextChangedListener(new TextWatcher() {

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // TODO Auto-generated method stub
                if (edteleven.getText().toString().length() == 1)     //size as per your requirement
                {
                    edttwelve.requestFocus();
                }
            }

            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
                // TODO Auto-generated method stub
                edtten.requestFocus();
            }

            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub
            }

        });



        edttwelve.addTextChangedListener(new TextWatcher() {

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // TODO Auto-generated method stub
                if (edttwelve.getText().toString().length() == 1)     //size as per your requirement
                {
                    edtthirteen.requestFocus();
                }
            }

            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
                // TODO Auto-generated method stub
                edteleven.requestFocus();
            }

            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub
            }

        });

        edtthirteen.addTextChangedListener(new TextWatcher() {

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // TODO Auto-generated method stub
                if (edtthirteen.getText().toString().length() == 1)     //size as per your requirement
                {
                    edtfourteen.requestFocus();
                }
            }

            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
                // TODO Auto-generated method stub
                edttwelve.requestFocus();
            }

            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub
            }

        });


        edtfourteen.addTextChangedListener(new TextWatcher() {

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // TODO Auto-generated method stub
                if (edtfourteen.getText().toString().length() == 1)     //size as per your requirement
                {
                    edtfifteen.requestFocus();
                }
            }

            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
                // TODO Auto-generated method stub
                edtthirteen.requestFocus();
            }

            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub
            }

        });

        edtfifteen.addTextChangedListener(new TextWatcher()
        {

            public void onTextChanged(CharSequence s, int start, int before, int count)
            {
                // TODO Auto-generated method stub
                if (edtfifteen.getText().toString().length() == 1)     //size as per your requirement
                {
                    edtsixteen.requestFocus();
                }
            }

            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
                // TODO Auto-generated method stub
                edtfourteen.requestFocus();
            }

            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub
            }

        });

        edtsixteen.addTextChangedListener(new TextWatcher()
        {

            public void onTextChanged(CharSequence s, int start, int before, int count)
            {
                // TODO Auto-generated method stub

            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after)
            {
                // TODO Auto-generated method stub
                edtfifteen.requestFocus();
            }

            public void afterTextChanged(Editable s)
            {
                // TODO Auto-generated method stub
            }

        });





    }



}
