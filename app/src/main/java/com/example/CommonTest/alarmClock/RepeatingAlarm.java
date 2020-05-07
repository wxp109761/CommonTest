package com.example.CommonTest.alarmClock;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class RepeatingAlarm extends BroadcastReceiver{

  @Override
  public void onReceive(Context context, Intent intent) {
    if (intent.getAction()!=null&&intent.getAction().equals("com.gcc.alarm")) {//自定义的action
      intent = new Intent(context,alarmActivity.class);
      intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
      context.startActivity(intent);
    }
  }
}