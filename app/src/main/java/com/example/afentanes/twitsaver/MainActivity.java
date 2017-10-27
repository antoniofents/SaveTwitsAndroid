package com.example.afentanes.twitsaver;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import com.example.afentanes.twitsaver.db.TwitsSqlHelper;
import com.example.afentanes.twitsaver.db.TwitsTableContract;

//import android.support.design.widget.FloatingActionButton;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initListViewAdapter( (ListView) findViewById(R.id.new_twits));

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


    public  void refreshMainList(){
        initListViewAdapter( (ListView) findViewById(R.id.new_twits));
    }


    private void init() {
    /*
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }

        });*/




    }


}
