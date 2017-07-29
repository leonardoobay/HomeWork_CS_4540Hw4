package com.example.leona.news_app;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.leona.news_app.data.Contract;
import com.example.leona.news_app.model.NewsItem;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by leona on 6/28/2017.
 */

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ItemHolder>{

   // private ArrayList<NewsItem> newsData;
    private ItemClickListener listener;
    //added context &cursor
    private Cursor cursor;
    private Context context;
    public static final String TAG = "newsadapter";
    //************************
    public NewsAdapter(Cursor cursor, ItemClickListener listener){
        this.cursor = cursor;
        this.listener = listener;
    }
    public interface ItemClickListener{
        void onItemClick(Cursor cursor, int clickedItemIndex);

    }

    @Override
    public ItemHolder onCreateViewHolder(ViewGroup parent, int viewType){
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;
        View view = inflater.inflate(R.layout.recycleview, parent, shouldAttachToParentImmediately);
        ItemHolder holder = new ItemHolder(view);
        return holder;
    }
    @Override
    public void onBindViewHolder(ItemHolder holder, int position){
        holder.bind(position);
    }
    @Override
    public int getItemCount(){
        return cursor.getCount();
    }
    class ItemHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView news_title;
        TextView news_description;
        TextView news_time;

        ImageView img;
        ItemHolder(View view){
            super(view);
            news_title =(TextView)view.findViewById(R.id.news_title);
            news_description = (TextView)view.findViewById(R.id.news_description);
            news_time = (TextView)view.findViewById(R.id.news_time);
            img = (ImageView)view.findViewById(R.id.img);
            view.setOnClickListener(this);
        }

        public void bind(int pos){
            cursor.moveToPosition(pos);
            news_title.setText(cursor.getString(cursor.getColumnIndex(Contract.TABLE_NewsItems.COLUMN_NAME_TITLE)));
            news_description.setText(cursor.getString(cursor.getColumnIndex(Contract.TABLE_NewsItems.COLUMN_NAME_DESCRIPTION)));
            news_time.setText(cursor.getString(cursor.getColumnIndex(Contract.TABLE_NewsItems.COLUMN_NAME_PUBLISHED_AT)));
            String url = cursor.getString(cursor.getColumnIndex(Contract.TABLE_NewsItems.COLUMN_NAME_URL_TO_IMAGE));
            Log.d(TAG, url);
            //if the image url is not null, Picasso will load the image into the view
            if(url != null){
                Picasso.with(context).load(url).into(img);
            }
        }
        @Override
        public void onClick(View v){
            int pos = getAdapterPosition();
            listener.onItemClick(cursor,pos);
        }
    }


    //***********************


//    public NewsAdapter(ArrayList<NewsItem> newsData, ItemClickListener listener) {
//        this.newsData = newsData;
//        this.listener = listener;
//    }
//
//    public interface ItemClickListener {
//        void onItemClick(int itemIndex);
//    }
//
//    @Override
//    public NewsItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        Context context = parent.getContext();
//
//        LayoutInflater inflater = LayoutInflater.from(context);
//
//        View view = inflater.inflate(R.layout.recycleview, parent, false);
//
//        NewsItemViewHolder holder = new NewsItemViewHolder(view);
//
//        return holder;
//    }
//
//    @Override
//    public void onBindViewHolder (NewsItemViewHolder holder, int position) {
//        holder.bind(position);
//    }
//
//    @Override
//    public int getItemCount() {
//        if (newsData == null) {
//            return 0;
//        }
//        else {
//            return newsData.size();
//        }
//    }
//
//    class NewsItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
//        TextView title;
//        TextView description;
//        TextView publishedAt;
//      //  ImageView urlToImage;
//
//        NewsItemViewHolder(View view){
//            super(view);
//            title = (TextView)view.findViewById(R.id.news_title);
//            description = (TextView)view.findViewById(R.id.news_description);
//            publishedAt = (TextView)view.findViewById(R.id.news_time);
//           // urlToImage  = (ImageView)view.findViewById(R.id.news_image);
//            view.setOnClickListener(this);
//        }
//
//        public void bind(int pos){
//            NewsItem item = newsData.get(pos);
//            title.setText(item.getTitle());
//            description.setText(item.getDescription());
//            publishedAt.setText(item.getPublishedAt());
//          //  urlToImage.setImageURI(item.getUrlToImage());
//        }
//
//        @Override
//        public void onClick(View v) {
//            int pos = getAdapterPosition();
//            listener.onItemClick(pos);
//        }
//    }

    /**
     * Created by leona on 7/28/2017.
     */


}
