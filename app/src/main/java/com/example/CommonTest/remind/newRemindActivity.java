package com.example.CommonTest.remind;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.example.CommonTest.R;
import com.example.CommonTest.alarmClock.alarmActivity;
import com.github.jorgecastilloprz.FABProgressCircle;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.manu.mdatepicker.MDatePickerDialog;
import es.dmoral.toasty.Toasty;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.EventListener;
import java.util.Random;

public class newRemindActivity extends Activity implements EventListener {
    @BindView(R.id.new_bg)
    ImageView newBg;
    @BindView(R.id.new_toolbar)
    Toolbar newToolbar;
    @BindView(R.id.toolbar_layout)
    CollapsingToolbarLayout toolbarLayout;
    @BindView(R.id.app_bar)
    AppBarLayout appBar;
    @BindView(R.id.new_todo_title)
    EditText newTodoTitle;

    @BindView(R.id.new_todo_dsc)
    EditText newTodoDsc;

    @BindView(R.id.new_todo_date)
    TextView newTodoDate;

    @BindView(R.id.fab_ok)
    FloatingActionButton fabOk;
    @BindView(R.id.fabProgressCircle)
    FABProgressCircle fabProgressCircle;


    String TAG = "Readmind";
    private static int[] imageArray = new int[]{R.drawable.img_1,
            R.drawable.img_2,
            R.drawable.img_3,
            R.drawable.img_4,
            R.drawable.img_5,
            R.drawable.img_6,
            R.drawable.img_7,
            R.drawable.img_8,};

    private int imgId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_todo);
        ButterKnife.bind(this);
        // setSupportActionBar(newToolbar);
        // getSupportActionBar().setDisplayShowTitleEnabled(false);
        // getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        // initPermission();
        // initBaiduRecognizer();

        initHeadImage();
        // checkNotificationPermission();
    }

    private String todoDate = null, todoTime = null;


    private void initHeadImage() {

        Random random = new Random();
        imgId = imageArray[random.nextInt(8)];
        RequestOptions options = new RequestOptions()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .skipMemoryCache(true);
        Glide.with(getApplicationContext())
                .load(imgId)
                .apply(options)
                .into(newBg);

    }

    @OnClick({R.id.new_todo_date, R.id.fab_ok})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.new_todo_date:
                MDatePickerDialog dialog = new MDatePickerDialog.Builder(this)
                        //附加设置(非必须,有默认值)
                        .setCanceledTouchOutside(true)
                        .setGravity(Gravity.BOTTOM)
                        .setSupportTime(true)
                        .setTitle("日期--时间")
                        .setTwelveHour(true)
                        .setCanceledTouchOutside(false)
                        .setCancelStatus(14f,R.color.textColorPrimary)
                        .setConfirmStatus(14f,R.color.textColorPrimary)
                        //结果回调(必须)
                        .setOnDateResultListener(new MDatePickerDialog.OnDateResultListener() {
                            @Override
                            public void onDateResult(long date) {
                                Calendar calendar = Calendar.getInstance();
                                calendar.setTimeInMillis(date);
                                SimpleDateFormat dateFormat = (SimpleDateFormat) SimpleDateFormat.getDateInstance();
                                dateFormat.applyPattern("yyyy-MM-dd HH:mm:ss");
                                Toast.makeText(newRemindActivity.this, dateFormat.format(new Date(date)), Toast.LENGTH_SHORT).show();
                                todoDate=dateFormat.format(new Date((date)));
                                newTodoDate.setText(todoDate);
                            }
                        })
                        .build();
                dialog.show();
                break;
            case R.id.fab_ok:
                if (todoDate == null) {
                    Toasty.info(newRemindActivity.this, "没有设置日期--时间", Toast.LENGTH_SHORT, true).show();
                } else {
                    fabProgressCircle.show();
                    startService(new Intent(newRemindActivity.this, AlarmService.class));
                    finish();
                }
                break;
        }
    }
}
