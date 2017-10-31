package com.example.afentanes.twitsaver;

import android.app.job.JobScheduler;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import com.example.afentanes.twitsaver.db.TwitsSqlHelper;
import com.example.afentanes.twitsaver.db.TwitsTableContract;
import com.example.afentanes.twitsaver.layout.TwitsAdapter;
import com.example.afentanes.twitsaver.listeners.SaveTwitListener;
import com.example.afentanes.twitsaver.twiterapi.TwitRefreshable;
import com.example.afentanes.twitsaver.twiterapi.TwitsReader;
import com.twitter.sdk.android.core.models.Tweet;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

//import android.support.design.widget.FloatingActionButton;

public class MainActivity extends AppCompatActivity {


    private long lastTweetId;
    private ArrayList <Twit> twits =new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recoverTwits(savedInstanceState);
        init();
        startCheckService();


    }





    private void initListViewAdapter( List <Twit> twits){
        ListView listView = (ListView) findViewById(R.id.new_twits);
        listView.setAdapter(new TwitsAdapter(this, twits.toArray(new Twit[twits.size()])));
        listView.setOnItemClickListener(new SaveTwitListener(this));

    }


    public void startCheckService(){
        TwitSaverUtil.scheduleJob(getApplicationContext());
        registerReceiver();

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        if(outState!=null){
            persistTwits(outState);
        }
    }

    private void registerReceiver() {
        registerReceiver(new TwitsBroadCastReceiver(){
                @Override
                public void resfreshTweets(ArrayList<Twit> tweets) {
                    Collections.sort(tweets);
                    if(tweets.get(0).id!=lastTweetId){
                        lastTweetId= tweets.get(0).id;
                        twits=tweets;
                        CharSequence text = "new tweets available! " + tweets.get(0).text;
                        int duration = Toast.LENGTH_SHORT;
                        Toast toast = Toast.makeText(getApplicationContext(), text, duration);
                        toast.setGravity(Gravity.CENTER|Gravity.CENTER, 0, 0);
                        toast.show();
                    }
                }

        }, new IntentFilter("com.example.afentanes.twitsaver"));
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

    private void recoverTwits(Bundle bundle){

        if(this.twits.isEmpty()){

            if(bundle!=null){
                ArrayList<Twit> twitsFromBundle = bundle.getParcelableArrayList("twits");
                if(twitsFromBundle!=null && twitsFromBundle.size()>0)
                    this.twits=twitsFromBundle;
                initListViewAdapter(twits);
            }

        }

    }

    private void persistTwits(Bundle bundle){
        bundle.putParcelableArrayList("twits",  twits);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.create_new:
                Intent intent = new Intent( this, TwitsStoreActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }



}
