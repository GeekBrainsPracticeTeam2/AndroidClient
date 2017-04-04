package com.example.alex.androidclient.data;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.example.alex.androidclient.helpers.DBHelper;

/**
 * Created by alex on 04.04.17.
 */

public class DataProvider extends ContentProvider {
    private final String LOG_TAG = this.getClass().getSimpleName();

    //Uri authority
    static final String AUTHORITY = "com.example.alex.androidclient";

    // path
    static final String CONTACT_PATH = "contacts";

    // Общий Uri
    public static final Uri CONTACT_CONTENT_URI = Uri.parse("content://"
            + AUTHORITY + "/" + CONTACT_PATH);

    // Типы данных
    // набор строк
    static final String CONTACT_CONTENT_TYPE = "vnd.android.cursor.dir/vnd."
            + AUTHORITY + "." + CONTACT_PATH;

    // одна строка
    static final String CONTACT_CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd."
            + AUTHORITY + "." + CONTACT_PATH;

    //// UriMatcher
    // общий Uri
    static final int URI_CONTACTS = 1;

    // Uri с указанным ID
    static final int URI_CONTACTS_ID = 2;

    private static final UriMatcher uriMatcher;
    static {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(AUTHORITY, CONTACT_PATH, URI_CONTACTS);
        uriMatcher.addURI(AUTHORITY, CONTACT_PATH + "/#", URI_CONTACTS_ID);
    }

    DBHelper dbHelper;
    SQLiteDatabase db;


    @Override
    public boolean onCreate() {
        dbHelper = new DBHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection,
                        @Nullable String selection, @Nullable String[] selectionArgs,
                        @Nullable String sortOrder) {
        switch (uriMatcher.match(uri)) {
            case URI_CONTACTS:

                break;
            case URI_CONTACTS_ID:

                break;
            default:
                throw new IllegalArgumentException("Wrong URI: " + uri);
        }

        db = dbHelper.getWritableDatabase();
        Cursor cursor = db.query(CONTACT_PATH, projection, selection, selectionArgs, null,
                null, sortOrder);
        cursor.setNotificationUri(getContext().getContentResolver(), CONTACT_CONTENT_URI);

        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        return null;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }
}
