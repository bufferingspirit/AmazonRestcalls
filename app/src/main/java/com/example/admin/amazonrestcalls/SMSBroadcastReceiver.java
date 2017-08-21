package com.example.admin.amazonrestcalls;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

public class SMSBroadcastReceiver extends BroadcastReceiver {

    public static final String TAG = "SMSBroadcastReceiver";
    SMSActivity smsAct;

    public SMSBroadcastReceiver(SMSActivity sms){
        smsAct = sms;
    }


    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.

        switch(intent.getAction()){

            case "android.provider.Telephony.SMS_RECEIVED":
                Log.d(TAG, "onReceive: SMS Received");
                smsAct.refreshSmsInbox();
                break;
            case "android.provider.Telephony.SMS_DELIVER":
                Log.d(TAG, "onReceive: SMS Deliver");
                smsAct.refreshSmsSent();
                break;
            case "android.provider.Telephony.DATA_SMS_RECEIVED":
                Log.d(TAG, "onReceive: Data SMS Received");
                smsAct.refreshSmsInbox();
                break;
            case "android.provider.Telephony.RESULT_SMS_DUPLICATED":
                Log.d(TAG, "onReceive: Duplicate SMS Received");
                Toast.makeText(context, "Duplicate Received", Toast.LENGTH_SHORT).show();
                break;
            case "android.provider.Telephony.SMS_REJECTED":
                Log.d(TAG, "onReceive: SMS Rejected");
                Toast.makeText(context, "SMS Rejected", Toast.LENGTH_SHORT).show();
                break;
        }
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
