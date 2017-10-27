package com.example.afentanes.twitsaver.db;

import android.provider.BaseColumns;

/**
 * Created by afentanes on 10/25/17.
 */

public class TwitsTableContract {

    public static class TwitsEntry implements BaseColumns {
        public static final String TABLE_NAME = "twits";
        public static final String COLUMN_NAME_TWIT = "twit";
        public static final String COLUMN_NAME_USER = "user";
        public static final String COLUMN_NAME_ID = "id";

    }


    public static String CREATE_TWITS_TABLE="CREATE TABLE " +TwitsEntry.TABLE_NAME+ " ("+
            TwitsEntry.COLUMN_NAME_ID +" INTEGER PRIMARY KEY, "+
            TwitsEntry.COLUMN_NAME_USER + " TEXT, " +
            TwitsEntry.COLUMN_NAME_TWIT+ " TEXT );";

    public static String[] TWITS_PROJECTION = new String[]{TwitsEntry.COLUMN_NAME_ID, TwitsEntry.COLUMN_NAME_USER,TwitsEntry.COLUMN_NAME_TWIT};
    public static String[] TWITS_BASIC_PROJECTION = new String[]{TwitsEntry.COLUMN_NAME_TWIT};
}
