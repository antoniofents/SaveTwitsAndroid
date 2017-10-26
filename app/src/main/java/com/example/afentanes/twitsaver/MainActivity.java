package com.example.afentanes.twitsaver;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.afentanes.twitsaver.db.TwitsSqlHelper;
import com.example.afentanes.twitsaver.db.TwitsTableContract;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.button_saver).setOnClickListener(new SaveTwitListener(){
            @Override
            public void onClick(View view) {
                super.onClick(view);
                updateCurrentTwits();
            }
        });

        updateCurrentTwits();

    }

    private void updateCurrentTwits() {
        ((TextView)findViewById(R.id.current_twits)).setText("current twits: " + getCurrentTwitsSaved());
    }


    private int getCurrentTwitsSaved(){
        SQLiteDatabase twitsDb = new TwitsSqlHelper(this.getApplicationContext()).getWritableDatabase();

        Cursor cursor = twitsDb.rawQuery("SELECT COUNT (*) FROM " + TwitsTableContract.TwitsEntry.TABLE_NAME, null);
        if(cursor.getCount()>0){
            cursor.moveToFirst();
           int count= cursor.getInt(0);
            cursor.close();
            return count;
        }
        return 0;
    }
}
