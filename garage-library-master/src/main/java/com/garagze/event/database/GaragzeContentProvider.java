package com.garagze.event.database;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;

public class GaragzeContentProvider extends ContentProvider {

    private static final String TAG = GaragzeContentProvider.class.getSimpleName();

    private static final UriMatcher sURIMatcher = new UriMatcher(
            UriMatcher.NO_MATCH);

    static {
        sURIMatcher.addURI(GaragzeContract.AUTHORITY, GaragzeContract.TABLE,
                GaragzeContract.EVENT_DIR);
        sURIMatcher.addURI(GaragzeContract.AUTHORITY, GaragzeContract.TABLE
                + "/#", GaragzeContract.EVENT_ITEM);
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        // Implement this to handle requests to delete one or more rows.

        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public String getType(Uri uri) {
        // TODO: Implement this to handle requests for the MIME type of the data
        // at the given URI.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        // TODO: Implement this to handle requests to insert a new row.
        //throw new UnsupportedOperationException("Not yet implemented");

        GaragzeDbAdapter dbAdapter = new GaragzeDbAdapter(getContext());
        SQLiteDatabase db = dbAdapter.open().db;

        long rowId = db.insert("events", null, values);

        if (rowId == -1) {
            Log.d(TAG, "Failed to insert " + values + " to " + uri);
            return null;
        } else {
            Uri itemUri = ContentUris.withAppendedId(uri, rowId);
            super.getContext().getContentResolver().notifyChange(itemUri, null);
            return itemUri;
        }

    }

    @Override
    public boolean onCreate() {
        // TODO: Implement this to initialize your content provider on startup.
        return false;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {

        GaragzeDbAdapter dbAdapter = new GaragzeDbAdapter(getContext());
        SQLiteDatabase db = dbAdapter.open().db;

        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
        qb.setTables(GaragzeContract.TABLE);

        switch (sURIMatcher.match(uri)) {
            case GaragzeContract.EVENT_DIR:
                break;
            case GaragzeContract.EVENT_ITEM:
                qb.appendWhere(GaragzeContract.Column.ID + "="
                        + uri.getLastPathSegment());
                break;
            default:
                throw new IllegalArgumentException("Illegal uri: " + uri);
        }

        String orderBy = (TextUtils.isEmpty(sortOrder)) ? GaragzeContract.DEFAULT_SORT
                : sortOrder;

        Cursor cursor = qb.query(db, projection, selection, selectionArgs, null, null, orderBy);

        // register for uri changes
        cursor.setNotificationUri(getContext().getContentResolver(), uri);

        Log.d(TAG, "queried records: " + cursor.getCount());
        return cursor;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        // TODO: Implement this to handle requests to update one or more rows.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
