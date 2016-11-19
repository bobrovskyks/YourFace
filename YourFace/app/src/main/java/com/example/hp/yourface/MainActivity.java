package com.example.hp.yourface;

import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutCompat;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;

public class MainActivity extends AppCompatActivity {

    final int REQUEST_CODE_PHOTO = 1;

    Button CreateButton;
    Button LoadButton;
    Button InfoButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        CreateButton = (Button) findViewById(R.id.Create);
        LoadButton = (Button) findViewById(R.id.Load);
        InfoButton = (Button) findViewById(R.id.Info);

        CreateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast toast = Toast.makeText(MainActivity.this, "Wait", Toast.LENGTH_SHORT);
                toast.show();
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, REQUEST_CODE_PHOTO);
            }
        });

        LoadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast toast = Toast.makeText(MainActivity.this, "Wait", Toast.LENGTH_SHORT);
                toast.show();
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent, 1);
            }
        });
        InfoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast toast = Toast.makeText(MainActivity.this, "Wait", Toast.LENGTH_SHORT);
               /* LinearLayout toastImage = (LinearLayout) toast.getView();
                ImageView imageView = new ImageView(MainActivity.this);
                imageView.setImageResource(R.drawable.logo);
                toastImage.addView(imageView,0);*/
                toast.show();
                Intent intentTwoActivity = new Intent(MainActivity.this, Main2Activity.class);
                startActivity(intentTwoActivity);
            }
        });
    }
}
