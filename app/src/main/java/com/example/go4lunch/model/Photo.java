package com.example.go4lunch.model;

import com.google.gson.annotations.SerializedName;

public class Photo {

    @SerializedName("photo_reference")
    private String photoReference;

    public Photo(){}

    public Photo(String photoReference) {
        this.photoReference = photoReference;
    }

    public String getPhotoReference() {
        return photoReference;
    }

    public void setPhotoReference(String photoReference) {
        this.photoReference = photoReference;
    }
}
