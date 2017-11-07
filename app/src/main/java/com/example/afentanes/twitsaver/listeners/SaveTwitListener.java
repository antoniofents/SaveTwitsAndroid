package com.example.afentanes.twitsaver.listeners;

import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;

import com.example.afentanes.twitsaver.R;
import com.example.afentanes.twitsaver.Twit;
import com.example.afentanes.twitsaver.TwitSaverUtil;
import com.example.afentanes.twitsaver.db.TwitsSqlHelper;
import com.example.afentanes.twitsaver.db.TwitsTableContract;
import com.example.afentanes.twitsaver.layout.SaveTwitDialogFragment;


public class SaveTwitListener implements AdapterView.OnItemClickListener {

    private AppCompatActivity activity;
    public SaveTwitListener(AppCompatActivity appCompatActivity) {
        this.activity=appCompatActivity;
    }

    private void writeTwitIntoDatabase(Context context, String user, String twit) {
        SQLiteDatabase twitsDb = new TwitsSqlHelper(context).getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(TwitsTableContract.TwitsEntry.COLUMN_NAME_USER,user );
        contentValues.put(TwitsTableContract.TwitsEntry.COLUMN_NAME_TWIT, twit);

        twitsDb.insert(TwitsTableContract.TwitsEntry.TABLE_NAME, null, contentValues);
        twitsDb.close();
    }


    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {


        SaveTwitDialogFragment saveDialog = TwitSaverUtil.getDialogFragment(R.string.save_twit_dialog_message,R.string.save_twit,R.string.cancel_twit);
        Twit twit= (Twit) adapterView.getItemAtPosition(i);
        saveDialog.setSaveListener(getSaveTwitButtonListener(view.getContext(),
                twit.author , twit.text));
        saveDialog.show(activity.getSupportFragmentManager(), "show");
    }

    private DialogInterface.OnClickListener getSaveTwitButtonListener(final Context context, final String author, final String twit){
       return  new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                writeTwitIntoDatabase(context,author,twit );
            }
        };
    }

}
