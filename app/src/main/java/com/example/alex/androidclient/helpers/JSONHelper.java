package com.example.alex.androidclient.helpers;

import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.alex.androidclient.models.DailyStatistics;
import com.example.alex.androidclient.models.DictionaryPersons;
import com.example.alex.androidclient.models.DictionarySites;
import com.example.alex.androidclient.models.DictionaryUpdates;
import com.example.alex.androidclient.models.PersonStats;
import com.example.alex.androidclient.models.TotalStatistics;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Jag on 09.03.2017.
 */

public class JSONHelper {
    // final strings to work with Json streams
    public static final String JSON_PERSON = "person";
    public static final String JSON_COUNT = "count";
    public static final String UPDATES_DICTIONARY_NAME = "dictionaryName";
    public static final String UPDATES_LAST_UPDATE_DATE = "lastUpdateDate";
    public static final String DICTIONARY_ID = "id";
    public static final String DICTIONARY_URL = "url";
    public static final String DICTIONARY_NAME = "name";
    public static final String JSON_PERSONS_STATS = "personsStats";
    public static final String JSON_STATISTICS = "statistics";
    public static final String JSON_SITE_ID = "siteID";
    public static final String TABLES_UPDATES = "tables";
    public static final String DICTIONARY = "data";
    public static final String DICTIONARY_UPDATES_LAST_UPDATE_DATE = "lastUpdateDate";
    public static final String JSON_DATE = "date";
    private final String LOG_TAG = this.getClass().getSimpleName();
    // example Json fake-streams
    private static final String SAMPLE_JSON_TOTAL_STATISTICS = "{\"data\":[{\"siteId\":0,\"statistics\":" +
            "[{\"person\":0,\"count\":5},{\"person\":1,\"count\":7},{\"person\":2,\"count\":14}," +
            "{\"person\":3,\"count\":8},{\"person\":4,\"count\":27}]},{\"siteId\":1,\"statistics\":" +
            "[{\"person\":0,\"count\":3},{\"person\":1,\"count\":10},{\"person\":2,\"count\":7}," +
            "{\"person\":3,\"count\":10},{\"person\":4,\"count\":21}]},{\"siteId\":2,\"statistics\":" +
            "[{\"person\":0,\"count\":0},{\"person\":1,\"count\":1},{\"person\":2,\"count\":2}," +
            "{\"person\":3,\"count\":3},{\"person\":4,\"count\":4}]}]}";
    private static final String SAMPLE_JSON_UPDATE_STATUS = "{\"tables\":[{\"dictionaryName\":\"sites\",\"lastUpdateDate\":" +
            "\"2017-03-13 15:56:26\"},{\"dictionaryName\":\"persons\",\"lastUpdateDate\":\"2017-03-13 15:56:26\"}]}";
    private static final String SAMPLE_JSON_SITES_DIR_UPDATE = "{\"data\":[{\"id\":0,\"url\":" +
            "\"lenta.ru\"},{\"id\":1,\"url\":\"vesti.ru\"},{\"id\":2,\"url\":\"kp.ru\"}]}";
    private static final String SAMPLE_JSON_NAMES_DIR_UPDATE = "{\"data\":[{\"id\":0,\"name\":" +
            "\"Путин В.В.\"},{\"id\":1,\"name\":\"Медведев Д.А.\"},{\"id\":2,\"name\":" +
            "\"Навальный ?.?.\"},{\"id\":3,\"name\":\"Дуров П.?\"},{\"id\":4,\"name\":\"Сталин И.В.\"}]}";
    private static final String SAMPLE_JSON_DAILY_STATISTICS = "{\"data\": [{\"siteId\": 0,\"statistics\": " +
            "[{\"date\": \"2017-03-22\",\"personsStats\":[{\"person\": 0,\"count\": 5}, {\"person\": 1,\"count\": 7}, " +
            "{\"person\": 2,\"count\": 14}, {\"person\": 3,\"count\": 8}, {\"person\": 4,\"count\": 27}]}," +
            "{\"date\": \"2017-03-23\",\"personsStats\": [{\"person\": 0,\"count\": 4}, {\"person\": 1,\"count\": 5}, " +
            "{\"person\": 2,\"count\": 12}, {\"person\": 3,\"count\": 10}, {\"person\": 4,\"count\": 9}]}]}, " +
            "{\"siteId\": 1,\"statistics\": [{\"date\": \"2017-03-22\",\"personsStats\": [{\"person\": 0,\"count\": 0}, " +
            "{\"person\": 1,\"count\": 3}, {\"person\": 2,\"count\": 10}, {\"person\": 3,\"count\": 7}, " +
            "{\"person\": 4,\"count\": 17}]},{\"date\": \"2017-03-23\",\"personsStats\": [{\"person\": 0,\"count\": 33}, " +
            "{\"person\": 1,\"count\": 3}, {\"person\": 2,\"count\": 2}, {\"person\": 3,\"count\": 0}, " +
            "{\"person\": 4,\"count\": 19}]}]}, {\"siteId\": 2,\"statistics\": " +
            "[{\"date\": \"2017-03-22\",\"personsStats\": [{\"person\": 0,\"count\": 10}, {\"person\": 1,\"count\": 4}, " +
            "{\"person\": 2,\"count\": 7}, {\"person\": 3,\"count\": 2}, {\"person\": 4,\"count\": 15}]}," +
            "{\"date\": \"2017-03-23\",\"personsStats\": [{\"person\": 0,\"count\": 23}, {\"person\": 1,\"count\": 2}, " +
            "{\"person\": 2,\"count\": 3}, {\"person\": 3,\"count\": 1}, {\"person\": 4,\"count\": 22}]}]}]}";
    private RequestQueue requestQueue;
    private JSONObject jsonDataObject;
    private List<TotalStatistics> totalStats = new ArrayList<>();
    private List<DictionaryUpdates> dictionaryUpdatesList = new ArrayList<>();
    private List<DictionarySites> dictionarySitesList = new ArrayList<>();
    private List<DictionaryPersons> dictionaryPersonsList = new ArrayList<>();
    private List<DailyStatistics> dailyStats = new ArrayList<>();

