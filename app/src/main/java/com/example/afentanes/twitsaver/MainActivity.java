package com.example.afentanes.twitsaver;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.example.afentanes.twitsaver.layout.TwitsAdapter;
import com.example.afentanes.twitsaver.listeners.SaveTwitListener;
import com.example.afentanes.twitsaver.twiterapi.TwitsReader;
import com.twitter.sdk.android.core.models.Tweet;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.schedulers.Schedulers;

//import android.support.design.widget.FloatingActionButton;

public class MainActivity extends AppCompatActivity {


    private long lastTweetId;
    private List<Twit> twits = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recoverTwits(savedInstanceState);
        init();
        startCheckService();

    }


    private void initListViewAdapter(List<Twit> twits) {
        ListView listView = (ListView) findViewById(R.id.new_twits);
        listView.setAdapter(new TwitsAdapter(this, twits.toArray(new Twit[twits.size()])));
        listView.setOnItemClickListener(new SaveTwitListener(this));

    }


    public void startCheckService() {

        /*
        For TwitsReaderService

        TwitSaverUtil.scheduleJob(getApplicationContext());
        registerReceiver();*/

        Observable.create((observ -> Schedulers.newThread().createWorker().schedulePeriodically(() -> {

            TwitsReader twitsReader = new TwitsReader(this.getApplicationContext(), tweets -> {
                ArrayList<Twit> convertedTwits = new ArrayList(tweets.size());
                for (Tweet tweet : tweets) {
                    convertedTwits.add(new Twit(tweet));
                }
                ;
                observ.onNext(convertedTwits);
            });
            twitsReader.checkNewTwits();

        }, 0, 3000, TimeUnit.MILLISECONDS))).subscribe(tweets -> {
                List<Twit> twits = (List<Twit>) tweets;
                Collections.sort(twits);
                if (twits.get(0).id != lastTweetId)
                    refreshUITwits(twits);
            });

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        if (outState != null) {
            saveTwitsInstance(outState);
        }
    }

    private void registerReceiver() {
        registerReceiver(new TwitsBroadCastReceiver() {
            @Override
            public void resfreshTweets(ArrayList<Twit> tweets) {
                Collections.sort(twits);
                if (twits.get(0).id != lastTweetId)
                refreshUITwits(tweets);
            }

        }, new IntentFilter("com.example.afentanes.twitsaver"));
    }

    private void refreshUITwits(List<Twit> tweets) {

            lastTweetId = tweets.get(0).id;
            twits = tweets;
            CharSequence text = "new tweets available! " + tweets.get(0).text;
            int duration = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(getApplicationContext(), text, duration);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();

    }


    private void init() {

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Twits Tool");
        setSupportActionBar(toolbar);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                initListViewAdapter(twits);
            }

        });


    }

    private void recoverTwits(Bundle bundle) {

        if (this.twits.isEmpty()) {

            if (bundle != null) {
                ArrayList<Twit> twitsFromBundle = bundle.getParcelableArrayList("twits");
                if (twitsFromBundle != null && twitsFromBundle.size() > 0)
                    this.twits = twitsFromBundle;
                initListViewAdapter(twits);
            }

        }

    }

    private void saveTwitsInstance(Bundle bundle) {
        bundle.putParcelableArrayList("twits", (ArrayList) twits);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.available_twits_item:
                Intent intent = new Intent(this, TwitsStoreActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }


}
