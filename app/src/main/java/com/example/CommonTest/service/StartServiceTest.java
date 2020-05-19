package com.example.CommonTest.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;

public class StartServiceTest extends Service {
    String TAG="StartServiceTest";
    /**
     * 首次创建服务时，系统将调用此方法来执行一次性设置程序（在调用 onStartCommand() 或 onBind() 之前）。
     * 如果服务已在运行，则不会调用此方法。该方法只被调用一次
     */
    @Override
    public void onCreate() {
        Log.i(TAG,"创建service-- Thread ID = " + Thread.currentThread().getId());
        super.onCreate();
    }



    /**
     * 每次通过startService()方法启动Service时都会被回调。
     * @param intent
     * @param flags
     * @param startId
     * @return
     */
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG,"onStartCommand "+startId+"   threadId"+Thread.currentThread().getId());
        return super.onStartCommand(intent, flags, startId);
    }


    /**
     * 绑定服务时才会调用
     * 必须要实现的方法
     * @param intent
     * @return
     */
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {

        Log.d(TAG,intent.getExtras().getString("key"));
        return null;
    }




    /**
     * 服务销毁时的回调
     */
    @Override
    public void onDestroy() {
        Log.d(TAG,"销毁");
        super.onDestroy();
    }

}
