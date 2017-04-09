package com.example.alex.androidclient.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;
import java.util.List;

/**
 * Created by Jag on 22.03.2017.
 */

public class DailyStatistics implements Parcelable {
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(siteID);
    }

    protected DailyStatistics(Parcel in) {
        siteID = in.readInt();
    }

    public static final Creator<DailyStatistics> CREATOR = new Creator<DailyStatistics>() {
        @Override
        public DailyStatistics createFromParcel(Parcel in) {
            return new DailyStatistics(in);
        }

        @Override
        public DailyStatistics[] newArray(int size) {
            return new DailyStatistics[size];
        }
    };
}
