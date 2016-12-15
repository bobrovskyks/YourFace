package com.example.hp.yourface;

public class Photo {
    String PhotoID;
    String Date;
    Connecting StrategyC;

    public void setStrategy(Connecting strategy) { // set strategy
        StrategyC = strategy;
    }

    public void executeStrategy() {
        StrategyC.Connect();
    }

    public String GetPhotoID() {
        return this.PhotoID;
    }

    public String GetPhotoDate() {
        return this.Date;
    }
}
