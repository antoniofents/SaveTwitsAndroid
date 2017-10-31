package com.example.afentanes.twitsaver.twiterapi;

import android.app.Activity;
import android.util.Log;
import android.widget.ListView;

import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.Twitter;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.models.Tweet;
import com.twitter.sdk.android.tweetui.TimelineResult;
import com.twitter.sdk.android.tweetui.UserTimeline;

import java.util.List;

/**
 * Created by afentanes on 10/27/17.
 */

public class TwitsReader {


    private  long lastTwit;
    private List <Tweet> items;
    public  boolean newTweetsAvailable;
    private  TwitRefreshable refresher;
    public TwitsReader (Activity activity, long lastTwit){
        Twitter.initialize(activity);
        this.refresher= (TwitRefreshable) activity;
        this.lastTwit=lastTwit;
    }



    public void checkNewTwits(){

        final UserTimeline userTimeline = new UserTimeline.Builder()
                .screenName("ante_vlast")
                .build();
        userTimeline.previous(null, new Callback<TimelineResult<Tweet>>() {
            @Override
            public void success(Result<TimelineResult<Tweet>> result) {
                if(result.data.items.size()>0 && result.data.items.get(0).getId()!=lastTwit){
                 //  refresher.resfreshTweets(result.data.items );

                }
            }

            @Override
            public void failure(TwitterException exception) {
                Log.i("error al traer twits","");
            }


        });
        Log.i("","");
    }


}
