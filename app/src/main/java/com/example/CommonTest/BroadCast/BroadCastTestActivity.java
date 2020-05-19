package com.example.CommonTest.BroadCast;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.CommonTest.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class BroadCastTestActivity extends Activity {
    @BindView(R.id.standard_broad)
    Button standardBroad;
    @BindView(R.id.sordered_broad)
    Button sorderedBroad;
    MyBroadcastReceiver broadcastReceiver;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_broad_cast);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        NetworkChangeReceiver networkChangeReceiver = new NetworkChangeReceiver();
        registerReceiver(networkChangeReceiver, intentFilter);

        //动态注册
        IntentFilter myinfilter=new IntentFilter("com.example.BroadCast.MY_BROADCAST");
        broadcastReceiver=new MyBroadcastReceiver();
        registerReceiver(broadcastReceiver,myinfilter);
    }

    @OnClick({R.id.standard_broad, R.id.sordered_broad})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.standard_broad:
                //动态注册
                Intent intent=new Intent("com.example.BroadCast.MY_BROADCAST");
                intent.putExtra("key","你好");
                sendBroadcast(intent);
                break;
            case R.id.sordered_broad:
                //静态注册
                Intent intent1=new Intent();
                intent1.setAction("MYBROAD");
                intent1.putExtra("key","    你好22222");
                intent1.addFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES);
                intent1.setPackage("com.example.uploadingimages");
                // intent1.setComponent(new ComponentName("com.example.CommonTest.BroadCast","com.example.CommonTest.BroadCast.MyBroadcastReceiver2"));
                sendBroadcast(intent1);
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(broadcastReceiver);
    }

    class NetworkChangeReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
            if (networkInfo != null && networkInfo.isAvailable()) {
                Toast.makeText(context, "网络畅通", Toast.LENGTH_SHORT).show();

            } else {
                Toast.makeText(context, "网络失联", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
