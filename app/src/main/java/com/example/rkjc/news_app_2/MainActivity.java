package com.example.rkjc.news_app_2;

import android.content.Context;
import android.os.AsyncTask;
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



public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private RecyclerView mRecyclerView;
    private NewsRecyclerViewAdapter mAdapter;
    private ArrayList<NewsItem> newsItems = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView = findViewById(R.id.news_recyclerview);
        mAdapter = new NewsRecyclerViewAdapter(this, newsItems);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        NewsQueryTask task = new NewsQueryTask();
        task.execute();
    }

    public void populateRecyclerView(String jstring) {
        Log.e("mycode", jstring);
        newsItems = JsonUtils.parseNews(jstring);
        mAdapter.newsItems.addAll(newsItems);
        mAdapter.notifyDataSetChanged();
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

//    private URL makeNewsQueryTask() {
//       // String newsQuery = mSearchBoxEditText.getText().toString();
//        URL githubSearchUrl = NetworkUtils.buildUrl(newsQuery);
//        String urlString = githubSearchUrl.toString();
//        Log.d("mycode", urlString);
//        return githubSearchUrl;
//
//
//    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemThatWasClickedId = item.getItemId();
        if (itemThatWasClickedId == R.id.action_search) {
            mAdapter = new NewsRecyclerViewAdapter(this, newsItems);
            mRecyclerView.setAdapter(mAdapter);
            mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
            NewsQueryTask task = new NewsQueryTask();
            task.execute();
        }
        return super.onOptionsItemSelected(item);

    }


}
