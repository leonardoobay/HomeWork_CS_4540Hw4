package com.example.leona.news_app.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by leona on 7/27/2017.
 */

public class DBHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "Newz.db";
    private static final String TAG = "dbhelper";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    /*
                cv.put(COLUMN_NAME_DESCRIPTION, n.getDescription());
                cv.put(COLUMN_NAME_SOURCE, n.getSource());
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        String queryString = "CREATE TABLE " + Contract.TABLE_NewsItems.TABLE_NAME + " ("+
                Contract.TABLE_NewsItems._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                Contract.TABLE_NewsItems.COLUMN_NAME_TITLE + " TEXT NOT NULL, " +
                Contract.TABLE_NewsItems.COLUMN_NAME_PUBLISHED_AT + " DATE, " +
              //  Contract.TABLE_NewsItems.COLUMN_NAME_AUTHOR + " TEXT, " +
                Contract.TABLE_NewsItems.COLUMN_NAME_URL_TO_IMAGE + " TEXT, " +
                Contract.TABLE_NewsItems.COLUMN_NAME_DESCRIPTION + " TEXT, " +
              //  Contract.TABLE_NewsItems.COLUMN_NAME_SOURCE + " TEXT, " +
                Contract.TABLE_NewsItems.COLUMN_NAME_URL + " TEXT" +
                "); ";

        Log.d(TAG, "Create table SQL: " + queryString);
        db.execSQL(queryString);
    }

    //THIS METHOD MUST BE OVERRIDEN
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
