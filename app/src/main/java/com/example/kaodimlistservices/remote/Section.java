package com.example.kaodimlistservices.remote;

import com.example.kaodimlistservices.model.ImageThumbUrl;

public interface Section {
    boolean isHeader();

    String getName();
    ImageThumbUrl getImageThumbUrl();

    int sectionPosition();
}
