package com.example.alex.androidclient.models;

import java.util.List;

/**
 * Created by Jag on 10.03.2017.
 *
 * Total statistics model
 */

public class TotalStatistics {
    private int siteID;
    private List<PersonStats> statsList;

    public TotalStatistics(int siteID, List<PersonStats> statsList) {
        this.siteID = siteID;
        this.statsList = statsList;
    }

    public int getSiteID() {
        return siteID;
    }

    public void setSiteID(int siteID) {
        this.siteID = siteID;
    }

    public List<PersonStats> getStatsList() {
        return statsList;
    }

    public void setStatsList(List<PersonStats> statsList) {
        this.statsList = statsList;
    }
}
