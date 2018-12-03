package com.example.rkjc.news_app_2;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "news_item")
public class NewsItem {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String title;
    private String url;
    private String description;
    private String date;

    @Ignore
    public NewsItem(String title, String description, String date, String url) {
        this.title = "Title" +title;
        this.url = url;
        this.date= "Date" +date;
        this.description = "Description" +description;
    }

    public NewsItem(int id, String title, String description, String url){

        this.id = id;
        this.title = title;
        this.description = description;
        this.url = url;
    }

    public int getId() { return id; }

    public void setId(int id) { this.id = id; }

    public String getDate() {
        return date;
    }

    public String getDescription() {
        return description;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }


}
