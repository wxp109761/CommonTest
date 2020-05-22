package com.example.CommonTest.CrawingNews;

import android.app.Activity;
import android.os.Bundle;
import android.widget.FrameLayout;

import androidx.annotation.Nullable;

import com.example.CommonTest.R;
import com.just.agentweb.AgentWeb;

public class WebViewsActivity extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);
        String url=getIntent().getExtras().getString("url");
        FrameLayout  mContainer=findViewById(R.id.container);
     AgentWeb mAgentWeb = AgentWeb.with(this)
                .setAgentWebParent(mContainer, new FrameLayout.LayoutParams(-1, -1))
                .useDefaultIndicator(R.color.black)
                .createAgentWeb()
                .ready()
                .go(url);
    }
}
