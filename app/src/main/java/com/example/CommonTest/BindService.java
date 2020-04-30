package com.example.CommonTest;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;
import androidx.annotation.Nullable;

public class BindService extends Service {

    private int count;
    private boolean quit;
    private Thread thread;
    LocalBinder binder=new LocalBinder();

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }


    public class LocalBinder extends Binder {
        BindService getService(){
            return BindService.this;
        }
        public String getName(){
            return "hi---wzp";
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.i("Service","Service create");
        thread=new Thread(new Runnable() {
            @Override
            public void run() {
                while(!quit){
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    count++;
                    Log.d("xxx",count+"");
                }
            }
        });
        thread.start();
    }
    public int getCount(){
        return count;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.d("Service","解除绑定");
        return super.onUnbind(intent);
    }

    @Override
    public void onDestroy() {
        this.quit=true;
        super.onDestroy();
    }
}
