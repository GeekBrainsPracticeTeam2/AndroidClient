package com.example.alex.androidclient.managers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.alex.androidclient.helpers.DBHelper;
import com.example.alex.androidclient.helpers.JSONHelper;
import com.example.alex.androidclient.models.DictionaryPersons;
import com.example.alex.androidclient.models.DictionarySites;
import com.example.alex.androidclient.models.DictionaryUpdates;
import com.example.alex.androidclient.models.TotalStatistics;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jag on 12.03.2017.
 */

public class CacheManager {
    public static final String LOG_TAG = "CacheManager";
    JSONHelper jHelper;
    DBHelper dbHelper;
    SQLiteDatabase database;

    public CacheManager(Context context) {
        jHelper = new JSONHelper(1);
        dbHelper = new DBHelper(context);
    }

    public boolean checkUpdates(String table) {
        List<DictionaryUpdates> updates = jHelper.getDictionaryUpdatesList();
        database = dbHelper.getWritableDatabase();
        try {
            Cursor cursor = database.query(table, null, null, null, null, null, null);

            if (cursor.moveToFirst()) {
                int idIndex = cursor.getColumnIndex(DBHelper.TB_ID_COL_NAME);
                int dateIndex = cursor.getColumnIndex(DBHelper.TB_UPDATES_DATE);
                int tableNameIndex = cursor.getColumnIndex(DBHelper.TB_UPDATES_TBNAME);

                for (int i = 0; i < updates.size(); i++) {
                    if((updates.get(i).getDictionaryName().equalsIgnoreCase(table)) &&
                            updates.get(i).getDictionaryName().equalsIgnoreCase(cursor
                                    .getString(tableNameIndex))) {
                        if(updates.get(i).getLast_update_date() == cursor.getInt(dateIndex))
                            return true;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    public void updateDictionary(String table) {
        switch (table) {
            case DBHelper.TB_SITES_NAME:
                JSONHelper siteJsonHelper = new JSONHelper(2);
                Cursor c = database.query(DBHelper.TB_SITES_NAME, null, null, null, null, null, null);
                ContentValues cv = new ContentValues();
                for (int i = 0; i < siteJsonHelper.getDictionarySitesList().size(); i++) {
                    cv.put(DBHelper.TB_ID_COL_NAME, siteJsonHelper.getDictionarySitesList().get(i)
                            .getSiteID());
                    cv.put(DBHelper.TB_SITES_URL, siteJsonHelper.getDictionarySitesList().get(i)
                            .getSiteUrl());
                    database.insert(table, null, cv);
                }
                break;
            case DBHelper.TB_PERSONS_NAMES:
                JSONHelper personsJsoHelper = new JSONHelper(3);
                c = database.query(DBHelper.TB_PERSONS_NAMES, null, null, null, null, null, null);
                cv = new ContentValues();
                for (int i = 0; i < personsJsoHelper.getDictionaryPersonsList().size(); i++) {
                    cv.put(DBHelper.TB_ID_COL_NAME, personsJsoHelper.getDictionaryPersonsList()
                            .get(i).getPersonId());
                    cv.put(DBHelper.TB_PERSON_NAME, personsJsoHelper.getDictionaryPersonsList()
                            .get(i).getPersonName());
                    database.insert(table, null, cv);
                }
                break;
            default:
                break;
        }
    }

    public TotalStatistics getTotalStatistics() {
        JSONHelper jHelperTotalStats = new JSONHelper(0);
        return jHelperTotalStats.getTotalStats();
    }

    public List<DictionarySites> getSitesDictionary() {
        List<DictionarySites> dictionarySites = new ArrayList<>();
        if(checkUpdates(DBHelper.TB_SITES_NAME)) {
            updateDictionary(DBHelper.TB_SITES_NAME);
        }

        database = dbHelper.getWritableDatabase();
        try {
            Cursor cursor = database.query(DBHelper.TB_SITES_NAME, null, null, null, null, null, null);

            if(cursor.moveToFirst()) {
                // taking numbers of columns by their names
                int idIndex = cursor.getColumnIndex(DBHelper.TB_ID_COL_NAME);
                int urlIndex = cursor.getColumnIndex(DBHelper.TB_SITES_URL);
                do {
                    dictionarySites.add(new DictionarySites(cursor.getInt(idIndex),
                            cursor.getString(urlIndex)));
                } while (cursor.moveToNext());
            }

            Log.d(LOG_TAG, "Sites dictionary count of rows = " + cursor.getCount());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return dictionarySites;
    }

    public List<DictionaryPersons> getPersonsDictionary() {
        List<DictionaryPersons> dictionaryPersons = new ArrayList<>();
        if(checkUpdates(DBHelper.TB_PERSONS_NAMES)) {
            updateDictionary(DBHelper.TB_PERSONS_NAMES);
        }

        database = dbHelper.getWritableDatabase();
        try {
            Cursor cursor = database.query(DBHelper.TB_PERSONS_NAMES, null, null, null, null, null, null);

            if(cursor.moveToFirst()) {
                // taking numbers of columns by their names
                int idIndex = cursor.getColumnIndex(DBHelper.TB_ID_COL_NAME);
                int personNameIndex = cursor.getColumnIndex(DBHelper.TB_PERSON_NAME);
                do {
                    dictionaryPersons.add(new DictionaryPersons(cursor.getInt(idIndex),
                            cursor.getString(personNameIndex)));
                } while (cursor.moveToNext());
            }

            Log.d(LOG_TAG, "Persons dictionary count of rows = " + cursor.getCount());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return dictionaryPersons;
    }
}
