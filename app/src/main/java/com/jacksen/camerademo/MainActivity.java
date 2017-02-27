package com.jacksen.camerademo;

import android.content.Intent;
import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

/**
 * @author jacksen
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final int REQUEST_CODE_CAMERA = 0x001;

    private Button sysCameraBtn;
    private Button sysCropBtn;
    private Button customCameraBtn;
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sysCameraBtn = (Button) findViewById(R.id.open_sys_camera);
        sysCropBtn = (Button) findViewById(R.id.open_sys_crop);
        customCameraBtn = (Button) findViewById(R.id.open_custom_camera);
        imageView = (ImageView) findViewById(R.id.capture_picture);

        sysCameraBtn.setOnClickListener(this);
        customCameraBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.open_sys_camera:

                openSysCamera();

                break;
            case R.id.open_sys_crop:

                break;
            case R.id.open_custom_camera:
                break;
            default:
                break;
        }
    }

    /**
     * 打开系统相机
     */
    private void openSysCamera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, REQUEST_CODE_CAMERA);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_CODE_CAMERA) {
                if (data != null && data.getExtras() != null) {
                    Bundle bundle = data.getExtras();
                    Bitmap bitmap = (Bitmap) bundle.get("data");
                    imageView.setImageBitmap(bitmap);
                }
            }
        }
    }
}
