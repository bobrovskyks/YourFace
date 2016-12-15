package com.example.hp.yourface;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;

public class InternetSearch implements Searching { // search on the Internet.
    public String Search(Bitmap bitmap_) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://yandex.by/images/" + bitmap_));
        return null;
    }
}
