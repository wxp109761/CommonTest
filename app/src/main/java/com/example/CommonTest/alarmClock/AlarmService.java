package com.example.CommonTest.alarmClock;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;

import com.example.CommonTest.MainActivity;

public class AlarmService extends Service
{
  public static final String ACTION = "com.jz.alarmsample.alarm";

  @Override
  public IBinder onBind(Intent intent)
  {
    return null;
  }

  @Override
  public int onStartCommand(Intent intent, int flags, int startId)
  {
    Context context = getApplicationContext();
    long intervalMillis = intent.getLongExtra(AlarmManagerUtils.INTERVAL_MILLIS, 0);
    if (intervalMillis != 0)
      AlarmManagerUtils.setAlarmTime(context, System.currentTimeMillis() + intervalMillis, intent);
    Intent clockIntent = new Intent(context, MainActivity.class);
    clockIntent.putExtra(AlarmManagerUtils.ID, intent.getIntExtra(AlarmManagerUtils.ID, 0));
    clockIntent.putExtra(AlarmManagerUtils.TIPS, intent.getStringExtra(AlarmManagerUtils.TIPS));
    clockIntent.putExtra(AlarmManagerUtils.SOUND_OR_VIBRATOR, intent.getIntExtra(AlarmManagerUtils.SOUND_OR_VIBRATOR, 0));
    clockIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    context.startActivity(clockIntent);
    return super.onStartCommand(intent, flags, startId);
  }
}
