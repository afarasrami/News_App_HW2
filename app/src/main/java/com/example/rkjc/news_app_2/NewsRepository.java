package com.example.rkjc.news_app_2;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class NewsRepository {
    private static NewsItemDao newsItemDao;
    private LiveData<List<NewsItem>> mAllNewsItems;

    public NewsRepository(Application application) {
        NewsDatabase newsDatabase = NewsDatabase.getDatabase(application.getApplicationContext());
        newsItemDao = newsDatabase.newsItemDao();
        mAllNewsItems = newsItemDao.loadAllNewsItems();
    }

    public LiveData<List<NewsItem>> getAllNewsItems() {
        return mAllNewsItems;
    }

    public static void syncNews() {
        new SyncNewsTask(newsItemDao).execute(NetworkUtils.buildUrl());
    }

    public static class SyncNewsTask extends AsyncTask<URL, Void, String> {
        private NewsItemDao mAsyncTaskDao;
        public static ArrayList<NewsItem> newsItems;
        SyncNewsTask(NewsItemDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mAsyncTaskDao.clearAll();
        }

        @Override
        protected String doInBackground(URL... urls) {
            String newsSearchResults = "";
            try {
                newsSearchResults = NetworkUtils.getResponseFromHttpUrl(urls[0]);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return newsSearchResults;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            ArrayList<NewsItem> news = JsonUtils.parseNews(s);
            newsItems = news;
            mAsyncTaskDao.insert(news);
        }
    }
}
