package com.example.CommonTest.remind;

import android.content.Intent;
import android.os.Bundle;
import android.widget.FrameLayout;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.example.CommonTest.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import top.wefor.circularanim.CircularAnim;

public class RemindActivity extends AppCompatActivity {
    @BindView(R.id.fg_container)
    FrameLayout fgContainer;

    TodoFragment todoFragment = new TodoFragment();
    @BindView(R.id.fab)
    FloatingActionButton fab;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo);
        ButterKnife.bind(this);

        getSupportFragmentManager().beginTransaction().add(R.id.fg_container, todoFragment).commit();
    }

    @OnClick(R.id.fab)
    public void onViewClicked() {
        CircularAnim.fullActivity(RemindActivity.this,fab.getRootView())
                .go(new CircularAnim.OnAnimationEndListener() {
                    @Override
                    public void onAnimationEnd() {
                        Intent intent = new Intent(RemindActivity.this, newRemindActivity.class);
                        startActivityForResult(intent,1);
                    }
                });
    }
}
