package com.example.afentanes.twitsaver;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import com.example.afentanes.twitsaver.db.TwitsSqlHelper;
import com.example.afentanes.twitsaver.db.TwitsTableContract;
import com.example.afentanes.twitsaver.listeners.SaveTwitListener;


public class TwitsStoreActivity extends Activity {

    @Override
    public void onCreate( Bundle savedInstance){
        super.onCreate(savedInstance);
        setContentView(R.layout.twits_available);
        initListViewAdapter((ListView) findViewById(R.id.twits_available));

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
        twitsDb.close();
    }
}
