package com.example.hp.yourface;

import android.graphics.Bitmap;

public class SearchController { // Class control search
    Searching strategy;

    public void setStrategy(Searching strategy_) {
        strategy = strategy_;
    } // set strategy

    public String executeStrategy(Bitmap bitmap_) { // execution
        return (strategy.search(bitmap_));
    }
}
