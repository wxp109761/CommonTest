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

import com.example.CommonTest.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ServiceActivity extends Activity {

    String TAG = "ServiceActivity";
    @BindView(R.id.start_service)
    Button startService;
    @BindView(R.id.stop_service)
    Button stopService;
    @BindView(R.id.a_bind_service)
    Button aBindService;
    @BindView(R.id.a_to_b)
    Button aToB;
    @BindView(R.id.a_unbind_service)
    Button aUnbindService;
    @BindView(R.id.a_finish_service)
    Button aFinishService;
    private BindServiceTest mService=null;

    boolean isBind=false;
    private ServiceConnection conn = new ServiceConnection() {
        /**
         * 与服务器端交互的接口方法 绑定服务的时候被回调，在这个方法获取绑定Service传递过来的IBinder对象，
         * 通过这个IBinder对象，实现宿主和Service的交互。
         */
        @Override
        public void onServiceConnected(ComponentName name, IBinder binder) {
            isBind = true;
            // 获取Binder
            BindServiceTest.MyBinder mybinder = (BindServiceTest.MyBinder) binder;
            mService = mybinder.getService();
            Log.d(TAG, "BindActivity---A---onServiceConnected");
            int num = mService.getRandomNumber();
            Log.d("xxx", "BindActivity---A" + num + "    count " + mService.getCount());
        }

        /**
         * 当取消绑定的时候被回调。但正常情况下是不被调用的，它的调用时机是当Service服务被意外销毁时，
         * 例如内存的资源不足时这个方法才被自动调用。
         */
        @Override
        public void onServiceDisconnected(ComponentName name) {
            isBind = false;
            mService = null;
        }
    };


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service);
        ButterKnife.bind(this);
    }


    @OnClick({R.id.start_service, R.id.stop_service, R.id.a_bind_service, R.id.a_to_b, R.id.a_unbind_service, R.id.a_finish_service})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.start_service:
                Intent stServiceTest1 = new Intent(this, StartServiceTest.class);
                stServiceTest1.putExtra("key", "service1");
                startService(stServiceTest1);
                Intent stServiceTest2 = new Intent(this, StartServiceTest.class);
                stServiceTest2.putExtra("key", "service2");
                startService(stServiceTest2);
                Intent stServiceTest3 = new Intent(this, StartServiceTest.class);
                stServiceTest3.putExtra("key", "service3");
                startService(stServiceTest3);
                break;
            case R.id.stop_service:
                Intent stServiceTest4 = new Intent(this, StartServiceTest.class);
                stServiceTest4.putExtra("key", "service4");
                stopService(stServiceTest4);
                break;
            case R.id.a_bind_service:
                Intent intent = new Intent(this, BindServiceTest.class);
                intent.putExtra("from", "ActivityA");
                Log.d(TAG,"A执行了绑定服务");
                bindService(intent,conn,BIND_AUTO_CREATE);
                break;
            case R.id.a_to_b:
                Intent intent2 = new Intent(this,BindActivity.class);
                Log.i(TAG, "----------------------------------------------------------------------");
                Log.i(TAG, "BindActivity--A 启动 ActivityB");
                startActivity(intent2);
                break;
            case R.id.a_unbind_service:
                Log.d(TAG,"A执行了解绑服务");
                unbindService(conn);
                break;
            case R.id.a_finish_service:
                Log.d(TAG,"A执行了finish");
                this.finish();
                break;
//            case R.id.getdata:
//                if (mService != null) {
//                    // 通过绑定服务传递的Binder对象，获取Service暴露出来的数据
//                    Log.d(TAG, "从服务端获取数据：" + mService.getCount());
//                } else {
//                    Log.d(TAG, "还没绑定呢，先绑定,无法从服务端获取数据");
//                }
//            break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
