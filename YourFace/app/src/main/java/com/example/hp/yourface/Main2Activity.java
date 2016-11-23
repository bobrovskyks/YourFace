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


public class Main2Activity extends AppCompatActivity {
    static final int REQUEST_CODE_PHOTO = 1;
    Button InternetButton;
    ImageView result;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main2);
        InternetButton = (Button) findViewById(R.id.searchinternet);
        result = (ImageView) findViewById(R.id.imageView2);
        Bundle extras = getIntent().getExtras().getBundle("data");
        Bitmap imageBitmap = (Bitmap) extras.get("data");
        result.setImageBitmap(imageBitmap);


        Toast toast = Toast.makeText(Main2Activity.this, "55555 faces", Toast.LENGTH_LONG);
        toast.show();

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

        for (int i = 0; i < faces.size(); i++) {
            Face thisFace = faces.valueAt(i);
            float x1 = thisFace.getPosition().x;
            float y1 = thisFace.getPosition().y;
            float x2 = x1 + thisFace.getWidth();
            float y2 = y1 + thisFace.getHeight();
            tempCanvas.drawRoundRect(new RectF(x1, y1, x2, y2), 2, 2, myRectPaint);
        }
        result.setImageDrawable(new BitmapDrawable(getResources(), tempBitmap));

        InternetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast toast = Toast.makeText(Main2Activity.this, "Browser running...", Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER, 0, 0);
                /*LinearLayout toastImage = (LinearLayout) toast.getView();
                ImageView imageView = new ImageView(Main2Activity.this);
                imageView.setImageResource(R.drawable.camera);
                toastImage.addView(imageView, 0);
                toast.show();
                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if(takePictureIntent.resolveActivity(getPackageManager())!=null) {
                    startActivityForResult(takePictureIntent, REQUEST_CODE_PHOTO);
                }*/


                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://yandex.by/images/"));
                startActivity(intent);

            }
        });
    }
   /* proverka na internet public boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }*/
        /*click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if(takePictureIntent.resolveActivity(getPackageManager())!=null) {
                    startActivityForResult(takePictureIntent, REQUEST_CODE_PHOTO);
                }
            }
        });*/

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
