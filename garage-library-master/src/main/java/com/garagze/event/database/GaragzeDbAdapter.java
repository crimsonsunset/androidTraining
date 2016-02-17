package com.garagze.event.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.garagze.event.domain.SaleEvent;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jamesharmon on 8/5/15.
 */
public class GaragzeDbAdapter {

    private static final int DATABASE_VERSION = 12;
    private static final String DATABASE_NAME = "garagze.db";

    private GaragzeDbHelper dbHelper;

    public GaragzeDbAdapter(Context context) {
        dbHelper = new GaragzeDbHelper(
                context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public SQLiteDatabase db;

    public GaragzeDbAdapter open() {
        db = dbHelper.getWritableDatabase();
        return this;
    }
    public void close() {
        dbHelper.close();
    }

    private Cursor getAllEntries() {
        String[] columns = new String[10];
        columns[0] = "id";
        columns[1] = "date";
        columns[2] = "title";
        columns[3] = "street";
        columns[4] = "city";
        columns[5] = "rating";
        columns[6] = "distance";
        columns[7] = "description";
        columns[8] = "latitude";
        columns[9] = "longitude";
        return db.query("events", columns, null, null, null, null, null);
    }

    public List<SaleEvent> getAllSaleEvents() {
        Cursor cursor = getAllEntries();

        ArrayList<SaleEvent> events = new ArrayList<SaleEvent>();

        if (cursor.moveToFirst()) {
            do {
                SaleEvent event = new SaleEvent();

                event.setId(cursor.getString(cursor.getColumnIndex("id")));
                event.setDate( new java.util.Date( cursor.getLong(1)) );
                event.setTitle(cursor.getString(2));
                event.setStreet(cursor.getString(3));
                event.setCity(cursor.getString(4));
                event.setRating(Float.parseFloat(cursor.getString(5)));
                event.setDistance(Double.parseDouble(cursor.getString(6)));
                event.setDescription(cursor.getString(7));
                event.setLatitude(cursor.getDouble(8));
                event.setLongitude(cursor.getDouble(9));

                events.add(event);

                Log.v("GaragzeDbAdapter", "Get event: id=" + event.getId());
            } while (cursor.moveToNext());
        }
        return events;
    }

    public long insertSaleEvent(SaleEvent event) {

        ContentValues values = new ContentValues();

        values.put("id", event.getId());
        values.put("date", Long.toString(event.getDate().getTime()) );
        values.put("title", event.getTitle());
        values.put("street", event.getStreet());
        values.put("city", event.getCity());
        values.put("rating", event.getRating());
        values.put("distance", event.getDistance());
        values.put("description", event.getDescription());
        values.put("latitude", event.getLatitude());
        values.put("longitude", event.getLongitude());

        Log.v("GaragzeDbAdapter", "Insert event:id="+event.getId());

        return db.insert("events", null, values);
    }


    public int deleteEvent(String eventId) {
        String whereClause = "id = ?";
        String[] whereArgs = new String[1];
        whereArgs[0] = eventId;
        return db.delete("events", whereClause, whereArgs);
    }

    public int deleteAllEvents() {
        return db.delete("events", null, null);
    }

    public int updateEvent(SaleEvent event) {
        String whereClause = "id = ?";

        String[] whereArgs = new String[1];
        whereArgs[0] = event.getId();

        ContentValues values = new ContentValues();

        values.put("id", event.getId());
        values.put("date", Long.toString(event.getDate().getTime()));
        values.put("title", event.getTitle());
        values.put("street", event.getStreet());
        values.put("city", event.getCity());
        values.put("rating", event.getRating());
        values.put("distance", event.getDistance());
        values.put("description", event.getDescription());
        values.put("latitude", event.getLatitude());
        values.put("longitude", event.getLongitude());

        return db.update("events", values, whereClause, whereArgs);
    }

    public SaleEvent getEvent(String eventId) {
        String[] columns = new String[10];
        columns[0] = "id";
        columns[1] = "date";
        columns[2] = "title";
        columns[3] = "street";
        columns[4] = "city";
        columns[5] = "rating";
        columns[6] = "distance";
        columns[7] = "description";
        columns[8] = "latitude";
        columns[9] = "longitude";
        String selection = "id = ?";
        String[] selectionArgs = new String[1];
        selectionArgs[0] = eventId;
        String groupBy = null;
        String having = null;
        String orderBy = null;

        Cursor cursor = db.query("events", columns, selection, selectionArgs, groupBy, having, orderBy);

        ArrayList<SaleEvent> events = new ArrayList<SaleEvent>();
        if (cursor.moveToFirst()) {
            do {
                SaleEvent event = new SaleEvent();
                event.setId(cursor.getString(0));
                event.setDate(new java.util.Date(cursor.getLong(1)));
                event.setTitle(cursor.getString(2));
                event.setStreet(cursor.getString(3));
                event.setCity(cursor.getString(4));
                event.setRating(Float.parseFloat(cursor.getString(5)));
                event.setDistance(Double.parseDouble(cursor.getString(6)));
                event.setDescription(cursor.getString(7));
                event.setLatitude(cursor.getDouble(8));
                event.setLongitude(cursor.getDouble(9));
                events.add(event);
                Log.v("GaragzeDbAdapter", "Get event: id=" + event.getId());
            } while (cursor.moveToNext());
        }
        return events.get(0);
    }

}
