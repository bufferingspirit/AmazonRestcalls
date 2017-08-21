package com.example.admin.amazonrestcalls;

import android.annotation.TargetApi;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.jar.Manifest;

public class SMSActivity extends AppCompatActivity {

    SmsManager smsManager;
    EditText etMessage, etPhoneNumber;
    ArrayAdapter arrayAdapter;
    ListView lvMessage;
    ArrayList<String> smsMessagesList = new ArrayList<>();
    SMSBroadcastReceiver smsBroadcastReceiver;


    @TargetApi(Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sms);

        smsManager = SmsManager.getDefault();
        etMessage = (EditText) findViewById(R.id.etMessage);
        etPhoneNumber = (EditText) findViewById(R.id.etPhoneNumber);
        lvMessage = (ListView) findViewById(R.id.lvMessage);

        arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, smsMessagesList);
        lvMessage.setAdapter(arrayAdapter);

        requestPermissions(new String [] {"android.permission.READ_SMS"}, 123);
    }

    @Override
    protected void onStart() {
        super.onStart();

        smsBroadcastReceiver = new SMSBroadcastReceiver(this);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.provider.Telephony.SMS_RECEIVED");
        intentFilter.addAction("android.provider.Telephony.SMS_DELIVER");
        intentFilter.addAction("android.provider.Telephony.DATA_SMS_RECEIVED");
        intentFilter.addAction("android.provider.Telephony.RESULT_SMS_DUPLICATED");
        intentFilter.addAction("android.provider.Telephony.SMS_REJECTED");

        registerReceiver(smsBroadcastReceiver, intentFilter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        unregisterReceiver(smsBroadcastReceiver);
    }

    public void sendMessage(View view){
        String number = etPhoneNumber.getText().toString();
        String message = etMessage.getText().toString();

        //todo string check
        smsManager.sendTextMessage(number, null, message, null, null);
        Toast.makeText(getApplicationContext(), "SMS sent.", Toast.LENGTH_LONG).show();
        refreshSmsSent();
    }


    public void refreshSmsInbox() {
        ContentResolver contentResolver = getContentResolver();
        Cursor smsInboxCursor = contentResolver.query(Uri.parse("content://sms/inbox"), null, null, null, null);
        int indexBody = smsInboxCursor.getColumnIndex("body");
        int indexAddress = smsInboxCursor.getColumnIndex("address");
        if (indexBody < 0 || !smsInboxCursor.moveToFirst()) return;
        //arrayAdapter.clear();
        do {
            String str = "SMS From: " + smsInboxCursor.getString(indexAddress) +
                    "\n" + smsInboxCursor.getString(indexBody) + "\n";
            smsMessagesList.add(str);
        } while (smsInboxCursor.moveToNext());
        arrayAdapter.notifyDataSetChanged();
    }

    public void refreshSmsSent(){
        ContentResolver contentResolver = getContentResolver();
        Cursor smsInboxCursor = contentResolver.query(Uri.parse("content://sms/sent"), null, null, null, null);
        int indexBody = smsInboxCursor.getColumnIndex("body");
        int indexAddress = smsInboxCursor.getColumnIndex("address");
        if (indexBody < 0 || !smsInboxCursor.moveToFirst()) return;
        //arrayAdapter.clear();
        do {
            String str = "SMS To: " + smsInboxCursor.getString(indexAddress) +
                    "\n" + smsInboxCursor.getString(indexBody) + "\n";
            smsMessagesList.add(str);
        } while (smsInboxCursor.moveToNext());
        arrayAdapter.notifyDataSetChanged();
    }

}
