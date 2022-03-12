package com.enset.tp3_rest_api;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.enset.tp3_rest_api.model.News;
import com.enset.tp3_rest_api.model.NewsListAdapter;
import com.enset.tp3_rest_api.model.NewsListResponse;
import com.enset.tp3_rest_api.service.RestNewsAPI;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity
{
    private DatePickerDialog picker;
    private TextView editTextDate;
    public static final String API_KEY="2344af3a20884e18a2d000a3414bdce5";
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        StrictMode.ThreadPolicy strictMode=new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(strictMode);

        Button buttonSearch = findViewById(R.id.buttonSearch);
        //buttonSearch.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#EBE645")));
        EditText editTextKey = findViewById(R.id.editTextKey);
        editTextDate = findViewById(R.id.editTextDate);

        editTextDate.setInputType(InputType.TYPE_NULL);
        editTextDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);
                // date picker dialog
                picker = new DatePickerDialog(MainActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth)
                            {
                                String month = String.valueOf(monthOfYear+1);
                                String day = String.valueOf(dayOfMonth);
                                if(monthOfYear<10) {
                                    // monthOfYear = 1, 2, 3, but we need 01, 02, 03 so
                                     month = "0".concat(String.valueOf(monthOfYear+1));
                                }
                                if(dayOfMonth<10) {
                                     day = "0".concat(String.valueOf(dayOfMonth));
                                }
                                editTextDate.setText(year + "-" + month + "-" + day);
                            }

                        }, year, month, day);
                picker.show();
            }
        });

        ListView listNews = findViewById(R.id.listNews);
        List<News> data = new ArrayList<>();
        NewsListAdapter newsListAdapter = new NewsListAdapter(this, R.layout.list_news_item, data);
        listNews.setAdapter(newsListAdapter);

        listNewsHome(data, newsListAdapter, "world today", LocalDate.now());

        buttonSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String key = editTextKey.getText().toString();
                LocalDate date = LocalDate.parse(editTextDate.getText());
               listNewsHome(data, newsListAdapter, key, date);

            }
        });

        listNews.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                 //String author = data.get(i).getAuthor();

                Intent intent=new Intent(getApplicationContext(),NewsDetail.class);
                intent.putExtra("news",data.get(i));
                startActivity(intent);
            }
        });
    }

    public void listNewsHome(List<News> data, NewsListAdapter newsListAdapter, String key, LocalDate date)
    {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://newsapi.org//v2/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        data.clear();
        RestNewsAPI restNewsAPI = retrofit.create(RestNewsAPI.class);
        Call<NewsListResponse> callNews2 = restNewsAPI.searchNews(key, date, API_KEY);
        callNews2.enqueue(new Callback<NewsListResponse>() {
            @Override
            public void onResponse(Call<NewsListResponse> call, Response<NewsListResponse> response)
            {
                NewsListResponse newsListResponse = response.body();
                for (News news: newsListResponse.getNews())
                {
                    data.add(news);
                }
                newsListAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<NewsListResponse> call, Throwable t)
            { Log.e("error", "call error!!!!!!!!!!!!!!!!"); }
        });

        editTextDate.setText(date.toString());
    }
}
