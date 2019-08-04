package com.example.kaodimlistservices.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ImageThumbUrl {

    @SerializedName("lg")
    @Expose
    private String lg;
    @SerializedName("md")
    @Expose
    private String md;
    @SerializedName("sm")
    @Expose
    private String sm;

    public String getLg() {
        return lg;
    }

    public void setLg(String lg) {
        this.lg = lg;
    }

    public String getMd() {
        return md;
    }

    public void setMd(String md) {
        this.md = md;
    }

    public String getSm() {
        return sm;
    }

    public void setSm(String sm) {
        this.sm = sm;
    }

}