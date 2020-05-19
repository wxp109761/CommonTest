package com.example.CommonTest.BroadCast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.example.CommonTest.MainActivity;

public class BootCompleteReceiver extends BroadcastReceiver {
    private static final String TAG = "BootCompleteReceiver";
    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.
        Toast.makeText(context, "Boot Complete", Toast.LENGTH_LONG).show();
        Intent intent1 = new Intent(context, MainActivity.class);
        //必须要添加这个标签 否则启动失败
        intent1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        //跳转
        context.startActivity(intent1);
//        throw new UnsupportedOperationException("Not yet implemented");

    }
}
