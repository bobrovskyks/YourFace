package com.example.hp.yourface;

public class Photo {
    String photoID;
    String date;
    Connecting strategyC;

    public void setStrategy(Connecting strategy) { // set strategy
        strategyC = strategy;
    }

    public void executeStrategy() {
        strategyC.connect();
    }

    public String getPhotoID() {
        return this.photoID;
    }

    public String getPhotoDate() {
        return this.date;
    }
}
