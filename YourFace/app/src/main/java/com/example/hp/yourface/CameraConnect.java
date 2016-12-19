package com.example.hp.yourface;

import android.content.Intent;

public class CameraConnect implements Connecting { // check the connection to the camera
    public boolean connect() {
        Intent intent = new Intent();
        if ((intent.setAction(Intent.ACTION_CAMERA_BUTTON)) != null) {
            return true;
        } else {
            return false;
        }
    }
}
