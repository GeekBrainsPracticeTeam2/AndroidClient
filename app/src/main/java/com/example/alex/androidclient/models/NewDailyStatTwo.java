package com.example.alex.androidclient.models;

import java.util.Date;
import java.util.List;

/**
 * Created by alex on 23.03.17.
 */

public class NewDailyStatTwo {
    private int personID;
    private List<NewDailyStatThree> newDailyStatThreeList;

    public NewDailyStatTwo(int personID, List<NewDailyStatThree> newDailyStatThreeList) {
        this.personID = personID;
        this.newDailyStatThreeList = newDailyStatThreeList;
    }
}
