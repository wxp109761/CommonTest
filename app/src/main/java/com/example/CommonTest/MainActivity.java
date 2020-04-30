package com.example.CommonTest;

import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.example.Animation.AnimationActivity;
import com.example.Handler.HandlerActivity;
import com.example.QINIU.ImgActivity;
import com.example.asyncTask.AsyncTaskActivity;
import com.example.fragment.FragmentActivity;
import com.example.messageService.MessageServiceActivity;
import com.example.sqllite.SqlLiteActivity;
import com.example.video.VideoActivity;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.service)
    Button btn_service;
    @BindView(R.id.bindservice)
    Button btn_bindservice;
    @BindView(R.id.unbindservice)
    Button btn_unbindservice;
    @BindView(R.id.getdata)
    Button btn_getdata;
    @BindView(R.id.asynctask)
    Button btn_asynctask;
    @BindView(R.id.fragment)
    Button btn_fragment;
    @BindView(R.id.message_service)
    Button btn_messageService;
    @BindView(R.id.animation)
    Button btn_animation;
    @BindView(R.id.sqllite)
    Button btn_sqllite;
    @BindView(R.id.video_activity)
    Button btn_videoActivity;
    @BindView(R.id.handler)
    Button btn_handler;
    @BindView(R.id.qiniu_img)
    Button qiniuImg;
    private String TAG = "MainActivity";
    Intent it;

    private ServiceConnection conn;
    private BindService mService;
    Intent intent;
    private BindService binder = new BindService();
    Myservice3 myservice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        it = new Intent(this, MyService2.class);
        intent = new Intent(this, BindService.class);


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
                Log.d(TAG, "绑定成功调用：onServiceConnected");
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


    @OnClick({R.id.service, R.id.unbindservice, R.id.getdata, R.id.fragment, R.id.message_service, R.id.animation, R.id.sqllite, R.id.video_activity, R.id.handler, R.id.asynctask,R.id.qiniu_img})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.service:
                startService(it);
                break;
            case R.id.bindservice:
                Log.d("XXX", "绑定调用");
                bindService(intent, conn, Service.BIND_AUTO_CREATE);
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

            case R.id.animation:
                Intent intent1 = new Intent(MainActivity.this, AnimationActivity.class);
                startActivity(intent1);
                break;
            case R.id.message_service:
                Intent intent2 = new Intent(MainActivity.this, MessageServiceActivity.class);
                startActivity(intent2);
                break;
            case R.id.sqllite:
                Intent intent3 = new Intent(MainActivity.this, SqlLiteActivity.class);
                startActivity(intent3);
                break;

            case R.id.fragment:
                Intent intent4 = new Intent(MainActivity.this, FragmentActivity.class);
                startActivity(intent4);
                break;
            case R.id.video_activity:
                Intent intent5 = new Intent(MainActivity.this, VideoActivity.class);
                startActivity(intent5);
                break;
            case R.id.handler:
                Intent intent6 = new Intent(MainActivity.this, HandlerActivity.class);
                startActivity(intent6);
                break;
            case R.id.asynctask:
                Intent intent7 = new Intent(MainActivity.this, AsyncTaskActivity.class);
                startActivity(intent7);
                break;
            case R.id.qiniu_img:
                Intent intent8 = new Intent(MainActivity.this, ImgActivity.class);
                startActivity(intent8);
                break;
        }
    }


}
