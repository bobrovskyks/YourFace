package com.example.hp.yourface;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Environment;
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

import java.io.File;
import java.io.FileOutputStream;


public class SecondWindow extends AppCompatActivity { // the second window in the app
    static final int REQUEST_CODE_PHOTO = 1;
    final int TYPE_PHOTO = 1;
    Button internetButton, galleryButton, saveButton;
    ImageView result;
    File directory;
    public Bitmap imageBitmap;
    float x1 = 0, y1 = 0, x2 = 0, y2 = 0;
    int count = 0;
    Content content = new Content(); // create a facade

    @Override
    protected void onCreate(Bundle savedInstanceState) { // the window creation
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main2);
        createDirectory();
        internetButton = (Button) findViewById(R.id.searchinternet);
        saveButton = (Button) findViewById(R.id.save);
        galleryButton = (Button) findViewById(R.id.searchgallery);
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
        if (!faceDetector.isOperational()) { // Cold not set up the Face Detector!
            Toast.makeText(this, "Cold not set up the Face Detector!", Toast.LENGTH_LONG).show();
            return;
        }

        Frame frame = new Frame.Builder().setBitmap(imageBitmap).build();
        SparseArray<Face> faces = faceDetector.detect(frame);
        Toast toast = Toast.makeText(SecondWindow.this, faces.size() + " faces detected", Toast.LENGTH_LONG);
        toast.show();
        count = faces.size();
        for (int i = 0; i < faces.size(); i++) { // draw rectangles
            Face thisFace = faces.valueAt(i);
            x1 = thisFace.getPosition().x;
            y1 = thisFace.getPosition().y;
            x2 = x1 + thisFace.getWidth();
            y2 = y1 + thisFace.getHeight();
            tempCanvas.drawRoundRect(new RectF(x1, y1, x2, y2), 2, 2, myRectPaint);
        }
        result.setImageDrawable(new BitmapDrawable(getResources(), tempBitmap));

        internetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { // when you click on the search button on the Internet
                switch (count) {
                    case 0: {
                        toastShow("No face in the photo(");
                        break;
                    }
                    case 1: {
                        SavePicture(createSmallBitmap(imageBitmap));
                        toastShow("Browser running...");
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://yandex.by/images/"));
                        startActivity(intent);
                        break;
                    }
                    default: {
                        SavePicture(imageBitmap);
                        toastShow("Browser running...");
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://yandex.by/images/"));
                        startActivity(intent);
                        break;
                    }
                }
            }
        });

        galleryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { // when you click on the search button in the gallery
                String path = content.gallerySearch(imageBitmap);
                if (path != null) {
                    result.setImageBitmap(BitmapFactory.decodeFile(Environment.getExternalStorageDirectory() + "/YourFace/" + path));
                } else {
                    toastShow("No similar(");
                }
            }
        });

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { // when you click on the save button
                if (count == 1) {
                    toastShow("Save...");
                    SavePicture(createSmallBitmap(imageBitmap));
                }
                if (count == 0) {
                    toastShow("No face in the photo(");
                }
                if (count > 1) {
                    toastShow("More than one person in the photo. Take a picture again)");
                }
            }
        });

    }

    Bitmap createSmallBitmap(Bitmap _b) { // create a small bitmap
        Bitmap bmp = Bitmap.createBitmap(_b, (int) x1, (int) y1, (int) (x2 - x1), (int) (y2 - y1));
        bmp.setDensity(Bitmap.DENSITY_NONE);
        return bmp;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode,
                                    Intent data) { // onActivityResult
        if (requestCode == REQUEST_CODE_PHOTO && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            result.setImageBitmap(imageBitmap);
        }
    }

    private String SavePicture(Bitmap bmp) { // save to a folder
        try {
            File file = new File(Environment.getExternalStorageDirectory() + "/YourFace",
                    System.currentTimeMillis() / 1000 + ".jpg");
            FileOutputStream fOut = new FileOutputStream(file);
            Toast toast = Toast.makeText(SecondWindow.this, Environment.getExternalStorageDirectory()
                    + "/YourFace", Toast.LENGTH_SHORT);
            toast.show();
            bmp.compress(Bitmap.CompressFormat.JPEG, 100, fOut);
            fOut.flush();
            fOut.close();
        } catch (Exception e) {
            Toast toast = Toast.makeText(SecondWindow.this, "Error(", Toast.LENGTH_SHORT);
            toast.show();
            return e.getMessage();
        }
        return "";
    }

    public void toastShow(String text_) { // display toast
        Toast toast = Toast.makeText(SecondWindow.this, text_, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

    private void createDirectory() { // create a folder
        directory = new File(
                Environment.getExternalStorageDirectory(),
                "YourFace");
        if (!directory.exists())
            directory.mkdirs();
    }

}
