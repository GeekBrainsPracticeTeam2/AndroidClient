package com.example.alex.androidclient;

import android.app.Application;
import android.util.Log;

import com.example.alex.androidclient.managers.CacheManager;
import com.example.alex.androidclient.models.DailyStatistics;
import com.example.alex.androidclient.models.DictionaryPersons;
import com.example.alex.androidclient.models.DictionarySites;
import com.example.alex.androidclient.models.DictionaryUpdates;
import com.example.alex.androidclient.models.PersonStats;
import com.example.alex.androidclient.models.TotalStatistics;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by alex on 17.03.17.
 */

public class MyApp extends Application {
    private final String LOG_TAG = this.getClass().getSimpleName();
    private static MyApp instance = null;
    private CacheManager cacheManager;
    private List<DictionarySites> dictionarySites;
    private String[] siteUrl;
    private List<DictionaryPersons> dictionaryPersons;
    private String[] namePerson;

    private int[] likeCount;
    private List<PersonStats> personStatsList;
    private PersonStats personStats;
    private TotalStatistics totalStatisticsBySite;

    private int siteID = 0;

    private List<DailyStatistics> dailyStatList;
    private DailyStatistics dailyStat;

    public List<DailyStatistics> getDailyStatisticses() {
        return dailyStatList;
    }

    private Date startDate, finishDate;

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public void setFinishDate(Date finishDate) {
        this.finishDate = finishDate;
    }

    public void setSiteID(int siteID) {
        this.siteID = siteID;
    }

    public String[] getSiteUrl() {
        try {
            initDictionarySites();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return siteUrl;
    }

    public String[] getNamePerson() {
        try {
            initDictionaryPersons();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return namePerson;
    }

    public int[] getLikeCount() {
        initLikeCount();
        return likeCount;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;

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
        if (cacheManager == null && instance.getBaseContext() != null) {
            cacheManager = new CacheManager(instance, this);
        } else {
            Log.d(LOG_TAG, "Context is NULL!!!");
        }
    }

    private void initDictionarySites() throws JSONException {
        cacheManager.getSitesDictionary();
    }

    private void initDictionaryPersons() throws JSONException {
        cacheManager.getPersonsDictionary();
    }

    public void setDictionarySites(List<DictionarySites> dictionarySitesList) {
        Log.d(LOG_TAG, "Start setDictionarySites");
        this.dictionarySites = dictionarySitesList;
        siteUrl = new String[dictionarySites.size()];

        for (int i = 0; i < dictionarySites.size(); i++) {
            String url = dictionarySites.get(i).getSiteUrl();
            siteUrl[i] = url;
            Log.d(LOG_TAG, "siteUrl(" + i + ") = " + url);
        }
//        Log.d(LOG_TAG, "End initDictionarySites");
    }

    public void setDictionaryPersons(List<DictionaryPersons> dictionaryPersonsList) {
        Log.d(LOG_TAG, "Start setDictionaryPersons");
        this.dictionaryPersons = dictionaryPersonsList;
        namePerson = new String[dictionaryPersons.size()];
        for (int i = 0; i < dictionaryPersons.size(); i++) {
            String person = dictionaryPersons.get(i).getPersonName();
            namePerson[i] = person;
            Log.d(LOG_TAG, "namePerson(" + i + ") = " + namePerson[i]);
        }
//        Log.d(LOG_TAG, "End initDictionaryPersons");
    }

    public void setTotalStatisticsBySite(TotalStatistics totalStatisticsBySite) {
        this.totalStatisticsBySite = totalStatisticsBySite;
        /*
        initLikeCount();
         */
//      Log.d(LOG_TAG, "totalStatisticsList = " + totalStatisticsBySite);

        personStatsList = new ArrayList<>();
        personStatsList = totalStatisticsBySite.getStatsList();
//                Log.d(LOG_TAG, "Size statsList = " + statsList.size());

        likeCount = new int[personStatsList.size()];
//                Log.d(LOG_TAG, "Length likeCount = " + likeCount.length);
        for (int i = 0; i < personStatsList.size(); i++) {
            int like = personStatsList.get(i).getLikesCount();
            likeCount[i] = like;
//            Log.d(LOG_TAG, "likeCount(" + i + ") = " + likeCount[i]);
        }
    }

    private void initLikeCount() {
//        Log.d(LOG_TAG, "Start initLikeCount");

        if (totalStatisticsBySite == null && likeCount == null) {
            try {
                totalStatisticsBySite = new TotalStatistics();
                cacheManager.getTotalStatisticsBySite(siteID);

            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            try {
                /*totalStatisticsBySite = new TotalStatistics();*/
                cacheManager.getTotalStatisticsBySite(siteID);
                personStatsList = totalStatisticsBySite.getStatsList();
//                Log.d(LOG_TAG, "siteID = " + siteID);
//                Log.d(LOG_TAG, "totalStatisticsBySite = " + totalStatisticsBySite);
//                Log.d(LOG_TAG, "statsList = " + statsList);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
//        Log.d(LOG_TAG, "End initLikeCount");
    }

    public List<DailyStatistics> prepareDailyStatForRecycler(int selectedSite,
                                                              int selectedPerson) {
        Log.d(LOG_TAG, "Start prepareDailyStatForRecycler. SelectedSite is " + selectedSite +
                " and selectedPerson is " + selectedPerson);

        // creating new List with only needed data
        List<DailyStatistics> choosenDailyStatsList = new ArrayList<>();

        // let's check, if dailyStistics is loaded?
        try {
            dailyStatList = cacheManager.getDailyStatistics(startDate, finishDate);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // than let's select data we need to display in recycler
        for (int i = 0; i < dailyStatList.size(); i++) {
            Log.d(LOG_TAG, "dailyStatList.get(i).getSiteID()" + dailyStatList.get(i).getSiteID());
            // creating new DailyStatistics object to add to choosenDailyStats
            if (dailyStatList.get(i).getSiteID() == selectedSite) {
                dailyStat = dailyStatList.get(i);

                if (selectedSite == dailyStat.getSiteID()) {
                    List<PersonStats> personStatsList = dailyStat.getStatsList();
                    // will put chosen data here
                    List<PersonStats> choosenPersonStatsList = new ArrayList<>();

                    for (int j = 0; j < personStatsList.size(); j++) {
                        personStats = personStatsList.get(j);
                        if (personStats.getPersonID() == selectedPerson) {
                            if (personStats.getLikesCount() > 0) {
                                choosenPersonStatsList.add(new PersonStats(personStats.getPersonID(),
                                        personStats.getLikesCount()));
                            }
                        }
                    }
                    if(!choosenPersonStatsList.isEmpty()) {
                        choosenDailyStatsList.add(new DailyStatistics(dailyStat.getSiteID(),
                                dailyStat.getDate(), choosenPersonStatsList));
                    }
                }
            }
        }
        return choosenDailyStatsList;
    }
}
