package com.example.kaodimlistservices.remote;

import com.example.kaodimlistservices.model.ServiceSubgroup;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface GetDataService  {
    @GET("home")
    Call<List<ServiceSubgroup>> getHomeService();

    @GET("events")
    Call<List<ServiceSubgroup>> getEventService();
}
