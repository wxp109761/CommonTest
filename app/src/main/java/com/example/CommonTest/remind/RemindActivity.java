package com.example.CommonTest.remind;

import android.app.Activity;
import android.os.Bundle;
import android.widget.FrameLayout;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.example.CommonTest.R;

public class RemindActivity extends AppCompatActivity {
    @BindView(R.id.fg_container)
    FrameLayout fgContainer;

    TodoFragment todoFragment=new TodoFragment();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo);
        ButterKnife.bind(this);

        getSupportFragmentManager().beginTransaction().add(R.id.fg_container,todoFragment).commit();
    }
}
