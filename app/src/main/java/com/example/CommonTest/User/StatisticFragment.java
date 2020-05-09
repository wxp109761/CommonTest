package com.example.CommonTest.User;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import androidx.fragment.app.Fragment;
import com.example.CommonTest.R;
import es.dmoral.toasty.Toasty;

import java.util.Date;
import java.util.List;

public class StatisticFragment extends Fragment {

    public StatisticFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_statistic, container, false);


        return view;
    }

    public void setData(){


        new Thread(new Runnable() {
            //开启一个线程处理逻辑，然后在线程中在开启一个UI线程，当子线程中的逻辑完成之后，
            //就会执行UI线程中的操作，将结果反馈到UI界面。
            @Override
            public void run() {
                // 模拟耗时的操作，在子线程中进行。
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                // 更新主线程ＵＩ，跑在主线程。
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                    }
                });


            }});
    }
}
