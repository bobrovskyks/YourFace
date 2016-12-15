package com.example.hp.yourface;

import android.graphics.Bitmap;

public class SearchController { // Class control search
    Searching Strategy;

    public void setStrategy(Searching strategy) {
        Strategy = strategy;
    } // set strategy

    public String executeStrategy(Bitmap bitmap_) { // execution
        return (Strategy.Search(bitmap_));
    }
}
