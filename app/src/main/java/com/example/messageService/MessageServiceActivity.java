package com.example.messageService;

import android.app.Activity;
import android.content.*;
import android.os.*;
import android.util.Log;
import android.view.View;
import android.widget.*;
import com.example.CommonTest.R;
import com.example.CommonTest.messageService;

public class MessageServiceActivity extends Activity implements View.OnClickListener {


    /**
     * 与服务端交互的Messenger
     */
    Messenger mService = null;
    /** Flag indicating whether we have called bind on the service. */
    boolean mBound;
    /**
     * 实现与服务端链接的对象
     */
    private ServiceConnection mConnection = new ServiceConnection() {
        public void onServiceConnected(ComponentName className, IBinder service) {
            /**
             * 通过服务端传递的IBinder对象,创建相应的Messenger
             * 通过该Messenger对象与服务端进行交互
             */
            mService = new Messenger(service);
            mBound = true;
        }
        public void onServiceDisconnected(ComponentName className) {
            // This is called when the connection with the service has been
            // unexpectedly disconnected -- that is, its process crashed.
            mService = null;
            mBound = false;
        }
    };




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_service);
        Button bindService= (Button) findViewById(R.id.bindservice);
        Button unbindService= (Button) findViewById(R.id.unbindservice);
        Button sendMsg= (Button) findViewById(R.id.sendmsg);

        bindService.setOnClickListener(this);
        sendMsg.setOnClickListener(this);
        unbindService.setOnClickListener(this);

    }
    ListView listView;

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bindservice:
                Log.d("xx","onClick-->bindService");
                //当前Activity绑定服务端
                bindService(new Intent(MessageServiceActivity.this, messageService.class), mConnection,
                        Context.BIND_AUTO_CREATE);
                break;
            case R.id.unbindservice:
                if (mBound) {
                    Log.d("xx","onClick-->unbindService");
                    unbindService(mConnection);
                    mBound = false;
                }
                break;
            case R.id.sendmsg:
                if (!mBound) return;
                // 创建与服务交互的消息实体Message
                Message msg = Message.obtain(null, 1, 0, 0);
                try {
                    //发送消息
                    mService.send(msg);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
                break;

        }
    }

}