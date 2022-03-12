package com.enset.tp3_rest_api.model;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.icu.text.DateFormat;
import android.icu.text.SimpleDateFormat;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import com.enset.tp3_rest_api.R;

import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class NewsListAdapter extends ArrayAdapter<News>
{

   // private List<News> news;
    private int resource;

    public NewsListAdapter(@NonNull Context context, int resource, List<News> news)
    {
        super(context, resource, news);
       // this.news = news;
        this.resource = resource;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent)
    {
        View listViewItem = convertView;
        if(listViewItem==null)
        {
            listViewItem = LayoutInflater.from(getContext()).inflate(resource, parent, false);
        }

        ImageView imageNews = listViewItem.findViewById(R.id.imageNewsList);
        TextView titleTextItem = listViewItem.findViewById(R.id.titleTextItem);
        TextView dateTextItem = listViewItem.findViewById(R.id.dateTextItem);
      //  titleTextItem.setText(news.get(position).getTitle());
        titleTextItem.setText(getItem(position).getTitle());

        //formatting date in Java using SimpleDateFormat
        Date time = getItem(position).getPublishedAt();
        SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd-MMM-YYY HH:mm:SS");
        String date = DATE_FORMAT.format(time);
        dateTextItem.setText(date);

        try
        {
            URL url = new URL(getItem(position).getImage());
            Bitmap bitmap = BitmapFactory.decodeStream(url.openStream());
            imageNews.setImageBitmap(bitmap);
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return listViewItem;
    }
}
