package com.example.alex.androidclient;

import android.app.Application;
import android.util.Log;

import com.example.alex.androidclient.managers.CacheManager;
import com.example.alex.androidclient.models.DailyStatistics;
import com.example.alex.androidclient.models.DictionaryPersons;
import com.example.alex.androidclient.models.DictionarySites;
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
    private CacheManager cacheManager;
    private List<DictionarySites> dictionarySites;
    private String[] siteUrl;
    private List<DictionaryPersons> dictionaryPersons;
    private String[] namePerson;

    private int[] likeCount;
    private List<PersonStats> personStatsList;
    private List<PersonStats> personStatsListForRecycler;
    private PersonStats personStats;
    private PersonStats personStatsForRecycler;
    private TotalStatistics totalStatisticsBySite;
    private int siteID = 0;

    private List<DailyStatistics> dailyStatList;
    private List<DailyStatistics> dailyStatListForRecycler;
    private DailyStatistics dailyStat;
    private DailyStatistics dailyStatForRecycler;

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
        if (cacheManager == null) {
            cacheManager = new CacheManager(this);
        }
    }

    private void initDictionarySites() throws JSONException {
        Log.d(LOG_TAG, "Start initDictionarySites");
        if (dictionarySites == null && siteUrl == null) {
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
        if (dictionaryPersons == null && namePerson == null) {
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

    private void initLikeCount() {
//        Log.d(LOG_TAG, "Start initLikeCount");

        if (totalStatisticsBySite == null && likeCount == null) {
            try {
                totalStatisticsBySite = new TotalStatistics();
                totalStatisticsBySite = cacheManager.getTotalStatisticsBySite(siteID);
//                Log.d(LOG_TAG, "totalStatisticsList = " + totalStatisticsBySite);

                personStatsList = new ArrayList<>();
                personStatsList = totalStatisticsBySite.getStatsList();
//                Log.d(LOG_TAG, "Size statsList = " + statsList.size());

                likeCount = new int[personStatsList.size()];
//                Log.d(LOG_TAG, "Length likeCount = " + likeCount.length);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            try {
                totalStatisticsBySite = cacheManager.getTotalStatisticsBySite(siteID);
                personStatsList = totalStatisticsBySite.getStatsList();
//                Log.d(LOG_TAG, "siteID = " + siteID);
//                Log.d(LOG_TAG, "totalStatisticsBySite = " + totalStatisticsBySite);
//                Log.d(LOG_TAG, "statsList = " + statsList);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        for (int i = 0; i < personStatsList.size(); i++) {
            int like = personStatsList.get(i).getLikesCount();
            likeCount[i] = like;
//            Log.d(LOG_TAG, "likeCount(" + i + ") = " + likeCount[i]);
        }
//        Log.d(LOG_TAG, "End initLikeCount");
    }

    public List<DailyStatistics> prepareDailyStatForRecycler(int selectedSite,
                                                              int selectedPerson) {
        Log.d(LOG_TAG, "Start prepareDailyStatForRecycler");

        dailyStatListForRecycler = new ArrayList<>();
        personStatsListForRecycler = new ArrayList<>();

        try {
            dailyStatList = cacheManager.getDailyStatistics(startDate, finishDate);
        } catch (Exception e) {
            e.printStackTrace();
        }
            dailyStat = dailyStatList.get(selectedSite);
            if (siteID == dailyStat.getSiteID()) {
                Date date = dailyStat.getDate();

                personStatsList = dailyStat.getStatsList();
                int likesCount = 0;

                for (int i = 0; i < personStatsList.size(); i++) {
                    Log.d(LOG_TAG, "Start prepareDailyStatForRecycler, Start for i = " + i);
                    personStats = personStatsList.get(i);
                    if (personStats.getPersonID() == selectedPerson) {
                        Log.d(LOG_TAG, "Likes count for person ID: " + selectedPerson + " is " +
                                personStats.getLikesCount());
                        likesCount = personStats.getLikesCount();
                        if (likesCount != 0) {
                            personStatsForRecycler = new PersonStats(selectedPerson, likesCount);
                            personStatsListForRecycler.add(personStatsForRecycler);
                        }
                    }
                    dailyStatForRecycler = new DailyStatistics(selectedSite, date,
                            personStatsListForRecycler);
                    dailyStatListForRecycler.add(dailyStatForRecycler);
                }
                return dailyStatListForRecycler;
            }
        return null;
    }
}
