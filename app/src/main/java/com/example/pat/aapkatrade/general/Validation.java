package com.example.pat.aapkatrade.general;

import android.telephony.PhoneNumberUtils;
import android.text.TextUtils;
import android.widget.EditText;

import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by PPC21 on 10-Jan-17.
 */

public class Validation {


    public static boolean validateEdittext(EditText et) {
        return et.getText().length() != 0;
    }

    public static boolean isEmptyStr(String s) {
        return TextUtils.isEmpty(s) || s.trim().equals("");
    }

    public static boolean isNonEmptyStr(String s) {
        return !isEmptyStr(s);
    }


    public static boolean isValidEmail(String email) {
        if (isNonEmptyStr(email)) {
            String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                    + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

            Pattern pattern = Pattern.compile(EMAIL_PATTERN);
            Matcher matcher = pattern.matcher(email);
            return matcher.matches();
        }
        return false;
    }

    public static boolean isValidPassword(String password) {
        return isNonEmptyStr(password) && password.length() >= 6;
    }

    public static boolean isValidDOB(String dob) {
        if (isNonEmptyStr(dob)) {
            if (dob.split("-").length == 3) {
                return true;
            }
        }
        return false;
    }

    public static boolean isPasswordMatching(String password, String confirmPassword) {
        if (isValidPassword(password) && isValidPassword(confirmPassword)) {
            if (password.equals(confirmPassword))
                return true;
        }
        return false;
    }

    public static String getNumberPrefix(String number) {
        if (number != null) {
            if (number.length() > 9) {
                return number.substring(0, number.length() - 9);
            }
        }
        return "";
    }

    public static boolean isValidNumber(String number, String prefix)
    {
        if (isNonEmptyStr(number)) {
//            if (prefix == null) {
//                prefix = "";
//            }
//            if (number != null) {
//                if (number.length() > 0) {
//                    if ((number.length() == 9 + prefix.length()) && (getNumberPrefix(number).equals(prefix))) {
//                        return true;
//                    }
//                }
//            }
            return number.length() == 10;
        }
        return false;
    }

    public static boolean isPincode(String pincode) {
        if (isNonEmptyStr(pincode)) {
            if (pincode.length() == 6 && !pincode.startsWith("0")) {
                return true;
            }
        }
        return false;
    }

    public static boolean isNumber(String s) {
        return TextUtils.isDigitsOnly(s);
    }

}
