package com.example.rkjc.news_app_2;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements NewsRecyclerViewAdapter.NewsClick {
    private static final String TAG = "MainActivity";
    private RecyclerView recyclerView;
    private NewsRecyclerViewAdapter mAdapter;
    private ArrayList<NewsItem> newsItems = new ArrayList<>();
    private NewsItemViewModel newsItemViewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        mRecyclerView = findViewById(R.id.news_recyclerview);
//        mAdapter = new NewsRecyclerViewAdapter(this, newsItems);
//        mRecyclerView.setAdapter(mAdapter);
//        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
//        NewsQueryTask task = new NewsQueryTask();
//        task.execute();
        recyclerView = (RecyclerView)findViewById(R.id.news_recyclerview);
        newsItemViewModel = ViewModelProviders.of(this).get(NewsItemViewModel.class);
        newsItemViewModel.getAllNewsItems().observe(this, new Observer<List<NewsItem>>() {
            @Override
            public void onChanged(@Nullable List<NewsItem> newsItems) {
                mAdapter = new NewsRecyclerViewAdapter(MainActivity.this, new ArrayList<NewsItem>(newsItems));
                recyclerView.setAdapter(mAdapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
                newsItems = NewsRepository.SyncNewsTask.newsItems;
            }
        });
    }

    public void populateRecyclerView(String jstring) {
        Log.e("mycode", jstring);
        newsItems = JsonUtils.parseNews(jstring);
        mAdapter.newsItems.addAll(newsItems);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onNewsClick(int i) {
        String link = newsItems.get(i).getUrl();
        Intent browseIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(link));
        startActivity(browseIntent);
    }

    class NewsQueryTask extends AsyncTask<String, Void, String>{
        private final String TAG = "MainActivity Async";

        @Override
        protected String doInBackground(String... urls) {
            Log.d(TAG, "Task do in background");
            String jstring = null;
            try {
                jstring = NetworkUtils.getResponseFromHttpUrl(NetworkUtils.buildUrl());
            } catch (IOException e) {
                e.printStackTrace();
            }
            return jstring;
        }
        @Override
        protected void onPostExecute(String newsResult) {
            populateRecyclerView(newsResult);
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemThatWasClickedId = item.getItemId();
        if (itemThatWasClickedId == R.id.action_search) {
            newsItemViewModel.syncNews();
            return true;
        }
        return super.onOptionsItemSelected(item);

    }


}
