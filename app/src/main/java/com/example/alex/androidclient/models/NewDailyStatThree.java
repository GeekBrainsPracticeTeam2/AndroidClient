package com.example.alex.androidclient.models;

import java.util.Date;

/**
 * Created by alex on 23.03.17.
 */

public class NewDailyStatThree {
    private Date[] dates;
    private int[] likeCount;

    public NewDailyStatThree(Date[] dates, int[] likeCount) {
        this.dates = dates;
        this.likeCount = likeCount;
    }
}
