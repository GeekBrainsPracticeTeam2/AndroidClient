package com.example.alex.androidclient.models;

/**
 * Created by Jag on 13.03.2017.
 */

public class DictionaryUpdates {

    private String dictionaryName;
    private long last_update_date;

    public String getDictionaryName() {
        return dictionaryName;
    }

    public void setDictionaryName(String dictionaryName) {
        this.dictionaryName = dictionaryName;
    }

    public long getLast_update_date() {
        return last_update_date;
    }

    public void setLast_update_date(long last_update_date) {
        this.last_update_date = last_update_date;
    }
}
