package com.garagze.event.database;

import android.net.Uri;
import android.provider.BaseColumns;

public class GaragzeContract {

    // DB specific constants
    public static final String DB_NAME = "garagze.db";
    public static final int DB_VERSION = 1;
    public static final String TABLE = "events";

    // Provider specific constants
    // content://com.garagze.event.EventProvider/events
    public static final String AUTHORITY = "com.garagze.event.EventProvider";
    public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/" + TABLE);
    public static final int EVENT_ITEM = 1;
    public static final int EVENT_DIR = 2;
    public static final String EVENT_TYPE_ITEM = "vnd.android.cursor.item/vnd.com.garagze.provider.event";
    public static final String EVENT_TYPE_DIR = "vnd.android.cursor.dir/vnd.com.garagze.provider.event";
    public static final String DEFAULT_SORT = Column.DISTANCE + " DESC";  // Descending

    public class Column {
        public static final String ID = BaseColumns._ID;  // "_id"
        //public static final String ID = "id";
        public static final String DATE = "date";
        public static final String TITLE = "title";
        public static final String STREET = "street";
        public static final String CITY = "city";
        public static final String RATING = "rating";
        public static final String DISTANCE = "distance";
        public static final String DESCRIPTION = "description";
        public static final String LATITUDE = "latitude";
        public static final String LONGITUDE = "longitude";

    }
}
