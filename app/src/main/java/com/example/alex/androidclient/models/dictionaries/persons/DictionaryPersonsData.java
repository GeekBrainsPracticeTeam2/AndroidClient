package com.example.alex.androidclient.models.dictionaries.persons;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DictionaryPersonsData {

    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("Id")
    @Expose
    private int id;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String toString() {
        return name + " " + id;
    }

}