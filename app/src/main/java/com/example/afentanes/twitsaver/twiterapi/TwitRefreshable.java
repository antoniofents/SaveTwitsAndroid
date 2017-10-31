package com.example.afentanes.twitsaver.twiterapi;

import com.twitter.sdk.android.core.models.Tweet;

import java.util.List;

/**
 * Created by afentanes on 10/27/17.
 */

public interface TwitRefreshable {


    public void resfreshTweets(List<Tweet> tweets);
    
}
