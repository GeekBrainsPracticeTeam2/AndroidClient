package com.example.alex.androidclient.models;

/**
 * Created by Jag on 13.03.2017.
 */

public class DictionaryUpdates {

    private String dictionaryName;
    private long lastUpdateDate;

    public String getDictionaryName() {
        return dictionaryName;
    }

    public void setDictionaryName(String dictionaryName) {
        this.dictionaryName = dictionaryName;
    }

    public long getLastUpdateDate() {
        return lastUpdateDate;
    }

    public void setLastUpdateDate(long lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
    }
}
