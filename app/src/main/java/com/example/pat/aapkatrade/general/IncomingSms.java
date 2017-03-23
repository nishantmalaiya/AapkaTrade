package com.example.pat.aapkatrade.general;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.util.Log;

import com.example.pat.aapkatrade.login.ActivityOTPVerify;

public class IncomingSms extends BroadcastReceiver {

    final SmsManager sms = SmsManager.getDefault();
    ActivityOTPVerify activityOTPVerify;


    public IncomingSms() {

    }


    @Override
    public void onReceive(Context context, Intent intent) {


        final Bundle bundle = intent.getExtras();

        try {

            if (bundle != null) {

                final Object[] pdusObj = (Object[]) bundle.get("pdus");
                SmsMessage[] messages = new SmsMessage[pdusObj.length];

                for (int i = 0; i < pdusObj.length; i++) {
                    messages[i] = SmsMessage.createFromPdu((byte[]) pdusObj[i]);
                }

                for (SmsMessage currentMessage : messages) {

                    String sender = currentMessage.getDisplayOriginatingAddress();
                    String msg = currentMessage.getDisplayMessageBody();
                    activityOTPVerify = new ActivityOTPVerify();
                    activityOTPVerify.update_otp(msg);

                    Log.e("Sender::", sender);
                    Log.e("Msg::", msg);
                    Intent myIntent = new Intent("otp");
                    myIntent.putExtra("message", msg);
                    LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
                }


//                for (int i = 0; i < pdusObj.length; i++) {
//
//                    SmsMessage currentMessage = SmsMessage.createFromPdu((byte[]) pdusObj[i]);
//                    String phoneNumber = currentMessage.getDisplayOriginatingAddress();
//
//                    String senderNum = phoneNumber;
//                    String message = currentMessage.getDisplayMessageBody().split(":")[1];
//
//                    message = message.substring(0, message.length()-1);
//                    Log.i("SmsReceiver", "senderNum: " + senderNum + "; message: " + message);
//
//                    Intent myIntent = new Intent("otp");
//                    myIntent.putExtra("message",message);
//                    LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
//                    // Show Alert
//
//                } // end for loop
            } // bundle is null

        } catch (Exception e) {
            Log.e("SmsReceiver", "Exception smsReceiver" + e);

        }


        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.
//        throw new UnsupportedOperationException("Not yet implemented");
    }
}
