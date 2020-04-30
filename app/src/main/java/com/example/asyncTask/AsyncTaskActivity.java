package com.example.asyncTask;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.annotation.Nullable;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.example.CommonTest.R;

public class AsyncTaskActivity extends Activity {
    @BindView(R.id.load)
    Button load;
    @BindView(R.id.cancelload)
    Button cancelload;
    @BindView(R.id.tv_show)
    TextView tvShow;
    @BindView(R.id.progress_bar)
    ProgressBar progressBar;

    MyAsyncTask myAsyncTask;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_asynctask);
        ButterKnife.bind(this);
        myAsyncTask = new MyAsyncTask();
    }

    @OnClick({R.id.load, R.id.cancelload})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.load:
                myAsyncTask.execute();
                break;
            case R.id.cancelload:
                myAsyncTask.cancel(true);
                break;
        }
    }
    
    private class MyAsyncTask extends AsyncTask<String, Integer, String> {
        @Override
        protected void onPreExecute() {
            tvShow.setText("加载中");
        }
        @Override
        protected String doInBackground(String... strings) {
            try {
                int count = 0;
                int length = 1;
                while (count < 99) {
                    count += length;
                    // 可调用publishProgress（）显示进度, 之后将执行onProgressUpdate（）
                    publishProgress(count);
                    // 模拟耗时任务
                    Thread.sleep(50);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return null;
        }
        @Override
        protected void onProgressUpdate(Integer... values) {
            progressBar.setProgress(values[0]);
            tvShow.setText("loading..." + values[0] + "%");
        }

        @Override
        protected void onPostExecute(String s) {
            tvShow.setText("加载完成");
        }

        @Override
        protected void onCancelled(String s) {
            tvShow.setText("取消");
            progressBar.setProgress(0);
        }
    }

}
