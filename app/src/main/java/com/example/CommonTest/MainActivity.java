package com.example.CommonTest;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.CommonTest.BroadCast.BroadCastTestActivity;
import com.example.CommonTest.CrawingNews.CrawingNewsActivity;
import com.example.CommonTest.alarmClock.alarmActivity;
import com.example.CommonTest.animation.AnimationActivity;
import com.example.CommonTest.asyncTask.AsyncTaskActivity;
import com.example.CommonTest.fileDownload.FileDownLoad;
import com.example.CommonTest.fragment.FragmentActivity;
import com.example.CommonTest.handler.HandlerActivity;
import com.example.CommonTest.messageService.MessageServiceActivity;
import com.example.CommonTest.qiniu.ImgActivity;
import com.example.CommonTest.remind.RemindActivity;
import com.example.CommonTest.service.ServiceActivity;
import com.example.CommonTest.slide.FilterPopupWindow;
import com.example.CommonTest.sqllite.SqlLiteActivity;
import com.example.CommonTest.utils.Utils;
import com.example.CommonTest.video.VideoActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.service_test_btn)
    Button serviceTestBtn;
    @BindView(R.id.asynctask)
    Button asynctask;
    @BindView(R.id.fragment)
    Button fragment;
    @BindView(R.id.message_service)
    Button messageService;
    @BindView(R.id.animation)
    Button animation;
    @BindView(R.id.sqllite)
    Button sqllite;
    @BindView(R.id.video_activity)
    Button videoActivity;
    @BindView(R.id.handler)
    Button handler;
    @BindView(R.id.qiniu_img)
    Button qiniuImg;
    @BindView(R.id.remind_activity)
    Button remindActivity;
    @BindView(R.id.crawing_news)
    Button crawingNews;
    @BindView(R.id.broad_cast_btn)
    Button broadCastBtn;
    @BindView(R.id.file_download)
    Button fileDownload;
    @BindView(R.id.naozhong)
    Button naozhong;
    private String TAG = "MainActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        Utils.getPer(getBaseContext(), MainActivity.this);
    }



    @OnClick({R.id.service_test_btn, R.id.asynctask, R.id.fragment, R.id.message_service, R.id.animation, R.id.sqllite, R.id.video_activity, R.id.handler, R.id.qiniu_img, R.id.remind_activity, R.id.crawing_news, R.id.broad_cast_btn, R.id.file_download, R.id.naozhong})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.service_test_btn:
                Intent intent = new Intent(MainActivity.this, ServiceActivity.class);
                startActivity(intent);
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
            case R.id.remind_activity:
                Intent intent9 = new Intent(MainActivity.this, RemindActivity.class);
                startActivity(intent9);
                break;
            case R.id.naozhong:
                Intent intent10 = new Intent(MainActivity.this, alarmActivity.class);
                startActivity(intent10);
                break;
            case R.id.file_download:
                Intent intent11 = new Intent(MainActivity.this, FileDownLoad.class);
                startActivity(intent11);
                break;
            case R.id.crawing_news:
                Intent intent12 = new Intent(MainActivity.this, CrawingNewsActivity.class);
                startActivity(intent12);
                break;
            case R.id.broad_cast_btn:
                Intent intent13 = new Intent(MainActivity.this, BroadCastTestActivity.class);
                startActivity(intent13);
                break;
        }
    }
}
