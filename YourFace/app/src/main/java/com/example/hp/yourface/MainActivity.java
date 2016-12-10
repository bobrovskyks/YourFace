package com.example.hp.yourface;

import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.media.session.PlaybackStateCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutCompat;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;
import android.os.Environment;

import com.google.android.gms.common.api.GoogleApiClient;

import java.io.File;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    final int REQUEST_CODE_PHOTO = 1;
    Button CreateButton;
    Button InfoButton;
    Content content = new Content();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        CreateButton = (Button) findViewById(R.id.Create);
        InfoButton = (Button) findViewById(R.id.Info);
        CreateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast toast = Toast.makeText(MainActivity.this, "Camera running...", Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER, 0, 0);
                LinearLayout toastImage = (LinearLayout) toast.getView();
                ImageView imageView = new ImageView(MainActivity.this);
                imageView.setImageResource(R.drawable.camera);
                toastImage.addView(imageView, 0);
                toast.show();
                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                    startActivityForResult(takePictureIntent, REQUEST_CODE_PHOTO);
                }
            }
        });

        InfoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast toast = Toast.makeText(MainActivity.this, "Mobile app YourFace." +
                        "Designed to detect faces in a photo and search for similar photos and save areas of people's faces." +
                        "\n" + "Version 1.7", Toast.LENGTH_LONG);
                toast.setGravity(Gravity.CENTER, 0, 0);
                LinearLayout toastImage = (LinearLayout) toast.getView();
                ImageView imageView = new ImageView(MainActivity.this);
                imageView.setImageResource(R.drawable.info);
                toastImage.addView(imageView, 0);
                toast.show();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode,
                                    Intent data) {
        if (requestCode == REQUEST_CODE_PHOTO && resultCode == RESULT_OK) {
            Intent intentTwoActivity = new Intent(MainActivity.this, Main2Activity.class);
            intentTwoActivity.putExtra("data", data.getExtras());
            startActivity(intentTwoActivity);
        }
    }
}
