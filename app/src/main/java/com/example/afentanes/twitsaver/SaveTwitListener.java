package com.example.afentanes.twitsaver;

import android.content.ContentValues;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.support.constraint.ConstraintLayout;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.afentanes.twitsaver.db.TwitsSqlHelper;
import com.example.afentanes.twitsaver.db.TwitsTableContract;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;



public class SaveTwitListener implements View.OnClickListener {

    @Override
    public void onClick(View view) {
        LinearLayout linearLayout = (LinearLayout) view.getParent();
        TextView viewById = linearLayout.findViewById(R.id.editText);
        writeTwitIntoDatabase(view.getContext(),String.valueOf(viewById.getText()), "user");
    }

    public void writeTwitIntoDatabase(Context context, String twit, String user){
        SQLiteDatabase twitsDb = new TwitsSqlHelper(context).getWritableDatabase();

        ContentValues contentValues= new ContentValues();
        contentValues.put(TwitsTableContract.TwitsEntry.COLUMN_NAME_USER, user);
        contentValues.put(TwitsTableContract.TwitsEntry.COLUMN_NAME_TWIT, twit);

        twitsDb.insert(TwitsTableContract.TwitsEntry.TABLE_NAME, null, contentValues);
    }


}
