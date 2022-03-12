package com.enset.tp3_rest_api.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

public class News implements Serializable
{
    private String author;
    private String title;
    private String url;
    @SerializedName("urlToImage")
    private String Image;
    private Date publishedAt;
    private  String content;

    public String getAuthor() {
        return author;
    }

    public String getTitle() {
        return title;
    }

    public String getUrl() {
        return url;
    }

    public String getImage() {
        return Image;
    }

    public Date getPublishedAt()
    {
        return publishedAt;
    }

    public String getContent() {
        return content;
    }
}
