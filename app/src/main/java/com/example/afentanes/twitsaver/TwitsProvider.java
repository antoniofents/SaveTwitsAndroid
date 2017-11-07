package com.example.afentanes.twitsaver;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.example.afentanes.twitsaver.db.TwitsSqlHelper;
import com.example.afentanes.twitsaver.db.TwitsTableContract;

/**
 * Created by afentanes on 10/31/17.
 */

public class TwitsProvider extends ContentProvider {

    private static final String PROVIDER_NAME = "afentanes.twitsaver.twitssprovider";
    private static final Uri CONTENT_URI = Uri.parse("content://" + PROVIDER_NAME + "/twits");


    private static final int TWIT = 1;
    private static final int TWITS = 2;
    private static final int TWITS_ALL = 3;

    TwitsSqlHelper twitsHelper;

    private static UriMatcher getUriMatcher() {
        UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

        uriMatcher.addURI(PROVIDER_NAME, "twit/id", TWIT);
        uriMatcher.addURI(PROVIDER_NAME, "twits/user", TWITS);
        uriMatcher.addURI(PROVIDER_NAME, "twits/all", TWITS_ALL);
        return uriMatcher;
    }


    @Override
    public String getType(Uri uri) {
        switch (getUriMatcher().match(uri)) {
            case TWIT:
                return "vnd.android.cursor.item/vnd.com.afentanes.twitsaver.twitssprovider.twit";
            default:
                return "vnd.android.cursor.dir/vnd.com.afentanes.twitsaver.twitssprovider.twits";

        }
    }

    @Override
    public boolean onCreate() {
        twitsHelper= new TwitsSqlHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] strings, @Nullable String s, @Nullable String[] strings1, @Nullable String s1) {

        switch (getUriMatcher().match(uri)) {
            case TWIT:
                return twitsHelper.getReadableDatabase().rawQuery("SELECT rowid _id , * FROM " + TwitsTableContract.TwitsEntry.TABLE_NAME + " WHERE "+TwitsTableContract.TwitsEntry.COLUMN_NAME_ID + "= ?", strings1, null);
            case TWITS:
                return twitsHelper.getReadableDatabase().rawQuery("SELECT rowid _id , * FROM " + TwitsTableContract.TwitsEntry.TABLE_NAME + " WHERE "+TwitsTableContract.TwitsEntry.COLUMN_NAME_USER + "= ?", new String[]{uri.getPathSegments().get(0)});
            default:
                return twitsHelper.getReadableDatabase().rawQuery("SELECT rowid _id , * FROM " + TwitsTableContract.TwitsEntry.TABLE_NAME ,null);

        }

    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {
        return null;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String s, @Nullable String[] strings) {
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String s, @Nullable String[] strings) {
        return 0;
    }
}
