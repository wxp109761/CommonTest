package com.example.CommonTest.CrawingNews;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.example.CommonTest.R;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Response;

public class CrawingNewsActivity extends Activity {
    int page=0;
    private  String DEFAULT_MAIN_URL = "http://api.search.sina.com.cn/?q=[高校实验室]&c=news&sort=time&ie=utf-8&from=dfz_api&page="+page;

    @BindView(R.id.list_view)
    ListView listView;
    List<NewsBean.ResultBean.ListBean> listBean;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crawing);
        ButterKnife.bind(this);
        getData();
        RefreshLayout refreshLayout = (RefreshLayout)findViewById(R.id.refreshLayout);
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                refreshlayout.finishRefresh(false);//传入false表示刷新失败
            }
        });
        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshlayout) {
                refreshlayout.finishLoadMore(2000/*,false*/);//传入false表示加载失败
                page++;
                getData();
               // listView.set
            }
        });

    }


    public void getData(){
        MyAsyncTask myAsyncTask=new MyAsyncTask();
        myAsyncTask.execute();
    }
    String TAG = "CreaingNewsActivity";



class MyAsyncTask extends AsyncTask<String, Void, String> {

    @Override
    protected String doInBackground(String... params) {

        try {
            URL url = new URL(DEFAULT_MAIN_URL);
            HttpURLConnection openConnection = (HttpURLConnection) url
                    .openConnection();
            openConnection.setConnectTimeout(5000);
            openConnection.setReadTimeout(5000);
            int responseCode = openConnection.getResponseCode();
            if (responseCode == 200) {
                InputStream inputStream = openConnection.getInputStream();
                StreamUtils streamUtils = new StreamUtils();
                String parseStream = streamUtils.parseSteam(inputStream);
                return parseStream;
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        Gson gson = new Gson();
        NewsBean fromJson = gson.fromJson(result,
                NewsBean.class);
        listBean= fromJson.getResult().getList();
        NewsAdapter adapter=new NewsAdapter(getApplicationContext(),listBean,R.layout.adapter_item_list);
        listView.setAdapter(adapter);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                for (int i = 0; i < listBean.size(); i++) {
                    if (i == position) {
                        String url = listBean.get(i).getUrl();
                        Intent it=new Intent(getApplicationContext(), WebViewsActivity.class);
                        it.putExtra("url",url);
                        startActivity(it);
                    }
                }
            }
        });
    }
}
}
