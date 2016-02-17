package com.garagze.event.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by jamesharmon on 8/5/15.
 */
public class GaragzeDbHelper extends SQLiteOpenHelper {

    public GaragzeDbHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    private static final String DATABASE_CREATE =
            "create table events (" +
                    "_id integer primary key autoincrement, " +
                    "id text, " +
                    "date date, " +
                    "title text, " +
                    "street text, " +
                    "city text, " +
                    "state text, " +
                    "zip text, " +
                    "latitude double, " +
                    "longitude double, " +
                    "description text, " +
                    "rating double, " +
                    "distance double " +
                    ");";

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.v("GaragzeDBHelper", "Create Table using " + DATABASE_CREATE);
        db.execSQL(DATABASE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.v("GaragzeDBHelper", "Drop table");
        db.execSQL("drop table if exists events");
        onCreate(db);
    }
}
