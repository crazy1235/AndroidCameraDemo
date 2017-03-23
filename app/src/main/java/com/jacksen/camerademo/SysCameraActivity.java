package com.jacksen.camerademo;

import android.content.ClipData;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * use system camera demo
 *
 * @author jacksen
 */
public class SysCameraActivity extends AppCompatActivity {

    private static final int REQUEST_CODE_CAMERA = 0x0010;

    private ImageView imageView;
    private Uri imageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sys_camera);

        imageView = (ImageView) findViewById(R.id.imageView);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startCameraIntent();
            }
        });
    }


    /**
     * create image file
     *
     * @return
     */
    private File createImageFile() {
        String imageFileName = "image_" + System.currentTimeMillis();
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        try {
            return File.createTempFile(imageFileName, ".jpg", storageDir);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * start the camera to  take picture
     */
    private void startCameraIntent() {
        Intent takePicIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        // ensure that there is a camera activity to handle this intent
        if (takePicIntent.resolveActivity(getPackageManager()) != null) {
            File imageFile = createImageFile();

            if (imageFile != null) {
                imageUri = FileProvider.getUriForFile(this, BuildConfig.APPLICATION_ID + ".fileProvider", imageFile);
                // output uri
                takePicIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);


                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    takePicIntent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
                } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    ClipData clipData = ClipData.newUri(getContentResolver(), "a photo", imageUri);
                    takePicIntent.setClipData(clipData);
                    takePicIntent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
                } else {
                    List<ResolveInfo> resInfoList =
                            getPackageManager().queryIntentActivities(takePicIntent, PackageManager.MATCH_DEFAULT_ONLY);

                    for (ResolveInfo resolveInfo : resInfoList) {
                        String packageName = resolveInfo.activityInfo.packageName;
                        grantUriPermission(packageName, imageUri, Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
                    }
                }

                startActivityForResult(takePicIntent, REQUEST_CODE_CAMERA);

            } else {

            }

        } else {
            // device doesn't have camera
            Toast.makeText(this, "device doesn't have camera", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE_CAMERA) {
            if (resultCode == RESULT_OK) {
                /*Intent i = new Intent(Intent.ACTION_VIEW);
                i.setDataAndType(imageUri, "image/jpeg");
                i.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                try {
                    startActivity(i);
                } catch (ActivityNotFoundException e) {
                    Toast.makeText(this, "I cannot find an image viewer app that supports a content: Uri", Toast.LENGTH_LONG).show();
                }*/

                imageView.setImageURI(imageUri);

            }
        }

    }
}
