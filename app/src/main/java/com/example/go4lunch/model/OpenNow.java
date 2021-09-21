package com.example.go4lunch.model;

import com.google.gson.annotations.SerializedName;

public class OpenNow {
    @SerializedName("open_now")
    private Boolean openNow;

    public OpenNow(){}

    public OpenNow(Boolean openNow) {
        this.openNow = openNow;
    }

    public Boolean isOpenNow() {
        return openNow;
    }

    public void setOpenNow(Boolean openNow) {
        this.openNow = openNow;
    }
}
