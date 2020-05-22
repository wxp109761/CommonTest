package com.example.CommonTest.Camera;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.CommonTest.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CameraTest extends Activity {
    @BindView(R.id.local_camera)
    Button localCamera;
    @BindView(R.id.take_pic)
    Button takePic;
    @BindView(R.id.image_view)
    ImageView imageView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);
        ButterKnife.bind(this);
    }

    static final int REQUEST_IMAGE_CAPTURE = 1;

    @OnClick({R.id.local_camera, R.id.take_pic})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.local_camera:
                if(checkCameraHardware(this)){
                    Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                        startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
                    }
                }else {
                    Toast.makeText(this,"相机不可用",Toast.LENGTH_SHORT);
                }


                break;
            case R.id.take_pic:
                break;
        }
    }

    /** Check if this device has a camera */
    private boolean checkCameraHardware(Context context) {
        if (context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_ANY)){
            // this device has a camera
            return true;
        } else {
            // no camera on this device
            return false;
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            imageView.setImageBitmap(imageBitmap);
        }
    }
}
