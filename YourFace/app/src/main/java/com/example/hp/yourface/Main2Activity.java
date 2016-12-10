package com.example.hp.yourface;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.SparseArray;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.gms.vision.Frame;
import com.google.android.gms.vision.face.Face;
import com.google.android.gms.vision.face.FaceDetector;
import com.google.android.gms.vision.face.Landmark;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;


public class Main2Activity extends AppCompatActivity {
    static final int REQUEST_CODE_PHOTO = 1;
    final int TYPE_PHOTO = 1;
    Button InternetButton, GalleryButton, SaveButton;
    ImageView result;
    File directory;
    Bitmap imageBitmap;
    float x1 = 0, y1 = 0, x2 = 0, y2 = 0;
    int count = 0;
    Content content = new Content();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main2);
        createDirectory();
        InternetButton = (Button) findViewById(R.id.searchinternet);
        SaveButton = (Button) findViewById(R.id.save);
        GalleryButton = (Button) findViewById(R.id.searchgallery);
        result = (ImageView) findViewById(R.id.imageView2);
        Bundle extras = getIntent().getExtras().getBundle("data");
        imageBitmap = (Bitmap) extras.get("data");
        result.setImageBitmap(imageBitmap);
        Paint myRectPaint = new Paint();
        myRectPaint.setStrokeWidth(1);
        myRectPaint.setColor(Color.GREEN);
        myRectPaint.setStyle(Paint.Style.STROKE);
        Bitmap tempBitmap = Bitmap.createBitmap(imageBitmap.getWidth(), imageBitmap.getHeight(), Bitmap.Config.RGB_565);
        Canvas tempCanvas = new Canvas(tempBitmap);
        tempCanvas.drawBitmap(imageBitmap, 0, 0, null);

        FaceDetector faceDetector = new FaceDetector.Builder(getApplicationContext()).setTrackingEnabled(false).build();
        if (!faceDetector.isOperational()) {
            Toast.makeText(this, "Cold not set up the Face Detector!", Toast.LENGTH_LONG).show();
            return;
        }

        Frame frame = new Frame.Builder().setBitmap(imageBitmap).build();
        SparseArray<Face> faces = faceDetector.detect(frame);
        Toast toast = Toast.makeText(Main2Activity.this, faces.size() + " faces detected", Toast.LENGTH_LONG);
        toast.show();
        count = faces.size();
        for (int i = 0; i < faces.size(); i++) {
            Face thisFace = faces.valueAt(i);
            x1 = thisFace.getPosition().x;
            y1 = thisFace.getPosition().y;
            x2 = x1 + thisFace.getWidth();
            y2 = y1 + thisFace.getHeight();
            tempCanvas.drawRoundRect(new RectF(x1, y1, x2, y2), 2, 2, myRectPaint);
        }
        result.setImageDrawable(new BitmapDrawable(getResources(), tempBitmap));

        InternetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (count) {
                    case 0: {
                        Toast toast = Toast.makeText(Main2Activity.this, "No face in the photo(", Toast.LENGTH_SHORT);
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        toast.show();
                        break;
                    }
                    case 1: {
                        SavePicture(CreateSmallBitmap(imageBitmap));
                        Toast toast = Toast.makeText(Main2Activity.this, "Browser running...", Toast.LENGTH_SHORT);
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://yandex.by/images/"));
                        startActivity(intent);
                        break;
                    }
                    default: {
                        SavePicture(imageBitmap);
                        Toast toast = Toast.makeText(Main2Activity.this, "Browser running...", Toast.LENGTH_SHORT);
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://yandex.by/images/"));
                        startActivity(intent);
                        break;
                    }
                }
            }
        });

        GalleryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int[] mas;
                mas = content.PictureColor(imageBitmap);
                Toast toast = Toast.makeText(Main2Activity.this, mas[0] + " " + mas[1] + " " + mas[2] + " " + mas[3] + " ", Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
            }
        });

        SaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (count == 1) {
                    Toast toast = Toast.makeText(Main2Activity.this, "Save...", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                    SavePicture(CreateSmallBitmap(imageBitmap));
                }
                if (count == 0) {
                    Toast toast = Toast.makeText(Main2Activity.this, "No face in the photo(", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                }
                if (count > 1) {
                    Toast toast = Toast.makeText(Main2Activity.this, "More than one person in the photo. Take a picture again)", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                }
            }
        });

    }

    Bitmap CreateSmallBitmap(Bitmap _b) {
        Bitmap bmp = Bitmap.createBitmap(_b, (int) x1, (int) y1, (int) (x2 - x1), (int) (y2 - y1));
        bmp.setDensity(Bitmap.DENSITY_NONE);
        return bmp;
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

    private String SavePicture(Bitmap bmp) {
        try {
            File file = new File(Environment.getExternalStorageDirectory() + "/YourFace", System.currentTimeMillis() / 1000 + ".jpg");
            FileOutputStream fOut = new FileOutputStream(file);
            Toast toast = Toast.makeText(Main2Activity.this, Environment.getExternalStorageDirectory() + "/YourFace", Toast.LENGTH_SHORT);
            toast.show();
            bmp.compress(Bitmap.CompressFormat.JPEG, 100, fOut);
            fOut.flush();
            fOut.close();
        } catch (Exception e) {
            Toast toast = Toast.makeText(Main2Activity.this, "Error(", Toast.LENGTH_SHORT);
            toast.show();
            return e.getMessage();
        }
        return "";
    }

    private static String getGalleryPath() {
        return Environment.getExternalStorageDirectory() + "/";
    }

    private Uri generateFileUri(int type) {
        File file = null;
        switch (type) {
            case TYPE_PHOTO:
                file = new File(directory.getPath() + "/" + "photo_"
                        + System.currentTimeMillis() + ".jpg");
                break;
        }
        return Uri.fromFile(file);
    }

    private void createDirectory() {
        directory = new File(
                Environment.getExternalStorageDirectory(),
                "YourFace");
        if (!directory.exists())
            directory.mkdirs();
    }

}
