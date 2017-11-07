package com.example.afentanes.twitsaver.twiterapi;

import android.app.Activity;
import android.content.Context;
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


    private List <Tweet> items;
    public  boolean newTweetsAvailable;
    private  TwitRefreshable refresher;
    public TwitsReader (Context context,TwitRefreshable refreshable ){
        Twitter.initialize(context);
        this.refresher=  refreshable;
    }



    public void checkNewTwits(){

        final UserTimeline userTimeline = new UserTimeline.Builder()
                .screenName("ante_vlast")
                .build();
        userTimeline.previous(null, new Callback<TimelineResult<Tweet>>() {
            @Override
            public void success(Result<TimelineResult<Tweet>> result) {
                   refresher.resfreshTweets(result.data.items );

            }

            @Override
            public void failure(TwitterException exception) {
                Log.i("error al traer twits","");
            }


        });
        Log.i("","");
    }


}
