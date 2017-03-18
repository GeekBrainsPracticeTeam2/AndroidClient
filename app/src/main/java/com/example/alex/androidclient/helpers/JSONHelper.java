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

    public static final String LOG_TAG = "JSONHelper";
    private static final String SAMPLE_JSON_TOTAL_STATISTICS = "{\"data\":[{\"siteID\":0,\"statistics\"" +
            ":[{\"person\":0,\"count\":5},{\"person\":1,\"count\":7},{\"person\":2,\"count\":14}," +
            "{\"person\":3,\"count\":8},{\"person\":4,\"count\":27}]},{\"siteID\":1,\"statistics\":" +
            "[{\"person\":0,\"count\":3},{\"person\":1,\"count\":10},{\"person\":2,\"count\":7}," +
            "{\"person\":3,\"count\":10},{\"person\":4,\"count\":21}]}]}";
    private static final String SAMPLE_JSON_UPDATE_STATUS = "{\"tables\":[{\"dictionaryName\":\"sites\",\"lastUpdateDate\":" +
            "\"2017-03-13 15:56:26\"},{\"dictionaryName\":\"persons\",\"lastUpdateDate\":\"2017-03-13 15:56:26\"}]}";
    private static final String SAMPLE_JSON_SITES_DIR_UPDATE = "{\"data\":[{\"ID\":0,\"url\":" +
            "\"lenta.ru\"},{\"ID\":1,\"url\":\"vesti.ru\"},{\"ID\":2,\"url\":\"kp.ru\"}]}";
    private static final String SAMPLE_JSON_NAMES_DIR_UPDATE = "{\"data\":[{\"ID\":0,\"name\":" +
            "\"Путин В.В.\"},{\"ID\":1,\"name\":\"Медведев Д.А.\"},{\"ID\":2,\"name\":\"Навальный ?.?.\"}]}";

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
                /*List<PersonStats> statsList = Ason.deserializeList(array, PersonStats.class);
                totalStats.add(new TotalStatistics(jsonDataObject.getInt(NAMES_SITE_ID),
                        statsList));*/
                break;
            // get updates for dictionaries
            case 1:
                array = jsonDataObject.getJSONArray(TABLES_UPDATES);
                Log.d(LOG_TAG, "Updates size is " + array.length());
                for (int i = 0; i < array.length(); i++) {
                    JSONArray updates = array.getJSONArray(i);
                    DictionaryUpdates dicUpdatetes = new DictionaryUpdates();
                    dicUpdatetes.setDictionaryName(updates.getString(0));
                    dicUpdatetes.setLastUpdateDate(updates.getLong(1));
                    dictionaryUpdatesList.add(dicUpdatetes);
                }
                Log.d(LOG_TAG, "dictionaryUpdatesList size is " + dictionaryUpdatesList.size());
                /*
                dictionaryUpdatesList = Ason.deserializeList(dictionaryUpdates, DictionaryUpdates.class);
                 */
                break;
            // get update for sites dictionary
            case 2:
                array = jsonDataObject.getJSONArray(DICTIONARY);
                Log.d(LOG_TAG, "Sites dictionary size is " + array.length());
                for (int i = 0; i < array.length(); i++) {
                    JSONArray sites = array.getJSONArray(i);
                    dictionarySitesList.add(new DictionarySites(sites.getInt(0), sites.getString(1)));
                }
                Log.d(LOG_TAG, "dictionarySitesList size is " + dictionarySitesList.size());
                /*
                dictionarySitesList = Ason.deserializeList(dictionarySites, DictionarySites.class);
                 */
                break;
            // get update for persons dictionary
            case 3:
                array = jsonDataObject.getJSONArray(DICTIONARY);
                Log.d(LOG_TAG, "Persons dictionary size is " + array.length());
                /*
                dictionaryPersonsList = Ason.deserializeList(dictionaryPersons, DictionaryPersons.class);
                 */
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