    private int mode = -1;

    public JSONHelper(int mode) throws JSONException {
        this.mode = mode;
        switch (mode){
            case 0:
                jsonDataObject = new JSONObject(SAMPLE_JSON_TOTAL_STATISTICS);
                break;
            case 1:
                jsonDataObject = new JSONObject(SAMPLE_JSON_UPDATE_STATUS);
                break;
            case 2:
                jsonDataObject = new JSONObject(SAMPLE_JSON_SITES_DIR_UPDATE);
                break;
            case 3:
                jsonDataObject = new JSONObject(SAMPLE_JSON_NAMES_DIR_UPDATE);
                break;
            default:
                break;
        }
        fetchData();
    }

    public JSONHelper(Date startDate, Date finishDate) throws JSONException {
        /*
        date params in future we will use to ask REST-service for needed interval
         */
        jsonDataObject = new JSONObject(SAMPLE_JSON_DAILY_STATISTICS);
        this.mode = 4; // we need to know mode to fetchData in right way
        fetchData();
    }

    private void fetchData() throws JSONException {
        Log.d(LOG_TAG, "Start fetchData");
        switch (mode){
            // get total statistics
            case 0:
                fetchTotalStatistics();
                break;
            // get updates for dictionaries
            case 1:
                fetchUpdatesForDictionaries();
                break;
            // get update for sites dictionary
            case 2:
                fetchSitesDictionaryUpdate();
                break;
            // get update for persons dictionary
            case 3:
                fetchPersonsDictionaryUpdate();
                break;
            case 4:
                fetchDailyStatistics();
                break;
        }
    }

