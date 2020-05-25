package com.example.CommonTest.SurfaceViewTest;

import android.app.Activity;
import android.os.Bundle;
import android.view.Display;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.Nullable;

public class SurfaceViewActivity extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        Display display=getWindowManager().getDefaultDisplay();
        setContentView(new SurfaceViewTest(this,display.getWidth(),display.getHeight()));
    }
}
