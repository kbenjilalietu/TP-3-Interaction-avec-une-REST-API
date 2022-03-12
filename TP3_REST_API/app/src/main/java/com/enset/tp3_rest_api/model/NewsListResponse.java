package com.enset.tp3_rest_api.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class NewsListResponse
{
    private int totalResults;
    @SerializedName("articles")
    private List<News> news= new ArrayList<>();

    public int getTotalResults() {
        return totalResults;
    }

    public List<News> getNews() {
        return news;
    }
}
