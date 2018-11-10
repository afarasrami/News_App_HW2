package com.example.rkjc.news_app_2;

public class NewsItem {
    private String title;
    private String url;
    private String description;
    private String date;


    public NewsItem(String title, String description, String date, String url) {
        this.title = "Title" +title;
        this.url = url;
        this.date= "Date" +date;
        this.description = "Description" +description;
    }

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
