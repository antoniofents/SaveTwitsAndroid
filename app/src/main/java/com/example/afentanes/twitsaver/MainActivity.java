package com.example.afentanes.twitsaver;

import android.app.job.JobScheduler;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import com.example.afentanes.twitsaver.db.TwitsSqlHelper;
import com.example.afentanes.twitsaver.db.TwitsTableContract;
import com.example.afentanes.twitsaver.listeners.SaveTwitListener;
import com.example.afentanes.twitsaver.twiterapi.TwitRefreshable;
import com.twitter.sdk.android.core.models.Tweet;

import java.util.List;

//import android.support.design.widget.FloatingActionButton;

public class MainActivity extends AppCompatActivity implements TwitRefreshable{


    private long lastTweetId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        initListViewAdapter( (ListView) findViewById(R.id.new_twits));
        checkForNewTwits();
        TwitSaverUtil.scheduleJob(getApplicationContext());
        registerReceiver(new TwitsBroadCastReceiver(), new IntentFilter("com.example.afentanes.twitsaver"));


    }




    private void initListViewAdapter( ListView listView){

        SQLiteDatabase twitsDb= new TwitsSqlHelper(getApplicationContext()).getReadableDatabase();
        Cursor cursor = twitsDb.rawQuery("SELECT rowid _id , * FROM " + TwitsTableContract.TwitsEntry.TABLE_NAME, null);

       SimpleCursorAdapter adapter= new SimpleCursorAdapter(listView.getContext(),
                android.R.layout.simple_list_item_1, null,
                TwitsTableContract.TWITS_BASIC_PROJECTION,
                new int[]{android.R.id.text1}, 0){

       };
        listView.setAdapter(adapter);
        adapter.changeCursor(cursor);
        listView.setOnItemClickListener(new SaveTwitListener(this));
        twitsDb.close();
    }


    public void checkForNewTwits(){

        Intent myServiceIntent = new Intent(this, TwitsReaderService.class);
        startService(myServiceIntent);
      //  JobInfo.Builder builder = new JobInfo.Builder(0, myServiceComponent);
        //builder.setRequiresDeviceIdle(true);
        //builder.setRequiredNetworkType(JobInfo.NETWORK_TYPE_UNMETERED);
        //TwitsReader twitsReader= new TwitsReader(this, lastTweetId);
        //twitsReader.checkNewTwits();
    }

    public  void refreshMainList(){
        initListViewAdapter( (ListView) findViewById(R.id.new_twits));
    }


    private void init() {

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Twits Tool");
        setSupportActionBar(toolbar);;

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkForNewTwits();
            }

        });




    }


    @Override
    public void resfreshTweets(List<Tweet> tweets) {
        lastTweetId=tweets.get(0).getId();
        CharSequence text = "new tweets available! " + lastTweetId;
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(getApplicationContext(), text, duration);
        toast.setGravity(Gravity.CENTER|Gravity.CENTER, 0, 0);
        toast.show();

    }


}
