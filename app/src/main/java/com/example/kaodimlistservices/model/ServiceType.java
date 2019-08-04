package com.example.kaodimlistservices.model;

import com.example.kaodimlistservices.remote.Section;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ServiceType implements Section {

    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("image_thumb_url")
    @Expose
    private ImageThumbUrl imageThumbUrl;
    private int section;

    public ServiceType(int section) {
        this.section = section;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ImageThumbUrl getImageThumbUrl() {
        return imageThumbUrl;
    }

    public void setImageThumbUrl(ImageThumbUrl imageThumbUrl) {
        this.imageThumbUrl = imageThumbUrl;
    }

    @Override
    public boolean isHeader() {
        return false;
    }

    @Override
    public int sectionPosition() {
        return section;
    }

}