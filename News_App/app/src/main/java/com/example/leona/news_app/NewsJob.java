package com.example.leona.news_app;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.os.AsyncTask;
import android.widget.Toast;

/**
 * Created by leona on 7/28/2017.
 */

public class NewsJob extends JobService {
    AsyncTask mBackgroundTask;

@Override
public boolean onStartJob(final JobParameters job) {
    mBackgroundTask = new AsyncTask() {
        @Override
        protected void onPreExecute() {
            Toast.makeText(NewsJob.this, "News refreshed", Toast.LENGTH_SHORT).show();
            super.onPreExecute();
        }

        @Override
        protected Object doInBackground(Object[] params) {
            //refreshnews stories when button is clicked or every so time
            RefreshTasks.refreshNewsStories(NewsJob.this);
            return null;
        }

        @Override
        protected void onPostExecute(Object o) {
            jobFinished(job, false);
            super.onPostExecute(o);

        }
    };


    mBackgroundTask.execute();

    return true;
}


    @Override
    public boolean onStopJob(JobParameters job){
        if (mBackgroundTask !=null)

            mBackgroundTask.cancel(false);
            return true;
    }

}
