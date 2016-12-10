package com.example.hp.yourface;

import android.graphics.Bitmap;

public class Content {
    Search search = new Search();
    Internet internet = new Internet();

    int[] PictureColor(Bitmap _bitmap) {
        return search.averageARGB(_bitmap);
    }

    boolean CheckInternet() {
        return internet.isOnline();
    }
}
