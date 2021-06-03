package com.example.eventspoc.retrofit;
import com.example.eventspoc.models.GetAllEvents;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiInterface {
    @GET("events")
    Call<GetAllEvents> getEvents(@Query("apikey") String apikey,
                                 @Query("radius") String radius,
                                 @Query("unit") String unit,
                                 @Query("startDateTime") String startDate,
                                 @Query("endDateTime") String endDate,
                                 @Query("city") String cityName);

}
