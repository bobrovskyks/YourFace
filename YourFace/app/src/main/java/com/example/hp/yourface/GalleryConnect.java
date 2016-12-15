package com.example.hp.yourface;

import android.content.Intent;

public class GalleryConnect implements Connecting { // check the connection to the gallery
    public boolean Connect() {
        Intent intent = new Intent();
        intent.setType("image/*");
        if ((intent.setAction(Intent.ACTION_PICK)) != null) {
            return true;
        } else {
            return false;
        }
    }
}
