package com.example.CommonTest.CrawingNews;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.example.CommonTest.R;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CrawingNewsActivity extends Activity {
    private static final String DEFAULT_MAIN_URL = "http://www.sina.com.cn/mid/search.shtml?range=all&c=news&q=%E9%AB%98%E6%A0%A1%E5%AE%9E%E9%AA%8C%E5%AE%A4&from=home&ie=utf-8";
    @BindView(R.id.text)
    TextView text;
    @BindView(R.id.list_view)
    ListView listView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crawing);
        ButterKnife.bind(this);
        getDatas();
    }

    private static final String USER_AGENT = "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/57.0.2987.133 Safari/537.36";

    String TAG = "CreaingNewsActivity";

    private void getDatas() {
        List<String> lists = new ArrayList<>();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    long a = System.currentTimeMillis();
                    Connection connection;
                    Document doc;
                    connection = Jsoup.connect(DEFAULT_MAIN_URL);
                    connection.header("User-Agent", USER_AGENT);
                    doc = connection.get();
                    text.setText(doc.getElementById("li").text());
                    //原料在<p class="subcontent">中
                    Elements burdens = doc.select("span");

                    Document
                            document1 = Jsoup.connect("http://ds.suning.cn/ds/generalForTile/000000000133537397-9173-2-0000000000-1--ds000000000.jsonp")
                            .ignoreContentType(true)
                            .data("query", "Java")
                            .userAgent("Mozilla")
                            .cookie("auth", "token")
                            .timeout(3000)
                            .get();
                    //打印出模拟ajax请求返回的数据，一个json格式的数据，对它进行解析就可以了
                    System.out.println(document1.text());

                    System.out.println("  zzz " + burdens.size());
                    for (int i = 0; i < burdens.size(); i++) {
                        //使用Element.select(String selector)查找元素，使用Node.attr(String key)方法取得一个属性的值
                        String title = burdens.get(i).select("a").toString();
                        String burden = burdens.get(i).text();
                        //Log.e(TAG, "tvTitle:" + title);
                        Log.e(TAG, "burden:" + burden);
                    }
                    Elements pageElements = doc.select("div.ui-page-inner");
                    Element nowPageElement = pageElements.select("a.now_page").get(0);
                    Element nextPageElement = nowPageElement.nextElementSibling();
//            mNextPageUrl = nextPageElement.attr("href");
//            isFirstIn = false;
                    long b = System.currentTimeMillis();
                    Log.e(TAG, "所用时间：" + (b - a) + "毫秒");
                } catch (Exception e) {
                    Log.e(TAG, e.toString());

                }
            }
        }
        ).start();
    }

}
