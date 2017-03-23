package com.example.alex.androidclient.models;

import java.util.List;

/**
 * Created by alex on 23.03.17.
 */

public class NewDailyStatOne {
    private int siteID;
    private List<NewDailyStatTwo> newDailyStatTwoList;

    public NewDailyStatOne(int siteID, List<NewDailyStatTwo> newDailyStatTwoList) {
        this.siteID = siteID;
        this.newDailyStatTwoList = newDailyStatTwoList;
    }
}
