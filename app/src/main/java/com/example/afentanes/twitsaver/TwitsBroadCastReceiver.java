package com.example.afentanes.twitsaver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import java.util.ArrayList;

/**
 * Created by afentanes on 10/27/17.
 */

public abstract class TwitsBroadCastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

        ArrayList <Twit> twits = intent.getParcelableArrayListExtra("twits");
        if(twits!=null && twits.size()>0 ){
            resfreshTweets(twits);
        }


    }

    public abstract  void resfreshTweets(ArrayList<Twit> tweets);
}
