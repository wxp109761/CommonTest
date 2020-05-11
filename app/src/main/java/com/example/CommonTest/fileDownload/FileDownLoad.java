package com.example.CommonTest.fileDownload;

import android.app.DownloadManager;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.bigkoo.pickerview.TimePickerView;
import com.example.CommonTest.R;
import com.example.CommonTest.utils.FileUtils;
import com.flyco.dialog.listener.OnBtnClickL;
import com.flyco.dialog.widget.MaterialDialog;
import com.liulishuo.filedownloader.FileDownloader;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;

import static com.bigkoo.pickerview.view.WheelTime.dateFormat;

public class FileDownLoad extends AppCompatActivity {
    @BindView(R.id.btn_downlaod)
    Button btnDownlaod;
    DownloadManager downloadManager;
    String url = "http://192.168.43.45:9001/xjrecord/getExcelRecord/78714756973858818/XX/xx/xx/all";
    @BindView(R.id.time_picker)
    Button timePicker;
    @BindView(R.id.time_text)
    TextView timeText;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FileDownloader.setup(this);//注意作者已经不建议使用init方法
        setContentView(R.layout.file_download);
        ButterKnife.bind(this);
    }


    /**
     * 从服务器下载文件
     *
     * @param path     下载文件的地址
     * @param FileName 文件名字
     */
    public static void downLoad(final String path, final String FileName) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    URL url = new URL(path);
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
                    con.setReadTimeout(5000);
                    con.setConnectTimeout(5000);
                    con.setRequestProperty("Charset", "UTF-8");
                    con.setRequestMethod("GET");
                    if (con.getResponseCode() == 200) {
                        InputStream is = con.getInputStream();//获取输入流
                        FileOutputStream fileOutputStream = null;//文件输出流
                        if (is != null) {
                            FileUtils fileUtils = new FileUtils();
                            fileOutputStream = new FileOutputStream(fileUtils.createFile(FileName));//指定文件保存路径，代码看下一步
                            byte[] buf = new byte[1024];
                            int ch;
                            while ((ch = is.read(buf)) != -1) {
                                fileOutputStream.write(buf, 0, ch);//将获取到的流写入文件中
                            }
                            System.out.println("下载成功");
                        }
                        if (fileOutputStream != null) {
                            fileOutputStream.flush();
                            fileOutputStream.close();
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    @OnClick({R.id.btn_downlaod, R.id.time_picker})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_downlaod:
                downLoad(url, "巡检记录表.xls");
                final MaterialDialog dialog = new MaterialDialog(this);
                dialog.content(
                        "是否打开下载的文档")//
                        .btnText("取消", "确定")//
//                .showAnim(mBasIn)//
//                .dismissAnim(mBasOut)//
                        .show();

                dialog.setOnBtnClickL(
                        new OnBtnClickL() {//left btn click listener
                            @Override
                            public void onBtnClick() {

                                dialog.dismiss();
                            }
                        },
                        new OnBtnClickL() {//right btn click listener
                            @Override
                            public void onBtnClick() {
                                String fileUrl = Environment.getExternalStorageDirectory().toString() + "/shidoe/巡检记录表.xls";
                                Intent intent = new Intent(Intent.ACTION_VIEW);
                                File apkFile = new File(fileUrl);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                                    intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                                    Uri uri = FileProvider.getUriForFile(getBaseContext(), "com.example.labProvider", apkFile);
                                    intent.setDataAndType(uri, "application/vnd.ms-excel");
                                } else {
                                    intent.setDataAndType(Uri.fromFile(apkFile), "application/vnd.ms-excel");
                                }
                                startActivity(intent);
                                dialog.dismiss();
                            }
                        }
                );

                break;
            case R.id.time_picker:
                TimePickerView pvTime = new TimePickerView.Builder(this, new TimePickerView.OnTimeSelectListener() {
                    @Override
                    public void onTimeSelect(Date date, View v) {
                       String todoDate=dateFormat.format(date);
                        timeText.setText(todoDate);
                    }
                })
                        .setType(new boolean[]{true, true, true, true, true, false})// 默认全部显示
                        .setCancelText("取消")//取消按钮文字
                        .setSubmitText("确定")//确认按钮文字
                        .setContentSize(18)//滚轮文字大小
                         .setTitleSize(20)//标题文字大小
                        .setTitleText("日期时间选择")//标题文字
                        .setOutSideCancelable(true)//点击屏幕，点在控件外部范围时，是否取消显示
                        .isCyclic(true)//是否循环滚动
                        .setTitleColor(Color.BLACK)//标题文字颜色
                        .setSubmitColor(R.color._0091ea)//确定按钮文字颜色
                        .setCancelColor(R.color._0091ea)//取消按钮文字颜色
                        .setTitleBgColor(Color.WHITE)//标题背景颜色 Night mode
                        .setBgColor(Color.WHITE)//滚轮背景颜色 Night mode
                        //    .setDate(selectedDate)// 如果不设置的话，默认是系统时间*/
                        //    .setRangDate(startDate,endDate)//起始终止年月日设定
                        //.setLabel("年","月","日","时","分","秒")//默认设置为年月日时分秒
                        .isCenterLabel(false) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
                        //.isDialog(true)//是否显示为对话框样式
                        .build();

                pvTime.show();
                break;
        }
    }
}
