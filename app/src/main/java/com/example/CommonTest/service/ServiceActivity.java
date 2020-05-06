package com.example.CommonTest.service;

import android.app.Activity;
import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import androidx.annotation.Nullable;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.example.CommonTest.R;

public class ServiceActivity extends Activity {
    @BindView(R.id.service)
    Button service;
    @BindView(R.id.bindservice)
    Button bindservice;
    @BindView(R.id.unbindservice)
    Button unbindservice;
    @BindView(R.id.getdata)
    Button getdata;
    String TAG="ServiceActivity.class";




    private ServiceConnection conn;
    private BindService mService;

    private BindService binder = new BindService();
    Myservice3 myservice;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service);
        ButterKnife.bind(this);
    }

    Intent  service2intend,binserviceIntent;
    void initView(){
        service2intend= new Intent(this, MyService2.class);
        binserviceIntent = new Intent(this, BindService.class);


        ServiceConnection conn3 = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                Myservice3.myBinder binder = (Myservice3.myBinder) service;
                myservice = binder.getService();
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {
                myservice = null;
            }
        };

        conn = new ServiceConnection() {
            /**
             * 与服务器端交互的接口方法 绑定服务的时候被回调，在这个方法获取绑定Service传递过来的IBinder对象，
             * 通过这个IBinder对象，实现宿主和Service的交互。
             */
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                Log.d("SerVice.Class", "绑定成功调用：onServiceConnected");
                // 获取Binder
                BindService.LocalBinder binder = (BindService.LocalBinder) service;
                mService = binder.getService();
                Log.d("xxx", mService + "");
                Log.d("Xxx", binder.getName());
            }

            /**
             * 当取消绑定的时候被回调。但正常情况下是不被调用的，它的调用时机是当Service服务被意外销毁时，
             * 例如内存的资源不足时这个方法才被自动调用。
             */
            @Override
            public void onServiceDisconnected(ComponentName name) {
                mService = null;
            }
        };

    }



    @OnClick({R.id.service, R.id.bindservice, R.id.unbindservice, R.id.getdata})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.service:
                startService(service2intend);
                break;
            case R.id.bindservice:
                Log.d("XXX", "绑定调用");
                bindService(binserviceIntent, conn, Service.BIND_AUTO_CREATE);
                break;
            case R.id.unbindservice:
                Log.d(TAG, "解除绑定调用：unbindService");
                // 解除绑定
                if (mService != null) {
                    mService = null;
                    unbindService(conn);
                }
                break;
            case R.id.getdata:
                if (mService != null) {
                    // 通过绑定服务传递的Binder对象，获取Service暴露出来的数据
                    Log.d(TAG, "从服务端获取数据：" + mService.getCount());
                } else {
                    Log.d(TAG, "还没绑定呢，先绑定,无法从服务端获取数据");
                }
                break;
        }
    }
}
