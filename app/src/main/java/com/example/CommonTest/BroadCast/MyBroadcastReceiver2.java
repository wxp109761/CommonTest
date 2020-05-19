package com.example.CommonTest.BroadCast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

public class MyBroadcastReceiver2 extends BroadcastReceiver {
    private static final String TAG = "MyBroadcastReceiver";

    public MyBroadcastReceiver2() {

    }

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(TAG, "onReceive: recieve the broadcast!2222");
        String data=intent.getExtras().getString("key");
        Toast.makeText(context, "received in MyBroadcastReceiver2222"+data, Toast.LENGTH_SHORT).show();
    }
}