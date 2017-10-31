package com.example.afentanes.twitsaver.listeners;

import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteCursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;

import com.example.afentanes.twitsaver.MainActivity;
import com.example.afentanes.twitsaver.db.TwitsSqlHelper;
import com.example.afentanes.twitsaver.db.TwitsTableContract;
import com.example.afentanes.twitsaver.layout.SaveTwitDialogFragment;


public class SaveTwitListener implements AdapterView.OnItemClickListener {

    private AppCompatActivity activity;
    public SaveTwitListener(AppCompatActivity appCompatActivity) {
        this.activity=appCompatActivity;
    }

    private void writeTwitIntoDatabase(Context context, String twit, String user) {
        SQLiteDatabase twitsDb = new TwitsSqlHelper(context).getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(TwitsTableContract.TwitsEntry.COLUMN_NAME_USER,twit );
        contentValues.put(TwitsTableContract.TwitsEntry.COLUMN_NAME_TWIT, user);

        twitsDb.insert(TwitsTableContract.TwitsEntry.TABLE_NAME, null, contentValues);
        twitsDb.close();
    }


    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

        SaveTwitDialogFragment saveDialog = new SaveTwitDialogFragment();
        SQLiteCursor itemAtPosition = (SQLiteCursor) adapterView.getItemAtPosition(i);
        saveDialog.setSaveListener(getSaveTwitListener(view.getContext(),
                itemAtPosition.getString(itemAtPosition.getColumnIndex(TwitsTableContract.TwitsEntry.COLUMN_NAME_TWIT)),
                itemAtPosition.getString(itemAtPosition.getColumnIndex(TwitsTableContract.TwitsEntry.COLUMN_NAME_USER)) ));
        saveDialog.show(activity.getSupportFragmentManager(), "show");
    }

    private DialogInterface.OnClickListener getSaveTwitListener(final Context context, final String twit, final String user){
       return  new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                writeTwitIntoDatabase(context,user,twit+"a" );
                ((MainActivity)activity).refreshMainList();
            }
        };
    }

}
