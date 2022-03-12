package com.enset.tp3_rest_api;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.icu.text.SimpleDateFormat;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.enset.tp3_rest_api.model.News;

import java.net.URL;
import java.util.Date;

public class NewsDetail extends AppCompatActivity
{
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.news_detail_layout);

        Intent intent = getIntent();
        News news = (News) intent.getSerializableExtra("news");
        //String image = intent.getStringExtra("news.image");


        ImageView imageNewsDetail = findViewById(R.id.imageNewsDetail);
        TextView textTitleNewsDetail = findViewById(R.id.textTitleNewsDetail);
        TextView textAuthorNewsDetail = findViewById(R.id.textAuthorNewsDetail);
        TextView textDateNewsDetail = findViewById(R.id.textDateNewsDetail);
        TextView textContentNewsDetail = findViewById(R.id.textContentNewsDetail);
        TextView textUrlNewsDetail = findViewById(R.id.textUrlNewsDetail);

        setTitle(news.getTitle());


       textTitleNewsDetail.setText(news.getTitle());
       textAuthorNewsDetail.setText(news.getAuthor());

        //formatting date in Java using SimpleDateFormat
        Date time = news.getPublishedAt();
        SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd-MMM-YYY HH:mm:SS");
        String date = DATE_FORMAT.format(time);
        textDateNewsDetail.setText(date);

       textContentNewsDetail.setText(news.getContent());
       textUrlNewsDetail.setText(news.getUrl());



       try
        {
            URL urlimage = new URL(news.getImage());
            Bitmap bitmap = BitmapFactory.decodeStream(urlimage.openStream());
            imageNewsDetail.setImageBitmap(bitmap);
        }
        catch (Exception e) {
            e.printStackTrace();
        }


    }
}
