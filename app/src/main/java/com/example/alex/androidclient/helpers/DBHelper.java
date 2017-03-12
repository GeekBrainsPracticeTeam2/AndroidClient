package com.example.alex.androidclient.helpers;

/**
 * Created by Jag on 09.03.2017.
 *
 * Database helper for caching data
 */

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {
    public static final String DB_NAME = "humaninweb.db";
    public static final String TB_SITES_NAME = "sites";
    public static final String TB_URL = "url";
    public static final String TB_PERSONS_NAMES = "persons";
    public static final String TB_PERSON_NAME = "name";
    public static final String TB_UPDATES = "last_update";
    public static final String TB_UPDATES_DATE = "date";
    public static final String TB_UPDATES_TBNAME = "table_name";
    public static final String TB_ID_COL_NAME = "_id";
    final String DROP_TABLE = "DROP TABLE IF EXISTS "
                                + TB_SITES_NAME + ", "
                                + TB_PERSONS_NAMES + ", "
                                + TB_UPDATES;


    public DBHelper(Context context) {
        super(context, DB_NAME, null, 1);
    }
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        try {
            sqLiteDatabase.execSQL("CREATE TABLE " + TB_SITES_NAME + " ("
                    + TB_ID_COL_NAME + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + TB_URL + " STRING);");
            sqLiteDatabase.execSQL("CREATE TABLE " + TB_PERSONS_NAMES +" ("
                    + TB_ID_COL_NAME + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + TB_PERSON_NAME + " STRING);");
            sqLiteDatabase.execSQL("CREATE TABLE " + TB_UPDATES +" ("
                    + TB_ID_COL_NAME + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + TB_UPDATES_DATE + " INTEGER, "
                    + TB_UPDATES_TBNAME + " STRING);");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL(DROP_TABLE);
        onCreate(sqLiteDatabase);
    }
}
