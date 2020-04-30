package com.example.video;

import android.app.Activity;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.*;
import android.util.Log;
import android.view.*;
import android.widget.*;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.example.adapter.VideoAdapter;
import com.example.bean.Video;
import com.example.CommonTest.R;
import com.example.utils.FileManager;
import com.example.utils.Utils;

import java.util.List;

public class VideoActivity extends Activity implements  View.OnTouchListener, GestureDetector.OnGestureListener, VideoAdapter.ClickInterface {
    @BindView(R.id.vv_player)
    VideoView videoView;
    @BindView(R.id.sb_play)
    SeekBar sbPlay;
    @BindView(R.id.iv_playControl)
    ImageView ivPlayControl;
    @BindView(R.id.tv_currentTime)
    TextView tvCurrentTime;
    @BindView(R.id.tv_totalTime)
    TextView tvTotalTime;
    @BindView(R.id.ll_playControl)
    LinearLayout llPlayControl;
    @BindView(R.id.iv_screenSwitch)
    ImageView ivScreenSwitch;
    @BindView(R.id.iv_volume)
    ImageView ivVolume;
    @BindView(R.id.sb_volume)
    SeekBar sbVolume;
    @BindView(R.id.ll_volumeControl)
    LinearLayout llVolumeControl;
    @BindView(R.id.ll_control)
    LinearLayout llControl;
    @BindView(R.id.rl_video)
    RelativeLayout rlVideo;

    @BindView(R.id.recycle_view)
    RecyclerView recycleView;

    private static final int UPDATE_TIME = 1;
    private int screenWidth;
    private int screenHeight;
    private AudioManager audioManager;
    //手势识别
    private GestureDetector detector;
    private VolumeReceiver volumeReceiver;

