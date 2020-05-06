package com.example.CommonTest.remind;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.*;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.example.CommonTest.R;
import com.github.jorgecastilloprz.FABProgressCircle;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import es.dmoral.toasty.Toasty;

import java.util.Calendar;
import java.util.Date;
import java.util.EventListener;
import java.util.Random;

public class Remind extends Activity implements EventListener {
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
    @BindView(R.id.mic_title)
    Button micTitle;
    @BindView(R.id.new_todo_dsc)
    EditText newTodoDsc;
    @BindView(R.id.mic_dsc)
    Button micDsc;
    @BindView(R.id.new_todo_date)
    TextView newTodoDate;
    @BindView(R.id.new_todo_time)
    TextView newTodoTime;
    @BindView(R.id.repeat)
    Switch repeat;
    @BindView(R.id.fab_ok)
    FloatingActionButton fabOk;
    @BindView(R.id.fabProgressCircle)
    FABProgressCircle fabProgressCircle;

    private static int[] imageArray = new int[]{R.drawable.img_1,
            R.drawable.img_2,
            R.drawable.img_3,
            R.drawable.img_4,
            R.drawable.img_5,
            R.drawable.img_6,
            R.drawable.img_7,
            R.drawable.img_8,};

    private int mYear,mMonth,mDay;//当前日期
    private int mHour,mMin;//当前时间
    private long remindTime;
    private Calendar ca;
    private int imgId;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_todo);
        ButterKnife.bind(this);
        setSupportActionBar(newToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ca = Calendar.getInstance();
        getDate();
        getTime();
        initPermission();
        initBaiduRecognizer();
        initView();
        initHeadImage();
        checkNotificationPermission();
    }
    private String todoDate = null, todoTime = null;
    private void initView() {
        fabOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (todoDate==null){
                    Toasty.info(Remind.this, "没有设置日期", Toast.LENGTH_SHORT, true).show();
                } else if (todoTime==null) {
                    Toasty.info(Remind.this, "没有设置提醒时间", Toast.LENGTH_SHORT, true).show();

                } else {
                    fabProgressCircle.show();
                    tod = nv_todo_title.getText().toString();
                    todoDsc = nv_todo_dsc.getText().toString();
                    Calendar calendarTime = Calendar.getInstance();
                    calendarTime.setTimeInMillis(System.currentTimeMillis());
                    calendarTime.set(Calendar.YEAR, mYear);
                    calendarTime.set(Calendar.MONTH, mMonth);
                    calendarTime.set(Calendar.DAY_OF_MONTH, mDay);
                    calendarTime.set(Calendar.HOUR_OF_DAY, mHour);
                    calendarTime.set(Calendar.MINUTE, mMin);
                    calendarTime.set(Calendar.SECOND, 0);
                    remindTime = calendarTime.getTimeInMillis();
                    Log.i(TAG, "时间是"+String.valueOf(remindTime));
                    //插入数据
                    User user = BmobUser.getCurrentUser(User.class);
                    todos = new Todos();
                    todos.setUser(user);
                    todos.setTitle(todoTitle);
                    todos.setDesc(todoDsc);
                    todos.setDate(todoDate);
                    todos.setTime(todoTime);
                    todos.setRemindTime(remindTime);
                    todos.setisAlerted(0);
                    todos.setIsRepeat(isRepeat);
                    todos.setImgId(imgId);
                    Date date = new Date(remindTime);
                    BmobDate bmobDate = new BmobDate(date);
                    todos.setBmobDate(bmobDate);

                    boolean isSync = (Boolean) SPUtils.get(getApplication(),"sync",true);
                    Log.i("ToDo", "isSync: " + isSync);

                    if (isSync){
                        //保存数据到Bmob
                        if(NetWorkUtils.isNetworkConnected(getApplication()) && User.getCurrentUser(User.class)!= null){
                            todos.save(new SaveListener<String>() {
                                @Override
                                public void done(String s, BmobException e) {
                                    if(e==null){
                                        //插入本地数据库
                                        new ToDoDao(getApplicationContext()).create(todos);
                                        Log.i("bmob","保存到Bmob成功 "+ todos.getObjectId());
                                        Log.i("bmob","保存到本地数据库成功 "+ todos.getObjectId());
//                                        Intent intent = new Intent(NewTodoActivity.this, MainActivity.class);
//                                        setResult(2, intent);
                                        startService(new Intent(NewTodoActivity.this, AlarmService.class));
                                        finish();
                                    }else{
                                        Log.i("bmob","保存到Bmob失败："+e.getMessage());
                                    }
                                }
                            });

                        } else {
                            Toasty.info(NewTodoActivity.this, "请先登录", Toast.LENGTH_SHORT, true).show();
                        }
                    } else {
                        new ToDoDao(getApplicationContext()).create(todos);
//                        Intent intent = new Intent(NewTodoActivity.this, MainActivity.class);
//                        setResult(2, intent);
                        startService(new Intent(NewTodoActivity.this, AlarmService.class));
                        finish();
                    }

                }

            }
        });


        nv_todo_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DatePickerDialog datePickerDialog = new DatePickerDialog(NewTodoActivity.this, onDateSetListener, mYear, mMonth, mDay);
                datePickerDialog.setCancelable(true);
                datePickerDialog.setCanceledOnTouchOutside(true);
                datePickerDialog.show();

            }
        });

        nv_todo_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                TimePickerDialog timePickerDialog = new TimePickerDialog(NewTodoActivity.this, onTimeSetListener, mHour,mMin, true);
                timePickerDialog.setCancelable(true);
                timePickerDialog.setCanceledOnTouchOutside(true);
                timePickerDialog.show();
            }
        });

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        nv_repeat.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked){
                    isRepeat = 1;
                } else {
                    isRepeat = 0;
                }

            }
        });

        mic_title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flag = R.id.mic_title;
                showVoiceDialog();
                start();
            }
        });

        mic_dsc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flag = R.id.mic_dsc;
                showVoiceDialog();
                start();
            }
        });

    }

    /**
     * 获取日期
     */
    private void getDate(){

        mYear = ca.get(Calendar.YEAR);
        mMonth = ca.get(Calendar.MONTH);
        mDay = ca.get(Calendar.DAY_OF_MONTH);
    }
    /**
     * 获取时间
     */
    private void getTime(){
        mHour = ca.get(Calendar.HOUR_OF_DAY);
        mMin = ca.get(Calendar.MINUTE);
    }
    private ImageView new_bg;
    private void initHeadImage(){

        Random random = new Random();
        imgId = imageArray[random.nextInt(8)];
        RequestOptions options = new RequestOptions()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .skipMemoryCache(true);
        Glide.with(getApplicationContext())
                .load(imgId)
                .apply(options)
                .into(new_bg);

    }
}
