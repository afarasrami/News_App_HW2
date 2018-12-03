package com.example.rkjc.news_app_2;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import java.util.List;

public class NewsItemViewModel extends AndroidViewModel {
    private NewsRepository repository;

    private LiveData<List<NewsItem>> mAllNewsItems;

    public NewsItemViewModel(Application application) {
        super(application);
        repository = new NewsRepository(application);
        mAllNewsItems = repository.getAllNewsItems();
    }

    public LiveData<List<NewsItem>> getAllNewsItems() {
        return mAllNewsItems;
    }

    public void syncNews() {
        repository.syncNews();
    }
}