    private int currentPosition;
    List<Video> videoList;
    private Handler uiHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            int currentTime = videoView.getCurrentPosition();
            Utils.updateTimeFormat(tvCurrentTime, currentTime);
            sbPlay.setProgress(currentTime);
            uiHandler.sendEmptyMessageDelayed(UPDATE_TIME, 500);
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.video_activity);
        ButterKnife.bind(this);

        screenWidth = getResources().getDisplayMetrics().widthPixels;
        screenHeight = getResources().getDisplayMetrics().heightPixels;
        audioManager = (AudioManager) getSystemService(AUDIO_SERVICE);

        detector = new GestureDetector(this, this);
        FileManager fileManager = FileManager.getInstance(this);
        videoList = fileManager.getVideos();
        initUI();
        //注册音量变化广播接收器
        volumeReceiver = new VolumeReceiver(VideoActivity.this, ivVolume, sbVolume);
        IntentFilter filter = new IntentFilter();
        filter.addAction("android.media.VOLUME_CHANGED_ACTION");
        registerReceiver(volumeReceiver, filter);
        //为videoView设置视频路径
        String path = Environment.getDataDirectory().getAbsolutePath();

        Log.d("XXX", path);
        videoView.setVideoPath(videoList.get(0).getPath());

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        //GridLayoutManager layoutManager = new GridLayoutManager(this,5);

        //layoutManager.setOrientation(RecyclerView.HORIZONTAL);
        recycleView.setLayoutManager(layoutManager);
        VideoAdapter adapter = new VideoAdapter(videoList, this);
        recycleView.setAdapter(adapter);
        adapter.setOnclick(this);

    }

    private void initUI() {
        sbVolume.setMax(audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC));
        sbVolume.setProgress(audioManager.getStreamVolume(AudioManager.STREAM_MUSIC));
        initEvent();
    }

    private void initEvent() {
        sbPlay.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser) {
                    videoView.seekTo(progress);
                    Utils.updateTimeFormat(tvCurrentTime, progress);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                uiHandler.removeMessages(UPDATE_TIME);
                if (!videoView.isPlaying()) {
                    setPlayStatus();
                    videoView.start();
                }
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                uiHandler.sendEmptyMessage(UPDATE_TIME);
            }
        });
        sbVolume.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, progress, 0);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                ivPlayControl.setImageResource(R.drawable.ic_video_next);
                videoView.seekTo(0);
                sbPlay.setProgress(0);
                Utils.updateTimeFormat(tvCurrentTime, 0);
                videoView.pause();
                uiHandler.removeMessages(UPDATE_TIME);
            }
        });

        videoView.setOnTouchListener(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (videoView.canPause()) {
            setPauseStatus();
            videoView.pause();
            currentPosition = videoView.getCurrentPosition();
        }
        if (uiHandler.hasMessages(UPDATE_TIME)) {
            uiHandler.removeMessages(UPDATE_TIME);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (videoView.canSeekForward()) {
            videoView.seekTo(currentPosition);
        }
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            setSystemUiHide();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (videoView.canPause()) {
            setPauseStatus();
            videoView.pause();
        }
        if (uiHandler.hasMessages(UPDATE_TIME)) {
            uiHandler.removeMessages(UPDATE_TIME);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (videoView.canPause()) {
            videoView.pause();
        }
        if (uiHandler.hasMessages(UPDATE_TIME)) {
            uiHandler.removeMessages(UPDATE_TIME);
        }
        unregisterReceiver(volumeReceiver);
    }


    /**
     * 设置播放状态
     */
    private void setPlayStatus() {
        ivPlayControl.setImageResource(R.drawable.ic_video_pause);
        sbPlay.setMax(videoView.getDuration());
        Utils.updateTimeFormat(tvTotalTime, videoView.getDuration());
    }

    /**
     * 设置暂停状态
     */
    private void setPauseStatus() {
        ivPlayControl.setImageResource(R.drawable.ic_video_next);
    }


    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        screenWidth = getResources().getDisplayMetrics().widthPixels;
        screenHeight = getResources().getDisplayMetrics().heightPixels;
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            setSystemUiHide();
            setVideoViewScale(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            ivScreenSwitch.setImageResource(R.drawable.ic_video_shrink);
            llVolumeControl.setVisibility(View.VISIBLE);
        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            setVideoViewScale(ViewGroup.LayoutParams.MATCH_PARENT, Utils.dp2px(VideoActivity.this, 240f));
            ivScreenSwitch.setImageResource(R.drawable.ic_video_expand);
            llVolumeControl.setVisibility(View.GONE);
            setSystemUiVisible();
        }
    }

    private void setSystemUiHide() {
        if (Build.VERSION.SDK_INT >= 19) {
            View decorView = getWindow().getDecorView();
            decorView.setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        }
    }

    private void setSystemUiVisible() {
        if (Build.VERSION.SDK_INT >= 19) {
            View decorView = getWindow().getDecorView();
            decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);
        }
    }

    /**
     * 设置布局大小
     *
     * @param width  宽度
     * @param height 高度
     */
    private void setVideoViewScale(int width, int height) {
        ViewGroup.LayoutParams params =rlVideo.getLayoutParams();
        params.width = width;
        params.height = height;
        rlVideo.setLayoutParams(params);
        ViewGroup.LayoutParams layoutParams = videoView.getLayoutParams();
        layoutParams.width = width;
        layoutParams.height = height;
        videoView.setLayoutParams(layoutParams);
    }

    @Override
    public void onBackPressed() {
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            ivScreenSwitch.setImageResource(R.drawable.ic_video_shrink);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onDown(MotionEvent e) {
        if (llControl.getVisibility() == View.VISIBLE) {
            llControl.setVisibility(View.GONE);
        } else {
            llControl.setVisibility(View.VISIBLE);
        }
        return true;
    }

    @Override
    public void onShowPress(MotionEvent e) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        return true;
    }

    @Override
    public void onLongPress(MotionEvent e) {

    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        float offsetX = e1.getX() - e2.getX();
        float offsetY = e1.getY() - e2.getY();
        float absOffsetX = Math.abs(offsetX);
        float absOffsetY = Math.abs(offsetY);
        if ((e1.getX() < screenWidth / 2) && (e2.getX() < screenWidth / 2) && (absOffsetX < absOffsetY)) {
            changeBrightness(offsetY);
        } else if ((e1.getX() > screenWidth / 2) && (e2.getX() > screenWidth / 2) && (absOffsetX < absOffsetY)) {
            changeVolume(offsetY);
        }
        return true;
    }

    private void changeVolume(float offset) {
        int maxVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        int currentVolume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
        int index = (int) (offset / screenHeight * maxVolume);
        int volume = Math.max(currentVolume + index, 0);
        volume = Math.min(volume, maxVolume);
        audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, volume, 0);
        sbVolume.setProgress(volume);
    }

    private void changeBrightness(float offset) {
        WindowManager.LayoutParams attributes = getWindow().getAttributes();
        float brightness = attributes.screenBrightness;
        float index = offset / screenHeight / 2;
        brightness = Math.max(brightness + index, WindowManager.LayoutParams.BRIGHTNESS_OVERRIDE_OFF);
        brightness = Math.min(WindowManager.LayoutParams.BRIGHTNESS_OVERRIDE_FULL, brightness);
        attributes.screenBrightness = brightness;
        getWindow().setAttributes(attributes);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return detector.onTouchEvent(event);
    }


    @Override
    public void onItemClick(int position) {
        videoView.setVideoPath(videoList.get(position).getPath());
        Log.d("XXX", position + "");
    }

    @OnClick({R.id.iv_playControl, R.id.iv_screenSwitch})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_playControl:
                //播放暂停
                if (videoView.isPlaying()) {
                    setPauseStatus();
                    videoView.pause();
                    uiHandler.removeMessages(UPDATE_TIME);
                } else {
                    setPlayStatus();
                    videoView.start();
                    uiHandler.sendEmptyMessage(UPDATE_TIME);
                }
                break;
            //放大缩小
            case R.id.iv_screenSwitch:
                if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                    ivScreenSwitch.setImageResource(R.drawable.ic_video_shrink);
                } else if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                    ivScreenSwitch.setImageResource(R.drawable.ic_video_expand);
                }
                break;
        }
    }
}
