package com.example.afentanes.twitsaver;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteCursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import com.example.afentanes.twitsaver.db.TwitsSqlHelper;
import com.example.afentanes.twitsaver.db.TwitsTableContract;
import com.example.afentanes.twitsaver.layout.SaveTwitDialogFragment;


public class TwitsStoreActivity extends AppCompatActivity {

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
                android.R.layout.simple_list_item_activated_2, null,
                TwitsTableContract.TWITS_BASIC_PROJECTION,
                new int[]{android.R.id.text1}, 0){

        };
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                SaveTwitDialogFragment saveDialog= TwitSaverUtil.getDialogFragment(R.string.print_twit_dialog_message, R.string.print_twit, R.string.cancel_twit);
                saveDialog.setSaveListener(getSaveTwitButtonListener(view.getContext(),
                        (SQLiteCursor) adapterView.getItemAtPosition(i)));
                saveDialog.show(getSupportFragmentManager(), "show");
            }
        });
        adapter.changeCursor(cursor);
        twitsDb.close();
    }



    private DialogInterface.OnClickListener getSaveTwitButtonListener(final Context context, final SQLiteCursor itemAtPosition){
        return  new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent intent= new Intent(getResources().getString(R.string.print_twits_service));
                intent.setPackage("com.example.afentanes.twitprinter");
                Bundle bundle= new Bundle();
                bundle.putLong("id",itemAtPosition.getLong(itemAtPosition.getColumnIndex(TwitsTableContract.TwitsEntry.COLUMN_NAME_ID)));
                intent.putExtras(bundle);
                sendBroadcast(intent);
            }
        };
    }
}
