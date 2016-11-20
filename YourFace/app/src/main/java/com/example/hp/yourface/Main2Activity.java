package com.example.hp.yourface;

import android.content.Intent;
import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class Main2Activity extends AppCompatActivity {
    static final int REQUEST_CODE_PHOTO = 1;
    Button click ;
    ImageView result;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main2);
        click = (Button) findViewById(R.id.button);
        result = (ImageView) findViewById(R.id.imageView2);
        Bundle extras = getIntent().getExtras().getBundle("data");
        Bitmap imageBitmap = (Bitmap) extras.get("data");
        result.setImageBitmap(imageBitmap);
        click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if(takePictureIntent.resolveActivity(getPackageManager())!=null) {
                    startActivityForResult(takePictureIntent, REQUEST_CODE_PHOTO);
                }
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode,
                                    Intent data) {
        if (requestCode == REQUEST_CODE_PHOTO && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            result.setImageBitmap(imageBitmap);
        }
    }
}
