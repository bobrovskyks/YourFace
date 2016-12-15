package com.example.hp.yourface;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Environment;

import java.io.File;

import static java.lang.Math.abs;

public class GallerySearch implements Searching { // the search algorithm in the gallery
    public String path = null;
    static int percent = 26; // matching percentage

    public String Search(Bitmap bitmap_) {
        File[] fList;
        File F = new File(Environment.getExternalStorageDirectory() + "/YourFace");
        fList = F.listFiles();
        for (int i = 0; i < fList.length; i++) {
            if (fList[i].isFile()) {
                if (decode(fList[i].getName(), bitmap_) == true) {
                    path = fList[i].getName();
                    break;
                }
            }
        }
        return path;
    }

    boolean decode(String name_, Bitmap bitmap_) { // decode bitmap
        Bitmap bmp = BitmapFactory.decodeFile(Environment.getExternalStorageDirectory() + "/YourFace/" + name_);
        if (calculate(averageARGB(bmp), bitmap_) == true) {
            return true;
        } else {
            return false;
        }
    }

    int[] averageARGB(Bitmap pic) { // the algorithm of calculating colors
        int A, R, G, B;
        A = R = G = B = 0;
        int pixelColor;
        int width = pic.getWidth();
        int height = pic.getHeight();
        int size = width * height;

        for (int x = 0; x < width; ++x) {
            for (int y = 0; y < height; ++y) {
                pixelColor = pic.getPixel(x, y);
                A += Color.alpha(pixelColor);
                R += Color.red(pixelColor);
                G += Color.green(pixelColor);
                B += Color.blue(pixelColor);
            }
        }

        A /= size;
        R /= size;
        G /= size;
        B /= size;

        int[] average = {A, R, G, B};
        return average;
    }

    boolean calculate(int[] ARGB, Bitmap bitmap_) { // the comparison of the photos
        int[] ARGBColor;
        ARGBColor = averageARGB(bitmap_);
        if ((abs(ARGBColor[1] - ARGB[1]) + abs(ARGBColor[2] - ARGB[2])
                + abs(ARGBColor[3] - ARGB[3])) < percent) {
            return true;
        } else {
            return false;
        }
    }
}