    private void fetchDailyStatistics() throws JSONException {
        JSONArray array = jsonDataObject.getJSONArray(DICTIONARY);
        dailyStats.clear();
        for (int i = 0; i < array.length(); i++) {
            JSONObject stats = array.getJSONObject(i);
            JSONArray statsByDates = stats.getJSONArray(JSON_STATISTICS);
            Log.d(LOG_TAG, "Count of date is " + statsByDates.length());
            for (int j = 0; j < statsByDates.length(); j++) {
                JSONObject datesObject = statsByDates.getJSONObject(i);
                JSONArray personStats = datesObject.getJSONArray(JSON_PERSONS_STATS);
                Log.d(LOG_TAG, "PersonsStats size is " + personStats.length());
                List<PersonStats> personStts = new ArrayList<>();
                for (int k = 0; k < personStats.length(); k++) {
                    JSONObject stat = personStats.getJSONObject(k);
                    personStts.add(new PersonStats(stat.getInt(JSON_PERSON), stat.getInt(JSON_COUNT)));
                    Log.d(LOG_TAG, stat.toString());
                    Log.d(LOG_TAG, "PersonId = " + stat.getInt("person"));
                    Log.d(LOG_TAG, "likesCount = " + stat.getInt("count"));
                }
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                Date parsedDate = null;
                try {
                    parsedDate = format.parse(datesObject.getString(JSON_DATE));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                dailyStats.add(new DailyStatistics(stats.getInt(JSON_SITE_ID),
                        parsedDate, personStts));
            }
        }
    }

    private void fetchPersonsDictionaryUpdate() throws JSONException {
        JSONArray array = jsonDataObject.getJSONArray(DICTIONARY);
        Log.d(LOG_TAG, "Persons dictionary size is " + array.length());
        dictionaryPersonsList.clear();
        for (int i = 0; i < array.length(); i++) {
            JSONObject persons = array.getJSONObject(i);
            dictionaryPersonsList.add(new DictionaryPersons(persons.getInt(DICTIONARY_ID),
                    persons.getString(DICTIONARY_NAME)));
        }
        Log.d(LOG_TAG, "dictionaryPersonsList size is " + dictionaryPersonsList.size());
    }

    private void fetchSitesDictionaryUpdate() throws JSONException {
        JSONArray array = jsonDataObject.getJSONArray(DICTIONARY);
        Log.d(LOG_TAG, "Sites dictionary size is " + array.length());
        dictionarySitesList.clear();
        for (int i = 0; i < array.length(); i++) {
            JSONObject sites = array.getJSONObject(i);
            dictionarySitesList.add(new DictionarySites(sites.getInt(DICTIONARY_ID),
                    sites.getString(DICTIONARY_URL)));
        }
        Log.d(LOG_TAG, "dictionarySitesList size is " + dictionarySitesList.size());
    }

    private void fetchUpdatesForDictionaries() throws JSONException {
        JSONArray array = jsonDataObject.getJSONArray(TABLES_UPDATES);
        Log.d(LOG_TAG, "Updates size is " + array.length());
        dictionaryUpdatesList.clear();
        for (int i = 0; i < array.length(); i++) {
            JSONObject updates = array.getJSONObject(i);
            DictionaryUpdates dicUpdates = new DictionaryUpdates();
            dicUpdates.setDictionaryName(updates.getString(UPDATES_DICTIONARY_NAME));
            dicUpdates.setLastUpdateDate(updates.getString(UPDATES_LAST_UPDATE_DATE));
            Log.d(LOG_TAG, "" + dicUpdates.getDictionaryName());
            Log.d(LOG_TAG, "" + dicUpdates.getLastUpdateDate());
            dictionaryUpdatesList.add(dicUpdates);
        }
        Log.d(LOG_TAG, "dictionaryUpdatesList size is " + dictionaryUpdatesList.size());
    }

    private void fetchTotalStatistics() throws JSONException {
        Log.d(LOG_TAG, "fetchTotalStatistics");
        Log.d(LOG_TAG, "jsonDataObject = " + jsonDataObject);
        JSONArray array = jsonDataObject.getJSONArray(DICTIONARY);
        Log.d(LOG_TAG, "TotalStatistics size is " + array.length());
        totalStats.clear();
        for (int i = 0; i < array.length(); i++) {
            JSONObject stats = array.getJSONObject(i);
            JSONArray personStats = stats.getJSONArray(JSON_STATISTICS);
            Log.d(LOG_TAG, "PersonsStats size is " + personStats.length());
            List<PersonStats> personStts = new ArrayList<>();
            for (int j = 0; j < personStats.length(); j++) {
                JSONObject stat = personStats.getJSONObject(j);
                personStts.add(new PersonStats(stat.getInt(JSON_PERSON), stat.getInt(JSON_COUNT)));
//                        Log.d(LOG_TAG, stat.toString());
                        Log.d(LOG_TAG, "PersonId = " + stat.getInt(JSON_PERSON));
                        Log.d(LOG_TAG, "likesCount = " + stat.getInt(JSON_COUNT));
            }
            totalStats.add(new TotalStatistics(stats.getInt(JSON_SITE_ID), personStts));
        }
    }

    public void fetchData(String url) {
        // let's start to fetch data from Json
        StringRequest request = new StringRequest(Request.Method.GET, url,
                onPostsLoaded, onPostsError);

        requestQueue.add(request);
    }

    private final Response.Listener<String> onPostsLoaded = new Response.Listener<String>() {
        @Override
        public void onResponse(String response) {
        }
    };

    public List<TotalStatistics> getTotalStats() {
        Log.d(LOG_TAG, "Start getTotalStats");
        Log.d(LOG_TAG, "Size totalStatisticsList = " + totalStats.size());
        Log.d(LOG_TAG, "End getTotalStats");
        return totalStats;
    }

    private final Response.ErrorListener onPostsError = new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
            Log.e(LOG_TAG, error.toString());
        }
    };

    public List<DictionaryUpdates> getDictionaryUpdatesList() {
        return dictionaryUpdatesList;
    }

    public List<DictionarySites> getDictionarySitesList() {
        return dictionarySitesList;
    }

    public List<DictionaryPersons> getDictionaryPersonsList() {
        return dictionaryPersonsList;
    }

    public List<DailyStatistics> getDailyStats(Date startDate, Date finishDate) {
        return dailyStats;
    }
}
