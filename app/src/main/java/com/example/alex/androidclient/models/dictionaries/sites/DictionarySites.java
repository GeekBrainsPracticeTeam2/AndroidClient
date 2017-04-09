package com.example.alex.androidclient.models.dictionaries.sites;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DictionarySites {

    @SerializedName("data")
    @Expose
    private List<DictionarySitesData> dictionarySitesDataList = null;

    public List<DictionarySitesData> getDictionarySitesDataList() {
        return dictionarySitesDataList;
    }

    public void setDictionarySitesDataList(List<DictionarySitesData> dictionarySitesDataList) {
        this.dictionarySitesDataList = dictionarySitesDataList;
    }

    public String toString() {
        String result = "";
        for (int i = 0; i < dictionarySitesDataList.size(); i++) {
            result += dictionarySitesDataList.get(i).toString();
        }
        return result;
    }
}