package com.example.alex.androidclient.models;

import java.util.Date;
import java.util.List;

/**
 * Created by Jag on 22.03.2017.
 */

public class DailyStatistics {
    private int siteID;

    private Date date;
    private List<PersonStats> statsList;

    public DailyStatistics() {
    }

    public DailyStatistics(int siteID, Date date, List<PersonStats> statsList) {
        this.siteID = siteID;
        this.date = date;
        this.statsList = statsList;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
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
