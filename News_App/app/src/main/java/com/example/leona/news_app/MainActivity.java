package com.example.leona.news_app;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.nfc.Tag;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.SharedPreferencesCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import android.widget.ProgressBar;


import com.example.leona.news_app.data.Contract;
import com.example.leona.news_app.data.DBHelper;
import com.example.leona.news_app.data.DatabaseUtils;
import com.example.leona.news_app.model.NewsItem;
import com.example.leona.news_app.utilities.NetworkUtils;
import org.json.JSONException;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;


public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Void>, NewsAdapter.ItemClickListener{

    private RecyclerView rView;
    private ProgressBar spinner;
    static final String TAG = "mainactivty";
    //added cursor
    private Cursor cursor;
    private SQLiteDatabase db;
    private static final int NEWS_LOADER = 1;
  private  NewsAdapter mAdapter;
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
       // textView = (TextView) findViewById(R.id.displayJSON);
        spinner = (ProgressBar) findViewById(R.id.progress);
        rView = (RecyclerView) findViewById(R.id.rv_news);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);

        rView.setLayoutManager(layoutManager);
        //added prefs and boolean is first
        SharedPreferences prefs  = PreferenceManager.getDefaultSharedPreferences(this);
        //checks if it is the first time installing
        boolean isFirst = prefs.getBoolean("isfirst",true);
        if(isFirst)
        {
            load();
            SharedPreferences.Editor editor = prefs.edit();
            editor.putBoolean("isfirst",false);
            editor.commit();
        }
        //allows app to refresh every so often
        ScheduleUtilities.scheduleRefresh(this);
    }

    @Override
    protected void onStart(){
        super.onStart();
        db = new DBHelper(MainActivity.this).getReadableDatabase();
        cursor = DatabaseUtils.getAll(db);
        mAdapter = new NewsAdapter(cursor,this);
        rView.setAdapter(mAdapter);
        ScheduleUtilities.scheduleRefresh(this);
    }

    @Override
    protected  void onStop(){
        super.onStop();
        db.close();
        cursor.close();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.searchicon,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int itemNumber = item .getItemId();
        if(itemNumber == R.id.action_search){
            load();
        }

        return true;
    }


@Override
public android.support.v4.content.Loader<Void> onCreateLoader(int id, final Bundle args) {
    return new android.support.v4.content.AsyncTaskLoader<Void>(this) {

        @Override
        protected void onStartLoading() {
            super.onStartLoading();
            //makes progressbar visisble
            spinner.setVisibility(View.VISIBLE);
        }

        @Override
        public Void loadInBackground() {
            RefreshTasks.refreshNewsStories(MainActivity.this);
            return null;
        }

    };
}

//reads db
    @Override
    public void onLoadFinished(android.support.v4.content.Loader<Void> loader, Void data) {
        spinner.setVisibility(View.GONE);
        db = new DBHelper(MainActivity.this).getReadableDatabase();
        cursor = DatabaseUtils.getAll(db);

        mAdapter = new NewsAdapter(cursor, this);
        rView.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();

    }



    @Override
    public void onLoaderReset(android.support.v4.content.Loader<Void> loader) {
    }
    @Override
    public void onItemClick(Cursor cursor,int clickedItemIndex){
        cursor.moveToPosition(clickedItemIndex);
        String url = cursor.getString(cursor.getColumnIndex(Contract.TABLE_NewsItems.COLUMN_NAME_URL));
       Log.d(TAG,String.format("Url %s", url));
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(url));
        startActivity(intent);
    }
    public void load(){
        LoaderManager loaderManager = getSupportLoaderManager();
        loaderManager.restartLoader(NEWS_LOADER,null, this).forceLoad();
       mAdapter.notifyDataSetChanged();
    }


    //****************************stop code here *********************

    //**************************
//    public class NewsRequests extends AsyncTask<URL, Void, ArrayList<NewsItem>> {
//
//
//
//        @Override
//        protected void onPreExecute() {
//            super.onPreExecute();
//           // textView.setText("");
//            spinner.setVisibility(View.INVISIBLE);
//           // spinner.invalidate();
//
//        }
//        @Override
//        protected ArrayList<NewsItem> doInBackground(URL... params) {
//            ArrayList<NewsItem> output;
//            URL url = NetworkUtils.makeUrl();
//
//            try {
//                String json  = NetworkUtils.getResponseFromHttpUrl(url);
//
//                output = NetworkUtils.parseJSON(json);
//            }
//            catch (IOException e) {
//                e.printStackTrace();
//                return null;
//            }
//            catch (JSONException e) {
//                e.printStackTrace();
//                return null;
//            }
//
//            return output;
//        }
//
//
//
//        @Override
//        protected void onPostExecute(final ArrayList<NewsItem> newsData) {
//            super.onPostExecute(newsData);
//            spinner.setVisibility(View.INVISIBLE);
//            if (newsData != null) {
//                NewsAdapter adapter = new NewsAdapter(newsData, new NewsAdapter.ItemClickListener() {
//                    @Override
//                    public void onItemClick(int itemIndex) {
//                        Context context = MainActivity.this;
//                        String url = newsData.get(itemIndex).getUrl();
//
//                        Uri website = Uri.parse(url);
//
//                        Intent intent = new Intent(Intent.ACTION_VIEW, website);
//
//                        if (intent.resolveActivity(getPackageManager()) != null) {
//                            startActivity(intent);
//                        }
//                    }
//                });
//                rView.setAdapter(adapter);
//            }
//        }
//    }

//************************
//**************************
    //*************************************









}



