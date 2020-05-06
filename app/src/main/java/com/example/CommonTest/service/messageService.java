package com.example.CommonTest.service;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class messageService extends Service {
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return message.getBinder();
    }
    class  mHandler extends Handler{
        @Override
        public void handleMessage(@NonNull Message msg) {
            switch (msg.what){
                case 1:
                    Log.i("xxx","thanks,service has receive msg");
                    break;
                default:
                    super.handleMessage(msg);
            }
        }
    }
    Messenger message=new Messenger(new mHandler());

}
