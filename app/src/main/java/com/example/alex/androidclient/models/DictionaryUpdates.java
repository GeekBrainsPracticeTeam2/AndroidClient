package com.example.alex.androidclient.models;

/**
 * Created by Jag on 13.03.2017.
 */

public class DictionaryUpdates {

    private String dictionaryName;
    private String lastUpdateDate;

    public String getDictionaryName() {
        return dictionaryName;
    }

    public void setDictionaryName(String dictionaryName) {
        this.dictionaryName = dictionaryName;
    }

    public String getLastUpdateDate() {
        return lastUpdateDate;
    }

    public void setLastUpdateDate(String lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
    }
}
