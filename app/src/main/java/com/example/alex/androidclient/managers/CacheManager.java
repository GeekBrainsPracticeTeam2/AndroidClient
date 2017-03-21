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

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jag on 12.03.2017.
 */

public class CacheManager {
    private final String LOG_TAG = this.getClass().getSimpleName();
    JSONHelper jHelper;
    DBHelper dbHelper;
    SQLiteDatabase database;

    public CacheManager(Context context) throws JSONException {
        jHelper = new JSONHelper(1);
        dbHelper = new DBHelper(context);
    }

    public boolean checkUpdates(String table) {
        List<DictionaryUpdates> updates = jHelper.getDictionaryUpdatesList();
        database = dbHelper.getWritableDatabase();
        try {
            Cursor cursor = database.query(DBHelper.TB_UPDATES, null, null, null, null, null, null);

            if (cursor.moveToFirst()) {
                int idIndex = cursor.getColumnIndex(DBHelper.TB_ID_COL_NAME);
                int dateIndex = cursor.getColumnIndex(DBHelper.TB_UPDATES_DATE);
                int tableNameIndex = cursor.getColumnIndex(DBHelper.TB_UPDATES_TBNAME);
                Log.d(LOG_TAG, "checkUpdates for " + table);

                do {
                    for (int i = 0; i < updates.size(); i++) {
                        if ((updates.get(i).getDictionaryName().equalsIgnoreCase(table)) &&
                                updates.get(i).getDictionaryName().equalsIgnoreCase(cursor
                                        .getString(tableNameIndex))) {
                            if (!updates.get(i).getLastUpdateDate().equals(cursor.getString(dateIndex))) {
                                Log.d(LOG_TAG, "Dates of " + table + " is " + updates.get(i).getLastUpdateDate() +
                                        " date in DB is " + cursor.getString(dateIndex));
                                return true;
                            }
                        }
                    }
                } while (cursor.moveToNext());
            } else {
                // if table is empty, than add some data
                Log.d(LOG_TAG, "Adding empty data to Updates table");
                ContentValues cv = new ContentValues();
                cv.put(DBHelper.TB_UPDATES_TBNAME, DBHelper.TB_PERSONS_NAMES);
                cv.put(DBHelper.TB_UPDATES_DATE, "");
                database.insert(DBHelper.TB_UPDATES, null, cv);
                cv.put(DBHelper.TB_UPDATES_TBNAME, DBHelper.TB_SITES_NAME);
                cv.put(DBHelper.TB_UPDATES_DATE, "");
                database.insert(DBHelper.TB_UPDATES, null, cv);
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        database.close();

        return false;
    }

    public void updateDictionary(String table) throws JSONException {
        switch (table) {
            case DBHelper.TB_SITES_NAME:
                JSONHelper siteJsonHelper = new JSONHelper(2);
                database = dbHelper.getWritableDatabase();
                Cursor c = database.query(DBHelper.TB_SITES_NAME, null, null, null, null, null, null);
                database.delete(DBHelper.TB_SITES_NAME, null, null);
                ContentValues cv = new ContentValues();
                for (int i = 0; i < siteJsonHelper.getDictionarySitesList().size(); i++) {
                    cv.put(DBHelper.TB_ID_COL_NAME, siteJsonHelper.getDictionarySitesList().get(i)
                            .getSiteID());
                    cv.put(DBHelper.TB_SITES_URL, siteJsonHelper.getDictionarySitesList().get(i)
                            .getSiteUrl());
                    database.insert(table, null, cv);
                }
                database.close();
                updateDictionaryDate(table);
                break;
            case DBHelper.TB_PERSONS_NAMES:
                Log.d(LOG_TAG, "updateDictionary() persons");
                JSONHelper personsJsonHelper = new JSONHelper(3);
                database = dbHelper.getWritableDatabase();
                c = database.query(DBHelper.TB_PERSONS_NAMES, null, null, null, null, null, null);
                database.delete(DBHelper.TB_PERSONS_NAMES, null, null);
                cv = new ContentValues();
                for (int i = 0; i < personsJsonHelper.getDictionaryPersonsList().size(); i++) {
                    cv.put(DBHelper.TB_ID_COL_NAME, personsJsonHelper.getDictionaryPersonsList()
                            .get(i).getPersonId());
                    cv.put(DBHelper.TB_PERSON_NAME, personsJsonHelper.getDictionaryPersonsList()
                            .get(i).getPersonName());
                    Log.d(LOG_TAG, "Inserting in " + DBHelper.TB_PERSONS_NAMES + " " + personsJsonHelper.getDictionaryPersonsList()
                            .get(i).getPersonId() + " " + personsJsonHelper.getDictionaryPersonsList()
                            .get(i).getPersonName());
                    database.insert(table, null, cv);
                }
                database.close();
                updateDictionaryDate(table);
                break;
            default:
                break;
        }
    }

    private void updateDictionaryDate(String table) {
        Log.d(LOG_TAG, "Starting to update date dictionary");
        database = dbHelper.getWritableDatabase();
        ContentValues cv = new ContentValues();
        try {
            Cursor cursor = database.query(DBHelper.TB_UPDATES, null, null, null, null, null, null);
            List<DictionaryUpdates> updates = jHelper.getDictionaryUpdatesList();

            for (int i = 0; i < updates.size(); i++) {
                //if (table.equalsIgnoreCase(cursor.getString(tableNameIndex))) {
                cv.put(DBHelper.TB_UPDATES_DATE, updates.get(i).getLastUpdateDate());
                int res = database.update(DBHelper.TB_UPDATES,
                        cv,
                        "" + DBHelper.TB_UPDATES_TBNAME + " = ?",
                        new String[] {table});
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        database.close();
    }

    public List<TotalStatistics> getTotalStatistics() throws JSONException {
        Log.d(LOG_TAG, "Start getTotalStatistics");
        JSONHelper jHelperTotalStats = new JSONHelper(0);
        Log.d(LOG_TAG, "Size totalStatisticsList = " + jHelperTotalStats.getTotalStats().size());
        Log.d(LOG_TAG, "End getTotalStatistics");
        return jHelperTotalStats.getTotalStats();
    }

    public TotalStatistics getTotalStatisticsBySite(int siteId) throws JSONException {
        Log.d(LOG_TAG, "Start getTotalStatisticsBySite");
        Log.d(LOG_TAG, "siteId = " + siteId);
        JSONHelper jHelperTotalStats = new JSONHelper(0);
        List<TotalStatistics> totalStats = jHelperTotalStats.getTotalStats();
        Log.d(LOG_TAG, "Size totalStatis = " + jHelperTotalStats.getTotalStats().size());
        for (TotalStatistics stats: totalStats) {
            if(stats.getSiteID() == siteId){
                Log.d(LOG_TAG, "Size stats = " + stats.getStatsList().size());
                return stats;
            }
        }
        return null;
    }

    public List<DictionarySites> getSitesDictionary() throws JSONException {
        List<DictionarySites> dictionarySites = new ArrayList<>();
        Log.d(LOG_TAG, "" + checkUpdates(DBHelper.TB_SITES_NAME));
        if(checkUpdates(DBHelper.TB_SITES_NAME)) {
            Log.d(LOG_TAG, "Needs to update SitesDictionary");
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
        database.close();

        return dictionarySites;
    }

    public List<DictionaryPersons> getPersonsDictionary() throws JSONException {
        List<DictionaryPersons> dictionaryPersons = new ArrayList<>();
        if(checkUpdates(DBHelper.TB_PERSONS_NAMES)) {
            Log.d(LOG_TAG, "Needs to update PersonsDictionary");
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
        database.close();

        return dictionaryPersons;
    }
}
