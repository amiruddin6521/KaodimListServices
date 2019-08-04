package com.example.kaodimlistservices.model;

import java.util.List;

import com.example.kaodimlistservices.remote.Section;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ServiceSubgroup implements Section {

    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("service_types")
    @Expose
    private List<ServiceType> serviceTypes = null;
    private ImageThumbUrl imageThumbUrl;
    private int section;

    public ServiceSubgroup(int section) {
        this.section = section;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<ServiceType> getServiceTypes() {
        return serviceTypes;
    }

    public void setServiceTypes(List<ServiceType> serviceTypes) {
        this.serviceTypes = serviceTypes;
    }

    public ImageThumbUrl getImageThumbUrl() {
        return imageThumbUrl;
    }

    public void setImageThumbUrl(ImageThumbUrl imageThumbUrl) {
        this.imageThumbUrl = imageThumbUrl;
    }

    @Override
    public boolean isHeader() {
        return true;
    }

    @Override
    public int sectionPosition() {
        return section;
    }

}
