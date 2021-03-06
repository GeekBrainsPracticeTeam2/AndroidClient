package com.example.alex.androidclient;

import android.app.Application;
import android.util.Log;

import com.example.alex.androidclient.managers.CacheManager;
import com.example.alex.androidclient.models.DictionaryPersons;
import com.example.alex.androidclient.models.DictionarySites;
import com.example.alex.androidclient.models.PersonStats;
import com.example.alex.androidclient.models.TotalStatistics;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by alex on 17.03.17.
 */

public class MyApp extends Application {
    private final String LOG_TAG = this.getClass().getSimpleName();
    private CacheManager cacheManager;
    private List<DictionarySites> dictionarySites;
    private String[] siteUrl;
    private List<DictionaryPersons> dictionaryPersons;
    private String[] namePerson;

    private int[] likeCount;
    private List<PersonStats> statsList;
    private TotalStatistics totalStatisticsBySite;
    private int siteID = -1;

    public void setSiteID(int siteID) {
        this.siteID = siteID;
    }

    public String[] getSiteUrl() {
        return siteUrl;
    }

    public String[] getNamePerson() {
        return namePerson;
    }

    public int[] getLikeCount() {
        initLikeCount();
        return likeCount;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        try {
            initCacheManager();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            initDictionarySites();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            initDictionaryPersons();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void initCacheManager() throws JSONException {
        if (cacheManager == null){
            cacheManager = new CacheManager(this);
        }
    }

    private void initDictionarySites() throws JSONException {
        Log.d(LOG_TAG, "Start initDictionarySites");
        if (dictionarySites == null && siteUrl == null){
            try {
                dictionarySites = new ArrayList<>();
                dictionarySites = cacheManager.getSitesDictionary();
                siteUrl = new String[dictionarySites.size()];
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        for (int i = 0; i < dictionarySites.size(); i++) {
            String url = dictionarySites.get(i).getSiteUrl();
            siteUrl[i] = url;
            Log.d(LOG_TAG, "siteUrl(" + i + ") = " + url);
        }
        Log.d(LOG_TAG, "End initDictionarySites");
    }

    private void initDictionaryPersons() throws JSONException {
        Log.d(LOG_TAG, "Start initDictionaryPersons");
        if (dictionaryPersons == null && namePerson == null){
            try {
                dictionaryPersons = new ArrayList<>();
                dictionaryPersons = cacheManager.getPersonsDictionary();
                namePerson = new String[dictionaryPersons.size()];
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        for (int i = 0; i < dictionaryPersons.size(); i++) {
            String person = dictionaryPersons.get(i).getPersonName();
            namePerson[i] = person;
            Log.d(LOG_TAG, "namePerson(" + i + ") = " + namePerson[i]);
        }
        Log.d(LOG_TAG, "End initDictionaryPersons");
    }

    private void initLikeCount(){
        Log.d(LOG_TAG, "Start initLikeCount");

        if (siteID > -1) {

            if (totalStatisticsBySite == null && likeCount == null) {
                try {
                    totalStatisticsBySite = new TotalStatistics();
                    totalStatisticsBySite = cacheManager.getTotalStatisticsBySite(siteID);
                    Log.d(LOG_TAG, "totalStatisticsList = " + totalStatisticsBySite);
                    statsList = new ArrayList<>();
                    statsList = totalStatisticsBySite.getStatsList();
                    Log.d(LOG_TAG, "Size statsList = " + statsList.size());
                    likeCount = new int[statsList.size()];
                    Log.d(LOG_TAG, "Length likeCount = " + likeCount.length);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            for (int i = 0; i < statsList.size(); i++) {
                int like = statsList.get(i).getLikesCount();
                likeCount[i] = like;
                Log.d(LOG_TAG, "likeCount(" + i + ") = " + likeCount[i]);
            }
        }
        Log.d(LOG_TAG, "End initLikeCount");
    }
}
