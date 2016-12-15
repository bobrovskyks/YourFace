package com.example.hp.yourface;

import android.graphics.Bitmap;

public class Content { // facade
    SearchController SearchController_ = new SearchController();

    public String InternetSearch(Bitmap bitmap_) {
        SearchController_.setStrategy(new InternetSearch());
        return (SearchController_.executeStrategy(bitmap_));
    }

    public String GallerySearch(Bitmap bitmap_) {
        SearchController_.setStrategy(new GallerySearch());
        return (SearchController_.executeStrategy(bitmap_));
    }
}
