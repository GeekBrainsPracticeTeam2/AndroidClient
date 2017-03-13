package com.example.alex.androidclient.managers;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.example.alex.androidclient.helpers.DBHelper;
import com.example.alex.androidclient.helpers.JSONHelper;
import com.example.alex.androidclient.models.DictionaryUpdates;

import java.util.List;

/**
 * Created by Jag on 12.03.2017.
 */

public class CacheManager {
    JSONHelper jHelper;
    DBHelper dbHelper;
    SQLiteDatabase database;

    public CacheManager(Context context) {
        jHelper = new JSONHelper(1);
        dbHelper = new DBHelper(context);
        database = dbHelper.getWritableDatabase();
    }

    public boolean checkUpdates() {
        // TODO: add functionality
        List<DictionaryUpdates> updates = jHelper.getDictionaryUpdatesList();
        

        return true;
    }


}
