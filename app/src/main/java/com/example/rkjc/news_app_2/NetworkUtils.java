package com.example.rkjc.news_app_2;

import android.net.Uri;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

import static android.support.constraint.Constraints.TAG;

public class NetworkUtils {
    final static String BASE_URL = "https://newsapi.org/v1/articles";
    final static String QUERY_PARAM = "the-next-web";
    final static String sortBy = "latest";
    final static String API_KEY = "049d1acb0dce4fff8ed8b05cd9c461f0";
    final static String QUERY_PARAM_SOURCE = "source";
    final static String QUERY_PARAM_SORTBY = "sortBy";
    final static String QUERY_PARAM_APIKEY = "apiKey";

    public static URL buildUrl() {

        Uri builtUri = Uri.parse(BASE_URL).buildUpon()
                .appendQueryParameter(QUERY_PARAM_SOURCE, QUERY_PARAM)
                .appendQueryParameter(QUERY_PARAM_SORTBY, sortBy)
                .appendQueryParameter(QUERY_PARAM_APIKEY, API_KEY)
                .build();

        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        Log.v(TAG, "Built URI: " + url);

        return url;
    }

    public static String getResponseFromHttpUrl(URL url) throws IOException{
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream in =  urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasinput = scanner.hasNext();
            if(hasinput) {
                return scanner.next();
            }else {
                return null;
            }
        }finally {
            urlConnection.disconnect();
        }
    }

}
