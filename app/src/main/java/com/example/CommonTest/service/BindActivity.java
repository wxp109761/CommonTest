package com.example.CommonTest.service;

import android.app.Activity;
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

public class BindActivity extends Activity {
    @BindView(R.id.b_bind_service)
    Button bBindService;
    @BindView(R.id.b_unbind_service)
    Button bUnbindService;
    @BindView(R.id.b_finish_service)
    Button bFinishService;

    String TAG = "bindActivity-B";

    boolean isBind=false;
    private BindServiceTest mService=null;

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
            Log.d(TAG, "BindActivity---B---onServiceConnected");
            int num = mService.getRandomNumber();
            Log.d("xxx", "BindActivity-B" + num + "    count " + mService.getCount());
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
        setContentView(R.layout.activity_bindservice);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.b_bind_service, R.id.b_unbind_service, R.id.b_finish_service})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.b_bind_service:
                Intent intent = new Intent(this, BindServiceTest.class);
                intent.putExtra("from", "ActivityB");
                Log.d(TAG,"B执行了绑定服务");
                bindService(intent,conn,BIND_AUTO_CREATE);
                break;
            case R.id.b_unbind_service:
                Log.d(TAG,"B执行了解绑服务");
                unbindService(conn);
                break;
            case R.id.b_finish_service:
                Log.d(TAG,"B执行了finish");
                this.finish();
                break;
        }
    }
    @Override
    public void onDestroy(){
        super.onDestroy();
        Log.i(TAG, "ActivityB - onDestroy");
    }
}
