package com.example.leona.news_app.data;

import android.provider.BaseColumns;

/**
 * Created by leona on 7/27/2017.
 */
/*

 public String url;
    public String urlToImage;
    public String publishedAt;
    public String author;
    public String title;
    public String description;
    public String source;
 */

public class Contract {
    public static class TABLE_NewsItems implements BaseColumns {
        public static final String TABLE_NAME = "Newz";
        public static final String COLUMN_NAME_URL = "url";
        public static final String COLUMN_NAME_URL_TO_IMAGE = "url_to_image";
        public static final String COLUMN_NAME_PUBLISHED_AT = "published_at";
      //  public static final String COLUMN_NAME_AUTHOR = "author";
        public static final String COLUMN_NAME_TITLE = "title";
        public static final String COLUMN_NAME_DESCRIPTION = "description";
    //    public static final String COLUMN_NAME_SOURCE = "source";
    }
}
