package com.example.go4lunch.model;

import java.util.List;

public class Photos {
    private List<Photo> photos;

    public Photos(List<Photo> photos) {
        this.photos = photos;
    }

    public List<Photo> getPhotos() {
        return photos;
    }

    public void setPhotos(List<Photo> photos) {
        this.photos = photos;
    }
}
