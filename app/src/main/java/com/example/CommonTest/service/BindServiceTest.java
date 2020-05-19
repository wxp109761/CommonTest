package com.example.CommonTest.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;
import androidx.annotation.Nullable;

import java.util.Random;

public class BindServiceTest extends Service {
    private int count;
    private boolean quit;
    private Thread thread;


    public class MyBinder extends Binder {
        public BindServiceTest getService(){
            return BindServiceTest.this;
        }
        public String getName(){
            return "hi---wzp";
        }
    }

    private MyBinder binder=new MyBinder();

    private final Random generator=new Random();

   String   TAG="BindServiceTest";
    @Override
    public void onCreate() {
        super.onCreate();
        Log.i(TAG,"OnCreate   threadName---"+Thread.currentThread().getName());
//        thread=new Thread(new Runnable() {
//            @Override
//            public void run() {
//                while(!quit){
//                    try {
//                        Thread.sleep(1000);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                    count++;
//                    Log.d(TAG,count+"");
//                }
//            }
//        });
//        thread.start();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i(TAG, "onStartCommand - startId = " + startId + ", Thread = " + Thread.currentThread().getName());
        return START_NOT_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.i(TAG, "onBind - Thread = " + Thread.currentThread().getName());
        return binder;
    }
    @Override
    public boolean onUnbind(Intent intent) {
        Log.d(TAG,"解除绑定-from"+intent.getStringExtra("from"));
        return false;
    }


    public int getCount(){
        return count;
    }

    @Override
    public void onDestroy() {
        Log.d(TAG,"onDestroy - Thread = " + Thread.currentThread().getName());
        this.quit=true;
        super.onDestroy();
    }
    public int getRandomNumber() {
        return generator.nextInt();
    }
}
