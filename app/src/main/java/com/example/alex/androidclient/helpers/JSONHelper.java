package com.example.alex.androidclient.helpers;

import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.alex.androidclient.models.DictionaryPersons;
import com.example.alex.androidclient.models.DictionarySites;
import com.example.alex.androidclient.models.DictionaryUpdates;
import com.example.alex.androidclient.models.PersonStats;
import com.example.alex.androidclient.models.TotalStatistics;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jag on 09.03.2017.
 */

public class JSONHelper {

    private final String LOG_TAG = this.getClass().getSimpleName();

    private static final String SAMPLE_JSON_TOTAL_STATISTICS = "{\"data\":[{\"siteId\":0,\"statistics\"" +
            ":[{\"person\":0,\"count\":5},{\"person\":1,\"count\":7},{\"person\":2,\"count\":14}," +
            "{\"person\":3,\"count\":8},{\"person\":4,\"count\":27}]},{\"siteId\":1,\"statistics\":" +
            "[{\"person\":0,\"count\":3},{\"person\":1,\"count\":10},{\"person\":2,\"count\":7}," +
            "{\"person\":3,\"count\":10},{\"person\":4,\"count\":21}]}]}";
    private static final String SAMPLE_JSON_UPDATE_STATUS = "{\"tables\":[{\"dictionaryName\":\"sites\",\"lastUpdateDate\":" +
            "\"2017-03-13 15:56:26\"},{\"dictionaryName\":\"persons\",\"lastUpdateDate\":\"2017-03-13 15:56:26\"}]}";
    private static final String SAMPLE_JSON_SITES_DIR_UPDATE = "{\"data\":[{\"id\":0,\"url\":" +
            "\"lenta.ru\"},{\"id\":1,\"url\":\"vesti.ru\"},{\"id\":2,\"url\":\"kp.ru\"}]}";
    private static final String SAMPLE_JSON_NAMES_DIR_UPDATE = "{\"data\":[{\"id\":0,\"name\":" +
            "\"Путин В.В.\"},{\"id\":1,\"name\":\"Медведев Д.А.\"},{\"id\":2,\"name\":\"Навальный ?.?.\"}]}";

    public static final String NAMES_STATISTICS = "statistics";
    public static final String NAMES_SITE_ID = "siteID";
    public static final String TABLES_UPDATES = "tables";
    public static final String DICTIONARY = "data";
    public static final String DICTIONARY_UPDATES_LAST_UPDATE_DATE = "lastUpdateDate";
    private RequestQueue requestQueue;
    private JSONObject jsonDataObject;
    private List<TotalStatistics> totalStats;
    private List<DictionaryUpdates> dictionaryUpdatesList = new ArrayList<>();
    private List<DictionarySites> dictionarySitesList = new ArrayList<>();
    private List<DictionaryPersons> dictionaryPersonsList = new ArrayList<>();

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
        }
        fetchData();
    }

    private void fetchData() throws JSONException {
        JSONArray array;
        switch (mode){
            // get total statistics
            case 0:
                array = jsonDataObject.getJSONArray(NAMES_STATISTICS);
                Log.d(LOG_TAG, "PersonsStats size is " + array.length());
                totalStats.clear();
                for (int i = 0; i < array.length(); i++) {
                    JSONObject stats = array.getJSONObject(i);
                    JSONArray personStats = stats.getJSONArray("statistics");
                    List<PersonStats> personStts = new ArrayList<>();
                    for (int j = 0; j < personStats.length(); j++) {
                        personStts.add(new PersonStats(personStats.getInt(0), personStats.getInt(1)));
                    }
                    totalStats.add(new TotalStatistics(stats.getInt("siteId"), personStts));
                }
                /*List<PersonStats> statsList = Ason.deserializeList(array, PersonStats.class);
                totalStats.add(new TotalStatistics(jsonDataObject.getInt(NAMES_SITE_ID),
                        statsList));*/
                break;
            // get updates for dictionaries
            case 1:
                array = jsonDataObject.getJSONArray(TABLES_UPDATES);
                Log.d(LOG_TAG, "Updates size is " + array.length());
                dictionaryUpdatesList.clear();
                for (int i = 0; i < array.length(); i++) {
                    JSONObject updates = array.getJSONObject(i);
                    DictionaryUpdates dicUpdates = new DictionaryUpdates();
                    dicUpdates.setDictionaryName(updates.getString("dictionaryName"));
                    dicUpdates.setLastUpdateDate(updates.getString("lastUpdateDate"));
                    Log.d(LOG_TAG, "" + dicUpdates.getDictionaryName());
                    Log.d(LOG_TAG, "" + dicUpdates.getLastUpdateDate());
                    dictionaryUpdatesList.add(dicUpdates);
                }
                Log.d(LOG_TAG, "dictionaryUpdatesList size is " + dictionaryUpdatesList.size());
                break;
            // get update for sites dictionary
            case 2:
                array = jsonDataObject.getJSONArray(DICTIONARY);
                Log.d(LOG_TAG, "Sites dictionary size is " + array.length());
                dictionarySitesList.clear();
                for (int i = 0; i < array.length(); i++) {
                    JSONObject sites = array.getJSONObject(i);
                    dictionarySitesList.add(new DictionarySites(sites.getInt("id"), sites.getString("url")));
                }
                Log.d(LOG_TAG, "dictionarySitesList size is " + dictionarySitesList.size());
                break;
            // get update for persons dictionary
            case 3:
                array = jsonDataObject.getJSONArray(DICTIONARY);
                Log.d(LOG_TAG, "Persons dictionary size is " + array.length());
                dictionaryPersonsList.clear();
                for (int i = 0; i < array.length(); i++) {
                    JSONObject persons = array.getJSONObject(i);
                    dictionaryPersonsList.add(new DictionaryPersons(persons.getInt("id"), persons.getString("name")));
                }
                Log.d(LOG_TAG, "dictionaryPersonsList size is " + dictionaryPersonsList.size());
                break;
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
}
