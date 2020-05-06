package com.example.CommonTest.base;

import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;


/**
 * Activity基类:实时获取网络状态
 */
public class BasicActivity extends AppCompatActivity {
    private boolean isRegistered = false;
   // private NetworkReceiver networkReceiver;
    private static final String APP_ID = "1c54d5b204e98654778c77547afc7a66";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // Bmob.initialize(getApplication(), APP_ID);
        //注册网络状态监听广播
       // networkReceiver = new NetworkReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction(WifiManager.WIFI_STATE_CHANGED_ACTION);
        filter.addAction(WifiManager.NETWORK_STATE_CHANGED_ACTION);
        filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
       // registerReceiver(networkReceiver, filter);
        isRegistered = true;
//
//        Toasty.Config.getInstance()
//                .setSuccessColor(getResources().getColor(R.color.toastSuccess))
//                .setErrorColor(getResources().getColor(R.color.toastError))
//                .setInfoColor(getResources().getColor(R.color.toastInfo))
//                .apply();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //解绑
        if (isRegistered) {
//            unregisterReceiver(networkReceiver);
        }
    }
}
