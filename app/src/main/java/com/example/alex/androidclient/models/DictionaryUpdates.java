package com.example.alex.androidclient.models;

/**
 * Created by Jag on 13.03.2017.
 */

public class DictionaryUpdates {

    private int dictionaryID;
    private int last_update_date;

    public int getDictionaryID() {
        return dictionaryID;
    }

    public void setDictionaryID(int dictionaryID) {
        this.dictionaryID = dictionaryID;
    }

    public int getLast_update_date() {
        return last_update_date;
    }

    public void setLast_update_date(int last_update_date) {
        this.last_update_date = last_update_date;
    }
}
