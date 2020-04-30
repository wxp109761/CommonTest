package com.example.CommonTest;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import androidx.annotation.Nullable;

public class Myservice3 extends Service {



    myBinder myBinder=new myBinder();
    public class myBinder extends Binder{
        Myservice3 getService(){
            return Myservice3.this;
        }

    }
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return myBinder;
    }
}
