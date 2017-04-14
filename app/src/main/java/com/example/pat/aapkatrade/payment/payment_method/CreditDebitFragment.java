package com.example.pat.aapkatrade.payment.payment_method;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.alihafizji.library.CreditCardEditText;
import com.example.pat.aapkatrade.R;

import java.util.ArrayList;
import java.util.List;


public class CreditDebitFragment extends Fragment implements CreditCardEditText.CreditCartEditTextInterface
{


    EditText cardNumberEditText,cardDateEditText,cardCVCEditText;
    private static final int CARD_DATE_TOTAL_SYMBOLS = 5; // size of pattern MM/YY
    private static final int CARD_DATE_TOTAL_DIGITS = 4; // max numbers of digits in pattern: MM + YY
    private static final int CARD_DATE_DIVIDER_MODULO = 3; // means divider position is every 3rd symbol beginning with 1
    private static final int CARD_DATE_DIVIDER_POSITION = CARD_DATE_DIVIDER_MODULO - 1; // means divider position is every 2nd symbol beginning with 0
    private static final char CARD_DATE_DIVIDER = '/';
    private static final int CARD_CVC_TOTAL_SYMBOLS = 3;





    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {

        View view = inflater.inflate(R.layout.fragment_credit_debit, container, false);

        setup_layout(view);

        return view;
    }

    private void setup_layout(View v)
    {

        cardNumberEditText = (EditText) v.findViewById(R.id.creditcardEditText);

        cardDateEditText = (EditText) v.findViewById(R.id.cardDateEditText);

        cardCVCEditText = (EditText) v.findViewById(R.id.cardCVCEditText);


        cardCVCEditText.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after)
            {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() > CARD_CVC_TOTAL_SYMBOLS) {
                    s.delete(CARD_CVC_TOTAL_SYMBOLS, s.length());
                }
            }


        });

        cardDateEditText.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after)
            {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {

            }

            @Override
            public void afterTextChanged(Editable s)
            {
                if (!isInputCorrect(s, CARD_DATE_TOTAL_SYMBOLS, CARD_DATE_DIVIDER_MODULO, CARD_DATE_DIVIDER))
                {
                    s.replace(0, s.length(), buildCorrecntString(getDigitArray(s, CARD_DATE_TOTAL_DIGITS), CARD_DATE_DIVIDER_POSITION, CARD_DATE_DIVIDER));
                }
            }



        });

        cardNumberEditText.addTextChangedListener(new TextWatcher() {

            private static final int TOTAL_SYMBOLS = 19; // size of pattern 0000-0000-0000-0000
            private static final int TOTAL_DIGITS = 16; // max numbers of digits in pattern: 0000 x 4
            private static final int DIVIDER_MODULO = 5; // means divider position is every 5th symbol beginning with 1
            private static final int DIVIDER_POSITION = DIVIDER_MODULO - 1; // means divider position is every 4th symbol beginning with 0
            private static final char DIVIDER = '-';

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // noop
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // noop
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!isInputCorrect(s, TOTAL_SYMBOLS, DIVIDER_MODULO, DIVIDER)) {
                    s.replace(0, s.length(), buildCorrecntString(getDigitArray(s, TOTAL_DIGITS), DIVIDER_POSITION, DIVIDER));
                }
            }

        });


    }

    private String buildCorrecntString(char[] digits, int dividerPosition, char divider) {
        final StringBuilder formatted = new StringBuilder();

        for (int i = 0; i < digits.length; i++) {
            if (digits[i] != 0) {
                formatted.append(digits[i]);
                if ((i > 0) && (i < (digits.length - 1)) && (((i + 1) % dividerPosition) == 0)) {
                    formatted.append(divider);
                }
            }
        }

        return formatted.toString();
    }

    private char[] getDigitArray(final Editable s, final int size) {
        char[] digits = new char[size];
        int index = 0;
        for (int i = 0; i < s.length() && index < size; i++) {
            char current = s.charAt(i);
            if (Character.isDigit(current)) {
                digits[index] = current;
                index++;
            }
        }
        return digits;
    }


    private boolean isInputCorrect(Editable s, int totalSymbols, int dividerModulo, char divider)
    {
        boolean isCorrect = s.length() <= totalSymbols; // check size of entered string
        for (int i = 0; i < s.length(); i++)
        { // chech that every element is right
            if (i > 0 && (i + 1) % dividerModulo == 0)
            {
                isCorrect &= divider == s.charAt(i);
            }
            else
            {
                isCorrect &= Character.isDigit(s.charAt(i));
            }
        }
        return isCorrect;
    }


    @Override
    public List<CreditCardEditText.CreditCard> mapOfRegexStringAndImageResourceForCreditCardEditText(CreditCardEditText creditCardEditText)
    {

        List<CreditCardEditText.CreditCard> listOfPatterns = new ArrayList<CreditCardEditText.CreditCard>();

        CreditCardEditText.CreditCard newCard = new CreditCardEditText.CreditCard("^4[0-9]{12}(?:[0-9]{3})?$", getActivity().getResources().getDrawable(R.drawable.ic_card), "newcard");

        listOfPatterns.add(newCard);

        return listOfPatterns;

    }




}
