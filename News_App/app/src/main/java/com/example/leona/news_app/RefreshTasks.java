package com.example.leona.news_app;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.example.leona.news_app.data.DBHelper;
import com.example.leona.news_app.data.DatabaseUtils;
import com.example.leona.news_app.model.NewsItem;
import com.example.leona.news_app.utilities.NetworkUtils;

import org.json.JSONException;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by leona on 7/28/2017.
 */

public class RefreshTasks {
    public static final String ACTION_REFRESH = "refresh";


    public static void refreshNewsStories(Context context) {
        ArrayList<NewsItem> result = null;
        URL url = NetworkUtils.makeUrl();

        SQLiteDatabase db = new DBHelper(context).getWritableDatabase();

        try {
            DatabaseUtils.deleteAll(db);
            String json = NetworkUtils.getResponseFromHttpUrl(url);
            result = NetworkUtils.parseJSON(json);
            DatabaseUtils.bulkInsert(db, result);

        } catch (IOException e) {
            e.printStackTrace();

        } catch (JSONException e) {
            e.printStackTrace();
        }

        db.close();
    }
}
