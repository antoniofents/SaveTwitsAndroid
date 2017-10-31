package com.example.afentanes.twitsaver;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.Intent;

import com.example.afentanes.twitsaver.twiterapi.TwitRefreshable;
import com.example.afentanes.twitsaver.twiterapi.TwitsReader;
import com.twitter.sdk.android.core.models.Tweet;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by afentanes on 10/27/17.
 */

public class TwitsReaderService extends JobService implements  TwitRefreshable {
    @Override
    public boolean onStartJob(JobParameters jobParameters) {

        checkforTwits();
        jobFinished(jobParameters, true);

        return true;
    }

    @Override
    public boolean onStopJob(JobParameters jobParameters) {
        return true;
    }


    private void checkforTwits(){
        TwitsReader twitsReader= new TwitsReader(this);
        twitsReader.checkNewTwits();
    }
    @Override
    public void resfreshTweets(List<Tweet> tweets) {

        ArrayList <Twit> convertedTwits= new ArrayList(tweets.size());
        for(Tweet tweet: tweets){
            convertedTwits.add(new Twit(tweet));
        }

        final String BROADCAST_ACTION = "com.example.afentanes.twitsaver";
        Intent intent = new Intent(BROADCAST_ACTION);
        intent.putParcelableArrayListExtra("twits", convertedTwits);
        sendBroadcast(intent);
    }
}
