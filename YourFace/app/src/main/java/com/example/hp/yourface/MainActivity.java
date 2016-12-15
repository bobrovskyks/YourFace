package com.example.hp.yourface;

import android.content.Intent;
import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {  // the application's main window

    final int REQUEST_CODE_PHOTO = 1;
    Button CreateButton;
    Button InfoButton;
    Content content = new Content();
    VersionApp versionApp = new AdapterInternetConnect();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        CreateButton = (Button) findViewById(R.id.Create);
        InfoButton = (Button) findViewById(R.id.Info);
        CreateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { // when you press the camera button
                ToastShow("Camera running...", R.drawable.camera, 0);
                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                    startActivityForResult(takePictureIntent, REQUEST_CODE_PHOTO);
                }
            }
        });

        InfoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { // clicking on the information button
                ToastShow(versionApp.GetVersion(), R.drawable.info, 1);
            }
        });
    }

    public void ToastShow(String text_, int res_, int flag_) { // display toast
        Toast toast;
        if (flag_ == 0) {
            toast = Toast.makeText(MainActivity.this, text_, Toast.LENGTH_SHORT);
        } else {
            toast = Toast.makeText(MainActivity.this, text_, Toast.LENGTH_LONG);
        }
        toast.setGravity(Gravity.CENTER, 0, 0);
        LinearLayout toastImage = (LinearLayout) toast.getView();
        ImageView imageView = new ImageView(MainActivity.this);
        imageView.setImageResource(res_);
        toastImage.addView(imageView, 0);
        toast.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode,
                                    Intent data) { // onActivityResult
        if (requestCode == REQUEST_CODE_PHOTO && resultCode == RESULT_OK) {
            Intent intentTwoActivity = new Intent(MainActivity.this, SecondWindow.class);
            intentTwoActivity.putExtra("data", data.getExtras());
            startActivity(intentTwoActivity);
        }
    }
}
