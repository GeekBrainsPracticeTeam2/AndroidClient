package com.example.alex.androidclient.helpers;

import android.util.Log;

import com.afollestad.ason.Ason;
import com.afollestad.ason.AsonArray;
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

    public static final String LOG_TAG = "JSONHelper";
    private static final String SAMPLE_JSON_TOTAL_STATISTICS = "{\"siteID\":0,\"statistics\":" +
            "[{\"person\":0,\"count\":5},{\"person\":1,\"count\":7},{\"person\":2,\"count\":14}," +
            "{\"person\":3,\"count\":8},{\"person\":4,\"count\":27}]}";
    private static final String SAMPLE_JSON_UPDATE_STATUS = "{\"tables\":[{\"ID\":0,\"lu_date\":" +
            "\"2017-03-13 15:56:26\"},{\"ID\":1,\"lu_date\":\"2017-03-13 15:56:26\"}]}";
    private static final String SAMPLE_JSON_SITES_DIR_UPDATE = "{\"data\":[{\"ID\":0,\"url\":" +
            "\"lenta.ru\"},{\"ID\":1,\"url\":\"vesti.ru\"},{\"ID\":2,\"url\":\"kp.ru\"}]}";
    private static final String SAMPLE_JSON_NAMES_DIR_UPDATE = "{\"data\":[{\"ID\":0,\"name\":" +
            "\"Путин В.В.\"},{\"ID\":1,\"name\":\"Медведев Д.А.\"},{\"ID\":2,\"name\":\"Навальный ?.?.\"}]}";

    public static final String NAMES_STATISTICS = "statistics";
    public static final String NAMES_SITE_ID = "siteID";
    public static final String TABLES_UPDATES = "tables";
    public static final String DICTIONARY = "data";
    private RequestQueue requestQueue;
    private Ason ason;
    private List<TotalStatistics> totalStats;
    private List<DictionaryUpdates> dictionaryUpdatesList = new ArrayList<>();
    private List<DictionarySites> dictionarySitesList = new ArrayList<>();
    private List<DictionaryPersons> dictionaryPersonsList = new ArrayList<>();

    private int mode = -1;

    public JSONHelper(int mode) throws JSONException {
        this.mode = mode;
        switch (mode){
            case 0:
                ason = new Ason(SAMPLE_JSON_TOTAL_STATISTICS);
                break;
            case 1:
                ason = new Ason(SAMPLE_JSON_UPDATE_STATUS);
                break;
            case 2:
                ason = new Ason(SAMPLE_JSON_SITES_DIR_UPDATE);
                break;
            case 3:
                ason = new Ason(SAMPLE_JSON_NAMES_DIR_UPDATE);
                break;
        }
        fetchData();
    }

    private void fetchData() throws JSONException {
        JSONObject dataJsonObj;
        switch (mode){
            // get total statistics
            case 0:
                dataJsonObj = new JSONObject(SAMPLE_JSON_TOTAL_STATISTICS);
                JSONArray array = dataJsonObj.getJSONArray(NAMES_STATISTICS);
                List<PersonStats> statsList = Ason.deserializeList(array, PersonStats.class);
                totalStats.add(new TotalStatistics(ason.getInt(NAMES_SITE_ID),
                        statsList));
                break;
            // get updates for dictionaries
            case 1:
                AsonArray dictionaryUpdates = ason.getJsonArray(TABLES_UPDATES);
                dictionaryUpdatesList = Ason.deserializeList(dictionaryUpdates, DictionaryUpdates.class);
                break;
            // get update for sites dictionary
            case 2:
                AsonArray dictionarySites = ason.getJsonArray(DICTIONARY);
                Log.d(LOG_TAG, "Size of dictionary array " + dictionarySites.size());
                dictionarySitesList = Ason.deserializeList(dictionarySites, DictionarySites.class);
                break;
            // get update for persons dictionary
            case 3:
                AsonArray dictionaryPersons = ason.getJsonArray(DICTIONARY);
                dictionaryPersonsList = Ason.deserializeList(dictionaryPersons, DictionaryPersons.class);
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
