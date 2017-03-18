package com.example.alex.androidclient;

import android.app.Application;
import android.util.Log;

import com.example.alex.androidclient.managers.CacheManager;
import com.example.alex.androidclient.models.DictionaryPersons;
import com.example.alex.androidclient.models.DictionarySites;
import com.example.alex.androidclient.models.PersonStats;
import com.example.alex.androidclient.models.TotalStatistics;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by alex on 17.03.17.
 */

public class MyApp extends Application {
    public static final String LOG_TAG = "MyApp";
    private CacheManager cacheManager;
    private List<DictionarySites> dictionarySites;
    private String[] siteUrl;
    private List<DictionaryPersons> dictionaryPersons;
    private String[] namePerson;
    private int[] likeCount;
    private List<PersonStats> statsList;

    public String[] getSiteUrl() {
        return siteUrl;
    }

    public String[] getNamePerson() {
        return namePerson;
    }

    public int[] getLikeCount() {
        return likeCount;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        initCacheManager();

        initDictionarySites();

        initDictionaryPersons();
    }

    private void initCacheManager(){
        if (cacheManager == null){
            cacheManager = new CacheManager(this);
        }
    }

    private void initDictionarySites(){
        Log.d(LOG_TAG, "initDictionarySites");
        if (dictionarySites == null && siteUrl == null){
            dictionarySites = new ArrayList<>();
            dictionarySites = cacheManager.getSitesDictionary();
            siteUrl = new String[dictionarySites.size()];
        }
        for (int i = 0; i < dictionarySites.size(); i++) {
            String url = dictionarySites.get(i).getSiteUrl();
            siteUrl[i] = url;
            Log.d(LOG_TAG, url);
        }
    }

    private void initDictionaryPersons(){
        if (dictionaryPersons == null && namePerson == null){
            dictionaryPersons = new ArrayList<>();
            dictionaryPersons = cacheManager.getPersonsDictionary();
            namePerson = new String[dictionaryPersons.size()];
        }
        for (int i = 0; i < dictionaryPersons.size(); i++) {
            String person = dictionaryPersons.get(i).getPersonName();
            namePerson[i] = person;
        }
    }

    private int[] initDateForGeneralStat(TotalStatistics totalStatistics){
        if (statsList == null && likeCount == null){
            statsList = totalStatistics.getStatsList();
            likeCount = new int[statsList.size()];
        }

        for (int i = 0; i < statsList.size(); i++) {
            int like = statsList.get(i).getLikesCount();
            likeCount[i] = like;
        }
        return likeCount;
    }
}
