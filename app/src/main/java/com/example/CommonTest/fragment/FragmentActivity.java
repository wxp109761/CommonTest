package com.example.CommonTest.fragment;

import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.CommonTest.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class FragmentActivity extends AppCompatActivity implements View.OnClickListener {

    Fragment01 f01 = new Fragment01();
    Fragment02 f02 = new Fragment02();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment_test);
        Button fg = findViewById(R.id.fg_ceshi);
        fg.setOnClickListener(this);
        getSupportFragmentManager().beginTransaction().add(R.id.container, f01).commit();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fg_ceshi:
                if (f01.isAdded()) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.container, f02).commit();
                } else {
                    getSupportFragmentManager().beginTransaction().replace(R.id.container, f01).commit();
                }
                break;
        }
    }
}