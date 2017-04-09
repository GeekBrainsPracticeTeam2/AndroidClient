package com.example.alex.androidclient.models.dictionaries.persons;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DictionaryPersons {

    @SerializedName("data")
    @Expose
    private List<DictionaryPersonsData> dictionaryPersonsDataList = null;

    public List<DictionaryPersonsData> getDictionaryPersonsDataList() {
        return dictionaryPersonsDataList;
    }

    public void setDictionaryPersonsDataList(List<DictionaryPersonsData> dictionaryPersonsDataList) {
        this.dictionaryPersonsDataList = dictionaryPersonsDataList;
    }

    public String toString() {
        String result = "";
        for (int i = 0; i < dictionaryPersonsDataList.size(); i++) {
            result += dictionaryPersonsDataList.get(i).toString();
        }
        return result;
    }

}