package com.enset.tp3_rest_api.service;

import com.enset.tp3_rest_api.model.NewsListResponse;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface RestNewsAPI
{
    @GET("everything")
    Call<NewsListResponse> searchNews(
            @Query("q") String kye,
            @Query("from") LocalDate date,
            @Query("apiKey") String apiKey);

}
