package com.example.CommonTest.BroadCast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

public class MyBroadcastReceiver extends BroadcastReceiver {
    private static final String TAG = "MyBroadcastReceiver";
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(TAG, "onReceive: recieve the broadcast!");
        String data=intent.getExtras().getString("key");
        Toast.makeText(context, "received in MyBroadcastReceiver"+data, Toast.LENGTH_SHORT).show();
    }
}