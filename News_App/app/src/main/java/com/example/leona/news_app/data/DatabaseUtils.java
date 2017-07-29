package com.example.leona.news_app.data;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.leona.news_app.model.NewsItem;

import java.util.ArrayList;

//import static com.example.leona.news_app.data.Contract.TABLE_NewsItems.COLUMN_NAME_AUTHOR;
import static com.example.leona.news_app.data.Contract.TABLE_NewsItems.COLUMN_NAME_DESCRIPTION;
import static com.example.leona.news_app.data.Contract.TABLE_NewsItems.COLUMN_NAME_PUBLISHED_AT;
//import static com.example.leona.news_app.data.Contract.TABLE_NewsItems.COLUMN_NAME_SOURCE;
import static com.example.leona.news_app.data.Contract.TABLE_NewsItems.COLUMN_NAME_TITLE;
import static com.example.leona.news_app.data.Contract.TABLE_NewsItems.COLUMN_NAME_URL;
import static com.example.leona.news_app.data.Contract.TABLE_NewsItems.COLUMN_NAME_URL_TO_IMAGE;
import static com.example.leona.news_app.data.Contract.TABLE_NewsItems.TABLE_NAME;


/**
 * Created by leona on 7/27/2017.
 */

public class DatabaseUtils {
    public static Cursor getAll(SQLiteDatabase db) {
        Cursor cursor = db.query(
                TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
//                null,
//                null,
                COLUMN_NAME_PUBLISHED_AT + " DESC"
        );
        return cursor;
    }

    public static void bulkInsert(SQLiteDatabase db, ArrayList<NewsItem> Newz) {

        /*



        public static final String COLUMN_NAME_DESCRIPTION = "description";
        public static final String COLUMN_NAME_SOURCE = "source";
         */
        db.beginTransaction();
        try {
            for (NewsItem n : Newz) {
                ContentValues cv = new ContentValues();
                cv.put(COLUMN_NAME_TITLE, n.getTitle());
              //  cv.put(COLUMN_NAME_AUTHOR, n.getAuthor());
                cv.put(COLUMN_NAME_PUBLISHED_AT, n.getPublishedAt());
                cv.put(COLUMN_NAME_URL_TO_IMAGE, n.getUrlToImage());
                cv.put(COLUMN_NAME_URL, n.getUrl());
                cv.put(COLUMN_NAME_DESCRIPTION, n.getDescription());
               // cv.put(COLUMN_NAME_SOURCE, n.getSource());
                db.insert(TABLE_NAME, null, cv);
            }
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
            db.close();
        }
    }

    public static void deleteAll(SQLiteDatabase db) {
        db.delete(TABLE_NAME, null, null);
    }
}
