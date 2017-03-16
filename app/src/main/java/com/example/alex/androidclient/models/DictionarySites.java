package com.example.alex.androidclient.models;

/**
 * Created by Jag on 16.03.2017.
 */

public class DictionarySites {
    private int siteID;
    private String siteUrl;

    public DictionarySites(int siteID, String siteUrl) {
        this.siteID = siteID;
        this.siteUrl = siteUrl;
    }

    public int getSiteID() {
        return siteID;
    }

    public void setSiteID(int siteID) {
        this.siteID = siteID;
    }

    public String getSiteUrl() {
        return siteUrl;
    }

    public void setSiteUrl(String siteUrl) {
        this.siteUrl = siteUrl;
    }
}
