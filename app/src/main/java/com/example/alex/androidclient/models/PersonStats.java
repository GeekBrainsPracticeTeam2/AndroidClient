package com.example.alex.androidclient.models;

/**
 * Created by Jag on 10.03.2017.
 *
 * Person stats model
 */

public class PersonStats {
    private int personID;
    private int likesCount;

    public PersonStats(int personID, int likesCount) {
        this.personID = personID;
        this.likesCount = likesCount;
    }

    public int getPersonID() {
        return personID;
    }

    public void setPersonID(int personID) {
        this.personID = personID;
    }

    public int getLikesCount() {
        return likesCount;
    }

    public void setLikesCount(int likesCount) {
        this.likesCount = likesCount;
    }
}
