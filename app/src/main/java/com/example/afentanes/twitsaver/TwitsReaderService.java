package com.example.afentanes.twitsaver;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.Intent;

import com.example.afentanes.twitsaver.twiterapi.TwitRefreshable;
import com.twitter.sdk.android.core.models.Tweet;

import java.util.List;

/**
 * Created by afentanes on 10/27/17.
 */

public class TwitsReaderService extends JobService implements TwitRefreshable {
    @Override
    public boolean onStartJob(JobParameters jobParameters) {


        resfreshTweets(null);

        return true;
    }

    @Override
    public boolean onStopJob(JobParameters jobParameters) {
        return false;
    }

    @Override
    public void resfreshTweets(List<Tweet> tweets) {
        final String BROADCAST_ACTION = "com.example.afentanes.twitsaver";
        Intent intent = new Intent(BROADCAST_ACTION);
        sendBroadcast(intent);
    }
}
