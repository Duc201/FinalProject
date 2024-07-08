package com.example.urban_area_manager.feature.Auth.viewmodel;

import android.graphics.Bitmap;

public class ImageWrapper {
    private Bitmap bitmap;
    private String id;

    public ImageWrapper(Bitmap bitmap, String id) {
        this.bitmap = bitmap;
        this.id = id;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
